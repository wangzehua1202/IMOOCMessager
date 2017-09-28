package com.xingsu.italker.factory.presenter.account;


import com.xingsu.italker.common.factory.presenter.BaseContract;

/**
 * Created by Administrator on 2017/9/28 0028.
 */

public interface RegisterContract {
    interface View extends BaseContract.View<Presenter>{
        //注册成功
        void registerSuccess();

    }

    interface Presenter extends BaseContract.Presenter{
        //发起注册
        void register(String phone,String name,String password);

        //检查手机号是否正确
        boolean checkMobile(String phone);
    }

}
