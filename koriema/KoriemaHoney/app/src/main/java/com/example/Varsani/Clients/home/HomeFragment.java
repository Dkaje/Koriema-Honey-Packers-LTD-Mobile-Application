package com.example.Varsani.Clients.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.Clients.Adapters.AdapterProducts;
import com.example.Varsani.MainActivity;
import com.example.Varsani.utils.SessionHandler;
import com.example.Varsani.utils.Urls;
import com.example.Varsani.Clients.Models.ProductModal;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private SessionHandler session;
    private UserModel user;
    private List<ProductModal> list;
    private AdapterProducts adapterProducts;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        progressBar=root.findViewById(R.id.progressBar);
        recyclerView=root.findViewById(R.id.recyclerView);


        recyclerView.setLayoutManager( new LinearLayoutManager( getContext()));
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        session=new SessionHandler(getContext());
        user=session.getUserDetails();
        list= new ArrayList<>();
        getProdcuts();
        return root;
    }
    public void getProdcuts(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Urls.URL_GET_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{

                            Log.e("Response",""+response);
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");

                            if(status.equals("1")){
                                JSONArray jsonArray=jsonObject.getJSONArray("products");
                                for (int i=0; i < jsonArray.length();i++){
                                    JSONObject jsn=jsonArray.getJSONObject(i);
                                    String productID=jsn.getString("productID");
                                    String productName=jsn.getString("productName");
                                    String price=jsn.getString("price");
                                    String stock=jsn.getString("stock");
                                    String image=jsn.getString("image");
                                    String desc=jsn.getString("desc");

                                    ProductModal productModal=new ProductModal( productID, productName, stock, price, image,desc);
                                    list.add(productModal);
                                }
                                progressBar.setVisibility(View.GONE);
                                adapterProducts=new AdapterProducts(getContext(),list);
                                recyclerView.setAdapter(adapterProducts);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

}
