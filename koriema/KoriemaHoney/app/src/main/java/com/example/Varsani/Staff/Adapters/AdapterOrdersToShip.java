package com.example.Varsani.Staff.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.Varsani.Staff.Models.OrderToShipModel;
import com.example.Varsani.Staff.ShippingMrg.ItemsToShip;
import com.example.Varsani.utils.SessionHandler;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;

import java.util.List;

public class AdapterOrdersToShip extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<OrderToShipModel> items;

    private Context ctx;
    ProgressDialog progressDialog;
//    private OnItemClickListener mOnItemClickListener;
//    private OnMoreButtonClickListener onMoreButtonClickListener;

    //

    private SessionHandler session;
    private UserModel user;
    private String clientId = "";
    private String orderID = "";

    public static final String TAG = "Orders adapter";

//    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
//        this.mOnItemClickListener = mItemClickListener;
//    }
//
//    public void setOnMoreButtonClickListener(final OnMoreButtonClickListener onMoreButtonClickListener) {
//        this.onMoreButtonClickListener = onMoreButtonClickListener;
//    }

    public AdapterOrdersToShip(Context context, List<OrderToShipModel> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public TextView txv_orderID,txv_name, txv_orderDate;
        public TextView txv_orderStatus;


        public OriginalViewHolder(View v) {
            super(v);

            txv_name =v.findViewById(R.id.txv_name);
            txv_orderID =v.findViewById(R.id.txv_orderID);
            txv_orderStatus = v.findViewById(R.id.txv_orderStatus);
            txv_orderDate = v.findViewById(R.id.txv_orderDate);
            txv_orderID = v.findViewById(R.id.txv_orderID);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lv_orderstoship, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            final OriginalViewHolder view = (OriginalViewHolder) holder;

            final OrderToShipModel o= items.get(position);

            view.txv_orderID.setText("Order No "+o.getOrderID());
            view.txv_orderStatus.setText(o.getOrderStatus());
            view.txv_orderDate.setText("Order date "+o.getOrderDate());
            view.txv_name.setText(o.getClientName());

            view.txv_orderDate.setVisibility(View.GONE);

            view.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent in=new Intent(ctx, ItemsToShip.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    in.putExtra("orderID", o.getOrderID());
                    in.putExtra("clientName",o.getClientName());
                    in.putExtra("orderDate",o.getOrderDate());
                    in.putExtra("orderStatus",o.getOrderStatus());
                    in.putExtra("county",o.getCounty());
                    in.putExtra("town",o.getTown());
                    in.putExtra("address",o.getAddress());
                    ctx.startActivity(in);


                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

//    public interface OnItemClickListener {
//        void onItemClick(View view, ProductModal obj, int pos);
//    }
//
//    public interface OnMoreButtonClickListener {
//        void onItemClick(View view, ProductModal obj, MenuItem item);
//    }



}