package com.welson.zhihudaily.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.welson.zhihudaily.R;
import com.welson.zhihudaily.adapter.CommonRecyclerAdapter;
import com.welson.zhihudaily.contract.CommonContract;
import com.welson.zhihudaily.data.ContentStory;
import com.welson.zhihudaily.data.NewsStory;
import com.welson.zhihudaily.data.ThemeContent;
import com.welson.zhihudaily.presenter.CommonPresenter;
import com.welson.zhihudaily.utils.GlideUtil;

import java.util.ArrayList;

public class CommonFragment extends BaseFragment implements CommonContract.View{

    private ImageView headerImage,edtitorImage;
    private TextView headerTitle;
    private View commonHeaderView;
    private RecyclerView commonRecycler;
    private CommonRecyclerAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private ThemeContent themeContent;
    private ArrayList<NewsStory> newsStories;
    private CommonPresenter presenter;
    private int id;
    private boolean hasId = false;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_common;
    }

    @Override
    public void initView(View view) {
        commonRecycler = view.findViewById(R.id.common_recycler);
        refreshLayout = view.findViewById(R.id.common_refresh_layout);
        commonHeaderView = getLayoutInflater().inflate(R.layout.common_header,null);
        headerImage = commonHeaderView.findViewById(R.id.common_header_image);
        headerTitle = commonHeaderView.findViewById(R.id.common_header_title);
        edtitorImage = commonHeaderView.findViewById(R.id.editor_image);
    }

    @Override
    public void initData() {
        presenter = new CommonPresenter(this);
        newsStories = new ArrayList<>();
        initListener();
    }

    public void setId(int id){
        this.id = id;
        hasId = true;
        presenter.requestContentData(id);
    }

    private void initListener(){
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (hasId){
                    presenter.requestContentData(id);
                }else {
                    refreshLayout.setRefreshing(false);
                }
            }
        });
        adapter = new CommonRecyclerAdapter(getContext(),newsStories);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        commonRecycler.setLayoutManager(layoutManager);
        commonRecycler.setAdapter(adapter);
        adapter.setHeaderView(commonHeaderView);
    }

    @Override
    public void showContentSuccess(ThemeContent themeContent) {
        if (refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }
        this.themeContent = themeContent;
        GlideUtil.loadImage(getContext(),themeContent.getImage(),headerImage);
        headerTitle.setText(themeContent.getDescription());
        newsStories.clear();
        newsStories.addAll(themeContent.getStories());
        adapter.notifyDataSetChanged();
        commonRecycler.scrollToPosition(0);
    }

    @Override
    public void setPresenter(CommonContract.Presenter presenter) {

    }
}
