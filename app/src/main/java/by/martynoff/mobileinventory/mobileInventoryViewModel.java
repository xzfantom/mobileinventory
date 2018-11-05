package by.martynoff.mobileinventory;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.io.InputStream;
import java.util.List;

public class mobileInventoryViewModel extends AndroidViewModel {

    private mobileInventoryRepository mRepository;

    private LiveData<List<Warehouse>> allWarehouses;

    public mobileInventoryViewModel(Application application) {
        super(application);
        mRepository = new mobileInventoryRepository(application);
        allWarehouses = mRepository.getAllWarehouses();
    }

    LiveData<List<Warehouse>> getAllWarehouses() {
        return allWarehouses;
    }

    public void insert (Warehouse warehouse) {
        mRepository.insertWarehouse(warehouse);
    }

    public void importFile (InputStream filePath) {
        mRepository.importFile(filePath);
    }
}
