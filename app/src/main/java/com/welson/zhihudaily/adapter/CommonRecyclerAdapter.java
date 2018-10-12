package com.welson.zhihudaily.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.welson.zhihudaily.R;
import com.welson.zhihudaily.data.NewsStory;
import com.welson.zhihudaily.utils.GlideUtil;

import java.util.ArrayList;

public class CommonRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<NewsStory> newsStories;
    private Context context;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_NORMAL = 1;
    private View headerView;

    public CommonRecyclerAdapter(Context context,ArrayList<NewsStory> newsStories){
        this.context = context;
        this.newsStories = newsStories;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        if (getItemViewType(position) == TYPE_HEADER){
            return new ViewHolder(headerView);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.common_data_item,viewGroup,false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        int realPosititon = getRealPosition(position);
        if (getItemViewType(position) == TYPE_HEADER){
            ViewHolder mViewHolder = (ViewHolder)viewHolder;
        }else{
            ViewHolder mViewHolder = (ViewHolder)viewHolder;
            mViewHolder.commonItemText.setText(newsStories.get(realPosititon).getTitle());
            if (null == newsStories.get(realPosititon).getImages()){
                mViewHolder.commonItemImage.setVisibility(View.GONE);
            }else {
                GlideUtil.loadImage(context,newsStories.get(realPosititon).getImages().get(0),mViewHolder.commonItemImage);
            }
        }
    }

    public void setHeaderView(View view){
        headerView = view;
        notifyItemInserted(0);
    }

    private int getRealPosition(int position){
        return position - 1;
    }
    @Override
    public int getItemViewType(int position){
        if (position == 0){
            return TYPE_HEADER;
        }else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        return newsStories.size() + 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CardView commonItemCard;
        TextView commonItemText;
        ImageView commonItemImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            if (itemView == headerView){
                return;
            }
            commonItemCard = itemView.findViewById(R.id.common_data_card);
            commonItemText = itemView.findViewById(R.id.common_item_text);
            commonItemImage = itemView.findViewById(R.id.common_item_image);
        }
    }
}
