package com.welson.zhihudaily.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.welson.zhihudaily.R;
import com.welson.zhihudaily.activity.ArticleActivity;
import com.welson.zhihudaily.data.NewsStory;
import com.welson.zhihudaily.utils.DateUtil;
import com.welson.zhihudaily.utils.GlideUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static final int TYPE_BANNER = 0;
    public static final int TYPE_GROUP = 1;
    private static final int TYPE_NORMAL = 2;
    private Context context;
    private ArrayList<NewsStory> newsStories;
    private View headerView;
    private ArrayList<Long> idList;
    private HashMap<Integer,ArrayList<Long>> idMap;

    public HomeRecyclerAdapter(Context context, ArrayList<NewsStory> newsStories){
        this.context = context;
        this.newsStories = newsStories;
        idList = new ArrayList<>();
        idMap = new HashMap<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (headerView != null && viewType == TYPE_BANNER){
            return new CommonViewHolder(headerView);
        }else if (viewType == TYPE_GROUP){
            View view = LayoutInflater.from(context).inflate(R.layout.home_time_layout,viewGroup,false);
            return new GroupViewHolder(view);
        }
        View view = LayoutInflater.from(context).inflate(R.layout.home_data_item,viewGroup,false);
        return new CommonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        idList.clear();
        for (NewsStory ns : newsStories){
            idList.add(ns.getId());
        }
        if (getItemViewType(position) == TYPE_BANNER){
            return;
        }
        final int realPosition = getRealPosition(position);
        if (getItemViewType(position) == TYPE_NORMAL){
            CommonViewHolder commonViewHolder = (CommonViewHolder)viewHolder;
            commonViewHolder.homeDataText.setText(newsStories.get(realPosition).getTitle());
            GlideUtil.loadImage(context,newsStories.get(realPosition).getImages().get(0),commonViewHolder.homeDataImage);
            commonViewHolder.homeDataCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    idMap.clear();
                    idMap.put(realPosition,idList);
                    Intent intent = new Intent(context, ArticleActivity.class);
                    intent.putExtra("articleMapId",(Serializable) idMap);
                    context.startActivity(intent);
                }
            });
        }
        if (getItemViewType(position) == TYPE_GROUP){
            GroupViewHolder timeViewHolder = (GroupViewHolder)viewHolder;
            if (position == 1){
                timeViewHolder.homeTimeText.setText(context.getString(R.string.home_time_default_str));
            }else {
                timeViewHolder.homeTimeText.setText(DateUtil.getCurrentStringStr(newsStories.get(position).getDate()));
            }
            timeViewHolder.homeDataText.setText(newsStories.get(realPosition).getTitle());
            GlideUtil.loadImage(context,newsStories.get(realPosition).getImages().get(0),timeViewHolder.homeDataImage);
            timeViewHolder.homeDataCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    idMap.clear();
                    idMap.put(realPosition,idList);
                    for (int key:idMap.keySet()){
                        Log.d("dingyl","adapter key : " + key);
                    }
                    Intent intent = new Intent(context, ArticleActivity.class);
                    intent.putExtra("articleMapId",(Serializable) idMap);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return newsStories.size() + 1; //注意 这里如果添加了头部 count要加1，否则最后一项不显示
    }

    public void setHeaderView(View headerView){
        this.headerView = headerView;
        notifyItemInserted(0);
    }

    @Override
    public int getItemViewType(int position){
        if (headerView == null){
            if (position == 1){
                return TYPE_GROUP;
            }else if (!newsStories.get(position).getDate().equals(newsStories.get(position-1).getDate())){
                return TYPE_GROUP;
            }else {
                return TYPE_NORMAL;
            }
        }
        if (position == 0){
            return TYPE_BANNER;
        }else if (position == 1){
            return TYPE_GROUP;
        }else if (!newsStories.get(getRealPosition(position)).getDate()
                .equals(newsStories.get((getRealPosition(position))-1).getDate())){
            //Log.d("dingyl","position is : " + getRealPosition(position));
            //Log.d("dingyl","newsStories.get(position) : " + newsStories.get(getRealPosition(position)).getTitle());
            return TYPE_GROUP;
        }
        return TYPE_NORMAL;
    }

    private int getRealPosition(int position){
        return headerView == null ? position : position-1;
    }

    class GroupViewHolder extends RecyclerView.ViewHolder{

        TextView homeTimeText;
        CardView homeDataCard;
        TextView homeDataText;
        ImageView homeDataImage;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            homeTimeText = itemView.findViewById(R.id.home_time_text);
            homeDataCard = itemView.findViewById(R.id.home_data_card);
            homeDataText = itemView.findViewById(R.id.home_item_text);
            homeDataImage = itemView.findViewById(R.id.home_item_image);
        }
    }
    class CommonViewHolder extends RecyclerView.ViewHolder{

        CardView homeDataCard;
        TextView homeDataText;
        ImageView homeDataImage;

        public CommonViewHolder(@NonNull View itemView) {
            super(itemView);
            if (itemView == headerView){
                return;
            }
            homeDataCard = itemView.findViewById(R.id.home_data_card);
            homeDataText = itemView.findViewById(R.id.home_item_text);
            homeDataImage = itemView.findViewById(R.id.home_item_image);
        }
    }

}
