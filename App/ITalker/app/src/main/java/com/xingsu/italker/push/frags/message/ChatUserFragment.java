package com.xingsu.italker.push.frags.message;


import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.view.View;

import com.xingsu.italker.common.common.widget.PortaitView;
import com.xingsu.italker.push.R;

import butterknife.BindView;

/**
 * 用户聊天界面
 * A simple {@link Fragment} subclass.
 */
public class ChatUserFragment extends ChatFragment {

    @BindView(R.id.im_portrait)
    PortaitView mPortaitView;

    public ChatUserFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_chat_user;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        super.onOffsetChanged(appBarLayout, verticalOffset);
        View view = mPortaitView;
        if(verticalOffset == 0){
            //完全展开
            mPortaitView.setVisibility(View.VISIBLE);
            view.setScaleX(1);
            view.setScaleY(1);
            view.setAlpha(1);
        }else{
            //绝对值运算
            verticalOffset = Math.abs(verticalOffset);
            final int totalScrollRange = appBarLayout.getTotalScrollRange();
            if(verticalOffset >= totalScrollRange){
                //关闭状态
                mPortaitView.setVisibility(View.INVISIBLE);
                view.setScaleX(0);
                view.setScaleY(0);
                view.setAlpha(0);
            }else{
                //中间状态
                float progress = 1 - verticalOffset/(float)totalScrollRange;
                mPortaitView.setVisibility(View.VISIBLE);
                view.setScaleX(progress);
                view.setScaleY(progress);
                view.setAlpha(progress);
            }
        }
    }
}
