package com.welson.zhihudaily.fragment;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.welson.zhihudaily.R;
import com.welson.zhihudaily.contract.HomeContract;
import com.welson.zhihudaily.data.NewsLatest;
import com.welson.zhihudaily.data.NewsStory;
import com.welson.zhihudaily.data.NewsTopStory;
import com.welson.zhihudaily.presenter.HomePresenter;
import com.welson.zhihudaily.utils.BannerLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;

public class HomeFragment extends BaseFragment implements HomeContract.View{

    private static final String TAG = HomeFragment.class.getSimpleName();
    private SwipeRefreshLayout swipeRefreshLayout;
    private Banner homeBanner;
    private RecyclerView homeRecycler;
    private HomePresenter presenter;
    private ArrayList<String> bannerUrlList;
    private ArrayList<String> bannerTitleList;
    private ArrayList<NewsStory> newsStories;
    private ArrayList<NewsTopStory> newsTopStories;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(View view) {
        swipeRefreshLayout = view.findViewById(R.id.home_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE,
                Color.RED, Color.BLUE);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        homeBanner = view.findViewById(R.id.home_banner);
        homeRecycler = view.findViewById(R.id.home_recycler);
        presenter = new HomePresenter(this);
    }

    @Override
    public void initData() {
        bannerUrlList = new ArrayList<>();
        bannerTitleList = new ArrayList<>();
        presenter.requestData();
    }

    @Override
    public void showBannerSuccess(NewsLatest newsLatest) {
        newsTopStories = newsLatest.getTop_stories();
        bannerUrlList.clear();
        bannerTitleList.clear();
        Log.d(TAG,"newsTopStories.size is : " + newsTopStories.size());
        for (NewsTopStory nts:newsTopStories){
            bannerUrlList.add(nts.getImage());
            bannerTitleList.add(nts.getTitle());
            Log.d("dingyl","nts.getImages() is : " + nts.getImage());
        }
        startBanner();
    }

    @Override
    public void showMainDataSuccess(NewsLatest newsLatest) {

    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {

    }

    private void startBanner(){
        homeBanner.setImageLoader(new BannerLoader());
        homeBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        homeBanner.setBannerAnimation(Transformer.Default);
        homeBanner.setImages(bannerUrlList);
        homeBanner.setBannerTitles(bannerTitleList);
        homeBanner.isAutoPlay(true);
        homeBanner.setDelayTime(3000);
        homeBanner.setIndicatorGravity(Gravity.CENTER);
        homeBanner.start();
    }
}
