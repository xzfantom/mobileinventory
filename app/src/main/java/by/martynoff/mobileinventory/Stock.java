package by.martynoff.mobileinventory;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import java.math.BigDecimal;

@Entity (tableName = "stocks"
        , foreignKeys = {
        @ForeignKey(entity = WarehouseDAO.class, parentColumns = "id", childColumns = "warehouse_id"),
        @ForeignKey(entity = GoodDAO.class, parentColumns = "id", childColumns = "good_id")
        }
        , primaryKeys = {"warehouse_id", "good_id"}
        )
public class Stock {

    private long warehouse_id;

    private long good_id;

    @TypeConverters(Converters.class)
    private BigDecimal amountBase;

    @TypeConverters(Converters.class)
    private BigDecimal amountFact;

    Stock (Warehouse warehouse, Good good, BigDecimal amountBase, BigDecimal amountFact) {
        this.warehouse_id = warehouse.getId();
        this.good_id = good.getId();
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
