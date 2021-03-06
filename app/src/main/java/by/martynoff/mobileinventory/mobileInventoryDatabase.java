package by.martynoff.mobileinventory;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Warehouse.class, Good.class, Stock.class}, version = 2)
public abstract class mobileInventoryDatabase extends RoomDatabase {

    public abstract WarehouseDAO WarehouseDAO();

    public abstract GoodDAO GoodDAO();

    public abstract StockDAO StockDAO();

    private static volatile mobileInventoryDatabase INSTANCE;

    static mobileInventoryDatabase getDatabase (final Context context){
        if (INSTANCE == null) {
            synchronized (mobileInventoryDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            mobileInventoryDatabase.class, context.getString(R.string.sqlite_base_name))
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }

        return INSTANCE;
    }
}
