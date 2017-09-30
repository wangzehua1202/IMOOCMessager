package com.xingsu.italker.factory.net;

import com.xingsu.italker.factory.model.api.RspModel;
import com.xingsu.italker.factory.model.api.account.AccountRspModel;
import com.xingsu.italker.factory.model.api.account.LoginModel;
import com.xingsu.italker.factory.model.api.account.RegisterModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
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
}
