package com.example.Varsani.Clients;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SingleItem extends AppCompatActivity {
    private static final String TAG = "Add to cart";
    private SessionHandler session;
    private UserModel user;

    private String productID;
    private String productName;
    private String stock;
    private String desc;
    private String imagename;
    private String price;

    private EditText quantity;

    private Button btn_add_cart;
    private LinearLayout layout_single;

    TextView txv_price,txv_product_name,
            txv_description,txv_instock;
    ImageView imageView;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item);

        Intent intent = getIntent();
        productID = intent.getStringExtra("proID");
        productName = intent.getStringExtra("proName");
        stock = intent.getStringExtra("stock");
        desc = intent.getStringExtra("desc");
        imagename = intent.getStringExtra("image");
        price = intent.getStringExtra("price");

        getSupportActionBar().setSubtitle(productName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        session=new SessionHandler(getApplicationContext());
        user=session.getUserDetails();

        txv_product_name=findViewById(R.id.txv_product_name);
        txv_price=findViewById(R.id.txv_price);
        txv_description=findViewById(R.id.txv_ad_description);
        txv_instock=findViewById(R.id.txv_instock);
        imageView=findViewById(R.id.image_product);
        progressBar=findViewById(R.id.progressBar);
        btn_add_cart=findViewById(R.id.btn_add_cart);
        layout_single=findViewById(R.id.layout_single);

        progressBar.setVisibility(View.GONE);
        txv_product_name.setText(productName);
        txv_price.setText("KSH "+price+ "/=");
        txv_description.setText(desc);
        txv_instock.setText(stock+" Available");

        String url = Urls.ROOT_URL_IMAGES;
        Picasso.with(SingleItem.this).load(url+imagename )
                .fit()
                .centerCrop()
                .into(imageView );

        btn_add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(session.isLoggedIn()){

                    qtyPrompts();

                }else{
                    Toast.makeText(getApplicationContext(),"You must login to add item to carty",Toast.LENGTH_SHORT).show();
                }

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
    public void qtyPrompts(){
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.prompts, null);

        //Create the AlertDialog with a reference to edit it later
        final AlertDialog dialog = new AlertDialog.Builder(this)
//                .setView(v)
                .setCancelable(false)
                .setPositiveButton("Add", null) //Set to null. We override the onclick
                .setNegativeButton("Close", null)
                .create();
        dialog.setView(promptsView);

        quantity =  promptsView.findViewById(R.id.edt_quantity);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (TextUtils.isEmpty(quantity.getText().toString())) {
                            quantity.setError( "Enter item quantity" );
                        }else{
                            dialog.dismiss();
                            progressBar.setVisibility(View.VISIBLE);
                            addToCart();
                        }
                    }
                });
            }
        });
        dialog.show();
    }
    public void addToCart(){
        btn_add_cart.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

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
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                btn_add_cart.setVisibility(View.GONE);
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                btn_add_cart.setVisibility(View.VISIBLE);
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "error"+e.toString(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            btn_add_cart.setVisibility(View.VISIBLE);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "error"+error.toString(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                btn_add_cart.setVisibility(View.VISIBLE);


            }
        })
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("clientID", user.getClientID());
                params.put("productID", productID);
                params.put("quantity", itemQty);
                Log.e(TAG, "params is "+params);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
