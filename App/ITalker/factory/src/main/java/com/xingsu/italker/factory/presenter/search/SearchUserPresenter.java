package com.xingsu.italker.factory.presenter.search;

import android.support.annotation.StringRes;

import com.xingsu.italker.common.factory.data.DataSource;
import com.xingsu.italker.common.factory.presenter.BasePresenter;
import com.xingsu.italker.factory.data.helper.UserHelper;
import com.xingsu.italker.factory.model.card.UserCard;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.List;

import retrofit2.Call;

/**
 * 搜索人的逻辑实现
 * Created by 王泽华 on 2017/10/15.
 */

public class SearchUserPresenter extends BasePresenter<SearchContract.UserView>
        implements SearchContract.Presenter, DataSource.Callback<List<UserCard>>{
    private Call searchCall;

    public SearchUserPresenter(SearchContract.UserView view) {
        super(view);
    }

    @Override
    public void search(String content) {
        start();

        Call call = searchCall;
        if(call != null && call.isCanceled()){
            //如果有上一次的请求，并且没有取消
            //则调用取消请求操作
            call.cancel();
        }
        searchCall = UserHelper.search(content,this);
    }

    @Override
    public void onDataLoaded(final List<UserCard> userCards) {
        //搜索成功
        final SearchContract.UserView view = getView();
        if(view != null){
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                     view.onSearchDone(userCards);
                }
            });
        }
    }

    @Override
    public void onDataNotAvailable(@StringRes final int strRes) {
        //搜索失败
        final SearchContract.UserView view = getView();
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
