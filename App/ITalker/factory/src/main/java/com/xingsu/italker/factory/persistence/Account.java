package com.xingsu.italker.factory.persistence;

import android.content.Context;
import android.content.SharedPreferences;

import com.xingsu.italker.factory.Factory;

/**
 * Created by Administrator on 2017/9/29 0029.
 */

public class Account {
    private static final String KEY_PUSH_ID = "KEY_PUSH_ID";
    private static final String KEY_IS_BIND = "KEY_IS_BIND";


    //设备的推送Id
    private static String pushId;
    //设备id是否已经绑定到了服务器
    private static boolean isBind;

    /**
     * 存储到xml文件，持久化
     */
    private static void save(Context context){
        //获取数据持久化的SP
        SharedPreferences sp = context.getSharedPreferences(Account.class.getName(),Context.MODE_PRIVATE);
        //存储数据
        sp.edit()
                .putString(KEY_PUSH_ID,pushId)
                .putBoolean(KEY_IS_BIND,isBind)
                .apply();
    }

    /**
     * 进行数据加载
     */
    public static void load(Context context){
        //获取数据持久化的SP
        SharedPreferences sp = context.getSharedPreferences(Account.class.getName(),Context.MODE_PRIVATE);
        pushId = sp.getString(KEY_PUSH_ID,"");
        isBind = sp.getBoolean(KEY_IS_BIND,false);
    }

    /**
     * 设置并存储设备的Id
     * @param pushId 设备的推送ID
     */
    public static void setPushId(String pushId){
        Account.pushId = pushId;
        Account.save(Factory.app());
    }

    /**
     * 获取推送id
     * @return
     */
    public static String getPushId(){
        return pushId;
    }

    /**
     * 返回当前账号是否登录
     * @return true表示已登录
     */
    public static boolean isLogin() {
        return true;
    }

    /**
     * 是否已经绑定
     * @return
     */
    public static boolean isBind() {
        return false;
    }

    /**
     * 设置绑定状态
     */
    public static void setBind(boolean isBind) {
        Account.isBind = isBind;
        Account.save(Factory.app());
    }
}
