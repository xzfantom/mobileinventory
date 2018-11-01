package by.martynoff.mobileinventory;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface GoodDAO {

    @Insert
    void insert(Good good);

    @Update
    void update(Good... goods);

    @Delete
    void delete(Good... goods);

    @Query("DELETE FROM goods")
    void deleteAll ();

    @Query("SELECT * FROM goods ORDER BY name ASC")
    LiveData<List<Good>> getAllGoods();
}
