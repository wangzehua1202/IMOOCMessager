package com.xingsu.italker.factory.presenter.contact;

import com.xingsu.italker.common.factory.presenter.BasePresenter;
import com.xingsu.italker.factory.Factory;
import com.xingsu.italker.factory.data.helper.UserHelper;
import com.xingsu.italker.factory.model.db.User;
import com.xingsu.italker.factory.persistence.Account;
import com.xingsu.italker.factory.presenter.account.RegisterContract;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

/**
 * Created by Administrator on 2017/10/17 0017.
 */

public class PersonalPresenter extends BasePresenter<PersonalContract.View>
    implements PersonalContract.Presenter{

    private User user;

    public PersonalPresenter(PersonalContract.View view) {
        super(view);
    }

    @Override
    public void start() {
        super.start();

        //个人界面用户数据优先从网络拉取
        Factory.runOnAsync(new Runnable() {
            @Override
            public void run() {
                PersonalContract.View view = getView();
                if(view != null) {
                    String id = getView().getUserId();
                    User user = UserHelper.searchFirstOfNet(id);
                    onLoaded(view, user);
                }
            }
        });
    }

    private void onLoaded(final PersonalContract.View view, final User user){
        this.user = user;
        //是否是我自己
        final boolean isSelf = user.getId().equalsIgnoreCase(Account.getUserId());
        //是否已经关注
        final boolean isFollow = isSelf || user.isFollow();
        //是否允许会话,不是自己，且已经关注
        final boolean allowSayHello = isFollow && !isSelf;
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.onLoadDone(user);
                view.setFollowStatus(isFollow);
                view.allowSayHello(allowSayHello);
            }
        });
    }

    @Override
    public User getUserPersonal() {
        return user;
    }
}
