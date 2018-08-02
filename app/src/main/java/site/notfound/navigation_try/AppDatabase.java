package site.notfound.navigation_try;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by lenovo on 2018/3/7.
 */

@Database(entities={Word.class},version=1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WordDao getWordDao();
}
