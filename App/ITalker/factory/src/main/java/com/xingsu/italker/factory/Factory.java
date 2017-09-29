package com.xingsu.italker.factory;

import android.support.annotation.StringRes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xingsu.italker.common.common.app.Application;
import com.xingsu.italker.common.factory.data.DataSource;
import com.xingsu.italker.factory.model.api.RspModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/9/26 0026.
 */

public class Factory {
    //单例模式
    private static final Factory instance;
    //全局的线程池
    private final Executor executor;
    //全局的gson
    private final Gson gson;

    static {
        instance = new Factory();
    }

    public Factory() {
        //新建一个4个线程的线程池
        executor = Executors.newFixedThreadPool(4);
        gson = new GsonBuilder()
                //设置时间格式
                .setDateFormat("yyyy-MM-dd HH:mm:ss:SSS")
                //TODO 设置一个过滤器，数据库级别的Model不进行Json转换
//                .setExclusionStrategies()
                .create();
    }

    /**
     * 返回全局的Application
     * @return Application
     */
    public static Application app(){
        return Application.getInstance();
    }

    /**
     * 异步运行的方法
     * @param runable Runnable
     */
    public static void runOnAsync(Runnable runable){
        instance.executor.execute(runable);
    }

    /**
     * 返回一个全局的Gson，在这里可以进行Gson的一些全局的初始化
     * @return Gson
     */
    public static Gson getGson(){
        return instance.gson;
    }

    /**
     * 进行错误Code的解析，把网络返回的Code值进行统一的规划并返回String资源
     * @param model RspModel
     * @param callback DataSource.FailedCallback 返回一个错误的资源ID
     */
    public static void decodeRspCode(RspModel model, DataSource.FailedCallback callback){
        if(model == null)
            return;
        //进行Code区分
        switch (model.getCode()) {
            case RspModel.SUCCEED:
                return;
            case RspModel.ERROR_SERVICE:
                decodeRspCode(R.string.data_rsp_error_service, callback);
                break;
            case RspModel.ERROR_NOT_FOUND_USER:
                decodeRspCode(R.string.data_rsp_error_not_found_user, callback);
                break;
            case RspModel.ERROR_NOT_FOUND_GROUP:
                decodeRspCode(R.string.data_rsp_error_not_found_group, callback);
                break;
            case RspModel.ERROR_NOT_FOUND_GROUP_MEMBER:
                decodeRspCode(R.string.data_rsp_error_not_found_group_member, callback);
                break;
            case RspModel.ERROR_CREATE_USER:
                decodeRspCode(R.string.data_rsp_error_create_user, callback);
                break;
            case RspModel.ERROR_CREATE_GROUP:
                decodeRspCode(R.string.data_rsp_error_create_group, callback);
                break;
            case RspModel.ERROR_CREATE_MESSAGE:
                decodeRspCode(R.string.data_rsp_error_create_message, callback);
                break;
            case RspModel.ERROR_PARAMETERS:
                decodeRspCode(R.string.data_rsp_error_parameters, callback);
                break;
            case RspModel.ERROR_PARAMETERS_EXIST_ACCOUNT:
                decodeRspCode(R.string.data_rsp_error_parameters_exist_account, callback);
                break;
            case RspModel.ERROR_PARAMETERS_EXIST_NAME:
                decodeRspCode(R.string.data_rsp_error_parameters_exist_name, callback);
                break;
            case RspModel.ERROR_ACCOUNT_TOKEN:
                Application.showToast(R.string.data_rsp_error_account_token);
                instance.logout();
                break;
            case RspModel.ERROR_ACCOUNT_LOGIN:
                decodeRspCode(R.string.data_rsp_error_account_login, callback);
                break;
            case RspModel.ERROR_ACCOUNT_REGISTER:
                decodeRspCode(R.string.data_rsp_error_account_register, callback);
                break;
            case RspModel.ERROR_ACCOUNT_NO_PERMISSION:
                decodeRspCode(R.string.data_rsp_error_account_no_permission, callback);
                break;
            case RspModel.ERROR_UNKNOWN:
            default:
                decodeRspCode(R.string.data_rsp_error_unknown, callback);
                break;
        }
    }

    private static void decodeRspCode(@StringRes final int resId, DataSource.FailedCallback callback){
        if(callback != null){
            callback.onDataNotAvailable(resId);
        }
    }

    /**
     * 收到账户退出的消息需要进行账户的退出
     */
    private void logout(){

    }
}
