package com.xingsu.italker.push.activites;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.xingsu.italker.common.common.app.Activity;
import com.xingsu.italker.common.common.app.Fragment;
import com.xingsu.italker.push.R;
import com.xingsu.italker.push.frags.account.AccountTrigger;
import com.xingsu.italker.push.frags.account.RegisterFragment;

import net.qiujuer.genius.ui.compat.UiCompat;

import butterknife.BindView;

public class AccountActivity extends Activity implements AccountTrigger{
    private Fragment mCurFragment;
    private Fragment mLoginFragment;
    private Fragment mRegisterFragment;

    @BindView(R.id.im_bg)
    ImageView mBg;
    /**
     * 账户Activity显示的入口
     * @param context Context
     */
    public static void show(Context context){
        context.startActivity(new Intent(context, AccountActivity.class));
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_account;
    }

    @Override
    protected void initWidget() {
        super.initWidget();


        //初始化Fragment
        mCurFragment = new RegisterFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.lay_container,mCurFragment)
                .commit();

        //初始化背景
        Glide.with(this)
                .load(R.drawable.bg_src_tianjin)
                .centerCrop()       //居中剪切
                .into(new ViewTarget<ImageView,GlideDrawable>(mBg) {

                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {

                        //拿到glide的Drawable
                        Drawable drawable = resource.getCurrent();
                        //使用适配类进行包装
                        drawable = DrawableCompat.wrap(drawable);
                        drawable.setColorFilter(UiCompat.getColor(getResources(),R.color.colorAccent), PorterDuff.Mode.SCREEN);//设置着色的效果和颜色，蒙版模式
                        //设置imageView
                        this.view.setImageDrawable(drawable);
                    }
                });
    }

    @Override
    public void triggerView() {
        Fragment fragment;
        if(mCurFragment == mLoginFragment){
            if(mRegisterFragment == null){

                //默认情况下为null
                //第一次，之后就不是null了
                mRegisterFragment = new RegisterFragment();
            }
            fragment = mRegisterFragment;
        }else{
            //默认请求下已经赋值，无需判断
            fragment = mLoginFragment;
        }

        //重新赋值当前正在显示的Fragment
        mCurFragment = fragment;
        //切换显示
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.lay_container,fragment)
                .commit();
    }
}
