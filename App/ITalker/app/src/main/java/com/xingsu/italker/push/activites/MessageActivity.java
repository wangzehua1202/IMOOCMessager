package com.xingsu.italker.push.activites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.xingsu.italker.common.common.app.Activity;
import com.xingsu.italker.common.common.app.Fragment;
import com.xingsu.italker.common.factory.model.Author;
import com.xingsu.italker.factory.model.db.Group;
import com.xingsu.italker.push.R;
import com.xingsu.italker.push.frags.message.ChatGroupFragment;
import com.xingsu.italker.push.frags.message.ChatUserFragment;

public class MessageActivity extends Activity {

    //接收者Id，可以是群，也可以是人的id
    public static final String KEY_RECEIVER_ID = "KEY_RECEIVER_ID";
    //是否是群
    private static final String KEY_RECEIVER_IS_GROUP = "KEY_RECEIVER_IS_GROUP";

    private String mReceiverId;
    private boolean mIsGroup;
    /**
     * 显示人的聊天记录
     * @param context 上下文
     * @param author 人的信息
     */
    public static void show(Context context, Author author){
        if(author == null || context == null || TextUtils.isEmpty(author.getId()))
            return;
        Intent intent = new Intent(context, MessageActivity.class);
        intent.putExtra(KEY_RECEIVER_ID, author.getId());
        intent.putExtra(KEY_RECEIVER_IS_GROUP, false);
        context.startActivity(intent);
    }


    /**
     * 发起群聊天
     * @param context
     * @param group 群的model
     */
    public static void show(Context context, Group group){
        if(group == null || context == null || TextUtils.isEmpty(group.getId()))
            return;
        Intent intent = new Intent(context, MessageActivity.class);
        intent.putExtra(KEY_RECEIVER_ID, group.getId());
        intent.putExtra(KEY_RECEIVER_IS_GROUP, true);
        context.startActivity(intent);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        mReceiverId = bundle.getString(KEY_RECEIVER_ID);
        mIsGroup = bundle.getBoolean(KEY_RECEIVER_IS_GROUP);
        return !TextUtils.isEmpty(mReceiverId);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitle("");
        Fragment fragment;
        if(mIsGroup)
            fragment = new ChatGroupFragment();
        else
            fragment = new ChatUserFragment();

        //从Activity传递参数到fragment
        Bundle bundle = new Bundle();
        bundle.putString(KEY_RECEIVER_ID, mReceiverId);
        fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.lay_container, fragment)
                .commit();

    }
}
