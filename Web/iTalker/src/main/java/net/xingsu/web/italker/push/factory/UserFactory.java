package net.xingsu.web.italker.push.factory;

import com.google.common.base.Strings;
import net.xingsu.web.italker.push.bean.db.User;
import net.xingsu.web.italker.push.bean.db.UserFollow;
import net.xingsu.web.italker.push.utils.Hib;
import net.xingsu.web.italker.push.utils.TextUtil;
import org.hibernate.Session;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public static User findById(String id){
        //通过id查询，更方便
        return Hib.query(session -> session.get(User.class,id));
    }

    //通过Name找到User
    public static User findByName(String name){
        return Hib.query(session -> (User) session
                .createQuery("from User where name = :name")
                .setParameter("name",name)
                .uniqueResult());
    }

    //更新用户信息到数据库
    public static User update(User user){
        return Hib.query(session -> {
            session.saveOrUpdate(user);
            return user;
        });
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
        return update(user);
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

    /**
     * 获取我的联系人的列表
     * @param self  User
     * @return  List<User>
     */
    public static List<User> contacts(User self){
        return Hib.query(session -> {
            //重新加载一次用户信息到self中，和当前的session绑定
           session.load(self,self.getId());

           //获取我关注的人
            Set<UserFollow> follows = self.getFollowing();

            //简写模式
            return follows.stream()
                    .map(UserFollow::getTarget)
                    .collect(Collectors.toList());
        });
    }

    /**
     * 关注人的操作
     * @param origin 发起者
     * @param target 被关注的人
     * @param alias 备注名
     * @return 被关注的人的信息
     */
    public static User follow(final User origin, final User target, final String alias){
        UserFollow follow = getUserFollow(origin,target);
        if(follow != null){
            //已关注，直接返回
            return follow.getTarget();
        }

        return Hib.query(session -> {
            //想要操作懒加载的数据，需要重新load一次
            session.load(origin,origin.getId());
            session.load(target,target.getId());

            //我关注人的时候，同时他也关注我，所以需要添加两条UserFollow数据
            UserFollow originFollow = new UserFollow();
            originFollow.setOrigin(origin);
            originFollow.setTarget(target);
            //备注是我对他的备注，他对我默认是没有备注的
            originFollow.setAlias(alias);

            UserFollow targetFollow = new UserFollow();
            targetFollow.setOrigin(target);
            targetFollow.setTarget(origin);

            //保存数据库操作
            session.save(originFollow);
            session.save(targetFollow);

            return target;
        });
    }

    /**
     * 查询两个人是否已经关注
     * @param origin 发起者
     * @param target 被关注人
     * @return 返回中间类UserFollow
     */
    public static UserFollow getUserFollow(final User origin, final User target){
        return Hib.query(session -> (UserFollow)session
                .createQuery("from UserFollow where originId = :originId and targetId = :targetId")
                .setParameter("originId",origin.getId())
                .setParameter("targetId",target.getId())
                //唯一查询一条数据
                .uniqueResult());
    }

    /**
     * 搜索联系人的实现
     * @param name 查询的name，允许为空
     * @return 查询到的用户集合，如果name为空，返回最近的用户
     */
    public static List<User> search(String name) {

        if(Strings.isNullOrEmpty(name))
            name = "";  //保证不能位null的情况，减少后面的判断和额外的错误
        final String searchName = "%" + name + "%";

        return Hib.query(session -> {
            //查询的条件，name忽略大小写，并且使用like模糊查询；头像和描述必须完善才能查询到
           return (List<User>) session.createQuery("from User where lower(name) like :name and portrait is not null and description is not null")
                   .setParameter("name",searchName)
                   .setMaxResults(20)       //最多返回20条数据
                   .list();
        });

    }
}
