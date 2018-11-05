package by.martynoff.mobileinventory;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface WarehouseDAO {

    @Insert
    void insert (Warehouse warehouse);

    @Update
    void update (Warehouse warehouse);

    @Delete
    void delete (Warehouse warehouse);

    @Query("DELETE FROM warehouses")
    void deleteAll ();

    @Query("SELECT * FROM warehouses ORDER BY name ASC")
    LiveData<List<Warehouse>> getAllWarehouses();

    @Query("SELECT * FROM warehouses WHERE code = :warehouse_code")
    LiveData<Warehouse> getWarehouseByCode (String warehouse_code);
}
