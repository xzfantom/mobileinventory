package by.martynoff.mobileinventory;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class StockListAdapter extends RecyclerView.Adapter<StockListAdapter.StockViewHolder> {

    private OnItemClickListener clickListener;

    public interface OnItemClickListener{
        void onItemClick (View view, int position);
    }

    class StockViewHolder extends RecyclerView.ViewHolder{
        private final TextView goodNameView;
        private final TextView baseQuantity;
        private final TextView factQuantity;
        private final View container;

        private StockViewHolder(View itemView){
            super(itemView);
            goodNameView = itemView.findViewById(R.id.goodNameView);
            baseQuantity = itemView.findViewById(R.id.baseQuantity);
            factQuantity = itemView.findViewById(R.id.factQuantity);
            container = itemView;
        }

    }

    private final LayoutInflater mInflater;
    private List<StockExtended> mStock;

    StockListAdapter (Context context, OnItemClickListener clickListener){
        mInflater = LayoutInflater.from(context);
        this.clickListener = clickListener;
    }

    @Override
    public StockListAdapter.StockViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.activity_stock_item, parent, false);

        return new StockListAdapter.StockViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StockListAdapter.StockViewHolder holder, final int position) {
        // TODO: rewrite on click listener to receive current.stock and what to do
        if (mStock != null) {
            final StockExtended current = mStock.get(position);
            holder.goodNameView.setText(current.good.name);
            holder.factQuantity.setText(current.stock.amountFact.toString());
            holder.baseQuantity.setText(current.stock.amountBase.toString());
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(view, position);
                }
            });
            holder.factQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(view, position);
                }
            });

        } else {
            // Covers the case of data not being ready yet.
            holder.goodNameView.setText("Nothing");
        }
    }

    void setStock(List<StockExtended> stock){
        mStock = stock;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mStock != null)
            return mStock.size();
        else return 0;
    }

}
