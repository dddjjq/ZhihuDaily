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

import java.util.ArrayList;
import java.util.HashMap;

public class ArticleFragment extends BaseFragment implements ArticleContract.View{

    private static final String TAG = ArticleFragment.class.getSimpleName();
    private WebView articleContent;
    private ArticlePresenter presenter;
    private long id;
    private ArticleActivity activity;
    private int discussCount;
    private int zanCount;
    private int longCommentSize;
    private int shortCommentSize;
    private ArrayList<Long> idList;
    private HashMap<Integer,ArrayList<Long>> idMap;
    private int currentPosition;
    private long currentId;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_article;
    }

    @Override
    public void initView(View view) {
        articleContent = view.findViewById(R.id.article_content);
    }

    @Override
    public void initData() {
        activity = (ArticleActivity)getActivity();
        presenter = new ArticlePresenter(this);
        id = getArguments().getLong("articleId");
        idMap = (HashMap)getArguments().getSerializable("articleMap");
        for (int key : idMap.keySet()){
            currentPosition = key;
            break;
        }
        idList = idMap.get(currentPosition);
        currentId = idList.get(currentPosition);
        presenter.requestArticleData(id);
        //presenter.requestExtraData(id);
    }

    @Override
    public void showArticleSuccess(Article article) {
        Log.d(TAG,"showArticleSuccess");
        articleContent.loadDataWithBaseURL("file:///android_asset/", HtmlUtils.structHtml(article),
                "text/html", "UTF-8", null);
        presenter.requestExtraData(currentId);
    }

    @Override
    public void showExtrasSuccess(NewsExtras extras) {
        Log.d(TAG,"showExtrasSuccess");
        discussCount = extras.getComments();
        zanCount = extras.getPopularity();
        longCommentSize = extras.getLong_comments();
        shortCommentSize = extras.getShort_comments();
        activity.setMenuCount(discussCount,zanCount,longCommentSize,shortCommentSize);
        activity.onWindowFocusChanged(true);
    }

    @Override
    public void showZipDataSuccess(ArticleZipData articleZipData) {
        Log.d(TAG,"showZipDataSuccess");
        articleContent.loadDataWithBaseURL("file:///android_asset/", HtmlUtils.structHtml(articleZipData.getArticle()),
                "text/html", "UTF-8", null);
        discussCount = articleZipData.getExtras().getComments();
        Log.d("dingyl","discussCount is : " + discussCount);
        zanCount = articleZipData.getExtras().getPopularity();
        longCommentSize = articleZipData.getExtras().getLong_comments();
        shortCommentSize = articleZipData.getExtras().getShort_comments();
        activity.setMenuCount(discussCount,zanCount,longCommentSize,shortCommentSize);
        activity.onWindowFocusChanged(true);
    }

    @Override
    public void setPresenter(ArticleContract.Presenter presenter) {

    }

    public void requestExtraData(){
        if (presenter != null) {
            presenter.requestExtraData(id);
            Log.d(TAG,"currentId : " + currentId);
        }
        Log.d("dingyl","==2==");
    }
}
