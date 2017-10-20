package net.xingsu.web.italker.push.service;

import com.fasterxml.jackson.databind.ser.Serializers;
import net.xingsu.web.italker.push.bean.api.base.ResponseModel;
import net.xingsu.web.italker.push.bean.api.message.MessageCreateModel;
import net.xingsu.web.italker.push.bean.card.MessageCard;
import net.xingsu.web.italker.push.bean.card.UserCard;
import net.xingsu.web.italker.push.bean.db.Group;
import net.xingsu.web.italker.push.bean.db.Message;
import net.xingsu.web.italker.push.bean.db.User;
import net.xingsu.web.italker.push.factory.MessageFactory;
import net.xingsu.web.italker.push.factory.PushFactory;
import net.xingsu.web.italker.push.factory.UserFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 消息发送的入口
 * Created by Administrator on 2017/10/20 0020.
 */
@Path("msg")
public class MessageService extends BaseService{
    /**
     * 发送一条消息
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<MessageCard> pushMessage(MessageCreateModel model){
        if(!MessageCreateModel.check(model)){
            return ResponseModel.buildParameterError();
        }

        User self = getSelf();

        //查询是否在数据库中已存在
        Message message = MessageFactory.findById(model.getId());
        if(message != null){
            return ResponseModel.buildOk(new MessageCard(message));
        }

        if(model.getReceiverType() == Message.RECEIVER_TYPE_GROUP){
            return pushToGroup(self, model);
        }else{
            return pushToUser(self, model);
        }
    }

    //发送到人
    private ResponseModel<MessageCard> pushToUser(User sender, MessageCreateModel model) {
        User receiver = UserFactory.findById(model.getReceiverId());
        if(receiver == null)
            return ResponseModel.buildNotFoundUserError("没有找到接收者");
        if(receiver.getId().equalsIgnoreCase(sender.getId())){
            //发送者和接收者是同一个人，就返回创建消息失败
            return ResponseModel.buildCreateError(ResponseModel.ERROR_CREATE_MESSAGE);
        }
        //存储数据库
        Message message = MessageFactory.add(sender, receiver, model);

        return buildAndPushResponse(sender, message);
    }

    //推送并构建一个返回信息
    private ResponseModel<MessageCard> buildAndPushResponse(User sender, Message message) {
        if(message == null){
            //存储数据库失败
            return ResponseModel.buildCreateError(ResponseModel.ERROR_CREATE_MESSAGE);
        }

        //进行推送
        PushFactory.pushNewMessage(sender, message);

        //返回
        return ResponseModel.buildOk(new MessageCard(message));
    }

    //发送到群
    private ResponseModel<MessageCard> pushToGroup(User sender, MessageCreateModel model) {
        //TODO Group group = GroupFactory.findById();
        return null;
    }
}
