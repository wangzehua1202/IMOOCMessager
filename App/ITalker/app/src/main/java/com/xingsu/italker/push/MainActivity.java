package com.xingsu.italker.push;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.xingsu.italker.common.app.Activity;
import com.xingsu.italker.common.widget.PortaitView;
import com.xingsu.italker.push.frags.main.ActiveFragment;
import com.xingsu.italker.push.frags.main.GroupFragment;
import com.xingsu.italker.push.helper.NavHelper;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends Activity implements BottomNavigationView.OnNavigationItemSelectedListener{

    @BindView(R.id.appbar)
    View mLayAppbar;

    @BindView(R.id.im_portait)
    PortaitView mPortait;

    @BindView(R.id.txt_title)
    TextView mTitle;

    @BindView(R.id.lay_container)
    FrameLayout mContainer;

    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;

    private NavHelper navHelper;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        //初始化底部辅助工具类
        navHelper = new NavHelper(context, containerId, fragmentManager, listener);

        //添加对底部按钮点击的监听
        mNavigation.setOnNavigationItemSelectedListener(this);

        Glide.with(this)
                .load(R.drawable.bg_src_morning)
                .centerCrop()
                .into(new ViewTarget<View,GlideDrawable>(mLayAppbar) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        this.view.setBackground(resource.getCurrent());
                    }
                });
    }

    @Override
    protected void initData() {
        super.initData();

    }

    @OnClick(R.id.im_search)
    void onSearchMenuClick(){

    }

    @OnClick(R.id.btn_action)
    void onActionClick(){

    }

    boolean isFirst;

    /**
     * 当我们底部导航被点击的时候触发
     * @param item MenuItem
     * @return true 代表我们能够处理这个点击
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.action_home){
            mTitle.setText(R.string.title_home);

            ActiveFragment activeFragment = new ActiveFragment();

            if(isFirst){
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.lay_container,activeFragment)
                        .commit();
                isFirst = false;
            }else{
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.lay_container,activeFragment)
                        .commit();
            }
        }
        else if(item.getItemId() == R.id.action_group){
            mTitle.setText(R.string.title_group);

            GroupFragment groupFragment = new GroupFragment();

            if(isFirst) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.lay_container, groupFragment)
                        .commit();
                isFirst = false;
            }else{
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.lay_container, groupFragment)
                        .commit();
            }
        }

        Log.e("TAG", "size: "+getSupportFragmentManager().getFragments().size() );
        mTitle.setText(item.getTitle());
        //转接事件流到工具类中
        return navHelper.performClickMenu(item.getItemId());
    }
}
