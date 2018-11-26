package com.example.rkjc.news_app_2;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class NewsItemViewModel extends AndroidViewModel{

    private NewsItemRepository newsItemRepository;

    private LiveData<List<NewsItem>> newsLiveDataList=null;

    public NewsItemViewModel(@NonNull Application application) {
        super(application);
        newsItemRepository = new NewsItemRepository(application);
        newsLiveDataList = newsItemRepository.getAllNewsItem();
    }

    public LiveData<List<NewsItem>> returnLiveDataList(){
        return newsLiveDataList;
    }

    public void syncDatabaseWithNewInfo(){
        newsItemRepository.syncDatabase();
    }
}
