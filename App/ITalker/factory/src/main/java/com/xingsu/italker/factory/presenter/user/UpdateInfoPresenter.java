package com.xingsu.italker.factory.presenter.user;

import android.support.annotation.StringRes;
import android.text.TextUtils;

import com.xingsu.italker.common.factory.data.DataSource;
import com.xingsu.italker.common.factory.presenter.BasePresenter;
import com.xingsu.italker.factory.Factory;
import com.xingsu.italker.factory.R;
import com.xingsu.italker.factory.data.helper.UserHelper;
import com.xingsu.italker.factory.model.api.user.UserUpdateModel;
import com.xingsu.italker.factory.model.card.UserCard;
import com.xingsu.italker.factory.model.db.User;
import com.xingsu.italker.factory.net.UploadHelper;
import com.xingsu.italker.factory.presenter.account.LoginContract;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

/**
 * Created by Administrator on 2017/9/30 0030.
 */

public class UpdateInfoPresenter extends BasePresenter<UpdateInfoContract.View>
implements UpdateInfoContract.Presenter, DataSource.Callback<UserCard>{
    public UpdateInfoPresenter(UpdateInfoContract.View view) {
        super(view);
    }

    @Override
    public void update(final String photoFilePath, final String desc, final boolean isMan) {
        start();

        final UpdateInfoContract.View view = getView();

        if(TextUtils.isEmpty(photoFilePath) || TextUtils.isEmpty(desc)){
            view.showError(R.string.data_account_update_invalid_parameter);
        }else{
            //上传头像
            Factory.runOnAsync(new Runnable() {
                @Override
                public void run() {
                    String url = UploadHelper.uploadPortrait(photoFilePath);
                    if(TextUtils.isEmpty(url)){
                        //上传失败
                        view.showError(R.string.data_upload_error);
                    }else{
                        //构建Model
                        UserUpdateModel model = new UserUpdateModel("", url, desc, isMan ? User.SEX_MAN : User.SEX_WOMAN);

                        //进行网络请求，上传
                        UserHelper.update(model,UpdateInfoPresenter.this);
                    }
                }
            });
        }
    }

    @Override
    public void onDataLoaded(UserCard userCard) {
        final UpdateInfoContract.View view = getView();
        if (view == null)
            return;
        // 强制执行在主线程中
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.updateSucceed();
            }
        });
    }

    @Override
    public void onDataNotAvailable(@StringRes final int strRes) {
        // 网络请求告知注册失败
        final UpdateInfoContract.View view = getView();
        if (view == null)
            return;
        // 此时是从网络回送回来的，并不保证处于主现场状态
        // 强制执行在主线程中
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                // 调用主界面注册失败显示错误
                view.showError(strRes);
            }
        });
    }
}
