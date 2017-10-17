package com.xingsu.italker.common.common.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.bumptech.glide.RequestManager;
import com.xingsu.italker.common.R;
import com.xingsu.italker.common.factory.model.Author;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 头像控件
 * Created by 王泽华 on 2017/9/9.
 */

public class PortaitView extends CircleImageView{

    public PortaitView(Context context) {
        super(context);
    }

    public PortaitView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PortaitView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setup(RequestManager manager, Author author){
        if(author == null)
            return;
        //进行显示
        setup(manager, author.getPortrait());
    }

    public void setup(RequestManager manager, String url){
        setup(manager, R.drawable.default_portrait, url);
    }

    public void setup(RequestManager manager, int resourceId, String url){
        if(url == null)
            url = "";
        manager.load(url)
                .placeholder(resourceId)
                .dontAnimate()  //CircleImageView 控件中不能使用渐变动画，会导致显示延迟
                .into(this);
    }
}
