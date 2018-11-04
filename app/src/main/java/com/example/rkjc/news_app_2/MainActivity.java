package com.example.rkjc.news_app_2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    private static String searchResults = "";
    private RecyclerView newsRecyclerView;
    private NewsAdapter newsAdapter;
    private ArrayList<NewsItem> newsList = new ArrayList<NewsItem>();

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsRecyclerView = (RecyclerView) findViewById(R.id.news_recyclerview);
        newsAdapter = new NewsAdapter(this, newsList);
        newsRecyclerView.setAdapter(newsAdapter);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        new NewsQueryTask().execute();
    }

    public void populateRecyclerView(String searchResults) {
        Log.d("mycode", searchResults);
        newsList = JsonUtils.parseNews(searchResults);
        newsAdapter.allNews = newsList;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                newsAdapter.notifyDataSetChanged();
            }
        });
    }

    class NewsQueryTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                searchResults = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.buildURL());
                populateRecyclerView(searchResults);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();

        if (itemThatWasClickedId == R.id.action_search) {
            new NewsQueryTask().execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

}
