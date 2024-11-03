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
    private OnEmojiClickListener emojiClickListener; // 点击事件监听器

    public EmojiAdapter(Context context, int[] emojiResIds, OnEmojiClickListener listener) {
        this.context = context;
        this.emojiResIds = emojiResIds;
        this.emojiClickListener = listener; // 初始化点击事件监听器
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

        // 设置点击事件
        emojiImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emojiClickListener != null) {
                    emojiClickListener.onEmojiClick(emojiResIds[position]); // 调用点击事件
                }
            }
        });

        return emojiImageView;
    }

    // 定义点击事件接口
    public interface OnEmojiClickListener {
        void onEmojiClick(int emojiResId);
    }
}
