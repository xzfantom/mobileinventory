package by.martynoff.mobileinventory;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "warehouses")
public class Warehouse {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    @ColumnInfo(name="warehouse_name")
    private String name;

    @ColumnInfo(name="warehouse_idd")
    private String idd;

    @Ignore
    public Warehouse (String name, String idd){
        this.name = name;
        this.idd = idd;
    }

    public Warehouse (String name){
        this.name = name;
    }

    public String getName () {
        return this.name;
    }

    public String getIdd() {
        return this.idd;
    }

    public void setIdd(String idd){
        this.idd = idd;
    }

    public long getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
