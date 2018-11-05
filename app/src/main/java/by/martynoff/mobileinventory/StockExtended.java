package by.martynoff.mobileinventory;

import android.arch.persistence.room.Embedded;

public class StockExtended {

    @Embedded
    public Good good;

    @Embedded
    public Stock stock;
}
