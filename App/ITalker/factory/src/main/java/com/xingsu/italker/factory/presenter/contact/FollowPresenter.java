package com.xingsu.italker.factory.presenter.contact;

import android.support.annotation.StringRes;

import com.xingsu.italker.common.factory.data.DataSource;
import com.xingsu.italker.common.factory.presenter.BasePresenter;
import com.xingsu.italker.factory.data.helper.UserHelper;
import com.xingsu.italker.factory.model.card.UserCard;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

/**
 * 关注的逻辑实现
 * Created by 王泽华 on 2017/10/16.
 */

public class FollowPresenter extends BasePresenter<FollowContract.View>
        implements FollowContract.Presenter, DataSource.Callback<UserCard>{
    public FollowPresenter(FollowContract.View view) {
        super(view);
    }

    @Override
    public void follow(String id) {
        start();
        UserHelper.follow(id,this);
    }

    @Override
    public void onDataLoaded(final UserCard userCard) {
        //成功
        final FollowContract.View view = getView();
        if(view != null){
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    view.onFollowSucceed(userCard);
                }
            });
        }
    }

    @Override
    public void onDataNotAvailable(@StringRes final int strRes) {
        //失败
        final FollowContract.View view = getView();
        if(view != null){
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    view.showError(strRes);
                }
            });
        }
    }
}
