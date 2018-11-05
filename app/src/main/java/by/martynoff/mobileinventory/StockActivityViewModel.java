package by.martynoff.mobileinventory;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class StockActivityViewModel extends AndroidViewModel {

    private mobileInventoryRepository mMobileInventoryRepository;

    private String currentWarehouse_code;
    private LiveData<List<StockExtended>> currentStock;

    public StockActivityViewModel (Application application){
        super(application);
        mMobileInventoryRepository = new mobileInventoryRepository(application);

    }

    LiveData<List<StockExtended>> getCurrentStock (String warehouse_code) {

        if (currentWarehouse_code != warehouse_code){
            currentWarehouse_code = warehouse_code;
            currentStock = mMobileInventoryRepository.getInventoryStock(currentWarehouse_code);
        }

        return currentStock;
    }


}
