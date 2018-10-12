package com.welson.zhihudaily.fragment;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.welson.zhihudaily.R;
import com.welson.zhihudaily.activity.MainActivity;
import com.welson.zhihudaily.adapter.HomeRecyclerAdapter;
import com.welson.zhihudaily.contract.HomeContract;
import com.welson.zhihudaily.data.NewsBefore;
import com.welson.zhihudaily.data.NewsLatest;
import com.welson.zhihudaily.data.NewsStory;
import com.welson.zhihudaily.data.NewsTopStory;
import com.welson.zhihudaily.presenter.HomePresenter;
import com.welson.zhihudaily.utils.BannerLoader;
import com.welson.zhihudaily.utils.DateUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;


public class HomeFragment extends BaseFragment implements HomeContract.View{

    private static final String TAG = HomeFragment.class.getSimpleName();
    private SwipeRefreshLayout swipeRefreshLayout;
    private Banner homeBanner;
    private View bannerView;
    private RecyclerView homeRecycler;
    private HomePresenter presenter;
    private ArrayList<String> bannerUrlList;
    private ArrayList<String> bannerTitleList;
    private ArrayList<NewsStory> newsStories;
    private ArrayList<NewsTopStory> newsTopStories;
    private HomeRecyclerAdapter adapter;
    private String currentDate = "";
    private MainActivity activity;

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
        homeRecycler = view.findViewById(R.id.home_recycler);
        bannerView = getLayoutInflater().inflate(R.layout.banner_layout,null);
        homeBanner = bannerView.findViewById(R.id.home_banner);
        presenter = new HomePresenter(this);
    }

    @Override
    public void initData() {
        activity = (MainActivity)getActivity();
        bannerUrlList = new ArrayList<>();
        bannerTitleList = new ArrayList<>();
        newsStories = new ArrayList<>();
        presenter.requestData();
        adapter = new HomeRecyclerAdapter(getContext(),newsStories);
        adapter.setHeaderView(bannerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        homeRecycler.setLayoutManager(layoutManager);
        homeRecycler.setAdapter(adapter);
        initListener();
    }

    @Override
    public void showBannerSuccess(NewsLatest newsLatest) {
        newsTopStories = newsLatest.getTop_stories();
        bannerUrlList.clear();
        bannerTitleList.clear();
        //Log.d(TAG,"newsTopStories.size is : " + newsTopStories.size());
        for (NewsTopStory nts:newsTopStories){
            bannerUrlList.add(nts.getImage());
            bannerTitleList.add(nts.getTitle());
        }
        startBanner();
    }

    @Override
    public void showMainDataSuccess(NewsLatest newsLatest) {
        if (swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
        currentDate = newsLatest.getDate();
        for (NewsStory ns : newsLatest.getStories()){
            ns.setDate(currentDate);
            //Log.d("dingyl","ns " + ns.getTitle() + " " + ns.getDate());
        }
        newsStories.clear();
        newsStories.addAll(newsLatest.getStories());
        adapter.notifyDataSetChanged();
        //Log.d(TAG,"newsLatest.getStories() is : " + newsLatest.getStories().get(0).getTitle());
    }

    @Override
    public void showMoreDataSuccess(NewsBefore newsBefore) {
        currentDate = newsBefore.getDate();
        for (NewsStory ns : newsBefore.getStories()){
            ns.setDate(currentDate);
        }
        newsStories.addAll(newsBefore.getStories());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        presenter.detachView();
    }

    private void initListener(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.requestData();
            }
        });
        homeRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState)
            {
                if (canLoadMore()){
                    presenter.requestDataWithDate(currentDate);
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager)homeRecycler.getLayoutManager();
                int nowPosition = linearLayoutManager.findFirstVisibleItemPosition();
                if (adapter.getItemViewType(nowPosition) == HomeRecyclerAdapter.TYPE_GROUP){
                    if (nowPosition == 1){
                        activity.toolbar.setTitle(getString(R.string.home_time_default_str));
                    }else {
                        activity.toolbar.setTitle(DateUtil.getCurrentStringStr(newsStories.get(nowPosition).getDate()));
                    }
                }else if (adapter.getItemViewType(nowPosition) == HomeRecyclerAdapter.TYPE_BANNER){
                    activity.toolbar.setTitle(getString(R.string.home_toolbar_title));
                }
            }
        });
    }

    private void startBanner(){
        homeBanner.setImageLoader(new BannerLoader());
        homeBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        homeBanner.setBannerAnimation(Transformer.Default);
        homeBanner.setImages(bannerUrlList);
        homeBanner.setBannerTitles(bannerTitleList);
        homeBanner.isAutoPlay(true);
        homeBanner.setDelayTime(3000);
        homeBanner.setIndicatorGravity(BannerConfig.CENTER);
        homeBanner.start();
    }

    private boolean canLoadMore(){
        LinearLayoutManager layoutManager = (LinearLayoutManager) homeRecycler.getLayoutManager();
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int state = homeRecycler.getScrollState();
       /* Log.d(TAG,"lastVisibleItemPosition : " + lastVisibleItemPosition);
        Log.d(TAG,"visibleItemCount : " + visibleItemCount);
        Log.d(TAG,"totalItemCount : " + totalItemCount);
        Log.d(TAG,"state : " + state);*/
        if (visibleItemCount > 0 && (lastVisibleItemPosition == totalItemCount - 1) && state == homeRecycler.SCROLL_STATE_IDLE){
            return true;
        }
        return false;
    }
}
