package com.xingsu.italker.factory.presenter.account;

import com.xingsu.italker.common.factory.presenter.BaseContract;

/**
 * Created by Administrator on 2017/9/28 0028.
 */

public class LoginContract {
    interface View extends BaseContract.View<Presenter>{
        //登录成功
        void loginSuccess();
    }

    interface Presenter extends BaseContract.Presenter{
        //发起一个登录
        void login(String phone,String password);
    }
}
