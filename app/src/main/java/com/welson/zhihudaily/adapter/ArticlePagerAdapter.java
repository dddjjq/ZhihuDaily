package com.welson.zhihudaily.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.welson.zhihudaily.fragment.ArticleFragment;

import java.util.ArrayList;

public class ArticlePagerAdapter extends FragmentPagerAdapter {

    private ArrayList<ArticleFragment> fragmentArrayList;

    public ArticlePagerAdapter(FragmentManager fm,ArrayList<ArticleFragment> fragmentArrayList) {
        super(fm);
        this.fragmentArrayList = fragmentArrayList;
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentArrayList.get(i);
    }

}
