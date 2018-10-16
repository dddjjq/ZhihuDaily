package com.welson.zhihudaily.activity;

import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;

import com.welson.zhihudaily.R;
import com.welson.zhihudaily.contract.ArticleContract;
import com.welson.zhihudaily.data.Article;
import com.welson.zhihudaily.data.ArticleZipData;
import com.welson.zhihudaily.data.NewsExtras;
import com.welson.zhihudaily.presenter.ArticlePresenter;
import com.welson.zhihudaily.utils.HtmlUtils;
import com.welson.zhihudaily.view.DiscussActionProvider;
import com.welson.zhihudaily.view.ZanActionProvider;

public class ArticleActivity extends AppCompatActivity implements ArticleContract.View{

    private Toolbar toolbar;
    private WebView articleContent;
    private ArticlePresenter presenter;
    private long id;
    private DiscussActionProvider discussActionProvider;
    private ZanActionProvider zanActionProvider;
    private int discussCount = 0;
    private int zanCount = 0;
    
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
        articleContent = findViewById(R.id.article_content);
        toolbar = findViewById(R.id.article_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void initData(){
        id = getIntent().getLongExtra("articleId",0);
        presenter = new ArticlePresenter(this);
        presenter.requestArticleData(id);
        //presenter.requestExtraData(id);
    }

    @Override
    public void showArticleSuccess(Article article) {
        articleContent.loadDataWithBaseURL("file:///android_asset/", HtmlUtils.structHtml(article),
                "text/html", "UTF-8", null);
    }

    @Override
    public void showExtrasSuccess(NewsExtras extras) {
        discussCount = extras.getComments();
        zanCount = extras.getPopularity();
        invalidateOptionsMenu();
    }

    @Override
    public void showZipDataSuccess(ArticleZipData articleZipData) {
        articleContent.loadDataWithBaseURL("file:///android_asset/", HtmlUtils.structHtml(articleZipData.getArticle()),
                "text/html", "UTF-8", null);
        discussCount = articleZipData.getExtras().getComments();
        zanCount = articleZipData.getExtras().getPopularity();
        invalidateOptionsMenu();
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
        Log.d("dingyl","onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.article_toolbar_menu, menu);
        MenuItem item1 = menu.findItem(R.id.article_discuss);
        MenuItem item2 = menu.findItem(R.id.article_zan);
        discussActionProvider = (DiscussActionProvider) MenuItemCompat.getActionProvider(item1);
        zanActionProvider = (ZanActionProvider) MenuItemCompat.getActionProvider(item2);
        return super.onCreateOptionsMenu(menu);
    }


    /*@Override
    public boolean onPrepareOptionsMenu(Menu menu){
        Log.d("dingyl","onPrepareOptionsMenu");
        if (discussCount > 0 && discussActionProvider.discussCount != null){
            Log.d("dingyl","==2==");
            discussActionProvider.setDiscussCount(discussCount+"");
        }
        if (zanCount > 0 && zanActionProvider.zanCount != null){
            zanActionProvider.setZanCount(zanCount+"");
        }
        return super.onPrepareOptionsMenu(menu);
    }*/

    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        Log.d("dingyl","onWindowFocusChanged");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (discussCount > 0 && discussActionProvider.discussCount != null){
                    discussActionProvider.setDiscussCount(discussCount+"");
                }
                if (zanCount > 0 && zanActionProvider.zanCount != null){
                    zanActionProvider.setZanCount(zanCount+"");
                }
            }
        },800);
    }
}
