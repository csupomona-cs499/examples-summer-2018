package edu.cpp.cs499.l07_firebase_demo;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.firebase.ui.auth.data.model.User;

import java.util.List;
@Dao
public interface DaoFriend {

    @Query("SELECT * FROM friend")
    List<Friend> getAll();

    @Insert
    void insertAll(List<Friend> friends);
}
