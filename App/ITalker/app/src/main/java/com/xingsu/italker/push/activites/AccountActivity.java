package com.xingsu.italker.push.activites;

import android.content.Context;
import android.content.Intent;

import com.xingsu.italker.common.app.Activity;
import com.xingsu.italker.push.R;
import com.xingsu.italker.push.frags.account.UpdateInfoFragment;

public class AccountActivity extends Activity {

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
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.lay_container,new UpdateInfoFragment())
                .commit();
    }
}
