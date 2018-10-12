package com.welson.zhihudaily.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.welson.zhihudaily.R;
import com.welson.zhihudaily.data.ThemeData;

import java.util.ArrayList;

public class NavigationListAdapter extends BaseAdapter {

    private ArrayList<ThemeData> themeDatas;
    private Context context;
    private static final int TYPE_HOME = 0;
    private static final int TYPE_NORMAL = 1;

    public NavigationListAdapter(Context context,ArrayList<ThemeData> themeDatas){
        this.context = context;
        this.themeDatas = themeDatas;
    }

    @Override
    public int getCount() {
        return themeDatas.size() + 1;//注意 这里如果添加了头部 count要加1，否则最后一项不显示
    }

    @Override
    public Object getItem(int position) {
        return themeDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        switch (getItemViewType(position)){
            case TYPE_HOME:
                ViewHolder headerViewHolder;
                if (convertView == null){
                    convertView = LayoutInflater.from(context).inflate(R.layout.navigation_item_home,parent,false);
                    headerViewHolder = new ViewHolder();
                    convertView.setTag(headerViewHolder);
                }else {
                    headerViewHolder = (ViewHolder) convertView.getTag();
                }
                break;
            case TYPE_NORMAL:
                final int realPosition = getRealPosition(position);
                ViewHolder viewHolder;
                if (convertView == null){
                    convertView = LayoutInflater.from(context).inflate(R.layout.navigation_item,parent,false);
                    viewHolder = new ViewHolder();
                    viewHolder.navigationItemText = convertView.findViewById(R.id.navigation_item_text);
                    viewHolder.navigationItemImage = convertView.findViewById(R.id.navigation_item_image);
                    convertView.setTag(viewHolder);
                }else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                viewHolder.navigationItemText.setText(themeDatas.get(realPosition).getName());
                break;
        }
        return convertView;
    }

    @Override
    public int getItemViewType(int position){
        if (position == 0){
            return TYPE_HOME;
        }else {
            return TYPE_NORMAL;
        }
    }

    private int getRealPosition(int position){
        return position - 1;
    }

    class ViewHolder{
        TextView navigationItemText;
        ImageView navigationItemImage;
    }

}
