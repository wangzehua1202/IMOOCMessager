package net.xingsu.web.italker.push.service;


import com.google.common.base.Strings;
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

    /**
     * 绑定设备Id
     * POST 127.0.0.1/api/account/login
     * @return
     */
    @POST
    @Path("/bind/{pushId}")
    //指定请求和返回的响应体为JSON
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //从请求头中获取token字段
    //pushId从url地址中获取
    public ResponseModel<AccountRspModel> bind(@HeaderParam("token") String token,
                                              @PathParam("pushId") String pushId){
        if(Strings.isNullOrEmpty(token)
                || Strings.isNullOrEmpty(pushId)){
            //返回参数异常
            return ResponseModel.buildParameterError();
        }

        //拿到自己的个人信息
        User user = UserFactory.findByToken(token);
        if(user != null){
            //进行设备Id绑定的操作
            user = UserFactory.findByToken(token);

            if(user != null) {
                //返回当前的账户
                AccountRspModel rspModel = new AccountRspModel(user);
                return ResponseModel.buildOk(rspModel);
            }else{
                //绑定失败是服务器异常
                return ResponseModel.buildServiceError();
            }
        }else{
            //失败
            return ResponseModel.buildLoginError();
        }
    }

}
