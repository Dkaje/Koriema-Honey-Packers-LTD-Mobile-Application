package com.example.Varsani.Staff.Store_mrg;

import static com.example.Varsani.utils.Urls.URL_GET_DRIVERS;
import static com.example.Varsani.utils.Urls.URL_SEND_REQUEST;
import static com.example.Varsani.utils.Urls.URL_SHIP_ORDER;
import static com.example.Varsani.utils.Urls.URL_SUPPLIER;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RequestSupplier extends AppCompatActivity {

    private ProgressBar progressBar;
    private EditText edt_supplier,edt_item;
    private Button btn_submit;

    private ArrayList<String> suppliers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_supplier);

        progressBar=findViewById(R.id.progressBar);
        edt_item=findViewById(R.id.edt_items);
        edt_supplier=findViewById(R.id.edt_supplier);
        btn_submit=findViewById(R.id.btn_submit);

        btn_submit.setVisibility(View.GONE);
        edt_supplier.setVisibility(View.GONE);
        edt_item.setVisibility(View.GONE);

        edt_supplier.setFocusable(false);

        suppliers=new ArrayList<>();

        edt_supplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAlertSupplier(view);
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAlert(view);
            }
        });


        getSupplier();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void send(){

        final String username=edt_supplier.getText().toString().trim();
        final String items=edt_item.getText().toString().trim();

        if(TextUtils.isEmpty(username)){
            Toast toast= Toast.makeText(getApplicationContext(), "Please select a drive", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP,0,250);
            toast.show();
            return;
        }
        if(TextUtils.isEmpty(items)){
            Toast toast= Toast.makeText(getApplicationContext(), "Enter items to request", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP,0,250);
            toast.show();
            return;
        }
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_SEND_REQUEST,
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
            protected Map<String,String> getParams()throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("username",username);
                params.put("items",items);
                Log.e("PARAMS",""+params);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void getSupplier(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_SUPPLIER,
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
                                    suppliers.add(username);
                                }
                                progressBar.setVisibility(View.GONE);
                                btn_submit.setVisibility(View.VISIBLE);
                                edt_supplier.setVisibility(View.VISIBLE);
                                edt_item.setVisibility(View.VISIBLE);
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

    public void getAlertSupplier(View v){
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
//        builder.setTitle("Select county ");
        final String[] array = suppliers.toArray(new String[suppliers.size()]);
        builder.setNegativeButton("Close",null);
        builder.setSingleChoiceItems( array, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                edt_supplier.setText(array[i]);
                dialogInterface.dismiss();

            }
        });
        builder.show();

    }

    public void getAlert(View v){
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Confirm");
        final String[] array = suppliers.toArray(new String[suppliers.size()]);
        builder.setNegativeButton("Close",null);
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                send();

                return;
            }
        });
        builder.show();

    }
}