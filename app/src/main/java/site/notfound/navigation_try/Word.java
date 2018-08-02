package site.notfound.navigation_try;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by lenovo on 2018/3/9.
 */

@Entity(indices = {@Index(value="word_id",unique=true)})
public class Word {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="word_id")
    private String wordId;
    private String symble;
    private String meanings;
    @ColumnInfo(name="create_time")
    private String createTime;
    private int first;
    private int second;
    private int third;

    public Word(String wordId, String symble, String meanings, String createTime) {
        this.wordId = wordId;
        this.symble = symble;
        this.meanings = meanings;
        this.createTime = createTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getWordId() {
        return wordId;
    }

    public void setWordId(String wordId) {
        this.wordId = wordId;
    }

    public String getSymble() {
        return symble;
    }

    public void setSymble(String symble) {
        this.symble = symble;
    }

    public String getMeanings() {
        return meanings;
    }

    public void setMeanings(String meanings) {
        this.meanings = meanings;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getThird() {
        return third;
    }

    public void setThird(int third) {
        this.third = third;
    }

    @Override
    public String toString() {
        return wordId.trim()+"  ["+symble+"]\n"+meanings;
    }
}
