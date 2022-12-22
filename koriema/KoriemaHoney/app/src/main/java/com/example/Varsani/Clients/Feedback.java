package com.example.Varsani.Clients;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
import com.example.Varsani.Clients.Adapters.AdapterFeedback;
import com.example.Varsani.Clients.Models.FeedbackModel;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Feedback extends AppCompatActivity {
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private List<FeedbackModel> list;
    private AdapterFeedback adapterFeedback;
    private SessionHandler session;
    private UserModel user;
    private EditText edt_feedback;
    private Button btn_feedback;
    private Spinner spinner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressBar=findViewById(R.id.progressBar);
        recyclerView=findViewById(R.id.recyclerView);
        btn_feedback=findViewById(R.id.btn_feedback);
        edt_feedback=findViewById(R.id.edt_feedback);
        spinner = findViewById(R.id.select_recipient);

        ArrayAdapter<CharSequence>  adapter= ArrayAdapter.createFromResource(this,R.array.recipient,R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

//        progressBar=findViewById(R.id.progressBar);
//        progressBar.setVisibility(View.GONE);
        session=new SessionHandler(getApplicationContext());
        user=session.getUserDetails();
        list=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        RecyclerView.LayoutManager layoutManager= new GridLayoutManager(getApplicationContext(),1);
        recyclerView.setLayoutManager(layoutManager);

//
        btn_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }
        });
        getFeedback();
    }
//
@Override
public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
        finish();
    }
    return super.onOptionsItemSelected(item);
}
    public void getFeedback(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Urls.UEL_FEEDBACK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("RESPONSE",response);
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            String msg=jsonObject.getString("message");
                            if(status.equals("1")){
                                list.clear();
                                JSONArray jsonArray=jsonObject.getJSONArray("details");
                                for(int i=0;i <jsonArray.length();i++){
                                    JSONObject jsn=jsonArray.getJSONObject(i);
                                    String comment=jsn.getString("comment");
                                    String reply=jsn.getString("reply");
                                    FeedbackModel feedbackModel=new FeedbackModel(comment,reply);
                                    list.add(feedbackModel);
                                }
                                adapterFeedback=new AdapterFeedback(getApplicationContext(),list);
                                recyclerView.setAdapter(adapterFeedback);
                                progressBar.setVisibility(View.GONE);
                            }else{
                                Toast.makeText(Feedback.this, msg, Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(Feedback.this, e.toString(), Toast.LENGTH_SHORT).show();
                            Log.e("ERROR 1",e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(Feedback.this, error.toString(), Toast.LENGTH_SHORT).show();
                Log.e("ERROR 2",error.toString());
            }
        }){
            @Override
            protected Map<String,String>getParams()throws AuthFailureError{
                Map<String,String>params=new HashMap<>();
                params.put("userID",user.getClientID());
                Log.e("PARAMS","" +params);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
    @Override
    public void onRestart(){
        super.onRestart();
        fileList();
        startActivity(getIntent());
    }
    public void send(){
        String recipient = spinner.getSelectedItem().toString();
        progressBar.setVisibility(View.VISIBLE);
        btn_feedback.setVisibility(View.GONE);
        final String feedback=edt_feedback.getText().toString().trim();

        if(TextUtils.isEmpty(feedback)){
            Toast.makeText(getApplicationContext(), "Please write a comment", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            btn_feedback.setVisibility(View.VISIBLE);
            return;
        }
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Urls.UEL_FEEDBACK_SEND,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("RESPONSE",response);
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            String msg=jsonObject.getString("message");
                            if(status.equals("1")){
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

                                edt_feedback.setText("");
                                progressBar.setVisibility(View.GONE);
                                btn_feedback.setVisibility(View.VISIBLE);
                                getFeedback();
                            }else{
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

                                progressBar.setVisibility(View.GONE);
                                btn_feedback.setVisibility(View.VISIBLE);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            btn_feedback.setVisibility(View.VISIBLE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                btn_feedback.setVisibility(View.VISIBLE);
            }
        }){
            @Override
            protected Map<String,String> getParams()throws AuthFailureError {
                Map<String,String>params=new HashMap<>();
                params.put("feedback",feedback);
                params.put("userID",user.getClientID());
                params.put("recipient",recipient);
                Log.e("PARAMS",""+params);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);



    }
}
