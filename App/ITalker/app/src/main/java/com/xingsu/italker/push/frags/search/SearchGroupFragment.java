package com.xingsu.italker.push.frags.search;


import com.xingsu.italker.common.common.app.Fragment;
import com.xingsu.italker.push.R;
import com.xingsu.italker.push.activites.SearchActivity;

/**
 * 搜索群界面的实现
 */
public class SearchGroupFragment extends Fragment implements SearchActivity.SearchFragment{


    public SearchGroupFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_search_group;
    }

    @Override
    public void search(String content) {

    }
}
