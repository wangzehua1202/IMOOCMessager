package com.xingsu.italker.push.frags.message;


import android.os.Bundle;

import com.xingsu.italker.common.common.app.Fragment;
import com.xingsu.italker.push.activites.MessageActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class ChatFragment extends Fragment {

    private String mReceiverId;

    @Override
    protected void initArgs(Bundle bundle) {
        super.initArgs(bundle);
        mReceiverId = bundle.getString(MessageActivity.KEY_RECEIVER_ID);
    }
}
