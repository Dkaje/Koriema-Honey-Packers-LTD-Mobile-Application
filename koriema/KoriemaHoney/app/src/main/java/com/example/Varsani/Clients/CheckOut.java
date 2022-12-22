package com.example.Varsani.Clients;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.example.Varsani.MainActivity;
import com.example.Varsani.utils.SessionHandler;
import com.example.Varsani.utils.Urls;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CheckOut extends AppCompatActivity {
    private SessionHandler session;
    private UserModel user;

    private LinearLayout layout_checkout;
    private RelativeLayout layout_card;
    private RelativeLayout layout_bottom;
    private TextView txv_name,txv_phoneNo,txv_email;
    private TextView txv_order_cost,txv_shipping_cost,txv_totalCost;
    private EditText edt_county,edt_town,edt_address,edt_mpesaCode;
    private ProgressBar progressBar,progressBarTown,progressCheckout;
    private Button btn_checkout;
    private RadioGroup radioGroup;
    private RadioButton rb_no_shipping,rb_shipping;

    private ArrayList<String> arrayCounties;
    private ArrayList<String> arrayTowns;
    ArrayAdapter<String> adapter;

    private  String countyName;
    private  String countyID;
    private  String townName;
    private  String o_cost;
    private  String s_cost;
    private  String shippingCost;
    private int cost;
    private int totalCost;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        getSupportActionBar().setTitle("Checkout");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layout_checkout=findViewById(R.id.layout_checkout);
        layout_card=findViewById(R.id.layout_card);
        layout_bottom=findViewById(R.id.layout_bottom);
        progressBar=findViewById(R.id.progressBar);
        progressCheckout=findViewById(R.id.progressCheckout);
        progressBarTown=findViewById(R.id.progressBarTown);
        btn_checkout=findViewById(R.id.btn_order);
        txv_email=findViewById(R.id.txv_email);
        txv_name=findViewById(R.id.txv_name);
        txv_phoneNo=findViewById(R.id.txv_phoneNo);
        txv_order_cost=findViewById(R.id.txv_order_cost);
        edt_mpesaCode=findViewById(R.id.edt_mpesaCode);
        txv_shipping_cost=findViewById(R.id.txv_shiping_cost);
        txv_totalCost=findViewById(R.id.txv_total_cost);
        edt_county=findViewById(R.id.edt_county);
        edt_town=findViewById(R.id.edt_town);
        edt_address=findViewById(R.id.edt_Address);
        edt_town=findViewById(R.id.edt_town);
        radioGroup=findViewById(R.id.rbtn_group);
        rb_no_shipping=findViewById(R.id.rb_no_shipping);
        rb_shipping=findViewById(R.id.rb_shipping);

        session=new SessionHandler(getApplicationContext());
        user=session.getUserDetails();

        layout_bottom.setVisibility(View.GONE);
        progressBarTown.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        progressCheckout.setVisibility(View.VISIBLE);
        layout_checkout.setVisibility(View.GONE);

        txv_name.setText(" "+user.getFirstname()+" "+user.getLastname());
        txv_email.setText(user.getEmail());
        txv_phoneNo.setText(user.getPhoneNo());

        edt_county.setFocusable(false);
        edt_town.setFocusable(false);

        arrayCounties =new ArrayList<>();
        arrayTowns=new ArrayList<>();

        edt_mpesaCode.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

        radioGroup.clearCheck();

        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertOrderNow(v);

            }


        });
        edt_county.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBarTown.setVisibility(View.VISIBLE);
                edt_town.setVisibility(View.GONE);
                edt_town.setText("");
                getAlertCounties(v);
            }
        });
        edt_town.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String checkCounty =edt_county.getText().toString().trim();
                if(TextUtils.isEmpty(checkCounty)){
                    Toast.makeText(getApplicationContext(),"Enter county to continue ",Toast.LENGTH_SHORT).show();
                }else{
                    getAlertTowns(v);

                }
            }
        });

        rb_no_shipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cost =Integer.parseInt(o_cost);   // order cost
                txv_totalCost.setText("Total  KES "+ cost);
                txv_shipping_cost.setText("Delivery  KES "+0);

            }
        });
        rb_shipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cost =Integer.parseInt(s_cost)+Integer.parseInt(o_cost); // get order total cost + shipping
                txv_totalCost.setText("Total  KES "+ cost);
                txv_shipping_cost.setText("Delivery KES "+s_cost);

            }
        });

        getDlvyDetails();
        getCounties();
        orderCost();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void getAlertCounties(View v){
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("County ");
        final String[] array = arrayCounties.toArray(new String[arrayCounties.size()]);
        builder.setNegativeButton("Close",null);
        builder.setSingleChoiceItems( array, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                edt_county.setText(array[i]);
                dialogInterface.dismiss();
                countyName=array[i];

                Toast.makeText(getApplicationContext(),countyName,Toast.LENGTH_SHORT).show();
                getTowns();


            }
        });
        builder.show();

    }
    public void getAlertTowns(View v){
        final android.app.AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Town");
        final String[] array = arrayTowns.toArray(new String[arrayTowns.size()]);
        builder.setSingleChoiceItems( array, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                edt_town.setText(array[i]);
                dialogInterface.dismiss();
                townName=array[i];
                builder.setNegativeButton("Close",null);

                getTowns();


            }
        });
        builder.show();

    }

    public void alertOrderNow(final  View v){
        AlertDialog.Builder  builder=new AlertDialog.Builder(v.getContext());
        builder.setTitle("Order/pay now ")
                .setNegativeButton("Close",null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        orderNow();
                        dialog.dismiss();
                    }
                }).create().show();
    }

    public void orderNow() {

        progressBar.setVisibility(View.VISIBLE);
        btn_checkout.setVisibility(View.GONE);
        final String county=edt_county.getText().toString().trim();
        final String mpesaCode=edt_mpesaCode.getText().toString().trim();
        final String address=edt_address.getText().toString().trim();
        final String townName=edt_town.getText().toString().trim();

        if(TextUtils.isEmpty(county)){
            Toast.makeText(getApplicationContext(),"Please enter your shipping county",Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            btn_checkout.setVisibility(View.VISIBLE);
            return;
        } if(TextUtils.isEmpty(townName)){
            Toast.makeText(getApplicationContext(),"Please enter your shipping town",Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            btn_checkout.setVisibility(View.VISIBLE);
            return;
        }
        if(TextUtils.isEmpty(address)){
            Toast.makeText(getApplicationContext(),"Please enter your shipping address",Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            btn_checkout.setVisibility(View.VISIBLE);
            return;
        }
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            // no radio buttons are checked
            progressBar.setVisibility(View.GONE);
            btn_checkout.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(),"Please select a shipping method",Toast.LENGTH_SHORT).show();
            return;
        } else if(rb_shipping.isChecked()) {
            totalCost=cost;

            shippingCost=s_cost;


        }else if(rb_no_shipping.isChecked()){
            totalCost=cost;
            shippingCost="0";
        }
        if(TextUtils.isEmpty(mpesaCode)){
            Toast.makeText(getApplicationContext(),"Please enter Mpesa code",Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            btn_checkout.setVisibility(View.VISIBLE);
            return;
        }
        if(mpesaCode.length()>10 ||mpesaCode.length()<10){
            Toast.makeText(getApplicationContext(), "Mpesa code  should contain 10 digits", Toast.LENGTH_SHORT).show();
            btn_checkout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
              return;
        }
        if(!mpesaCode.matches("^(?=.*[A-Z])(?=.*[0-9])[A-Z0-9]+$")){
            Toast.makeText(getApplicationContext(), "Mpesa code should have  characters and digit",
                    Toast.LENGTH_LONG).show();
            btn_checkout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return;
        }


        StringRequest stringRequest=new StringRequest(Request.Method.POST, Urls.URL_SUBMIT_ORDER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            String msg=jsonObject.getString("message");

                            if(status.equals("1")){
                                progressBar.setVisibility(View.GONE);
                                layout_card.setVisibility(View.GONE);
                                AlertDialog.Builder  builder=new AlertDialog.Builder(CheckOut.this);
                                builder.setTitle("SUCCESS ");
                                builder.setMessage(msg)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                                startActivity(i);
                                                dialog.dismiss();
                                            }
                                        });
                                        builder.setCancelable(false)
                                        .create().show();
                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                            }else if(status.equals("0")){
                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                btn_checkout.setVisibility(View.VISIBLE);

                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            Log.e("Error",e.toString());
                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.e("Error",error.toString());
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params= new HashMap<>();
                params.put("clientID",user.getClientID());
                params.put("countyID",countyID);
                params.put("townName",townName);
                params.put("address",address);
                params.put("orderCost",o_cost);
                params.put("shippingCost",shippingCost);
                params.put("mpesaCode",mpesaCode);
                params.put("totalCost", String.valueOf(totalCost));
                Log.e("values",""+params);
                return params;
            }

        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void getCounties(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Urls.URL_GET_COUNTIES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("Response"," "+response);
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            if(status.equals("1")){
                                JSONArray jsonArray=jsonObject.getJSONArray("counties");
                                for (int i =0; i <jsonArray.length();i++){
                                    JSONObject jsn=jsonArray.getJSONObject(i);
                                    String county=jsn.getString("countyName");
                                    arrayCounties.add(county);

                                }
                            }

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
        });

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void getTowns(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Urls.URL_GET_TOWNS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            Log.e("Response",""+response);
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            if (status.equals("1")){
                                JSONArray jsonArray= jsonObject.getJSONArray("towns");
                                arrayTowns.clear();
                                progressBarTown.setVisibility(View.GONE);
                                edt_town.setVisibility(View.VISIBLE);
                                for(int i=0;i <jsonArray.length();i++){
                                    JSONObject jsn=jsonArray.getJSONObject(i);
                                    String towns=jsn.getString("townName");
                                    String ID=jsn.getString("countyID");
                                    arrayTowns.add(towns);

                                    countyID=ID;
                                }
                            }else{
                                String msg=jsonObject.getString("message");
                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                            }

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
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String,String>params=new HashMap<>();
                params.put("countyName",countyName);
                return params;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public  void getDlvyDetails(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Urls.URL_DELIVERY_DETAILS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("Response",""+response);
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            if(status.equals("1")){
                                JSONArray jsonArray=jsonObject.getJSONArray("details");
                                for (int i=0; i<jsonArray.length();i++){
                                    JSONObject jsn=jsonArray.getJSONObject(i);
                                    String county_ID=jsn.getString("county_ID");
                                    String county=jsn.getString("county");
                                    String town=jsn.getString("town");
                                    String ship_address=jsn.getString("ship_address");

                                    edt_county.setText(county);
                                    edt_town.setText(town);
                                    edt_address.setText(ship_address);
                                    countyID=county_ID;

                                }

                            }else if (status.equals("0")){
                                String msg=jsonObject.getString("message");

                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                            }

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
            protected Map<String ,String>getParams() throws AuthFailureError{
                Map<String,String>params=new HashMap<>();
                params.put("clientID",user.getClientID());
                return params;
            }

        };
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public  void orderCost(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Urls.URL_GET_CHECKOUT_TOTAL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("Response","d "+response);
                            JSONObject jsonObject= new JSONObject(response);
                            String status=jsonObject.getString("status");

                            if(status.equals("1")){
                                String orderCost=jsonObject.getString("orderCost");
                                String  deliveryCost=jsonObject.getString("deliveryCost");
                                txv_order_cost.setText("Order cost Ksh "+orderCost);
                                txv_shipping_cost.setText("Delivery cost Ksh "+deliveryCost);
                                o_cost=orderCost;
                                s_cost=deliveryCost;
                                layout_bottom.setVisibility(View.VISIBLE);
                                layout_checkout.setVisibility(View.VISIBLE);
                                progressCheckout.setVisibility(View.GONE);
                            }

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
            protected  Map<String ,String> getParams()throws AuthFailureError{
                Map<String,String> params=new HashMap<>()  ;
                params.put("clientID",user.getClientID());

                return params;

            }

        };
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
