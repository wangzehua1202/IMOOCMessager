package net.qiujuer.web.italker.push.service;


import net.qiujuer.web.italker.push.bean.api.account.RegisterModel;
import net.qiujuer.web.italker.push.bean.db.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


//127.0.0.1/api/account/...
@Path("/account")
public class AccountService {

    /**
     * POST 127.0.0.1/api/account/login
     * @return
     */
    @POST
    @Path("/register")
    //指定请求和返回的响应体为JSON
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User register(RegisterModel model){
        User user = new User();
        user.setName(model.getName());
        user.setSex(24);
        return user;
    }
}
