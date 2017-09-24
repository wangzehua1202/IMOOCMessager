package com.xingsu.italker.push.frags.main;


import android.view.View;

import com.xingsu.italker.common.app.Fragment;
import com.xingsu.italker.common.widget.GalleryView;
import com.xingsu.italker.push.R;

import butterknife.BindView;

public class ActiveFragment extends Fragment {

    @BindView(R.id.galleryView)
    GalleryView mGalley;

    public ActiveFragment() {
        // Required empty public constructor
    }



    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_active;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
    }

    @Override
    protected void initData() {
        super.initData();

        mGalley.setup(getLoaderManager(), new GalleryView.SelectedChangeListener() {
            @Override
            public void onSelectedCountChanged(int count) {

            }
        });
    }
}
