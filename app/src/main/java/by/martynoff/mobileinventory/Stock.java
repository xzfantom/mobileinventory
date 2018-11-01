package by.martynoff.mobileinventory;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.math.BigDecimal;

@Entity (tableName = "stocks"
        , indices = {@Index(value = {"warehouse_code", "good_code"})}
        , foreignKeys = {
        @ForeignKey(entity = Warehouse.class, parentColumns = "code", childColumns = "warehouse_code"),
        @ForeignKey(entity = Good.class, parentColumns = "code", childColumns = "good_code")
        }
        , primaryKeys = {"warehouse_code", "good_code"}
        )
public class Stock {

    @NonNull
    public String warehouse_code;

    @NonNull
    public String good_code;

    @TypeConverters(Converters.class)
    public BigDecimal amountBase;

    @TypeConverters(Converters.class)
    public BigDecimal amountFact;

    Stock (String warehouse_code, String good_code, BigDecimal amountBase, BigDecimal amountFact) {
        this.warehouse_code = warehouse_code;
        this.good_code = good_code;
        this.amountBase = amountBase;
        this.amountFact = amountFact;
    }

    public static class Converters {

        @TypeConverter
        public BigDecimal fromLong (Long value) {
            return value == null ? null : new BigDecimal(value).divide(new BigDecimal(1000));
        }

        @TypeConverter
        public Long toLong (BigDecimal value) {
            return value == null ? null : value.multiply(new BigDecimal(1000)).longValue();
        }
    }


}
