package com.example.Varsani.Staff.Store_mrg;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.utils.Urls;
import com.example.Varsani.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddStock extends AppCompatActivity {

    private Button btn_submit;
    private EditText edt_quantity;
    private ProgressBar progressBar;
    private TextView txv_productName,txv_stock,txv_price;

    String stockID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setSubtitle("Add new stock");

        progressBar=findViewById(R.id.progressBar);
        edt_quantity=findViewById(R.id.edt_quantity);
        btn_submit=findViewById(R.id.btn_submit);

        txv_productName=findViewById(R.id.txv_product_name);
        txv_price=findViewById(R.id.txv_price);
        txv_stock=findViewById(R.id.txv_stock);

        progressBar.setVisibility(View.GONE);

        Intent intent=getIntent();
        stockID=intent.getStringExtra("stockID");
        String productName=intent.getStringExtra("productName");
        String stock=intent.getStringExtra("stock");
        String price=intent.getStringExtra("price");

        txv_stock.setText("Stock "+stock);
        txv_price.setText("@ KSH "+price);
        txv_productName.setText(productName);




        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
alertStock();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void alertStock(){

        AlertDialog .Builder alertDialog = new AlertDialog.Builder(AddStock.this);
        alertDialog.setMessage("Add stock");
        alertDialog.setNegativeButton("Close",null);
        alertDialog.setPositiveButton("Add Stock", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addStock();
            }
        });

        alertDialog.show();
    }


    public void addStock(){

        btn_submit.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        final  String quantity=edt_quantity.getText().toString().trim();

        if(TextUtils.isEmpty(quantity)){
            Toast toast=Toast.makeText(getApplicationContext(),"Please enter stock quantity",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP,0,250);
            toast.show();
            btn_submit.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return;
        }
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Urls.URL_ADD_STOCK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.e("RESPONSE",response);
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            String msg=jsonObject.getString("message");
                            if(status.equals("1")){
                                Toast toast=Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP,0,250);
                                toast.show();
                                finish();
                            }else{
                                Toast toast=Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP,0,250);
                                toast.show();

                                btn_submit.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            Toast toast=Toast.makeText(getApplicationContext(),"Please enter stock quantity",Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP,0,250);
                            toast.show();
                            btn_submit.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast toast=Toast.makeText(getApplicationContext(),"Please enter stock quantity",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,250);
                toast.show();
                btn_submit.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        }){
            @Override
            protected Map<String,String> getParams()throws AuthFailureError{
                Map<String,String>params=new HashMap<>();
                params.put("quantity",quantity);
                params.put("stockID",stockID);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
