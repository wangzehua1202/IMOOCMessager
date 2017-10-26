package com.xingsu.italker.push.frags.message;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xingsu.italker.common.common.app.Fragment;
import com.xingsu.italker.common.common.widget.PortaitView;
import com.xingsu.italker.common.common.widget.adapter.TextWatcherAdapter;
import com.xingsu.italker.common.common.widget.recycler.RecyclerAdapter;
import com.xingsu.italker.factory.model.db.Message;
import com.xingsu.italker.factory.model.db.User;
import com.xingsu.italker.factory.persistence.Account;
import com.xingsu.italker.push.R;
import com.xingsu.italker.push.activites.MessageActivity;

import net.qiujuer.genius.ui.compat.UiCompat;
import net.qiujuer.genius.ui.widget.Loading;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class ChatFragment extends Fragment
    implements AppBarLayout.OnOffsetChangedListener{

    protected String mReceiverId;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.appbar)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.edit_content)
    EditText mContent;

    @BindView(R.id.btn_submit)
    View mSubmit;

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
        initEditContent();

        //RecyclerView 基本设置
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    //初始化Toolbar
    protected void initToolbar(){
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

    //初始化输入框监听
    private void initEditContent(){
        mContent.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                String content = s.toString().trim();
                boolean needSendMsg = !TextUtils.isEmpty(content);
                //设置状态，改变对应的Icon
                mSubmit.setActivated(needSendMsg);
            }
        });
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
    }

    @OnClick(R.id.btn_face)
    void onFaceClick(){
        //TODO
    }

    @OnClick(R.id.btn_record)
    void onRecordClick(){
        //TODO

    }

    @OnClick(R.id.btn_submit)
    void onSubmitClick(){

        if(mSubmit.isActivated()){
            //发送
        }else{
            onMoreClick();
        }
    }

    private void onMoreClick(){
        //TODO
    }

    //内容的适配器
    private class Adapter extends RecyclerAdapter<Message>{

        @Override
        protected int getItemViewType(int position, Message message) {
            //发送的在右边，收到的在左边
            boolean isRight = Objects.equals(message.getSender().getId(), Account.getUserId());

            switch (message.getType()){
                //文字内容
                case Message.TYPE_STR:
                    return isRight ? R.layout.cell_chat_text_right : R.layout.cell_chat_text_left;
            }

            return 0;
        }

        @Override
        protected ViewHolder<Message> onCreateViewHolder(View root, int viewType) {
            switch (viewType){
                //左右都是同一个
                case R.layout.cell_chat_text_right:
                case R.layout.cell_chat_text_left:
                    return new TextHolder(root);

                //默认情况返回的Text类型的Holder处理
                default:
                    new TextHolder(root);
            }
        }
    }

    //Holder的基类
    class BaseHolder extends RecyclerAdapter.ViewHolder<Message>{
        @BindView(R.id.im_portrait)
        PortaitView mPortait;

        //允许为空，左边没有，右边有
        @Nullable
        @BindView(R.id.loading)
        Loading mLoading;

        public BaseHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Message message) {
            User sender = message.getSender();
            //进行数据加载
            sender.load();
            //进行头像加载
            mPortait.setup(Glide.with(ChatFragment.this), sender);

            if(mLoading != null){
                //当前布局在右边
                int status = message.getStatus();
                if(status == Message.STATUS_DONE) {
                    //正常状态,隐藏Loading
                    mLoading.stop();
                    mLoading.setVisibility(View.GONE);
                }
                else if(status == Message.STATUS_CREATED){
                    //正在发送中的状态
                    mLoading.setVisibility(View.VISIBLE);
                    mLoading.setProgress(0);
                    mLoading.setForegroundColor(UiCompat.getColor(getResources(), R.color.colorAccent));
                    mLoading.start();
                }
                else if(status == Message.STATUS_FAILED){
                    //发送失败状态,允许重新发送
                    mLoading.setVisibility(View.VISIBLE);
                    mLoading.stop();
                    mLoading.setProgress(1);
                    mLoading.setForegroundColor(UiCompat.getColor(getResources(), R.color.alertImportant));
                }

                //当状态是错误状态时候允许点击
                mPortait.setEnabled(status == Message.STATUS_FAILED);
            }
        }


        @OnClick(R.id.im_portrait)
        void onRePushClick(){
            //重新发送
            if(mLoading != null){
                //TODO 重新发送
            }
        }



    }

    //文字的Holder
    class TextHolder extends BaseHolder{

        @BindView(R.id.txt_content)
        TextView mContent;

        public TextHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Message message) {
            super.onBind(message);

            //把内容设置到布局上
            mContent.setText(message.getContent());
        }
    }

}
