package com.xingsu.italker.factory.presenter.search;

import com.xingsu.italker.common.factory.presenter.BasePresenter;

/**
 * 搜索人的逻辑实现
 * Created by 王泽华 on 2017/10/15.
 */

public class SearchUserPresenter extends BasePresenter<SearchContract.UserView>
        implements SearchContract.Presenter {

    public SearchUserPresenter(SearchContract.UserView view) {
        super(view);
    }

    @Override
    public void search(String content) {

    }
}
