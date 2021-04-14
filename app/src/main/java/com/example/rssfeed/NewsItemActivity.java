package com.example.rssfeed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class NewsItemActivity extends AppCompatActivity {

    TextView tvTitle, tvDate, tvDescription, tvLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_item);

        tvTitle = findViewById(R.id.tvTitle);
        tvDate = findViewById(R.id.tvDate);
        tvDescription = findViewById(R.id.tvDescription);
        tvLink = findViewById(R.id.tvLink);

        Intent intent = getIntent();
        NewsItem newsItem = (NewsItem) intent.getSerializableExtra("item");
        tvTitle.setText(newsItem.getTitle());
        tvDate.setText(newsItem.getDate().toString());
        tvDescription.setText(newsItem.getDescription());
        tvLink.setText(newsItem.getLink());
    }
}