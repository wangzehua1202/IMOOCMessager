package com.xingsu.italker.factory.data.helper;

import android.text.TextUtils;


import com.xingsu.italker.common.factory.data.DataSource;
import com.xingsu.italker.factory.Factory;
import com.xingsu.italker.factory.R;
import com.xingsu.italker.factory.model.api.RspModel;
import com.xingsu.italker.factory.model.api.account.AccountRspModel;
import com.xingsu.italker.factory.model.api.account.LoginModel;
import com.xingsu.italker.factory.model.api.account.RegisterModel;
import com.xingsu.italker.factory.model.db.User;
import com.xingsu.italker.factory.net.Network;
import com.xingsu.italker.factory.net.RemotService;
import com.xingsu.italker.factory.persistence.Account;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 王泽华 on 2017/9/28.
 */

public class AccountHelper {

    /**
     * 注册的接口,异步的调用
     * @param model 传递一个注册的Model进来
     * @param callback 成功与失败的接口回送
     */
    public static void register(RegisterModel model, final DataSource.Callback<User> callback){
        //调用Retrofit对我们网络请求接口做代理
        RemotService service = Network.remote();
        //得到一个Call
        Call<RspModel<AccountRspModel>> call = service.accountRegister(model);
        call.enqueue(new AccountRspCallback(callback));
    }

    /**
     * 登录的接口,异步的调用
     * @param model 传递一个登录的Model进来
     * @param callback 成功与失败的接口回送
     */
    public static void login(LoginModel model, final DataSource.Callback<User> callback){
        //调用Retrofit对我们网络请求接口做代理
        RemotService service = Network.remote();
        //得到一个Call
        Call<RspModel<AccountRspModel>> call = service.accountLogin(model);
        call.enqueue(new AccountRspCallback(callback));
    }


    /**
     * 对设备Id进行绑定的操作
     * @param callback Callback
     */
    public static void bindPush(final DataSource.Callback<User> callback){
        //检查是否为null
        String pushId = Account.getPushId();
        if(TextUtils.isEmpty(pushId))
            return;

        //调用Retrofit对我们网络请求接口做代理
        final RemotService service = Network.remote();
        Call<RspModel<AccountRspModel>> call = service.accountBind(pushId);

        call.enqueue(new AccountRspCallback(callback));
    }


    /**
     * 请求回调的部分封装
     */
    private static class AccountRspCallback implements Callback<RspModel<AccountRspModel>>{

        final DataSource.Callback<User> callback;

        public AccountRspCallback(DataSource.Callback<User> callback) {
            this.callback = callback;
        }

        @Override
        public void onResponse(Call<RspModel<AccountRspModel>> call, Response<RspModel<AccountRspModel>> response) {
            //请求成功返回
            //从返回中得到全局Model，内部使用gson解析
            RspModel<AccountRspModel> rspModel = response.body();
            if(rspModel.success()){
                //拿到实体
                AccountRspModel accountRspModel = rspModel.getResult();
                //获取用户信息
                User user = accountRspModel.getUser();
                //第一种，直接保存
                user.save();

                    /*
                    //第二种通过FlowManager保存，比起第一种，可以存集合，存列
                    FlowManager.getModelAdapter(User.class)
                            .save(user);

                    //第三种，事务
                    DatabaseDefinition definition = FlowManager.getDatabase(AppDatabase.class);
                    definition.beginTransactionAsync(new ITransaction() {
                        @Override
                        public void execute(DatabaseWrapper databaseWrapper) {
                            FlowManager.getModelAdapter(User.class)
                                    .save(user);
                        }
                    }).build().execute();
                    */
                //同步到xml持久化中
                Account.login(accountRspModel);
                //判断绑定状态,是否绑定设备
                if(accountRspModel.isBind()) {
                    //设置绑定状态为已绑定
                    Account.setBind(true);
                    //然后返回
                    if(callback != null)
                        callback.onDataLoaded(user);
                }else{
                    //没有绑定，则绑定设备
                    bindPush(callback);
                }
            }else{
                //错误解析
                Factory.decodeRspCode(rspModel, callback);
            }
        }

        @Override
        public void onFailure(Call<RspModel<AccountRspModel>> call, Throwable t) {
            //网络请求失败
            if(callback != null)
                callback.onDataNotAvailable(R.string.data_network_error);
        }
    }

}
