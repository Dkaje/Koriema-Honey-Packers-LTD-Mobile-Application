package com.example.Varsani.Clients;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.example.Varsani.MainActivity;
import com.example.Varsani.utils.SessionHandler;
import com.example.Varsani.utils.Urls;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EndFeedback extends AppCompatActivity {
    private SessionHandler session;
    private UserModel user;
    private EditText edt_feedback;
    private Button btn_send;
    private ProgressBar progressBar1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_feedback);

        btn_send=findViewById(R.id.btn_send);
        edt_feedback=findViewById(R.id.edt_feedback);
        progressBar1=findViewById(R.id.progressBar);
        progressBar1.setVisibility(View.GONE);
        session=new SessionHandler(getApplicationContext());
        user=session.getUserDetails();
                btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }
        });
    }
    public void send(){
        progressBar1.setVisibility(View.VISIBLE);
        btn_send.setVisibility(View.GONE);
        final String feedback=edt_feedback.getText().toString().trim();

        if(TextUtils.isEmpty(feedback)){
            Toast.makeText(getApplicationContext(), "Please write a comment", Toast.LENGTH_SHORT).show();
            progressBar1.setVisibility(View.GONE);
            btn_send.setVisibility(View.VISIBLE);
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
                                progressBar1.setVisibility(View.GONE);
                                btn_send.setVisibility(View.VISIBLE);
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

                                progressBar1.setVisibility(View.GONE);
                                btn_send.setVisibility(View.VISIBLE);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                            progressBar1.setVisibility(View.GONE);
                            btn_send.setVisibility(View.VISIBLE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                progressBar1.setVisibility(View.GONE);
                btn_send.setVisibility(View.VISIBLE);
            }
        }){
            @Override
            protected Map<String,String> getParams()throws AuthFailureError {
                Map<String,String>params=new HashMap<>();
                params.put("feedback",feedback);
                params.put("userID",user.getPhoneNo());
                Log.e("PARAMS",""+params);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);
    }

}
