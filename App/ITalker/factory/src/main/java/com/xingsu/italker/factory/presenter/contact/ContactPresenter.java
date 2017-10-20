package com.xingsu.italker.factory.presenter.contact;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.util.DiffUtil;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.CursorResult;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.xingsu.italker.common.common.widget.recycler.RecyclerAdapter;
import com.xingsu.italker.common.factory.data.DataSource;
import com.xingsu.italker.common.factory.presenter.BasePresenter;
import com.xingsu.italker.common.factory.presenter.BaseRecyclerPresenter;
import com.xingsu.italker.factory.data.helper.UserHelper;
import com.xingsu.italker.factory.data.user.ContactDataSource;
import com.xingsu.italker.factory.data.user.ContactRepository;
import com.xingsu.italker.factory.model.card.UserCard;
import com.xingsu.italker.factory.model.db.AppDatabase;
import com.xingsu.italker.factory.model.db.User;
import com.xingsu.italker.factory.model.db.User_Table;
import com.xingsu.italker.factory.persistence.Account;
import com.xingsu.italker.factory.presenter.BaseSourcePresenter;
import com.xingsu.italker.factory.utils.DiffUiDataCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * 联系人Presenter的实现
 * Created by Administrator on 2017/10/17 0017.
 */

public class ContactPresenter extends BaseSourcePresenter<User, User, ContactDataSource, ContactContract.View>
        implements ContactContract.Presenter, DataSource.SucceedCallback<List<User>>{

    public ContactPresenter(ContactContract.View view) {
        //初始化数据仓库
        super(new ContactRepository(), view);
    }

    @Override
    public void start() {
        super.start();

        //加载网络数据
        UserHelper.refreshContacts();

    }

    //运行到这里的时候是子线程
    @Override
    public void onDataLoaded(List<User> users) {
        //无论怎么操作，数据变更，最终都会通知到这里来
        final ContactContract.View view = getView();
        if(view == null)
            return;
        RecyclerAdapter<User> adapter = view.getRecyclerAdapter();
        List<User> old = adapter.getItems();

        //进行数据对比
        DiffUtil.Callback callback = new DiffUiDataCallback<User>(old,users);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        //调用基类方法进行界面刷新
        refreshData(result, users);
    }

}
