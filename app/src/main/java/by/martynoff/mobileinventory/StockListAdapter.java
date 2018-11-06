package by.martynoff.mobileinventory;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.List;

public class StockListAdapter extends RecyclerView.Adapter<StockListAdapter.StockViewHolder> {

    public static final int STOCK_LIST_ACTION_INC = 1;
    public static final int STOCK_LIST_ACTION_DEC = 2;
    public static final int STOCK_LIST_ACTION_SET = 3;
    public static final int STOCK_LIST_ACTION_DESCR = 4;

    private OnItemClickListener clickListener;

    public interface OnItemClickListener{
        void onItemClick (View view, Stock stock, int action);
    }

    class StockViewHolder extends RecyclerView.ViewHolder{
        private final TextView goodNameView;
        private final TextView baseQuantity;
        private final EditText factQuantity;
        private final ImageView incQuantity;
        private final ImageView decQuantity;

        private StockViewHolder(View itemView){
            super(itemView);
            goodNameView = itemView.findViewById(R.id.goodNameView);
            baseQuantity = itemView.findViewById(R.id.baseQuantity);
            factQuantity = itemView.findViewById(R.id.factQuantity);
            incQuantity = itemView.findViewById(R.id.incView);
            decQuantity = itemView.findViewById(R.id.decView);
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
    public void onBindViewHolder(final StockListAdapter.StockViewHolder holder, final int position) {
        // TODO: rewrite on click listener to receive current.stock and what to do
        if (mStock != null) {
            final StockExtended current = mStock.get(position);
            holder.goodNameView.setText(current.good.name);
            holder.factQuantity.setText(current.stock.amountFact.toString());
            holder.baseQuantity.setText(current.stock.amountBase.toString());

            holder.factQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.factQuantity.
                    current.stock.amountFact = new BigDecimal(holder.factQuantity.getText().toString());
                    clickListener.onItemClick(view, current.stock, STOCK_LIST_ACTION_SET);
                }
            });

            holder.decQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(view, current.stock, STOCK_LIST_ACTION_DEC);
                }
            });

            holder.incQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(view, current.stock, STOCK_LIST_ACTION_INC);
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
