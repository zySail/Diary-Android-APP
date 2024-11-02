package com.app.diary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.app.diary.R;

public class EmojiAdapter extends BaseAdapter {
    private Context context;
    private int[] emojiResIds; // 存储表情资源 ID

    public EmojiAdapter(Context context, int[] emojiResIds) {
        this.context = context;
        this.emojiResIds = emojiResIds;
    }

    @Override
    public int getCount() {
        return emojiResIds.length;
    }

    @Override
    public Object getItem(int position) {
        return emojiResIds[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView emojiImageView;
        if (convertView == null) {
            emojiImageView = new ImageView(context);
            emojiImageView.setLayoutParams(new ViewGroup.LayoutParams(400, 400)); // 设置图标大小
            emojiImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            emojiImageView = (ImageView) convertView;
        }

        // 设置表情图标
        emojiImageView.setImageResource(emojiResIds[position]);
        return emojiImageView;
    }
}

