package com.chihopang.readhub.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import com.chihopang.readhub.app.ReadhubApplication;
import com.chihopang.readhub.database.dao.NewsDao;
import com.chihopang.readhub.database.dao.TopicDao;
import com.chihopang.readhub.model.News;
import com.chihopang.readhub.model.Topic;

@Database(entities = {Topic.class, News.class}, version = 1, exportSchema = false)
public abstract class ReadhubDatabase extends RoomDatabase {
  private static final String DATABASE_NAME = "Readhub";

  public static ReadhubDatabase getInstance() {
    return DatabaseHolder.READHUB_DATABASE;
  }

  public abstract TopicDao getTopicDao();

  public abstract NewsDao getNewsDao();

  /*实现单例*/
  private static class DatabaseHolder {
    private static ReadhubDatabase READHUB_DATABASE =
        Room.databaseBuilder(ReadhubApplication.getContext(), ReadhubDatabase.class, DATABASE_NAME)
            .build();
  }
}
