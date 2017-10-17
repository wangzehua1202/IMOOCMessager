package com.xingsu.italker.factory.presenter.contact;

import com.xingsu.italker.common.common.widget.recycler.RecyclerAdapter;
import com.xingsu.italker.common.factory.presenter.BaseContract;
import com.xingsu.italker.factory.model.db.User;

import java.util.List;

/**
 * Created by Administrator on 2017/10/17 0017.
 */

public interface ContactContract {

    interface Presenter extends BaseContract.Presenter{

    }

    //都在基类完成了
    interface View extends BaseContract.RecyclerView<Presenter,User>{

    }
}
