package net.xingsu.web.italker.push.utils;

import com.gexin.rp.sdk.base.IBatch;
import com.gexin.rp.sdk.base.IIGtPush;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.google.common.base.Strings;
import com.google.gson.annotations.Expose;
import net.xingsu.web.italker.push.bean.api.base.PushModel;
import net.xingsu.web.italker.push.bean.db.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 消息推送工具类
 * Created by Administrator on 2017/10/20 0020.
 */
public class PushDispatcher {
    //采用"Java SDK 快速入门"， "第二步 获取访问凭证 "中获得的应用配置，用户可以自行替换
    private static final String appId = "ITLft7TPgQ9EY2siGdYRPA";
    private static final String appKey = "WX0uYauPpm6KKR0V7XjOs";
    private static final String masterSecret = "OqRL2bdbIS9AOg9NjArYO4";
    //别名推送方式
    // static String Alias = "";
    private final IGtPush pusher;
    static String host = "http://sdk.open.api.igexin.com/apiex.htm";
    //要收到消息的人和内容的列表
    private List<BatchBean> beans = new ArrayList<>();

    public PushDispatcher() {
        //最根本的发送者
        pusher = new IGtPush(host, appKey, masterSecret);
    }

    /**
     * 添加一条消息
     * @param receiver 接收者
     * @param model 接收的推送model
     * @return 是否添加成功
     */
    public boolean add(User receiver, PushModel model){
        //基础检查
        if(receiver == null || model == null || Strings.isNullOrEmpty(receiver.getPushId()))
            return false;

        String pushString = model.getPushString();
        if(Strings.isNullOrEmpty(pushString))
            return false;

        BatchBean bean = buildMessage(receiver.getPushId(), pushString);
        beans.add(bean);
        return true;
    }

    /**
     * 对要发送的数据进行格式化封装
     * @param clientId 接收者的设备id
     * @param text 要接收的数据
     * @return BatchBean
     */
    private BatchBean buildMessage(String clientId, String text) {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        template.setTransmissionContent(text);
        template.setTransmissionType(0); // 这个Type为int型，填写1则自动启动app

        SingleMessage message = new SingleMessage();
        message.setData(template);
        message.setOffline(true);
        message.setOfflineExpireTime(24 * 3600 * 1000);        //离线消息时长
        // 设置推送目标，填入appid和clientId
        Target target = new Target();
        target.setAppId(appId);
        target.setClientId(clientId);
        return new BatchBean(message, target);
    }


    //进行消息最终发送
    public boolean submit(){
        //构建打包的工具类
        IBatch batch = pusher.getBatch();
        //是否有数据需要发送
        boolean haveData = false;

        for (BatchBean bean : beans) {
            try {
                batch.add(bean.message, bean.target);
                haveData = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(!haveData)
            return false;

        IPushResult result = null;
        try {
            result = batch.submit();
        } catch (IOException e) {
            e.printStackTrace();
            //失败情况下尝试重复发送一次
            try {
                batch.retry();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        if(result != null){
            try {
                Logger.getLogger("PushDispatcher")
                        .log(Level.INFO, (String) result.getResponse().get("result"));
                return true;
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

        Logger.getLogger("PushDispatcher")
                .log(Level.WARNING, "推送服务器响应异常！");
        return false;

    }


    //给每个人发送消息的一个Bean封装
    private static class BatchBean{

        SingleMessage message;
        Target target;

        public BatchBean(SingleMessage message, Target target) {
            this.message = message;
            this.target = target;
        }
    }
}
