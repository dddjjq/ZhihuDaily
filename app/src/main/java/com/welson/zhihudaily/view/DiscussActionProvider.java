package com.welson.zhihudaily.view;

import android.content.Context;
import android.support.v4.view.ActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.welson.zhihudaily.R;

public class DiscussActionProvider extends ActionProvider {

    private Context context;
    public TextView discussCount;

    public DiscussActionProvider(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public View onCreateActionView() {
        int size = getContext().getResources().getDimensionPixelSize(android.support.design.R.dimen.abc_action_bar_default_height_material);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(size, size);//获取布局参数
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.article_discuss_layout,null);
        view.setLayoutParams(layoutParams);//设置布局参数 否则设置layout_margin这些不起作用
        discussCount = view.findViewById(R.id.discuss_count_text);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
            }
        });
        return view;
    }

    public void setDiscussCount(String count){
        if (discussCount != null){
            discussCount.setText(count);
        }
    }
}
