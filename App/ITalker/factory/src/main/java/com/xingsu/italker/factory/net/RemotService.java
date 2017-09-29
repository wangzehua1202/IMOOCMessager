package com.xingsu.italker.factory.net;

import com.xingsu.italker.factory.model.api.RspModel;
import com.xingsu.italker.factory.model.api.account.AccountRspModel;
import com.xingsu.italker.factory.model.api.account.RegisterModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

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
}
