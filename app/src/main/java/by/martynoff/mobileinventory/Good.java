package by.martynoff.mobileinventory;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "goods"
        ,indices = @Index("code")
)
public class Good {

    @NonNull
    @ColumnInfo(name="name")
    public String name;

    @PrimaryKey()
    @NonNull
    public String code;

    public String barcode;

    public String idd;

    Good (String code, String name, String barcode, String idd) {
        this.code = code;
        this.name = name;
        this.barcode = barcode;
        this.idd = idd;
    }

}
