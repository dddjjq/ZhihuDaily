package com.welson.zhihudaily.fragment;

import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.welson.zhihudaily.R;
import com.welson.zhihudaily.activity.ArticleActivity;
import com.welson.zhihudaily.contract.ArticleContract;
import com.welson.zhihudaily.data.Article;
import com.welson.zhihudaily.data.ArticleZipData;
import com.welson.zhihudaily.data.NewsExtras;
import com.welson.zhihudaily.presenter.ArticlePresenter;
import com.welson.zhihudaily.utils.HtmlUtils;

public class ArticleFragment extends BaseFragment implements ArticleContract.View{

    private static final String TAG = ArticleFragment.class.getSimpleName();
    private WebView articleContent;
    private ArticlePresenter presenter;
    private long id;
    private ArticleActivity activity;
    private int discussCount;
    private int zanCount;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_article;
    }

    @Override
    public void initView(View view) {
        Log.d(TAG,"initView");
        articleContent = view.findViewById(R.id.article_content);
    }

    @Override
    public void initData() {
        activity = (ArticleActivity)getActivity();
        presenter = new ArticlePresenter(this);
        id = getArguments().getLong("articleId");
        Log.d(TAG,"id : " + id);
        presenter.requestArticleData(id); //将数据整合
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
        activity.setMenuCount(discussCount,zanCount);
        activity.onWindowFocusChanged(true);
    }

    @Override
    public void showZipDataSuccess(ArticleZipData articleZipData) {
        Log.d(TAG,"showZipDataSuccess");
        articleContent.loadDataWithBaseURL("file:///android_asset/", HtmlUtils.structHtml(articleZipData.getArticle()),
                "text/html", "UTF-8", null);
        discussCount = articleZipData.getExtras().getComments();
        zanCount = articleZipData.getExtras().getPopularity();
        activity.setMenuCount(discussCount,zanCount);
        activity.onWindowFocusChanged(true);
    }

    @Override
    public void setPresenter(ArticleContract.Presenter presenter) {

    }
}
