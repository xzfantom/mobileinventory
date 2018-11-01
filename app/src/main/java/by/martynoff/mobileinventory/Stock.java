package by.martynoff.mobileinventory;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import java.math.BigDecimal;

@Entity (tableName = "stocks"
        , foreignKeys = {
        @ForeignKey(entity = Warehouse.class, parentColumns = "id", childColumns = "warehouse_id"),
        @ForeignKey(entity = Good.class, parentColumns = "id", childColumns = "good_id")
        }
        , primaryKeys = {"warehouse_id", "good_id"}
        )
public class Stock {

    private long warehouse_id;

    private long good_id;

    public long getWarehouse_id() {
        return warehouse_id;
    }

    public long getGood_id() {
        return good_id;
    }

    public BigDecimal getAmountBase() {
        return amountBase;
    }

    public BigDecimal getAmountFact() {
        return amountFact;
    }

    @TypeConverters(Converters.class)
    private BigDecimal amountBase;

    @TypeConverters(Converters.class)
    private BigDecimal amountFact;

    Stock (long warehouse_id, long good_id, BigDecimal amountBase, BigDecimal amountFact) {
        this.warehouse_id = warehouse_id;
        this.good_id = good_id;
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
