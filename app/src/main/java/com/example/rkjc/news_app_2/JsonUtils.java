package com.example.rkjc.news_app_2;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static ArrayList<NewsItem> parseNews(String jsonData) {
        ArrayList<NewsItem> listOfNews = new ArrayList<NewsItem>();

        try {
            JSONObject obj = new JSONObject(jsonData);
            JSONArray articles = obj.getJSONArray("articles");

            for (int i = 0; i < articles.length(); i++) {

                JSONObject curr = articles.getJSONObject(i);

                listOfNews.add(new NewsItem(curr.getString("author"),
                        curr.getString("title"), curr.getString("description"),
                        curr.getString("url"), curr.getString("urlToImage"),
                        curr.getString("publishedAt")));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return listOfNews;
    }

}