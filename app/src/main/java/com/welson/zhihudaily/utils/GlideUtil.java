package com.welson.zhihudaily.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class GlideUtil {

    public static void loadImage(Context context,String url,ImageView imageView){
        Glide.with(context).load(url).into(imageView);
    }
}
