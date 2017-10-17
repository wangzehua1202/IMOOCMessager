package com.xingsu.italker.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.igexin.sdk.PushConsts;
import com.xingsu.italker.factory.Factory;
import com.xingsu.italker.factory.data.helper.AccountHelper;
import com.xingsu.italker.factory.persistence.Account;

/**
 * 个推的消息接收器
 * Created by Administrator on 2017/9/29 0029.
 */

public class MessageReceiver extends BroadcastReceiver {
    private static final String TAG = MessageReceiver.class.getSimpleName();
    
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent == null)
            return;

        Bundle bundle = intent.getExtras();

        //判断当前消息的意图
        switch (bundle.getInt(PushConsts.CMD_ACTION)){
            case PushConsts.GET_CLIENTID:{
                Log.i(TAG, "GET_CLIENTID:" + bundle.toString());
                //当Id初始化的时候
                onClientInit(bundle.getString("clientid"));
                break;
            }
            case PushConsts.GET_MSG_DATA:{
                byte[] payload = bundle.getByteArray("payload");
                if(payload != null){
                    String message = new String(payload);
                    Log.i(TAG, "GET_MSG_DATA:"+ message);
                    onMessageArrived(message);
                }
            }
            default:
                Log.i(TAG, "OTHER" + bundle.toString());
                break;
        }
    }

    /**
     * 当Id初始化的时候
     * @param cid 设备id
     */
    private void onClientInit(String cid){
        //设置设备Id
        Account.setPushId(cid);

        if(Account.isLogin()){
            //账户登录状态，进行一次pushid绑定
            // 如果没有绑定则等待广播接收器进行绑定
            AccountHelper.bindPush(null);
        }
    }

    /**
     * 消息达到时
     * @param message
     */
    private void onMessageArrived(String message){
        //交给Factory处理
        Factory.dispatchPush(message);
    }
}
