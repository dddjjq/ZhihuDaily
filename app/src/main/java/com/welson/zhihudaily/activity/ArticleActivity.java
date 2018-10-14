package com.welson.zhihudaily.activity;

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
import com.welson.zhihudaily.data.NewsExtras;
import com.welson.zhihudaily.presenter.ArticlePresenter;
import com.welson.zhihudaily.utils.HtmlUtils;
import com.welson.zhihudaily.view.DiscussActionProvider;
import com.welson.zhihudaily.view.ZanActionProvider;

public class ArticleActivity extends AppCompatActivity implements ArticleContract.View{

    private Toolbar toolbar;
    private WebView articleContent;
    //private ImageView articleHeaderImage;
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
        initData();
    }

    @Override
    public void onResume(){
        super.onResume();
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
        presenter.requestExtraData(id);
    }

    @Override
    public void showArticleSuccess(Article article) {
        Log.d("dingyl","success");
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
        getMenuInflater().inflate(R.menu.article_toolbar_menu, menu);
        MenuItem item1 = menu.findItem(R.id.article_discuss);
        MenuItem item2 = menu.findItem(R.id.article_zan);
        discussActionProvider = (DiscussActionProvider) MenuItemCompat.getActionProvider(item1);
        zanActionProvider = (ZanActionProvider) MenuItemCompat.getActionProvider(item2);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        if (discussCount > 0 && discussActionProvider != null){
            discussActionProvider.setDiscussCount(discussCount+"");
        }
        if (zanCount > 0 && zanActionProvider != null){
            zanActionProvider.setZanCount(zanCount+"");
        }
    }
}
