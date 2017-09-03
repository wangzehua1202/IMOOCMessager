package net.qiujuer.web.italker.push.service;


import net.qiujuer.web.italker.push.bean.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


//127.0.0.1/api/account/...
@Path("/account")
public class AccountService {

    /**
     * GET 127.0.0.1/api/account/login
     * @return
     */
    @GET
    @Path("/login")
    public String get(){
        return "you get the login";
    }

    /**
     * POST 127.0.0.1/api/account/login
     * @return
     */
    @POST
    @Path("/login")
    //指定请求和返回的响应体为JSON
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User post(){
        User user = new User();
        user.setName("佩琪");
        user.setSex(24);
        return user;
    }
}
