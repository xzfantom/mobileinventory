package by.martynoff.mobileinventory;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "warehouses")
public class Warehouse {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name="code")
    public String code;

    @ColumnInfo(name="name")
    public String name;

    @ColumnInfo(name="idd")
    public String idd;

    public Warehouse (String code, String name, String idd){
        this.code = code;
        this.name = name;
        this.idd = idd;
    }

}
