package com.example.rkjc.news_app_2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsItemViewHolder> {

    Context context;
    ArrayList<NewsItem> allNews;

    public NewsAdapter(Context context, ArrayList<NewsItem> news) {
        this.context = context;
        this.allNews = news;
    }


    @NonNull
    @Override
    public NewsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.news_item, parent, shouldAttachToParentImmediately);
        NewsItemViewHolder viewHolder = new NewsItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsItemViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return allNews.size();
    }

    public class NewsItemViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView description;
        TextView date;

        public NewsItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
            date = (TextView) itemView.findViewById(R.id.date);
        }

        public void bind(final int position) {
            title.setText("Title:" + allNews.get(position).getTitle());
            description.setText("Description:" + allNews.get(position).getDescription());
            date.setText("Date:" + allNews.get(position).getPublishedAt());

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view){
                    String urlString = allNews.get(position).getUrl();
                    Uri webpage = Uri.parse(urlString);
                    Intent intent = new Intent(Intent.ACTION_VIEW, webpage, context, WebActivity.class);
                    intent.putExtra("urlString", urlString);
                    context.startActivity(intent);
                }

            });
        }
    }
}
