package net.xingsu.web.italker.push.service;


import net.xingsu.web.italker.push.bean.api.account.AccountRspModel;
import net.xingsu.web.italker.push.bean.api.account.LoginModel;
import net.xingsu.web.italker.push.bean.api.account.RegisterModel;
import net.xingsu.web.italker.push.bean.api.base.ResponseModel;
import net.xingsu.web.italker.push.bean.card.UserCard;
import net.xingsu.web.italker.push.bean.db.User;
import net.xingsu.web.italker.push.factory.UserFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


//127.0.0.1/api/account/...
@Path("/account")
public class AccountService {

    /**
     * 登录
     * POST 127.0.0.1/api/account/login
     * @return
     */
    @POST
    @Path("/login")
    //指定请求和返回的响应体为JSON
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<AccountRspModel> login(LoginModel model){
        if(!LoginModel.check(model)){
            //返回参数异常
            return ResponseModel.buildParameterError();
        }

        User user = UserFactory.login(model.getAccount(),model.getPassword());
        if(user != null){
            //返回当前的账户
            AccountRspModel rspModel = new AccountRspModel(user);
            return ResponseModel.buildOk(rspModel);
        }else{
            //登录失败
            return ResponseModel.buildLoginError();
        }
    }


    /**
     * 注册
     * POST 127.0.0.1/api/account/register
     * @return
     */
    @POST
    @Path("/register")
    //指定请求和返回的响应体为JSON
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<AccountRspModel> register(RegisterModel model){
        if(!RegisterModel.check(model)){
            //返回参数异常
            return ResponseModel.buildParameterError();
        }

        User user = UserFactory.findByPhone(model.getAccount().toString().trim());
        if(user != null){
            //已有账户
            return ResponseModel.buildHaveAccountError();
        }

        user = UserFactory.findByName(model.getName().toString().trim());
        if(user != null){
            //已有用户名
            return ResponseModel.buildHaveNameError();
        }

        //开始注册逻辑代码
        user = UserFactory.register(model.getAccount(),model.getPassword(),model.getName());

        if(user != null){
            //返回当前的账户
            AccountRspModel rspModel = new AccountRspModel(user);
            return ResponseModel.buildOk(rspModel);
        }else{
            //注册异常
            return ResponseModel.buildRegisterError();
        }
    }
}
