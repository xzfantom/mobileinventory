package by.martynoff.mobileinventory;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface WarehouseDAO {

    @Insert
    void insert (Warehouse warehouse);

    @Query("DELETE FROM warehouses")
    void deleteAll ();

    @Query("SELECT * FROM warehouses ORDER BY warehouse_name ASC")
    LiveData<List<Warehouse>> getAllWarehouses();
}
