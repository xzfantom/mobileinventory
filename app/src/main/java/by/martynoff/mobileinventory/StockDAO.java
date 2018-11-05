package by.martynoff.mobileinventory;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface StockDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Stock stock);

    @Update
    void update(Stock... stocks);

    @Query("SELECT * FROM stocks WHERE warehouse_code = :warehouse_code")
    LiveData<List<Stock>> getInventoryStock (String warehouse_code);

    @Query("SELECT * FROM stocks LEFT JOIN goods ON stocks.good_code=goods.code WHERE stocks.warehouse_code=:warehouse_code")
    LiveData<List<StockExtended>> getInventoryStockExt (String warehouse_code);
}
