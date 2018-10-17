package com.welson.zhihudaily.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.welson.zhihudaily.R;
import com.welson.zhihudaily.activity.CommentActivity;
import com.welson.zhihudaily.data.CommentData;
import com.welson.zhihudaily.data.CommentDataBean;
import com.welson.zhihudaily.utils.DateUtil;
import com.welson.zhihudaily.utils.GlideUtil;

import java.util.ArrayList;

public class CommentRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int TYPE_HEAD1 = 0;
    private static final int TYPE_HEAD2 = 1;
    private static final int TYPE_NORMAL1 = 2;
    private static final int TYPE_NORMAL2 = 3;
    private Context context;
    private ArrayList<CommentDataBean> longComments;
    private ArrayList<CommentDataBean> shortComments;
    private int longCommentSize;
    private int shortCommentSize;
    private long id;

    public CommentRecyclerAdapter(Context context,ArrayList<CommentDataBean> longComments,
                                  ArrayList<CommentDataBean> shortComments,int longCommentSize
                                    ,int shortCommentSize,long id){
        this.context = context;
        this.longComments = longComments;
        this.shortComments = shortComments;
        this.longCommentSize = longCommentSize;
        this.shortCommentSize = shortCommentSize;
        this.id = id;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        if (getItemViewType(position) == TYPE_HEAD1 || getItemViewType(position) == TYPE_HEAD2){
            View view = LayoutInflater.from(context).inflate(R.layout.comment_header_layout,viewGroup,false);
            return new HeaderViewHolder(view);
        }else{
            View view = LayoutInflater.from(context).inflate(R.layout.comment_item_layout,viewGroup,false);
            return new NormalViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch (getItemViewType(position)){
            case TYPE_HEAD1:
                HeaderViewHolder headerViewHolder1 = (HeaderViewHolder)viewHolder;
                headerViewHolder1.commentHeader.setText(longCommentSize + context.getString(R.string.long_comment_str));
                break;
            case TYPE_HEAD2:
                HeaderViewHolder headerViewHolder2 = (HeaderViewHolder)viewHolder;
                headerViewHolder2.commentHeader.setText(shortCommentSize + context.getString(R.string.short_comment_str));
                headerViewHolder2.commentLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CommentActivity activity = (CommentActivity)context;
                        activity.presenter.requestShortCommentData(id);
                        activity.showDialog(true);
                    }
                });
                break;
            case TYPE_NORMAL1:
                final int realPosition1 = getRealPosition(position,TYPE_NORMAL1);
                NormalViewHolder normalViewHolder1 = (NormalViewHolder)viewHolder;
                normalViewHolder1.authorName.setText(longComments.get(realPosition1).getAuthor());
                GlideUtil.loadImage(context,longComments.get(realPosition1).getAvatar(),normalViewHolder1.authorIcon);
                normalViewHolder1.zanCount.setText(longComments.get(realPosition1).getLikes()+"");
                normalViewHolder1.contentText.setText(longComments.get(realPosition1).getContent());
                normalViewHolder1.time.setText(DateUtil.getCommentTime(longComments.get(realPosition1).getTime()));
                break;
            case TYPE_NORMAL2:
                final int realPosition2 = getRealPosition(position,TYPE_NORMAL2);
                NormalViewHolder normalViewHolder2 = (NormalViewHolder)viewHolder;
                normalViewHolder2.authorName.setText(shortComments.get(realPosition2).getAuthor());
                GlideUtil.loadImage(context,shortComments.get(realPosition2).getAvatar(),normalViewHolder2.authorIcon);
                normalViewHolder2.zanCount.setText(shortComments.get(realPosition2).getLikes()+"");
                normalViewHolder2.contentText.setText(shortComments.get(realPosition2).getContent());
                normalViewHolder2.time.setText(DateUtil.getCommentTime(shortComments.get(realPosition2).getTime()));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return longComments.size() + shortComments.size() + 2;
    }


    @Override
    public int getItemViewType(int position){
        if (position == 0){
            return TYPE_HEAD1;
        }else if (position == (1 + longComments.size())){
            return TYPE_HEAD2;
        }else if (position > 0 && position < (longComments.size() + 1)){
            return TYPE_NORMAL1;
        }
        return TYPE_NORMAL2;
    }

    private int getRealPosition(int position,int type){
        if (type == TYPE_NORMAL1){
            return position - 1;
        }else {
            return position - 2 - longComments.size();
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder{

        TextView commentHeader;
        RelativeLayout commentLayout;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            commentHeader = itemView.findViewById(R.id.comment_header_text);
            commentLayout = itemView.findViewById(R.id.comment_header);
        }
    }

    class NormalViewHolder extends RecyclerView.ViewHolder{
        ImageView authorIcon;
        TextView authorName,zanCount,contentText,time;

        public NormalViewHolder(@NonNull View itemView) {
            super(itemView);
            authorIcon = itemView.findViewById(R.id.author_icon);
            authorName = itemView.findViewById(R.id.author_name);
            zanCount = itemView.findViewById(R.id.zan_count);
            contentText = itemView.findViewById(R.id.comment_content_text);
            time = itemView.findViewById(R.id.publish_time);
        }
    }
}
