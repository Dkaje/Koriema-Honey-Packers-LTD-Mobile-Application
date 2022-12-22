package com.example.Varsani.Clients;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.example.Varsani.Clients.Adapters.AdapterCart;
import com.example.Varsani.Clients.Models.CartModal;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartItems extends AppCompatActivity {
    private List<CartModal> list;
    private AdapterCart adapterCart;
    private SessionHandler session;
    private UserModel user;
    private LinearLayout layout_bottom;
    private TextView txv_cart_total,txv_success;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Button btn_cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_items);

        getSupportActionBar().setTitle("Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar=findViewById(R.id.progressBar);
        recyclerView=findViewById(R.id.recyclerView);
        txv_cart_total=findViewById(R.id.txv_cart_total);
        txv_success=findViewById(R.id.txv_success);
        layout_bottom=findViewById(R.id.layout_bottom);
        btn_cart=findViewById(R.id.btn_cart);

        layout_bottom.setVisibility(View.GONE);
        txv_success.setVisibility(View.GONE);
        txv_cart_total.setVisibility(View.GONE);

        recyclerView.setLayoutManager( new LinearLayoutManager( getApplicationContext() ) );
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);

        list=new ArrayList<>();

        session=new SessionHandler(getApplicationContext());
        user=session.getUserDetails();

        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),CheckOut.class);
                startActivity(intent);
            }
        });

        getProdcuts();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void getProdcuts(){

        StringRequest stringRequest=new StringRequest(Request.Method.POST, Urls.URL_GET_CART,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{

                            Log.e("Response",""+response);
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");


                            if(status.equals("1")){
                                JSONArray jsonArray=jsonObject.getJSONArray("items");
                                for (int i=0; i < jsonArray.length();i++){
                                    JSONObject jsn=jsonArray.getJSONObject(i);
                                    String itemID=jsn.getString("itemID");
                                    String productID=jsn.getString("productID");
                                    String productName=jsn.getString("productName");
                                    String price=jsn.getString("price");
                                    String quantity=jsn.getString("quantity");
                                    String image=jsn.getString("image");
                                    String subTotal=jsn.getString("subTotal");
                                    String stock=jsn.getString("stock");

                                    CartModal cartModal=new CartModal( productID,  productName,  quantity,  price,  image,  itemID , subTotal,stock);
                                    list.add(cartModal);

                                }
                                progressBar.setVisibility(View.GONE);
                                layout_bottom.setVisibility(View.VISIBLE);
                                adapterCart= new AdapterCart(getApplicationContext(),list);

                                recyclerView.setAdapter(adapterCart);

                                String cartTotal=jsonObject.getString("cartTotal");

                                txv_cart_total.setText("Cart total Ksh: "+cartTotal);

                                btn_cart.setText("KSH "+cartTotal+"/= Checkout");

                            }else if (status.equals("0")){
                                Toast.makeText(getApplicationContext(),"Shopping cart is empty",Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                txv_success.setVisibility(View.VISIBLE);

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
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("clientID",user.getClientID());

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
