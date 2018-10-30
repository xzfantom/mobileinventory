package by.martynoff.mobileinventory;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class mobileInventoryRepository {

    private WarehouseDAO warehouseDAO;
    private LiveData<List<Warehouse>> allWarehouses;

    public mobileInventoryRepository (Application application) {
        mobileInventoryDatabase db = mobileInventoryDatabase.getDatabase(application);
        warehouseDAO = db.WarehouseDAO();
        allWarehouses = warehouseDAO.getAllWarehouses();
    }

    LiveData<List<Warehouse>> getAllWarehouses() {
        return allWarehouses;
    }

    public void insert (Warehouse warehouse){
        new insertAsyncTask(warehouseDAO).execute(warehouse);
    }

    private static class insertAsyncTask extends AsyncTask<Warehouse, Void, Void> {
        private WarehouseDAO asyncTaskWarehouseDAO;

        insertAsyncTask(WarehouseDAO dao){
            asyncTaskWarehouseDAO = dao;
        }

        @Override
        protected Void doInBackground(final Warehouse... params) {
            asyncTaskWarehouseDAO.insert(params[0]);
            return null;
        }
    }
}
