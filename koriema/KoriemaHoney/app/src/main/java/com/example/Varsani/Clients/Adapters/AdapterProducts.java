package com.example.Varsani.Clients.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.Clients.SingleItem;
import com.example.Varsani.utils.SessionHandler;
import com.example.Varsani.utils.Urls;
import com.example.Varsani.Clients.Models.ProductModal;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AdapterProducts extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ProductModal> items;
    private List<ProductModal> searchView;

    private Context ctx;
    ProgressDialog progressDialog;
    private OnItemClickListener mOnItemClickListener;
    private OnMoreButtonClickListener onMoreButtonClickListener;

    //

    private SessionHandler session;
    private UserModel user;
    private String clientId = "";
    private String productID = "";
    private int count = 1;

    private EditText quantity;
    public static final String TAG = "Item_adapter";

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public void setOnMoreButtonClickListener(final OnMoreButtonClickListener onMoreButtonClickListener) {
        this.onMoreButtonClickListener = onMoreButtonClickListener;
    }

    public AdapterProducts(Context context, List<ProductModal> items) {
        this.items = items;
        this.searchView = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title, txv_stock;
        public TextView price;
        private ImageView imageCart;


        public OriginalViewHolder(View v) {
            super(v);
            image = v.findViewById( R.id.image_c);
            title =v.findViewById(R.id.txv_title);
            txv_stock =v.findViewById(R.id.txv_stock);
            price = v.findViewById(R.id.txv_price_c);
            imageCart = v.findViewById(R.id.imageCart);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lv_products, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            final OriginalViewHolder view = (OriginalViewHolder) holder;

            final ProductModal p = items.get(position);
            String url = Urls.ROOT_URL_IMAGES;
            Picasso.with(ctx)
                    .load(url+p.getImgUrl())
                    .fit()
                    .centerCrop()
                    .into(view.image);
            view.title.setText(p.getProductName());
            view.txv_stock.setText("In Stock "+p.getStock());
            view.price.setText("KES "+p.getPrice()+" /=");
            session = new SessionHandler(ctx);
            user = session.getUserDetails();
            try{
                clientId= user.getClientID();
            }catch (RuntimeException e){

            }
//            view.add.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    count = count+1;
//                    Log.e("count", "count "+ count);
//                    view.num.setText( Integer.toString(count));
//                }
//            });
//            view.minus.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(count>1){
//                        count = count-1;
//                        view.num.setText( Integer.toString(count));
//                    }
//                }
//            });
            view.imageCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (user.getUser_type().equals("Client")){

                        productID = p.getProductID();
                    String proName=p.getProductName();
                    String price=p.getPrice();
                    String image=p.getImgUrl();
                    String stock=p.getStock();
                    String desc=p.getDesc();
                    Intent in = new Intent(ctx, SingleItem.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    in.putExtra("proID", productID);
                    in.putExtra("proName", proName);
                    in.putExtra("price", price);
                    in.putExtra("image", image);
                    in.putExtra("stock", stock);
                    in.putExtra("desc", desc);
                    ctx.startActivity(in);

                    }else {

                        Toast.makeText( ctx,"Please login as a customer to order",Toast.LENGTH_SHORT ).show();

                    }
                }
            });
        }
    }

//    public void qtyPrompts(){
//
//        // get prompts.xml view
//        LayoutInflater li = LayoutInflater.from(ctx);
//        View promptsView = li.inflate(R.layout.prompts, null);
//        //Create the AlertDialog with a reference to edit it later
//        final AlertDialog dialog = new AlertDialog.Builder(ctx)
////                .setView(v)
//                .setCancelable(false)
//                .setPositiveButton(R.string.sendto, null) //Set to null. We override the onclick
//                .setNegativeButton(R.string.cancel, null)
//                .create();
//        dialog.setView(promptsView);
//
//        quantity =  promptsView.findViewById(R.id.edt_quantity);
//        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
//
//            @Override
//            public void onShow(DialogInterface dialogInterface) {
//
//                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
//                button.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View view) {
//                        if (TextUtils.isEmpty(quantity.getText().toString())) {
//                            quantity.setError( "Enter item quantity" );
//
////
//                        }else{
//                            dialog.dismiss();
//                            progressShow();
//                            addToCart();
//
//
//
//
//
//
//                        }
//
//
//                    }
//                });
//            }
//        });
//        dialog.show();
//
//    }
    public void addToCart(){
        final String itemQty = quantity.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.URL_ADD_CART,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            Log.e("response ",response);
                            String msg = jsonObject.getString("message");
                            int status = jsonObject.getInt("status");
                            if (status == 1){
                                Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }else {
                                Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "error1 "+msg );
                                progressDialog.dismiss();
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(ctx, "error"+e.toString(), Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "error2 "+e.toString());
                            progressDialog.dismiss();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ctx, "error"+error.toString(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "error3 "+error );
                progressDialog.dismiss();


            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("clientID", clientId);
                params.put("itemID", productID);
                params.put("quantity", itemQty);
                Log.e(TAG, "params is "+params);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);
    }

    public void progressShow(){
        progressDialog = new ProgressDialog( ctx);
        progressDialog.setMessage(" Please wait...");
        progressDialog.setTitle("Adding to cart... ");
        progressDialog.show();
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, ProductModal obj, int pos);
    }

    public interface OnMoreButtonClickListener {
        void onItemClick(View view, ProductModal obj, MenuItem item);
    }
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    items = searchView;
                } else {

                    ArrayList<ProductModal> filteredList = new ArrayList<>();

                    for (ProductModal androidVersion : items) {

                        if (androidVersion.getProductName().toLowerCase().contains(charString)) {

                            filteredList.add(androidVersion);
                        }
                    }

                    items = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = items;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                items = (ArrayList<ProductModal>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


}