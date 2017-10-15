package com.xingsu.italker.push.frags.search;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xingsu.italker.common.common.app.PresenterFragment;
import com.xingsu.italker.common.common.widget.EmptyView;
import com.xingsu.italker.common.common.widget.PortaitView;
import com.xingsu.italker.common.common.widget.recycler.RecyclerAdapter;
import com.xingsu.italker.factory.model.card.UserCard;
import com.xingsu.italker.factory.presenter.search.SearchContract;
import com.xingsu.italker.factory.presenter.search.SearchUserPresenter;
import com.xingsu.italker.push.R;
import com.xingsu.italker.push.activites.SearchActivity;

import java.util.List;

import butterknife.BindView;

/**
 * 搜索人的界面实现
 */
public class SearchUserFragment extends PresenterFragment<SearchContract.Presenter>
        implements SearchActivity.SearchFragment,SearchContract.UserView{

    @BindView(R.id.empty)
    EmptyView mEmptyView;

    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    private RecyclerAdapter<UserCard> mAdapter;

    public SearchUserFragment() {
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_search_user;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        //初始化Recycler
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter(mAdapter = new RecyclerAdapter<UserCard>() {
            @Override
            protected int getItemViewType(int position, UserCard userCard) {
                //返回cell的布局id
                return R.layout.cell_search_list;
            }

            @Override
            protected ViewHolder onCreateViewHolder(View root, int viewType) {
                return new SearchUserFragment.ViewHolder(root);
            }

        });

        //初始化占位布局
        mEmptyView.bind(mRecycler);
        setmPlaceHolderView(mEmptyView);
    }

    @Override
    public void search(String content) {
        //Activity->Fragment->Presenter->Net
        mPresenter.search(content);
    }


    @Override
    public void onSearchDone(List<UserCard> userCards) {
        //数据成功的情况下返回数据
        mAdapter.replace(userCards);
        //如果有数据，则是OK，没有数据显示空布局
        mPlaceHolderView.triggerOkOrEmpty(mAdapter.getItemCount() > 0 );
    }

    @Override
    protected SearchContract.Presenter initPresenter() {
        //初始化Presenter
        return new SearchUserPresenter(this);
    }

    /**
     * 每一个cell的布局操作
     */
    class ViewHolder extends RecyclerAdapter.ViewHolder<UserCard>{
        @BindView(R.id.im_portrait)
        PortaitView mPortaitView;

        @BindView(R.id.txt_name)
        TextView mName;

        @BindView(R.id.im_follow)
        ImageView mFollow;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(UserCard userCard) {
            Glide.with(SearchUserFragment.this)
                    .load(userCard.getPortrait())
                    .centerCrop()
                    .into(mPortaitView);

            mName.setText(userCard.getName());
            mFollow.setEnabled(userCard.isFollow());
        }
    }
}
