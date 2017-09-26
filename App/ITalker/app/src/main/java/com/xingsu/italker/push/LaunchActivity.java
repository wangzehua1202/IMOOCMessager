package com.xingsu.italker.push;


import com.xingsu.italker.common.common.app.Activity;
import com.xingsu.italker.push.activites.MainActivity;
import com.xingsu.italker.push.frags.assist.PermissionFragment;

public class LaunchActivity extends Activity {

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_launch;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(PermissionFragment.haveAll(this,getSupportFragmentManager())){
            MainActivity.show(this);
            finish();
        }
    }
}
