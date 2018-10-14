package com.welson.zhihudaily.view;

import android.content.Context;
import android.support.v4.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.welson.zhihudaily.R;

public class ZanActionProvider extends ActionProvider {
    private Context context;
    private TextView zanCount;

    public ZanActionProvider(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public View onCreateActionView() {
        int size = getContext().getResources().getDimensionPixelSize( android.support.design.R.dimen.abc_action_bar_default_height_material);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(size, size);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.article_zan_layout,null);
        view.setLayoutParams(layoutParams);
        zanCount = view.findViewById(R.id.zan_count_text);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
            }
        });

        return view;
    }

    public void setZanCount(String count){
        zanCount.setText(count);

    }
}
