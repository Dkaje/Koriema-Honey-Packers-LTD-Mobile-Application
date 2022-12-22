package com.example.Varsani.Staff.Finance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.Staff.Models.ClientItemsModal;
import com.example.Varsani.R;
import com.example.Varsani.Staff.Adapters.AdapterClientItems;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.Varsani.utils.Urls.URL_GET_APPROVE_ORDERS;
import static com.example.Varsani.utils.Urls.URL_GET_CLIENT_ITEMS;

public class OrderDetails extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView txv_name,txv_orderID,txv_orderStatus,txv_orderDate,
            txv_orderCost,txv_itemCost,txv_shippingCost,txv_address,txv_town,
           txv_county,txv_mpesaCode;
    private List<ClientItemsModal>list;
    private AdapterClientItems adapterClientItems;
    private RelativeLayout layout_bottom;
    private Button btn_appprove,btn_reject;

    String orderID;
    String orderStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        getSupportActionBar().setSubtitle("Order details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layout_bottom=findViewById(R.id.layout_bottom);
        progressBar=findViewById(R.id.progressBar);
        recyclerView=findViewById(R.id.recyclerView);
        txv_address=findViewById(R.id.txv_address);
        txv_town=findViewById(R.id.txv_town);
        txv_county=findViewById(R.id.txv_county);
        txv_name=findViewById(R.id.txv_name);
        txv_mpesaCode=findViewById(R.id.txv_mpesaCode);
        txv_orderCost=findViewById(R.id.txv_orderCost);
        txv_orderStatus=findViewById(R.id.txv_orderStatus);
        txv_shippingCost=findViewById(R.id.txv_shippingCost);
        txv_itemCost=findViewById(R.id.txv_itemCost);
        txv_orderID=findViewById(R.id.txv_orderID);
        txv_orderDate=findViewById(R.id.txv_orderDate);
        layout_bottom.setVisibility(View.GONE);
        btn_appprove=findViewById(R.id.btn_submit);
        btn_reject=findViewById(R.id.btn_reject);
        Intent intent=getIntent();

         orderID=intent.getStringExtra("orderID");
        String orderDate=intent.getStringExtra("orderDate");
         orderStatus=intent.getStringExtra("orderStatus");
        String orderCost=intent.getStringExtra("orderCost");
        String shippingCost=intent.getStringExtra("shippingCost");
        String itemCost=intent.getStringExtra("itemCost");
        String mpesaCode=intent.getStringExtra("mpesaCode");
        String clientName=intent.getStringExtra("clientName");
        String address=intent.getStringExtra("address");
        String town=intent.getStringExtra("town");
        String county=intent.getStringExtra("county");

        txv_orderDate.setText("Date " + orderDate);
        txv_orderCost.setText("KES " + orderCost);
        txv_orderStatus.setText(orderStatus );
        txv_mpesaCode.setText("Code " + mpesaCode);
        txv_shippingCost.setText("Shipping cost " + shippingCost);
        txv_itemCost.setText("Item cost " + itemCost);
        txv_name.setText(clientName );
        txv_town.setText("Town " +town );
        txv_county.setText(county +"-"+town);
        txv_address.setText("Address " +address );
        txv_orderID.setText("Order ID " +orderID );

        txv_itemCost.setVisibility(View.GONE);
        txv_shippingCost.setVisibility(View.GONE);
        txv_town.setVisibility(View.GONE);

        list=new ArrayList<>();

        recyclerView.setLayoutManager( new LinearLayoutManager(getApplicationContext()));
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getApplicationContext(),1);
        recyclerView.setLayoutManager(layoutManager);

        btn_appprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertApprove();
            }
        });
         btn_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        getClientItems();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void getClientItems(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_GET_CLIENT_ITEMS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.e("RESPONSE", response);
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            String msg=jsonObject.getString("message");

                            if(status.equals("1")){
                                JSONArray jsonArray=jsonObject.getJSONArray("details");
                                for(int i=0; i <jsonArray.length();i++){
                                    JSONObject jsn=jsonArray.getJSONObject(i);
                                    String itemName=jsn.getString("itemName");
                                    String quantity=jsn.getString("quantity");
                                    String itemPrice=jsn.getString("itemPrice");
                                    String subTotal=jsn.getString("subTotal");
                                    ClientItemsModal clientItemsModal=new ClientItemsModal(itemName,itemPrice,quantity,subTotal);
                                    list.add(clientItemsModal);
                                }
                                adapterClientItems=new AdapterClientItems(getApplicationContext(),list);
                                recyclerView.setAdapter(adapterClientItems);
                                progressBar.setVisibility(View.GONE);
                                if(orderStatus.equals("Pending approval")){
                                    layout_bottom.setVisibility(View.VISIBLE);
                                }

                            }else{
                                progressBar.setVisibility(View.GONE);
                                Toast toast=Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP,0,250);
                                toast.show();
                            }


                        }catch (Exception e){
                            e.printStackTrace();
                            Toast toast=Toast.makeText(getApplicationContext(), e.toString(),Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP,0,250);
                            toast.show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast toast=Toast.makeText(getApplicationContext(), error.toString(),Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,250);
                toast.show();
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String,String> params =new HashMap<>();
                params.put("orderID",orderID);
                Log.e("Params",""+ params);
                return  params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


    public void approveOrder(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_GET_APPROVE_ORDERS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.e("RESPONSE",response);
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            String msg=jsonObject.getString("message");
                            if (status.equals("1")){

                                Toast toast= Toast.makeText(OrderDetails.this, msg, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP,0,250);
                                toast.show();
                                finish();
                            }else{

                                Toast toast= Toast.makeText(OrderDetails.this, msg, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP,0,250);
                                toast.show();
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            Toast toast= Toast.makeText(OrderDetails.this, e.toString(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP,0,250);
                            toast.show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
               Toast toast= Toast.makeText(OrderDetails.this, error.toString(), Toast.LENGTH_SHORT);
               toast.setGravity(Gravity.TOP,0,250);
               toast.show();
            }
        }){
            @Override
            protected Map<String,String>getParams()throws AuthFailureError{
                Map<String,String> params=new HashMap<>();
                params.put("orderID",orderID);
                Log.e("PARAMS",""+params);
                return params;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void alertApprove(){
        android.app.AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage("Confirm");
        alertDialog.setCancelable(false);
        alertDialog.setButton2("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

                return;
            }
        });
        alertDialog.setButton("Approve ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                approveOrder();
                return;
            }
        });
        alertDialog.show();
    }
}
