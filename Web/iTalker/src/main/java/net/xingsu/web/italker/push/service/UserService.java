package net.xingsu.web.italker.push.service;

import com.google.common.base.Strings;
import net.xingsu.web.italker.push.bean.api.account.AccountRspModel;
import net.xingsu.web.italker.push.bean.api.base.ResponseModel;
import net.xingsu.web.italker.push.bean.api.user.UpdateInfoModel;
import net.xingsu.web.italker.push.bean.card.UserCard;
import net.xingsu.web.italker.push.bean.db.User;
import net.xingsu.web.italker.push.factory.UserFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * 用户信息处理的Service
 * Created by Administrator on 2017/9/28 0028.
 */
//127.0.0.1/api/user/...
@Path("/user")
public class UserService {

    /**
     * 用户信息修改接口
     * @param token
     * @param model
     * @return 自己的个人信息
     */
    @PUT
    //path 不需要写，就是当前路径//127.0.0.1/api/user
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<UserCard> update(@HeaderParam("token") String token, UpdateInfoModel model){
        if(Strings.isNullOrEmpty(token) || !UpdateInfoModel.check(model)){
            //返回参数异常
            return ResponseModel.buildParameterError();
        }

        //拿到自己的个人信息
        User user = UserFactory.findByToken(token);
        if(user != null){
            //更新用户信息
            user = model.updateToUser(user);
            //用户信息存储
            user = UserFactory.update(user);
            //构架自己的用户信息
            UserCard card = new UserCard(user,true);
            //返回
            return ResponseModel.buildOk(card);
        }else{
            //Token失效，无法进行绑定
            return ResponseModel.buildAccountError();
        }
    }
}
