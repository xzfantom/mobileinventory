package by.martynoff.mobileinventory;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class mobileInventoryRepository {

    private WarehouseDAO warehouseDAO;
    private GoodDAO goodDAO;
    private StockDAO stockDAO;
    private LiveData<List<Warehouse>> allWarehouses;

    public mobileInventoryRepository (Application application) {
        mobileInventoryDatabase db = mobileInventoryDatabase.getDatabase(application);
        warehouseDAO = db.WarehouseDAO();
        goodDAO = db.GoodDAO();
        stockDAO = db.StockDAO();

        allWarehouses = warehouseDAO.getAllWarehouses();
    }

    LiveData<List<Warehouse>> getAllWarehouses() {
        return allWarehouses;
    }

    public void insert (Warehouse warehouse){
        new insertAsyncTask(warehouseDAO).execute(warehouse);
    }

    public void importFile(String fileName){
        //File inputFile = new File(fileName);
        URI fileURI = null;
        try {
            fileURI = new URI(fileName);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        File ifi = new File(fileURI);

        StringBuilder txt = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(ifi));
            String line;

            while ((line = br.readLine()) != null) {
                txt.append(line);
                txt.append('\n');
            }
        } catch (IOException e){
            Log.e("mobileInventory", e.toString());
        }
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
