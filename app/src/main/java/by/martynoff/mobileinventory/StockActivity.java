package by.martynoff.mobileinventory;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.List;

public class StockActivity extends AppCompatActivity {

    private String warehouseCode;
    private StockActivityViewModel mStockActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        warehouseCode = getIntent().getStringExtra(MainActivity.WAREHOUSE_CODE);

        RecyclerView recyclerView = findViewById(R.id.stockRecyclerView);
        final StockListAdapter adapter = new StockListAdapter(this, new StockListAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View v, int position) {
                // TODO: Check what was pressed and add/substract 1 from fact quantity
                Log.d("StockActivity", "onItemClick " + position);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        mStockActivityViewModel = ViewModelProviders.of(this).get(StockActivityViewModel.class);
        mStockActivityViewModel.getCurrentStock(warehouseCode).observe(this, new Observer<List<StockExtended>>() {
            @Override
            public void onChanged(@Nullable List<StockExtended> stock) {
                adapter.setStock(stock);
            }
        });
    }
}
