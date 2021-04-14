package com.example.rssfeed;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

public class NewsItemAdapter extends ArrayAdapter<NewsItem>
{
    Context context;
    List<NewsItem> newsItems;

    public NewsItemAdapter(@NonNull Context context, int resource, @NonNull List<NewsItem> objects) {
        super(context, resource, objects);
        this.context = context;
        this.newsItems = objects;
    }


}
