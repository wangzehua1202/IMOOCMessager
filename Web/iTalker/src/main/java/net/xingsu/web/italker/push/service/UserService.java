package net.xingsu.web.italker.push.service;

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
public class UserService extends BaseService {

    /**
     * 用户信息修改接口
     * @param model
     * @return 自己的个人信息
     */
    @PUT
    //path 不需要写，就是当前路径//127.0.0.1/api/user
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<UserCard> update(UpdateInfoModel model){
        if(!UpdateInfoModel.check(model)){
            //返回参数异常
            return ResponseModel.buildParameterError();
        }

        //上下文中拿到自己的信息，经过过滤器后的self，必定有一个有效的token
        User self = getSelf();
        //更新用户信息
        self = model.updateToUser(self);
        //用户信息存储
        self = UserFactory.update(self);
        //构架自己的用户信息
        UserCard card = new UserCard(self,true);
        //返回
        return ResponseModel.buildOk(card);
    }
}
