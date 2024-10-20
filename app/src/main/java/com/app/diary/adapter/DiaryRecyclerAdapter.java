package com.app.diary.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.app.diary.R;
import com.app.diary.bean.Diary;
import com.app.diary.utils.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.List;

public class DiaryRecyclerAdapter extends RecyclerView.Adapter<DiaryRecyclerAdapter.DiaryViewHolder> {

    private List<Diary> list;//日记本列表数据
    private LayoutInflater inflater;//布局填充器

    private OnItemClickListener onItemClickListener;//点击事件

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @NonNull
    @Override
    public DiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //布局填充器可以重复使用，所以这里使用懒加载的方式创建
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        View view = inflater.inflate(R.layout.item_recycler_diary, parent, false);
        return new DiaryViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull DiaryViewHolder holder, int position) {
        Diary diary = list.get(position);

        holder.rowLinearLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(diary, position);
                }
            }

        });
        holder.dateTextView.setText(new SimpleDateFormat("yyyy年MM月dd日").format(diary.getDate()));
        holder.weatherTextView.setText(diary.getWeather());
        holder.titleTextView.setText(diary.getTitle());
        holder.wordCountTextView.setText("字数" + diary.getContent().length() + "个");
        holder.updateTimeTextView.setText("编辑于" + TimeUtils.getSimpleTime(diary.getUpdateTime().getTime()));
    }

    /**
     * 设置新数据
     */
    public void setNewData(@Nullable List<Diary> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    /**
     * 设置单个日记的点击事件
     */
    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    /**
     * 视图固定器
     */
    public class DiaryViewHolder extends RecyclerView.ViewHolder {

        LinearLayout rowLinearLayout;//行布局控件
        TextView dateTextView;//日记时间文本控件
        TextView weatherTextView;//天气文本控件
        TextView titleTextView;//标题文本控件
        TextView wordCountTextView;//字数文本控件
        TextView updateTimeTextView;//修改时间文本控件

        public DiaryViewHolder(@NonNull View itemView) {
            super(itemView);
            rowLinearLayout = itemView.findViewById(R.id.row_linearLayout);
            dateTextView = itemView.findViewById(R.id.date_textView);
            weatherTextView = itemView.findViewById(R.id.weather_textView);
            titleTextView = itemView.findViewById(R.id.title_textView);
            wordCountTextView = itemView.findViewById(R.id.wordCount_textView);
            updateTimeTextView = itemView.findViewById(R.id.updateTime_textView);
        }

    }

    /**
     * 日记的点击事件
     */
    public interface OnItemClickListener {

        void onItemClick(Diary diary, int position);

    }

}