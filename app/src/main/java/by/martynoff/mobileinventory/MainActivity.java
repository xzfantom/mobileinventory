package by.martynoff.mobileinventory;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private mobileInventoryViewModel mMobileInventoryViewModel;

    public static final int NEW_WAREHOUSE_ACTIVITY_REQUEST_CODE = 1;
    public static final int GET_CONTENT = 2;
    public static final String WAREHOUSE_CODE= "WAREHOUSE_CODE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final WarehouseListAdapter adapter = new WarehouseListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(Intent.createChooser(intent, "Select file"), GET_CONTENT);
            }
        });

        mMobileInventoryViewModel = ViewModelProviders.of(this).get(mobileInventoryViewModel.class);

        mMobileInventoryViewModel.getAllWarehouses().observe(this, new Observer<List<Warehouse>>() {
            @Override
            public void onChanged(@Nullable List<Warehouse> warehouses) {
                adapter.setWarehouses(warehouses);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WAREHOUSE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            //Warehouse warehouse = new Warehouse(data.getStringExtra(NewWarehouseActivity.EXTRA_REPLY));
            //mMobileInventoryViewModel.insert(warehouse);

        } else if (requestCode == GET_CONTENT && resultCode == RESULT_OK){
            Uri pathHolder = data.getData();
            try {
                InputStream is = getContentResolver().openInputStream(pathHolder);
                mMobileInventoryViewModel.importFile(is);
            }catch (IOException e) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }

            //getContentResolver().openFileDescriptor(pathHolder, "r")
            //

        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}
