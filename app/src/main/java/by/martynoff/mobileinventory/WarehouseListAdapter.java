package by.martynoff.mobileinventory;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class WarehouseListAdapter extends RecyclerView.Adapter<WarehouseListAdapter.WarehouseViewHolder> {

    class WarehouseViewHolder extends RecyclerView.ViewHolder {
        private final TextView warehouseItemView;

        private WarehouseViewHolder(View itemView) {
            super(itemView);
            warehouseItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Warehouse> mWarehouses; // Cached copy of warehouses

    WarehouseListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public WarehouseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);

        return new WarehouseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WarehouseViewHolder holder, int position) {
        if (mWarehouses != null) {
            final Warehouse current = mWarehouses.get(position);
            holder.warehouseItemView.setText(current.name);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, StockActivity.class);
                    intent.putExtra(MainActivity.WAREHOUSE_CODE, current.code);
                    context.startActivity(intent);
                }
            });
        } else {
            // Covers the case of data not being ready yet.
            holder.warehouseItemView.setText("No Word");
        }
    }

    void setWarehouses(List<Warehouse> warehouses){
        mWarehouses = warehouses;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mWarehouses != null)
            return mWarehouses.size();
        else return 0;
    }
}