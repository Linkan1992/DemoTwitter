package com.demotwitter.linkan.demotwitter.data.local.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.demotwitter.linkan.demotwitter.data.model.db.TimelineFeed;


@Database(entities = {TimelineFeed.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{

    public abstract TweetFeedDao feedDao();

}
