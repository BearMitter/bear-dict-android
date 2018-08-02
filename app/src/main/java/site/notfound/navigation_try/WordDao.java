package site.notfound.navigation_try;

/**
 * Created by Bear on 2018/3/9.
 */

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface WordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     void insertWords(Word... words);
    @Update
     void updateWord(Word... words);
    @Delete
    void deleteWords(Word... words);
    @Query("DELETE FROM WORD")
    void deleteAll();
    @Query("SELECT * FROM WORD")
    List<Word> getAllWords();

    @Query("SELECT * FROM WORD WHERE word_id= :wordId LIMIT 1")
    Word getWord(String wordId);

}
