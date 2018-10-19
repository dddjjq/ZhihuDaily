package com.welson.zhihudaily.activity;

import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.welson.zhihudaily.R;
import com.welson.zhihudaily.adapter.CommentRecyclerAdapter;
import com.welson.zhihudaily.contract.CommentContract;
import com.welson.zhihudaily.data.CommentData;
import com.welson.zhihudaily.data.CommentDataBean;
import com.welson.zhihudaily.data.CommentZipData;
import com.welson.zhihudaily.presenter.CommentPresenter;

import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity implements CommentContract.View,CommentRecyclerAdapter.HeaderClickListener{

    private RecyclerView commentRecycler;
    private Toolbar toolbar;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    private CommentRecyclerAdapter adapter;
    private ArrayList<CommentDataBean> longComments;
    private ArrayList<CommentDataBean> shortComments;
    public CommentPresenter presenter;
    private long id;
    private int longCommentSize;
    private int shortCommentSize ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        initView();
        initData();
    }

    private void initView(){
        commentRecycler = findViewById(R.id.comment_recycler);
        toolbar = findViewById(R.id.comment_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void initData(){
        id = getIntent().getLongExtra("commentId",0);
        longCommentSize = getIntent().getIntExtra("longCommentSize",0);
        shortCommentSize = getIntent().getIntExtra("shortCommentSize",0);
        longComments = new ArrayList<>();
        shortComments = new ArrayList<>();
        builder = new AlertDialog.Builder(this);
        builder.setMessage("努力加载中...");
        dialog = builder.create();
        adapter = new CommentRecyclerAdapter(this,longComments,shortComments,longCommentSize,shortCommentSize,this);
        commentRecycler.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        commentRecycler.setLayoutManager(layoutManager);
        commentRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        presenter = new CommentPresenter(this);
        presenter.requestLongCommentData(id);
        //presenter.requestShortCommentData(id);
    }

    @Override
    public void showLongCommentDataSuccess(CommentData commentData) {
        longComments.addAll(commentData.getComments());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showShortCommentDataSuccess(CommentData commentData) {
        shortComments.clear();
        shortComments.addAll(commentData.getComments());
        adapter.notifyDataSetChanged();
        //showDialog(false);
    }

    @Override
    public void showZipCommentDataSuccess(CommentZipData zipData) {
        /*longComments.addAll(zipData.getCommentData().getComments());
        longCommentSize = zipData.getNewsExtras().getLong_comments();
        shortCommentSize = zipData.getNewsExtras().getShort_comments();
        adapter.notifyDataSetChanged();*/
    }

    @Override
    public void setPresenter(CommentContract.Presenter presenter) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void showDialog(boolean b){
        if (b){
            dialog.show();
        }else {
            if (dialog != null){
                dialog.dismiss();
            }
        }
    }

    @Override
    public void onHeadClick(boolean b) {
        if (b){
            presenter.requestShortCommentData(id);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    commentRecycler.scrollToPosition(2);
                }
            },2000);
        }else {
            commentRecycler.smoothScrollToPosition(0);
        }
    }
}
