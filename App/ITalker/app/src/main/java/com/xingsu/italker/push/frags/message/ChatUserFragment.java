package com.xingsu.italker.push.frags.message;


import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.xingsu.italker.common.common.widget.PortaitView;
import com.xingsu.italker.push.R;
import com.xingsu.italker.push.activites.PersonalActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 用户聊天界面
 * A simple {@link Fragment} subclass.
 */
public class ChatUserFragment extends ChatFragment {

    @BindView(R.id.im_portrait)
    PortaitView mPortaitView;

    private MenuItem mUserInfoMenuItem;

    public ChatUserFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_chat_user;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();

        Toolbar toolbar = mToolbar;
        toolbar.inflateMenu(R.menu.chat_user);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.action_persion){
                    onPortaitViewClick();
                }
                return false;
            }
        });

        //拿到菜单icon
        mUserInfoMenuItem = toolbar.getMenu().findItem(R.id.action_persion);
    }

    //进行高度的综合运算，透明头像和icon
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        super.onOffsetChanged(appBarLayout, verticalOffset);
        View view = mPortaitView;
        MenuItem menuItem = mUserInfoMenuItem;

        if(view == null || menuItem == null)
            return ;

        if(verticalOffset == 0){
            //完全展开
            mPortaitView.setVisibility(View.VISIBLE);
            view.setScaleX(1);
            view.setScaleY(1);
            view.setAlpha(1);

            //隐藏菜单
            menuItem.setVisible(false);
            menuItem.getIcon().setAlpha(0);
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

                //显示菜单
                menuItem.setVisible(true);
                menuItem.getIcon().setAlpha(255);
            }else{
                //中间状态
                float progress = 1 - verticalOffset/(float)totalScrollRange;
                mPortaitView.setVisibility(View.VISIBLE);
                view.setScaleX(progress);
                view.setScaleY(progress);
                view.setAlpha(progress);

                //和头像相反
                menuItem.setVisible(true);
                menuItem.getIcon().setAlpha(255 - (int)(255 * progress));
            }
        }
    }

    @OnClick(R.id.im_portrait)
    void onPortaitViewClick(){
        PersonalActivity.show(getContext(), mReceiverId);
    }
}
