package by.martynoff.mobileinventory;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "goods")
public class Good {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    @ColumnInfo(name="good_name")
    private String name;

    private String code;

    private String barcode;

    private String idd;

    Good (String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getIdd() {
        return idd;
    }

    public void setIdd(String idd) {
        this.idd = idd;
    }

    public void setAll (String name, String code, String idd, String barcode){
        this.name = name;
        this.code = code;
        this.idd = idd;
        this.barcode = barcode;
    }

    public void setCodeAndIdd (String code, String idd){
        this.code = code;
        this.idd = idd;
    }
}
