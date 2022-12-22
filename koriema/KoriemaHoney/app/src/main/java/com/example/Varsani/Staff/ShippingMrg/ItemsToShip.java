package com.example.Varsani.Staff.ShippingMrg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import static com.example.Varsani.utils.Urls.URL_GET_CLIENT_ITEMS;
import static com.example.Varsani.utils.Urls.URL_GET_DRIVERS;
import static com.example.Varsani.utils.Urls.URL_SHIP_ORDER;

public class ItemsToShip extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProgressBar progressBar,progressBar1;
    private TextView txv_name,txv_orderID,txv_orderStatus,
            txv_orderDate,txv_address,txv_town,
            txv_county,txv_mpesaCode;
    private List<ClientItemsModal> list;
    private AdapterClientItems adapterClientItems;
    private RelativeLayout layout_bottom;
    private Button btn_ship;
    private ArrayList<String> drivers;
    private EditText edt_driver;

    String orderID;
    String orderStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_to_ship);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layout_bottom=findViewById(R.id.layout_bottom);
        progressBar=findViewById(R.id.progressBar);
        progressBar1=findViewById(R.id.progressBar1);
        recyclerView=findViewById(R.id.recyclerView);
        txv_address=findViewById(R.id.txv_address);
        txv_town=findViewById(R.id.txv_town);
        txv_county=findViewById(R.id.txv_county);
        txv_name=findViewById(R.id.txv_name);
        txv_mpesaCode=findViewById(R.id.txv_mpesaCode);
        txv_orderStatus=findViewById(R.id.txv_orderStatus);
        txv_orderID=findViewById(R.id.txv_orderID);
        txv_orderDate=findViewById(R.id.txv_orderDate);
        btn_ship=findViewById(R.id.btn_submit);
        edt_driver=findViewById(R.id.edt_driver);

        layout_bottom.setVisibility(View.GONE);
        progressBar1.setVisibility(View.GONE);
        edt_driver.setFocusable(false);
//        edt_driver.setEnabled(false);

        drivers=new ArrayList<>();

        Intent intent=getIntent();

        orderID=intent.getStringExtra("orderID");
        String orderDate=intent.getStringExtra("orderDate");
        orderStatus=intent.getStringExtra("orderStatus");
        String clientName=intent.getStringExtra("clientName");
        String address=intent.getStringExtra("address");
        String town=intent.getStringExtra("town");
        String county=intent.getStringExtra("county");

        txv_orderDate.setText(" Order date " + orderDate);
        txv_orderStatus.setText(orderStatus );
        txv_name.setText("Name " +clientName );
        txv_town.setText(town );
        txv_county.setText(county+"-"+town );
        txv_address.setText("Address " +address );
        txv_orderID.setText("Order ID " +orderID );


        txv_orderDate.setVisibility(View.GONE);
        txv_town.setVisibility(View.GONE);

        list=new ArrayList<>();

        recyclerView.setLayoutManager( new LinearLayoutManager(getApplicationContext()));
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getApplicationContext(),1);
        recyclerView.setLayoutManager(layoutManager);

        edt_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAlertDrivers(v);
            }
        });



        btn_ship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAlertShip(v);
            }
        });

        getClientItems();
        getDrivers();
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
                                if(orderStatus.equals("Pending shipping")){
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
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params =new HashMap<>();
                params.put("orderID",orderID);
                Log.e("Params",""+ params);
                return  params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


    public void shipOrder(){

        final String username=edt_driver.getText().toString().trim();

        if(TextUtils.isEmpty(username)){
            Toast toast= Toast.makeText(getApplicationContext(), "Please select a drive", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP,0,250);
            toast.show();
            return;
        }
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_SHIP_ORDER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.e("RESPONSE",response);
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            String msg=jsonObject.getString("message");
                            if (status.equals("1")){

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
            }
        }){
            @Override
            protected Map<String,String>getParams()throws AuthFailureError{
                Map<String,String> params=new HashMap<>();
                params.put("orderID",orderID);
                params.put("username",username);
                Log.e("PARAMS",""+params);
                return params;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void getDrivers(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_GET_DRIVERS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            Log.e("RESPONSE", response);
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            String msg=jsonObject.getString("message");
                            if(status.equals("1")){
                                JSONArray jsonArray=jsonObject.getJSONArray("details");
                                for(int i=0;i <jsonArray.length();i++){
                                    JSONObject jsn=jsonArray.getJSONObject(i);
                                    String username=jsn.getString("username");
                                    drivers.add(username);
                                }
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            Toast toast= Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0,250);
                            toast.show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast toast= Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0,250);
                toast.show();
            }
        });
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void getAlertDrivers(View v){
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
//        builder.setTitle("Select county ");
        final String[] array = drivers.toArray(new String[drivers.size()]);
        builder.setNegativeButton("Close",null);
        builder.setSingleChoiceItems( array, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                edt_driver.setText(array[i]);
                dialogInterface.dismiss();

            }
        });
        builder.show();

    }

    public void getAlertShip(View v){
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Confirm");
        final String[] array = drivers.toArray(new String[drivers.size()]);
        builder.setNegativeButton("Close",null);
        builder.setPositiveButton("Ship", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                shipOrder();

                return;
            }
        });
        builder.show();

    }
}
