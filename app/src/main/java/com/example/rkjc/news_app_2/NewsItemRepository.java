package com.example.rkjc.news_app_2;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.os.AsyncTask;
import android.provider.UserDictionary;

import java.io.IOException;
import java.util.List;

public class NewsItemRepository {

    private NewsItemDao newsDao;

    private LiveData<List<NewsItem>> allNews;

    public NewsItemRepository(Application application) {
        NewsItemRoomDatabase db = NewsItemRoomDatabase.getDatabase(application.getApplicationContext());
        newsDao = db.newsDao();
        System.out.println("INSIDE NewsItemRepository: - Calling the QUERY method inside the DAO.");
        allNews = newsDao.loadAllNewsitem();
    }

    public void syncDatabase() {
        new syncDatabaseTask(newsDao).execute();
    }

    private static class syncDatabaseTask extends AsyncTask<Void, Void, Void> {

        private NewsItemDao newsDao;

        syncDatabaseTask(NewsItemDao dao) {
            newsDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            newsDao.clearAll();

            try {

                String searchResults = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.buildURL());
                List<NewsItem> newsList = JsonUtils.parseNews(searchResults);
                System.out.println("INSIDE THE SYNCDATABASE TASK: - Just finished parsing new data from the internet.");
                newsDao.insert(newsList);
                System.out.println("INSIDE THE SYNCDATABASE TASK: - Just finished inserting new data into the database.");

            } catch (IOException e) {
                System.out.println("ERROR ON LINE 48 - NewsItemRepository: ");
                e.printStackTrace();
            }

            return null;
        }
    }

    public LiveData<List<NewsItem>> getAllNewsItem() {
        return allNews;
    }

    private static class getAllNewsItemTask extends AsyncTask<Void, Void, LiveData<List<NewsItem>>> {

        private NewsItemDao newsDao;

        private LiveData<List<NewsItem>> allNewsList;

        getAllNewsItemTask(NewsItemDao dao){
            newsDao = dao;
        }

        @Override
        protected LiveData<List<NewsItem>> doInBackground(Void... voids) {
            allNewsList = newsDao.loadAllNewsitem();
            return null;
        }
    }

}
