package com.welson.zhihudaily.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;

import com.welson.zhihudaily.R;
import com.welson.zhihudaily.adapter.ArticlePagerAdapter;
import com.welson.zhihudaily.contract.ArticleContract;
import com.welson.zhihudaily.data.Article;
import com.welson.zhihudaily.data.ArticleZipData;
import com.welson.zhihudaily.data.NewsExtras;
import com.welson.zhihudaily.fragment.ArticleFragment;
import com.welson.zhihudaily.presenter.ArticlePresenter;
import com.welson.zhihudaily.utils.HtmlUtils;
import com.welson.zhihudaily.view.DiscussActionProvider;
import com.welson.zhihudaily.view.ZanActionProvider;

import java.util.ArrayList;
import java.util.HashMap;

public class ArticleActivity extends AppCompatActivity implements ArticleContract.View,DiscussActionProvider.CommentClickListener{

    private static final String TAG = "ArticleActivity-TAG";
    private Toolbar toolbar;
    private ViewPager viewPager;
    private ArticlePresenter presenter;
    private long currentId;
    private int currentPosition;
    private DiscussActionProvider discussActionProvider;
    private ZanActionProvider zanActionProvider;
    private int discussCount = 0;
    private int zanCount = 0;
    private ArrayList<Long> idList;
    private HashMap<Integer,ArrayList<Long>> idMap;
    private ArrayList<ArticleFragment> fragmentArrayList;
    private ArticlePagerAdapter adapter;
    private int longCommentSize;
    private int shortCommentSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        initView();
    }

    @Override
    public void onResume(){
        super.onResume();
        initData();
    }

    private void initView(){
        viewPager = findViewById(R.id.article_viewpager);
        toolbar = findViewById(R.id.article_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void initData(){
        fragmentArrayList = new ArrayList<>();
        idMap = (HashMap) getIntent().getSerializableExtra("articleMapId");
        for (int key : idMap.keySet()){
            currentPosition = key;
            break;
        }
        Log.d(TAG,"currentPosition is : " + currentPosition);
        idList = idMap.get(currentPosition);
        currentId = idList.get(currentPosition);
        for (int i = 0 ;i< idList.size();i++){
            ArticleFragment fragment = new ArticleFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("articleMap",idMap);
            bundle.putLong("articleId",idList.get(i));
            fragment.setArguments(bundle);
            fragmentArrayList.add(fragment);
        }
        Log.d(TAG,"idList.size() : " + idList.size());
        adapter = new ArticlePagerAdapter(getSupportFragmentManager(),fragmentArrayList);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(currentPosition);
        viewPager.setOffscreenPageLimit(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                Log.d("dingyl","onPageSelected : " + i);
                ((ArticleFragment)adapter.getItem(i)).requestExtraData();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        /*presenter = new ArticlePresenter(this);
        presenter.requestArticleData(id);
        presenter.requestExtraData(id);*/
    }

    @Override
    public void showArticleSuccess(Article article) {
        /*articleContent.loadDataWithBaseURL("file:///android_asset/", HtmlUtils.structHtml(article),
                "text/html", "UTF-8", null);*/
    }

    @Override
    public void showExtrasSuccess(NewsExtras extras) {
        discussCount = extras.getComments();
        zanCount = extras.getPopularity();
        invalidateOptionsMenu();
    }

    @Override
    public void showZipDataSuccess(ArticleZipData articleZipData) {
        /*articleContent.loadDataWithBaseURL("file:///android_asset/", HtmlUtils.structHtml(articleZipData.getArticle()),
                "text/html", "UTF-8", null);
        discussCount = articleZipData.getExtras().getComments();
        zanCount = articleZipData.getExtras().getPopularity();
        invalidateOptionsMenu();*/
    }

    @Override
    public void setPresenter(ArticleContract.Presenter presenter) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Log.d("dingyl","onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.article_toolbar_menu, menu);
        MenuItem item1 = menu.findItem(R.id.article_discuss);
        MenuItem item2 = menu.findItem(R.id.article_zan);
        discussActionProvider = (DiscussActionProvider) MenuItemCompat.getActionProvider(item1);
        zanActionProvider = (ZanActionProvider) MenuItemCompat.getActionProvider(item2);
        discussActionProvider.setCommentListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        //Log.d("dingyl","onWindowFocusChanged");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (discussCount > 0 && discussActionProvider.discussCount != null){
                    discussActionProvider.setDiscussCount(getDiscussString(discussCount));
                }
                if (zanCount > 0 && zanActionProvider.zanCount != null){
                    zanActionProvider.setZanCount(getZanString(zanCount));
                }
            }
        },0);
    }

    public void setMenuCount(int discussCount,int zanCount,int longCommentSize,int shortCommentSize){
        this.discussCount = discussCount;
        this.zanCount = zanCount;
        this.longCommentSize = longCommentSize;
        this.shortCommentSize = shortCommentSize;
    }

    private String getZanString(int zanCount){
        if (zanCount < 1000){
            return zanCount + "";
        }else {
            return String.format("%.1f",zanCount*1.0/1000)+"k";
        }
    }

    private String getDiscussString(int discussCount){
        if (discussCount < 1000){
            return discussCount + "";
        }else {
            return String.format("%.1f",discussCount*1.0/1000)+"k";
        }
    }

    @Override
    public void onCommentClick() {
        Intent intent = new Intent(this,CommentActivity.class);
        intent.putExtra("commentId",currentId);
        intent.putExtra("longCommentSize",longCommentSize);
        intent.putExtra("shortCommentSize",shortCommentSize);
        startActivity(intent);
    }
}
