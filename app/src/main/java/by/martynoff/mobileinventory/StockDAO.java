package by.martynoff.mobileinventory;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;

@Dao
public interface StockDAO {

    @Insert
    void insert(Stock stock);

    @Update
    void update(Stock... stocks);

}
