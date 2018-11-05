package by.martynoff.mobileinventory;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class mobileInventoryRepository {

    private WarehouseDAO warehouseDAO;
    private GoodDAO goodDAO;
    private StockDAO stockDAO;

    private String currentWarehouse_code;

    private LiveData<List<Warehouse>> allWarehouses;
    private LiveData<List<StockExtended>> inventoryStock;

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

    LiveData<List<StockExtended>> getInventoryStock(String warehouse_code) {
        if (warehouse_code == null) {
            currentWarehouse_code = null;
            inventoryStock.getValue().clear();

        } else if (warehouse_code != currentWarehouse_code){
            currentWarehouse_code = warehouse_code;
            inventoryStock = stockDAO.getInventoryStockExt(currentWarehouse_code);

        }

        return inventoryStock;
    }

    public void insertWarehouse (Warehouse warehouse){
        new insertWarehouseAsyncTask(warehouseDAO).execute(warehouse);
    }

    public void deleteAllWarehouses(){
        new deleteAllWarehousesAsyncTask(warehouseDAO).execute();
    }

    public void insertGood (Good good){
        new insertGoodAsyncTask(goodDAO).execute(good);
    }

    public void insertStock (Stock stock){
        new insertStockAsyncTask(stockDAO).execute(stock);
    }

    public void importFile(InputStream inputStream){
        List<String> buffer = new ArrayList<>();
        deleteAllWarehouses();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = br.readLine()) != null) {
                buffer.add(line);
            }
        } catch (IOException e){
            Log.e("mobileInventory", e.toString());
        } finally {
            try {
                inputStream.close();
            } catch (Throwable ignore) {}
        }

        HashSet<String> hsWarehouses = new HashSet<>();
        HashSet<String> hsGoods = new HashSet<>();

        for (String curLine: buffer) {

            String[] parts = curLine.split(";");
            // 1. warehouse idd;2. warehouse name;3. warehouse code; 4. good idd;5. good name;6. good code;7. good barcode;8. quantity;
            if (!hsWarehouses.contains(parts[2])) {
                Warehouse warehouse = new Warehouse(parts[2], parts[1], parts[0]);
                insertWarehouse(warehouse);
                hsWarehouses.add(parts[2]);
            }

            if (!hsGoods.contains(parts[5])) {
                Good good = new Good(parts[5], parts[4], parts[6], parts[3]);
                insertGood(good);
                hsGoods.add(parts[5]);
            }

            Stock stock = new Stock(parts[2], parts[5], new BigDecimal(parts[7]), new BigDecimal("0.000"));
            insertStock(stock);

        }
    }

    private static class insertWarehouseAsyncTask extends AsyncTask<Warehouse, Void, Void> {
        private WarehouseDAO asyncTaskWarehouseDAO;

        insertWarehouseAsyncTask(WarehouseDAO dao){
            asyncTaskWarehouseDAO = dao;
        }

        @Override
        protected Void doInBackground(final Warehouse... params) {
            asyncTaskWarehouseDAO.insert(params[0]);
            return null;
        }
    }

    private static class deleteAllWarehousesAsyncTask extends AsyncTask<Void, Void, Void> {
        private WarehouseDAO asyncTaskWarehouseDAO;

        deleteAllWarehousesAsyncTask(WarehouseDAO dao){
            asyncTaskWarehouseDAO = dao;
        }

        @Override
        protected Void doInBackground(Void... params) {
            asyncTaskWarehouseDAO.deleteAll();
            return null;
        }
    }

    private static class insertGoodAsyncTask extends AsyncTask<Good, Void, Void> {
        private GoodDAO asyncTaskGoodDAO;

        insertGoodAsyncTask(GoodDAO dao){
            asyncTaskGoodDAO = dao;
        }

        @Override
        protected Void doInBackground(final Good... params) {
            asyncTaskGoodDAO.insert(params[0]);
            return null;
        }
    }

    private static class insertStockAsyncTask extends AsyncTask<Stock, Void, Void> {
        private StockDAO asyncTaskStockDAO;

        insertStockAsyncTask(StockDAO dao){
            asyncTaskStockDAO = dao;
        }

        @Override
        protected Void doInBackground(final Stock... params) {
            asyncTaskStockDAO.insert(params[0]);
            return null;
        }
    }

}
