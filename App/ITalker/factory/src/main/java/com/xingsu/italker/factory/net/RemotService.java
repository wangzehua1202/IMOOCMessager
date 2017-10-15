package com.xingsu.italker.factory.net;

import com.xingsu.italker.factory.model.api.RspModel;
import com.xingsu.italker.factory.model.api.account.AccountRspModel;
import com.xingsu.italker.factory.model.api.account.LoginModel;
import com.xingsu.italker.factory.model.api.account.RegisterModel;
import com.xingsu.italker.factory.model.api.user.UserUpdateModel;
import com.xingsu.italker.factory.model.card.UserCard;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * 网络请求的所有接口
 * Created by Administrator on 2017/9/29 0029.
 */

public interface RemotService {
    /**
     * 网络请求一个注册接口
     * @param model RegisterModel
     * @return RspModel<AccountRspModel>
     */
    @POST("account/register")
    Call<RspModel<AccountRspModel>> accountRegister(@Body RegisterModel model);

    /**
     * 登录接口
     * @param model LoginModel
     * @return RspModel<AccountRspModel>
     */
    @POST("account/login")
    Call<RspModel<AccountRspModel>> accountLogin(@Body LoginModel model);

    /**
     * 绑定设备id
     * @param pushId 设备id
     * @return account信息
     */
    @POST("account/bind/{pushId}")
    Call<RspModel<AccountRspModel>> accountBind(@Path(encoded = false, value = "pushId") String pushId);

    /**
     * 用户更新的接口
     * @param model
     * @return
     */
    @PUT("user")
    Call<RspModel<UserCard>> userUpdate(@Body UserUpdateModel model);

    /**
     * 用户搜索的接口
     * @param name
     * @return
     */
    @GET("user/search/{name}")
    Call<RspModel<List<UserCard>>> userSearch(@Path("name") String name);


}
