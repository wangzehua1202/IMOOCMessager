package com.xingsu.italker.factory.presenter;

import com.xingsu.italker.common.factory.data.DataSource;
import com.xingsu.italker.common.factory.data.DbDataSource;
import com.xingsu.italker.common.factory.presenter.BaseContract;
import com.xingsu.italker.common.factory.presenter.BaseRecyclerPresenter;

import java.util.List;

/**
 * 基础的仓库源Presenter定义
 * Created by Administrator on 2017/10/20 0020.
 */

public abstract class BaseSourcePresenter<Data, ViewModel,
        Source extends DbDataSource<Data>,
        View extends BaseContract.RecyclerView>
        extends BaseRecyclerPresenter<ViewModel, View>
        implements DataSource.SucceedCallback<List<Data>>{

    protected Source mSource;
    public BaseSourcePresenter(Source source, View view) {
        super(view);
        this.mSource = source;
    }

    @Override
    public void start() {
        super.start();
        if(mSource != null)
            mSource.load(this);
    }

    @Override
    public void destroy() {
        super.destroy();
        mSource.dispose();
        mSource = null;
    }

}
