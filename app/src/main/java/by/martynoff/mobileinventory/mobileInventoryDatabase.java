package by.martynoff.mobileinventory;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Warehouse.class}, version = 1)
public abstract class mobileInventoryDatabase extends RoomDatabase {

    public abstract WarehouseDAO WarehouseDAO();

    private static volatile mobileInventoryDatabase INSTANCE;

    static mobileInventoryDatabase getDatabase (final Context context){
        if (INSTANCE == null) {
            synchronized (mobileInventoryDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            mobileInventoryDatabase.class, "mobile_inventory")
                            .build();
                }
            }
        }

        return INSTANCE;
    }
}
