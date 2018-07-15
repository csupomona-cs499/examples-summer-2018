package edu.cpp.cs499.l07_firebase_demo;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.firebase.ui.auth.data.model.User;

@Database(entities = {Friend.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DaoFriend friendDao();
}
