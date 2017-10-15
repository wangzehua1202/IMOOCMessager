package com.xingsu.italker.factory.presenter.search;

import com.xingsu.italker.common.factory.presenter.BaseContract;
import com.xingsu.italker.factory.model.card.GroupCard;
import com.xingsu.italker.factory.model.card.UserCard;

import java.util.List;

/**
 * Created by 王泽华 on 2017/10/15.
 */

public interface SearchContract {
    interface Presenter extends BaseContract.Presenter{
        //搜索的内容
        void search(String content);
    }

    //搜索人的界面
    interface UserView extends BaseContract.View<Presenter>{
        void onSearchDone(List<UserCard> userCards);
    }

    //搜索群的界面
    interface GroupView extends BaseContract.View<Presenter>{
        void onSearchDone(List<GroupCard> groupCards);
    }

}
