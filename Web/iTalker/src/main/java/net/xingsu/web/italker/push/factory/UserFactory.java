package net.xingsu.web.italker.push.factory;

import net.xingsu.web.italker.push.bean.db.User;
import net.xingsu.web.italker.push.utils.Hib;
import net.xingsu.web.italker.push.utils.TextUtil;
import org.hibernate.Session;

/**
 * Created by Administrator on 2017/9/27 0027.
 */
public class UserFactory{

    public static User findByPhone(String phone){
        return Hib.query(session -> (User) session
                .createQuery("from User where phone = :inPhone")
                .setParameter("inPhone",phone)
                .uniqueResult());
    }

    public static User findByName(String name){
        return Hib.query(session -> (User) session
                .createQuery("from User where name = :name")
                .setParameter("name",name)
                .uniqueResult());
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
        User user = new User();

        user.setName(name);
        user.setPassword(password);
        //账户就是手机号
        user.setPhone(account);

        //进行数据库操作
        //创建一个会话
        Session session= Hib.session();
        //开启事务
        session.beginTransaction();
        try {
            //保存操作
            session.save(user);
            //提交事务
            session.getTransaction().commit();
            return user;
        }catch (Exception e){
            //失败则回滚事务
            session.getTransaction().rollback();
            return null;
        }
    }

    private static String encodePassword(String password){
        //去空格
        password = password.trim();
        //进行Md5非对称加密，使用加盐更安全（加上时间）
        password = TextUtil.getMD5(password);

        //再进行一次对称的Base64加密，也可以采用加盐加密的方案
        return TextUtil.encodeBase64(password);
    }

}
