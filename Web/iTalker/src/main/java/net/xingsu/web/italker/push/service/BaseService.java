package net.xingsu.web.italker.push.service;

import net.xingsu.web.italker.push.bean.db.User;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

/**
 * Created by Administrator on 2017/9/28 0028.
 */
public class BaseService {

    //添加一个上下文注解，该注解会给securityContext赋值
    //具体的值为我们的拦截器中所返回
    @Context
    protected SecurityContext securityContext;

    /**
     * 从上下文中直接获取自己的信息
     * @return User
     */
    protected User getSelf(){
        return (User) securityContext.getUserPrincipal();
    }
}
