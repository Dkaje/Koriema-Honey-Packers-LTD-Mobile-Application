package com.example.Varsani.Suppliers.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.Varsani.R;
import com.example.Varsani.Staff.Driver.AssignedItems;
import com.example.Varsani.Suppliers.Accept;
import com.example.Varsani.Suppliers.Model.MyRequetsModel;

import java.util.List;

public class AdapterMyRequest extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MyRequetsModel> items;

    private Context ctx;
    ProgressDialog progressDialog;
//    private OnItemClickListener mOnItemClickListener;
//    private OnMoreButtonClickListener onMoreButtonClickListener;

    //


    public static final String TAG = " adapter";

//    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
//        this.mOnItemClickListener = mItemClickListener;
//    }
//
//    public void setOnMoreButtonClickListener(final OnMoreButtonClickListener onMoreButtonClickListener) {
//        this.onMoreButtonClickListener = onMoreButtonClickListener;
//    }

    public AdapterMyRequest(Context context, List<MyRequetsModel> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public TextView txv_requestID,txv_name,txv_items,
                txv_requestDate, txv_requestStatus;


        public OriginalViewHolder(View v) {
            super(v);

            txv_items =v.findViewById(R.id.txv_items);
            txv_name =v.findViewById(R.id.txv_name);
            txv_requestID = v.findViewById(R.id.txv_requestID);
            txv_requestStatus = v.findViewById(R.id.txv_requestStatus);
            txv_requestDate = v.findViewById(R.id.txv_requestDate);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lv_my_request, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            final OriginalViewHolder view = (OriginalViewHolder) holder;

            final MyRequetsModel o= items.get(position);

            view.txv_requestID.setText(" No "+o.getRequestID());
            view.txv_requestDate.setText(o.getRequestDate());
            view.txv_requestStatus.setText("Status "+o.getRequestStatus());
            view.txv_items.setText("Items "+o.getItems());

            view.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent in=new Intent(ctx, Accept.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    in.putExtra("requestID", o.getRequestID());
                    in.putExtra("item",o.getItems());
                    in.putExtra("requestStatus",o.getRequestStatus());
                    in.putExtra("requestDate",o.getRequestDate());
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