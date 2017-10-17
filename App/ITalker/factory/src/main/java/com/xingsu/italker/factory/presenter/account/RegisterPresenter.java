package com.xingsu.italker.factory.presenter.account;

import android.support.annotation.StringRes;
import android.text.TextUtils;

import com.xingsu.italker.common.Common;
import com.xingsu.italker.common.factory.data.DataSource;
import com.xingsu.italker.common.factory.presenter.BasePresenter;
import com.xingsu.italker.factory.R;
import com.xingsu.italker.factory.data.helper.AccountHelper;
import com.xingsu.italker.factory.model.api.account.RegisterModel;
import com.xingsu.italker.factory.model.db.User;
import com.xingsu.italker.factory.persistence.Account;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/9/28 0028.
 */

public class RegisterPresenter extends BasePresenter<RegisterContract.View>
        implements RegisterContract.Presenter,DataSource.Callback<User> {
    public RegisterPresenter(RegisterContract.View view) {
        super(view);
    }

    /**
     * 注册部分实现
     * @param phone
     * @param name
     * @param password
     */
    @Override
    public void register(String phone, String name, String password) {
        //调用开始方法，在start中默认启动了Loadding
        start();

        //得到View接口
        RegisterContract.View view = getView();

        //校验
        if(!checkMobile(phone)){
            //提示
            view.showError(R.string.data_account_register_invalid_parameter_mobile);
        }
        else if(name.length() < 2){
            //姓名大于2位
            view.showError(R.string.data_account_register_invalid_parameter_name);
        }
        else if(password.length() < 6){
            //密码需要大于6位
            view.showError(R.string.data_account_register_invalid_parameter_password);
        }
        else{
            //进行网络请求

            //构造Model，进行请求调用
            RegisterModel model = new RegisterModel(phone,password,name, Account.getPushId());
            //进行网络请求，并设置回送接口为自己
            AccountHelper.register(model,this);
        }
    }

    /**
     * 检查手机号是否合法
     * @param phone 手机号码
     * @return 合法为true
     */
    @Override
    public boolean checkMobile(String phone) {
        //手机号不为空，并且满足格式
        return !TextUtils.isEmpty(phone)
                && Pattern.matches(Common.Constance.REGEX_MOBILE, phone);
    }


    @Override
    public void onDataLoaded(User user) {
        //当网络请求成功，注册好了，回送一个用户信息回来
        //告知界面，注册成功
        final RegisterContract.View view = getView();
        if(view == null)
            return;
        //此时是从网络回送回来的，并不保证是在主线程状态
        //强制执行在主线程中
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                //调用主界面注册成功
                view.registerSuccess();
            }
        });
    }

    @Override
    public void onDataNotAvailable(@StringRes final int strRes) {
        //网络请求告知注册失败
        final RegisterContract.View view = getView();
        if(view == null)
            return;
        //此时是从网络回送回来的，并不保证是在主线程状态
        //强制执行在主线程中
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                //调用主界面注册失败，显示错误
                view.showError(strRes);
            }
        });
    }
}
