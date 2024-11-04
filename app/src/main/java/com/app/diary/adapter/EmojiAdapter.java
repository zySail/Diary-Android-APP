package com.app.diary.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

// 表情包填充器
public class EmojiAdapter extends BaseAdapter {
    private Context context;
    private int[] emojiResIds; // 表情包资源ID数组
    private OnEmojiClickListener emojiClickListener;

    public EmojiAdapter(Context context, int[] emojiResIds, OnEmojiClickListener listener) {
        this.context = context;
        this.emojiResIds = emojiResIds;
        this.emojiClickListener = listener;
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
            emojiImageView.setLayoutParams(new ViewGroup.LayoutParams(400, 400)); // 设置插入的表情包大小
            emojiImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            emojiImageView = (ImageView) convertView;
        }

        // 设置表情图标
        emojiImageView.setImageResource(emojiResIds[position]);

        // 点击事件
        emojiImageView.setOnClickListener(v -> {
            if (emojiClickListener != null) {
                emojiClickListener.onEmojiClick(emojiResIds[position]); // 调用接口方法
            }
        });

        return emojiImageView;
    }

    // 接口定义
    public interface OnEmojiClickListener {
        void onEmojiClick(int emojiResId);
    }
}

