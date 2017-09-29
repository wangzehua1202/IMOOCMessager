package com.xingsu.italker.factory.data.helper;

import com.xingsu.italker.common.factory.data.DataSource;
import com.xingsu.italker.factory.Factory;
import com.xingsu.italker.factory.R;
import com.xingsu.italker.factory.model.api.RspModel;
import com.xingsu.italker.factory.model.api.account.AccountRspModel;
import com.xingsu.italker.factory.model.api.account.RegisterModel;
import com.xingsu.italker.factory.model.db.User;
import com.xingsu.italker.factory.net.Network;
import com.xingsu.italker.factory.net.RemotService;

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
        final RemotService service = Network.getRetrofit().create(RemotService.class);
        //得到一个Call
        Call<RspModel<AccountRspModel>> call = service.accountRegister(model);
        call.enqueue(new Callback<RspModel<AccountRspModel>>() {
            @Override
            public void onResponse(Call<RspModel<AccountRspModel>> call, Response<RspModel<AccountRspModel>> response) {
                //请求成功返回
                //从返回中得到全局Model，内部使用gson解析
                RspModel<AccountRspModel> rspModel = response.body();
                if(rspModel.success()){
                    //拿到实体
                    AccountRspModel accountRspModel = rspModel.getResult();
                    //判断绑定状态,是否绑定设备
                    if(accountRspModel.isBind()) {
                        User user = accountRspModel.getUser();
                        //TODO 进行的是数据库写入和缓存绑定
                        //然后返回
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
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });
    }

    /**
     * 对设备Id进行绑定的操作
     * @param callback Callback
     */
    public static void bindPush(final DataSource.Callback<User> callback){
        //TODO 抛出一个错误
        callback.onDataNotAvailable(R.string.app_name);
    }

}
