package com.xingsu.italker.push.frags.account;

import com.xingsu.italker.common.app.Fragment;
import com.xingsu.italker.common.widget.PortaitView;
import com.xingsu.italker.push.R;
import com.xingsu.italker.push.frags.media.GalleryFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 用户更新信息的界面
 */
public class UpdateInfoFragment extends Fragment {
    @BindView(R.id.im_portrait)
    PortaitView mPortrait;


    public UpdateInfoFragment() {
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_update_info;
    }

    @OnClick(R.id.im_portrait)
    void onPortraitClick(){
        new GalleryFragment()
                .setListener(new GalleryFragment.OnSelectedListener() {
                    @Override
                    public void onSelectedImage(String path) {

                    }
                })
                // show 的时候建议使用getChildFragmentManager，
                // tag GalleryFragment class的名字
                .show(getChildFragmentManager(),GalleryFragment.class.getName());
    }
}
