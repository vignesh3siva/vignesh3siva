package com.example.listingapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.listingapp.app.App;
import com.example.listingapp.roomDB.model.RandomUserModel;

public class RandomUserVM extends ViewModel {
    public LiveData<PagedList<RandomUserModel>> concertList;

    public LiveData<PagedList<RandomUserModel>> getAllRandomUserData() {
        concertList =
                new LivePagedListBuilder<>(
                        App.get().getDB().randomUserDAO().getAllRandomUserPaging(), 25).build();
        return concertList;
    }

}
