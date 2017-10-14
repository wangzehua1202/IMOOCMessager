package net.xingsu.web.italker.push.service;

import net.xingsu.web.italker.push.bean.api.base.ResponseModel;
import net.xingsu.web.italker.push.bean.api.user.UpdateInfoModel;
import net.xingsu.web.italker.push.bean.card.UserCard;
import net.xingsu.web.italker.push.bean.db.User;
import net.xingsu.web.italker.push.factory.UserFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

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


    /**
     * 拉取联系人
     * @return
     */
    @GET
    @Path("/contact")//path //127.0.0.1/api/user/contact
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<List<UserCard>> contact(){
        User self = getSelf();
        //拿到我的联系人
        List<User> users = UserFactory.contacts(self);

        //转换为UserCard
        List<UserCard> userCards = users.stream()
                //map操作，想到与转置操作，User->UserCard
                .map(user -> new UserCard(user,true))
        .collect(Collectors.toList());
        //返回
        return ResponseModel.buildOk(userCards);
    }

    /**
     * 关注人
     * @param followId
     * @return
     */
    @PUT
    @Path("/follow/{followId}")//path //127.0.0.1/api/user/follow/{followId}
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<UserCard> follow(@PathParam("followId") String followId){
        User self = getSelf();

        if(self.getId().equalsIgnoreCase(followId)){
            //返回参数异常
            return ResponseModel.buildParameterError();
        }

        //找到我要关注的人
        User followUser = UserFactory.findById(followId);
        if(followUser == null){
            //未找到人
            return ResponseModel.buildNotFoundUserError(null);
        }

        //备注默认没有，后面可以扩展
        followUser = UserFactory.follow(self,followUser,null);
        if(followUser == null){
            //关注失败返回服务器异常
            return ResponseModel.buildServiceError();
        }

        //TODO 通知我关注的人，我关注了他

        //返回关注的人的信息
        return ResponseModel.buildOk(new UserCard(followUser,true));

    }
}
