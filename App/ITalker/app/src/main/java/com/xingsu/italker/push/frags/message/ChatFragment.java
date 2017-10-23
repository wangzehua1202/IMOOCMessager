package com.xingsu.italker.push.frags.message;


import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.xingsu.italker.common.common.app.Fragment;
import com.xingsu.italker.common.common.widget.PortaitView;
import com.xingsu.italker.push.R;
import com.xingsu.italker.push.activites.MessageActivity;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class ChatFragment extends Fragment
    implements AppBarLayout.OnOffsetChangedListener{

    private String mReceiverId;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.appbar)
    AppBarLayout mAppBarLayout;

    @Override
    protected void initArgs(Bundle bundle) {
        super.initArgs(bundle);
        mReceiverId = bundle.getString(MessageActivity.KEY_RECEIVER_ID);
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        initToolbar();
        initAppbar();

        //RecyclerView 基本设置
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void initToolbar(){
        Toolbar toolbar = mToolbar;
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }


    //给appbar设置一个监听，得到关闭和打开的时候的进度
    private void initAppbar() {
        mAppBarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if(verticalOffset == 0){

        }
    }
}
