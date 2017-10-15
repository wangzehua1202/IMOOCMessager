package com.xingsu.italker.factory.data.helper;

import com.xingsu.italker.common.factory.data.DataSource;
import com.xingsu.italker.factory.Factory;
import com.xingsu.italker.factory.R;
import com.xingsu.italker.factory.model.api.RspModel;
import com.xingsu.italker.factory.model.api.user.UserUpdateModel;
import com.xingsu.italker.factory.model.card.UserCard;
import com.xingsu.italker.factory.model.db.User;
import com.xingsu.italker.factory.net.Network;
import com.xingsu.italker.factory.net.RemotService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/9/30 0030.
 */

public class UserHelper {
    //更新用户信息，异步
    public static void update(UserUpdateModel model, final DataSource.Callback<UserCard> callback){
        //调用Retrofit对我们网络请求接口做代理
        RemotService service = Network.remote();
        //得到一个Call
        Call<RspModel<UserCard>> call = service.userUpdate(model);
        call.enqueue(new Callback<RspModel<UserCard>>() {
            @Override
            public void onResponse(Call<RspModel<UserCard>> call, Response<RspModel<UserCard>> response) {
                RspModel<UserCard> rspModel = response.body();
                if(rspModel.success()){
                    UserCard userCard = rspModel.getResult();
                    //数据库的存储操作，需要把UserCard转换成User
                    //保存用户信息
                    User user = userCard.build();
                    user.save();
                    //返回成功
                    callback.onDataLoaded(userCard);
                }else{
                    //错误情况下进行错误分配
                    Factory.decodeRspCode(rspModel, callback);
                }
            }

            @Override
            public void onFailure(Call<RspModel<UserCard>> call, Throwable t) {
                //网络请求失败
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });
    }


    //搜索的方法
    public static Call search(String name, final DataSource.Callback<List<UserCard>> callback){
        RemotService service = Network.remote();
        Call<RspModel<List<UserCard>>> call = service.userSearch(name);

        call.enqueue(new Callback<RspModel<List<UserCard>>>() {
            @Override
            public void onResponse(Call<RspModel<List<UserCard>>> call, Response<RspModel<List<UserCard>>> response) {
                RspModel<List<UserCard>> rspModel = response.body();
                if(rspModel.success()){
                    //返回数据
                    callback.onDataLoaded(rspModel.getResult());
                }else{
                    //错误情况下进行错误分配
                    Factory.decodeRspCode(rspModel, callback);
                }
            }

            @Override
            public void onFailure(Call<RspModel<List<UserCard>>> call, Throwable t) {
                //网络请求失败
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });

        //把当前的调度者返回
        return call;
    }

}
