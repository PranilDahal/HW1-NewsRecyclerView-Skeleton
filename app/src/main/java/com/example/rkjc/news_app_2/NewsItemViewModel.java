package com.example.rkjc.news_app_2;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class NewsItemViewModel extends AndroidViewModel{

    private NewsItemRepository newsItemRepository;

    private LiveData<List<NewsItem>> newsLiveDataList;

    public NewsItemViewModel(@NonNull Application application) {
        super(application);
        newsItemRepository = new NewsItemRepository(application);
        System.out.println("INSIDE NewsItemViewModel: - Now calling method inside the repository to get data.");
        newsLiveDataList = newsItemRepository.getAllNewsItem();
    }

    public LiveData<List<NewsItem>> returnLiveDataList(){
        return newsLiveDataList;
    }

    public void syncDatabaseWithNewInfo(){
        newsItemRepository.syncDatabase();
    }
}
