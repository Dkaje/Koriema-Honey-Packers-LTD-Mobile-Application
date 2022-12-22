package com.example.Varsani.Clients;

import androidx.appcompat.app.AppCompatActivity;
import androidx.print.PrintHelper;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.utils.SessionHandler;
import com.example.Varsani.utils.Urls;
import com.example.Varsani.Clients.Adapters.AdapterOrdersItems;
import com.example.Varsani.Clients.Models.OrderItemModal;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderItems extends AppCompatActivity {

    private AdapterOrdersItems adapterOrdersItems;
    private List<OrderItemModal> list;
    private SessionHandler session;
    private UserModel user;

    private ProgressBar progressBar,progressBar1;
    private RecyclerView recyclerView;
    private TextView txv_orderID,txv_orderCost,txv_orderDate,
            txv_orderStatus,txv_mapesaCose;
    private TextView txv_shippingCost,txv_itemCost,txv_name;
    private Button btn_mark_order, btn_reject_order;
    private ImageView btn_print;

    private String orderID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_items);

        getSupportActionBar().setTitle("Order items");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView=findViewById(R.id.recyclerView);
        progressBar=findViewById(R.id.progressBar);
        progressBar1=findViewById(R.id.progressBar1);


        txv_orderID=findViewById(R.id.txv_orderID);
        txv_orderCost=findViewById(R.id.txv_orderCost);
        txv_orderDate=findViewById(R.id.txv_orderDate);
        txv_orderStatus=findViewById(R.id.txv_orderStatus);
        txv_mapesaCose=findViewById(R.id.txv_mpesaCode);
        txv_shippingCost=findViewById(R.id.txv_shippingCost);
        txv_itemCost=findViewById(R.id.txv_itemCost);
        btn_mark_order=findViewById(R.id.btn_mark_order);
        btn_print=findViewById(R.id.btn_print);
        txv_name=findViewById(R.id.txv_name);
        btn_reject_order = findViewById(R.id.btn_reject_order);

        session=new SessionHandler(getApplicationContext());
        user=session.getUserDetails();

        txv_name.setText(user.getFirstname()+" "+user.getLastname());

        btn_mark_order.setVisibility(View.GONE);
        btn_reject_order.setVisibility(View.GONE);
        progressBar1.setVisibility(View.GONE);

        final Intent intent=getIntent();
        orderID=intent.getStringExtra("orderID");
        String mpesaCode=intent.getStringExtra("mpesaCode");
        String orderCost=intent.getStringExtra("orderCost");
        String orderStatus=intent.getStringExtra("orderStatus");
        String orderDate=intent.getStringExtra("orderDate");
        String shippingCost=intent.getStringExtra("shippingCost");
        String itemCost=intent.getStringExtra("itemCost");


        txv_orderStatus.setText(orderStatus);
        txv_orderDate.setText("Order date "+orderDate);
        txv_orderCost.setText("Total Cost ksh "+orderCost);
        txv_orderID.setText("Order No "+orderID);
        txv_mapesaCose.setText("Mpesa code "+mpesaCode);
        txv_shippingCost.setText("Shipping cost "+shippingCost);
        txv_itemCost.setText("Item cost "+itemCost);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getApplicationContext(),1);
        recyclerView.setLayoutManager(layoutManager);

        session=new SessionHandler( getApplicationContext());
        user=session.getUserDetails();
        list=new ArrayList<>();

        if(orderStatus.equals("Confirm delivery")){
            btn_mark_order.setVisibility(View.VISIBLE);
            btn_reject_order.setVisibility(View.VISIBLE);
        }
        btn_mark_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAlert(v);
            }
        });

        btn_reject_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAlert2(v);
            }
        });

        btn_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                print();
            }
        });

        getOrderItems();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void getOrderItems(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Urls.URL_GET_ORDER_ITEMS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.e("Response"," "+response);
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            if(status.equals("1")){
                                JSONArray jsonArray=jsonObject.getJSONArray("items");
                                for(int i=0; i <jsonArray.length();i++){
                                    JSONObject jsn=jsonArray.getJSONObject(i);
                                    String itemName = jsn.getString("itemName");
                                    String itemPrice = jsn.getString("itemPrice");
                                    String quantity = jsn.getString("quantity");
                                    String subTotal = jsn.getString("subTotal");

                                    OrderItemModal orderItemModal = new OrderItemModal(itemName, itemPrice, quantity, subTotal);
                                    list.add(orderItemModal);
                                    Log.e("Array","" +itemName);

                                }
                            }
                            progressBar.setVisibility(View.GONE);
                            adapterOrdersItems=new AdapterOrdersItems(getApplicationContext(),list);
                            recyclerView.setAdapter(adapterOrdersItems);

                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params =new HashMap<>();
                params.put("orderID",orderID);
                return params;
            }

        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void getAlert(View v){
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Confirm");

        builder.setNegativeButton("Close",null);
        builder.setPositiveButton("Confirm delivery", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                markDelivered();

                return;
            }
        });
        builder.show();

    }

    public void getAlert2(View v){
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Reject");

        builder.setNegativeButton("Close",null);
        builder.setPositiveButton("Reject delivery", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                markDelivered();

                return;
            }
        });
        builder.show();

    }

    public void markDelivered(){
        btn_mark_order.setVisibility(View.GONE);
        progressBar1.setVisibility(View.VISIBLE);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, Urls.URL_MARK_DELIVERED,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.e("RESPONSE",response);
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            String msg=jsonObject.getString("message");
                            if(status.equals("1")){
                                Toast toast= Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP,0,250);
                                toast.show();
                                finish();
                            }else{
                                Toast toast= Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP,0,250);
                                toast.show();
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            btn_mark_order.setVisibility(View.GONE);
                            progressBar1.setVisibility(View.GONE);
                            Toast toast= Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP,0,250);
                            toast.show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast toast= Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,250);
                toast.show();
                btn_mark_order.setVisibility(View.VISIBLE);
                btn_reject_order.setVisibility(View.VISIBLE);
                progressBar1.setVisibility(View.GONE);

            }
        }){
            @Override
            protected Map<String,String>getParams()throws AuthFailureError{
                Map<String,String>params=new HashMap<>();
                params.put("orderID",orderID);
                return  params;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


    private void print(){
        btn_print.setVisibility(View.GONE);

        View view = getWindow().getDecorView().findViewById(android.R.id.content);
        view.setDrawingCacheEnabled(true);
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),View. MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        PrintHelper photoPrinter = new PrintHelper(this); // Assume that 'this' is your activity
        photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        photoPrinter.printBitmap("print", bitmap);

        btn_print.setVisibility(View.VISIBLE);
    }
}
