package com.example.Varsani.Suppliers;

import static com.example.Varsani.utils.Urls.URL_ACCEPT;
import static com.example.Varsani.utils.Urls.URL_GET_APPROVE_ORDERS;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.R;
import com.example.Varsani.Staff.Finance.OrderDetails;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Accept extends AppCompatActivity {
    private TextView txv_requestID,txv_name,txv_items,
            txv_requestDate, txv_requestStatus;

    private Button btn_submit;
    private ProgressBar progressBar;
    private String requestID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txv_items =findViewById(R.id.txv_items);
        txv_name =findViewById(R.id.txv_name);
        txv_requestID = findViewById(R.id.txv_requestID);
        txv_requestStatus = findViewById(R.id.txv_requestStatus);
        txv_requestDate = findViewById(R.id.txv_requestDate);
        progressBar=findViewById(R.id.progressBar);
        btn_submit=findViewById(R.id.btn_submit);

        progressBar.setVisibility(View.GONE);

        Intent in=getIntent();
        requestID=in.getStringExtra("requestID");
        txv_requestID.setText("ID "+in.getStringExtra("requestID"));
        txv_items.setText("Item/s "+in.getStringExtra("item"));
        txv_requestDate.setText("Date "+in.getStringExtra("requestDate"));
        txv_requestStatus.setText("Status "+in.getStringExtra("requestStatus"));


        btn_submit.setOnClickListener(v-> approve());
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void approve(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_ACCEPT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.e("RESPONSE",response);
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            String msg=jsonObject.getString("message");
                            if (status.equals("1")){

                                Toast toast= Toast.makeText(Accept.this, msg, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP,0,250);
                                toast.show();
                                finish();
                            }else{

                                Toast toast= Toast.makeText(Accept.this, msg, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP,0,250);
                                toast.show();
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            Toast toast= Toast.makeText(Accept.this, e.toString(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP,0,250);
                            toast.show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast toast= Toast.makeText(Accept.this, error.toString(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,250);
                toast.show();
            }
        }){
            @Override
            protected Map<String,String> getParams()throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("requestID",requestID);
                Log.e("PARAMS",""+params);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
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

                approve();
                return;
            }
        });
        alertDialog.show();
    }
}