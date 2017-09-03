package com.xingsu.italker.common.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 王泽华 on 2017/9/2.
 */

public abstract class Activity extends AppCompatActivity {
    //初始化界面
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在界面未初始化之前调用的初始化窗口
        initWindows();

        if(initArgs(getIntent().getExtras())){
            //得到界面Id并设置到Activity界面中
            int layId = getContentLayoutId();
            setContentView(layId);

            initWidget();
            initData();
        }else{
            finish();
        }
    }

    /**
     *初始化窗口
     */
    protected void initWindows(){

    }

    /**
     * 初始化相关参数
     * @param bundle 参数bundle
     * @return 如果参数正确返回true，错误返回flase
     */
    protected boolean initArgs(Bundle bundle){
        return true;
    }

    /**
     * 得到当前界面的资源文件Id
     * @return 资源文件Id
     */
    protected abstract  int getContentLayoutId();

    /**
     *  初始化控件
     */
    protected void initWidget(){
        ButterKnife.bind(this);
    }

    /**
     * 初始化数据
     */
    protected void initData(){

    }

    @Override
    public boolean onSupportNavigateUp() {
        //当点击界面导航返回时，Finish当前界面
        finish();
        return super.onSupportNavigateUp();
    }

    //手机自带返回
    @Override
    public void onBackPressed() {
        //得到当前Activity下的所有Fragment
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        //判断是否为空
        if(fragments != null && fragments.size() > 0){
            for (Fragment fragment : fragments){
                //判断是否为我们能够处理的Fragment类型
                if(fragment instanceof com.xingsu.italker.common.app.Fragment){
                    //判断是否拦截了返回按钮
                    if(((com.xingsu.italker.common.app.Fragment)fragment).onBackPressed()){
                        return;
                    }
                }
            }
        }
        super.onBackPressed();
        finish();
    }
}
