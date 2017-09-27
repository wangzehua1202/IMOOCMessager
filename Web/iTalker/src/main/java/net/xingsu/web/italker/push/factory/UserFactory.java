package net.xingsu.web.italker.push.factory;

import com.google.common.base.Strings;
import net.xingsu.web.italker.push.bean.db.User;
import net.xingsu.web.italker.push.utils.Hib;
import net.xingsu.web.italker.push.utils.TextUtil;
import org.hibernate.Session;

import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017/9/27 0027.
 */
public class UserFactory{

    //通过Token 字段查询用户信息
    //只能自己使用，查询信息是个人信息
    public static User findByToken(String Token){
        return Hib.query(session -> (User) session
                .createQuery("from User where token = :token")
                .setParameter("token",Token)
                .uniqueResult());
    }

    //通过Phone找到User
    public static User findByPhone(String phone){
        return Hib.query(session -> (User) session
                .createQuery("from User where phone = :inPhone")
                .setParameter("inPhone",phone)
                .uniqueResult());
    }

    //通过Name找到User
    public static User findByName(String name){
        return Hib.query(session -> (User) session
                .createQuery("from User where name = :name")
                .setParameter("name",name)
                .uniqueResult());
    }

    /**
     * 给当前的账户绑定Pushid
     * @param user 自己的User
     * @param pushId 设备的pushid
     * @return User
     */
    public static User bindPushId(User user, String pushId){
        if(Strings.isNullOrEmpty(pushId))
            return null;

        //1.查询是否有其他用户绑定了这个设备
        //取消绑定，避免推送混乱
        //查询的列表不包括自己
        Hib.queryOnly(session -> {
            List<User> userList = session
                    .createQuery("from User where lower(pushId) = :pushId and id != :userId")
                    .setParameter("pushId",pushId.toLowerCase())
                    .setParameter("userId",user.getId())
                    .list();

            for(User u : userList){
                //更新为null
                u.setPushId(null);
                session.saveOrUpdate(u);
            }
        });

        if(pushId.equalsIgnoreCase(user.getPushId())){
            //如果当前需要绑定的设备Id，之前已经绑定过了，那么不需要额外绑定
            return user;
        }else{
            //如果当前账户之前的设备Id，和需要绑定的不同
            //那么需要单点登录，让之前的设备退出账户
            //给之前的设备推送一条退出消息
            if(Strings.isNullOrEmpty(user.getPushId())){
                //TODO 推送一个退出消息
            }

            //更新新的设备Id
            user.setPushId(pushId);
            return Hib.query(session -> {
                session.saveOrUpdate(user);
                return user;
            });
        }
    }

    /**
     * 使用账户和密码进行登录
     * @param account
     * @param password
     * @return
     */
    public static User login(String account, String password){
        String accountStr = account.trim();
        //把原密码进行同样处理进行匹配
        String encodePassword = encodePassword(password);

        //寻找用户
        User user = Hib.query(session -> (User) session.createQuery("from User where phone = :phone and password = :password")
                .setParameter("phone",accountStr)
                .setParameter("password",encodePassword)
                .uniqueResult());

        if(user != null){
            user = login(user);
        }
        return user;
    }

    /**
     * 用户注册
     * 注册的操作需要写入数据库，并返回数据库中User的信息
     * @param account 账户名
     * @param password 密码
     * @param name 用户名
     * @return User
     */
    public static User register(String account, String password, String name){
        //去除账户中的首尾空格
        account = account.trim();

        //密码处理
        password = encodePassword(password);


        User user = createUser(account, password, name);
        if(user != null){
            user = login(user);
        }
        return user;

    }

    /**
     * 注册部分新建用户逻辑
     * @param account 手机号
     * @param password 加密后的密码
     * @param name 用户名
     * @return 返回一个用户
     */
    private static User createUser(String account, String password, String name){
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        //账户就是手机号
        user.setPhone(account);

        //数据库存储
        return Hib.query(session -> {
            session.save(user);
            return user;
        });
    }

    /**
     * 把一个User进行登录操作
     * 本质上是对Token进行操作
     * @param user User
     * @return User
     */
    private static User login(User user){
        //使用一个随机的UUID值充当Token
        String newToken = UUID.randomUUID().toString();
        //进行一次Base64格式化
        newToken = TextUtil.encodeBase64(newToken);
        user.setToken(newToken);
        return Hib.query(session -> {
            session.saveOrUpdate(user);
            return user;
        });
    }

    /**
     * 对密码进行操作
     * @param password
     * @return
     */
    private static String encodePassword(String password){
        //去空格
        password = password.trim();
        //进行Md5非对称加密，使用加盐更安全（加上时间）
        password = TextUtil.getMD5(password);

        //再进行一次对称的Base64加密，也可以采用加盐加密的方案
        return TextUtil.encodeBase64(password);
    }

}
