package com.example.Varsani.Staff;

import androidx.appcompat.app.AppCompatActivity;

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
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;
import com.example.Varsani.utils.SessionHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.Varsani.utils.Urls.URL_STAFF_LOGIN;

public class StaffLogin extends AppCompatActivity {

    private Button btn_login;
    private EditText edt_username,edt_password;
      private TextView txv_forget,txv_staff_login;
    private ProgressBar progressBar;
    private SessionHandler session;
    private UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_login);

        Intent intent=getIntent();
        String staff=intent.getStringExtra("Staff");

        Toast.makeText(this, staff, Toast.LENGTH_SHORT).show();
        getSupportActionBar().setSubtitle(staff+" login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_login=findViewById(R.id.login_btn);
        txv_forget=findViewById(R.id.txv_forget);
        txv_staff_login=findViewById(R.id.txv_staff_login);
        edt_password=findViewById(R.id.edt_password);
        edt_username=findViewById(R.id.edt_username);
        progressBar=findViewById(R.id.progressBar);

        txv_staff_login.setText(staff+" Login");

        session=new SessionHandler(getApplicationContext());
        user=session.getUserDetails();

        progressBar.setVisibility(View.GONE);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

//        txv_forget.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(getApplicationContext(),ForgetPass.class);
//                startActivity(intent);
//            }
//        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public  void login(){
        progressBar.setVisibility(View.VISIBLE);
        btn_login.setVisibility(View.GONE);

        final String username=edt_username.getText().toString().trim();
        final String password=edt_password.getText().toString().trim();

        if(TextUtils.isEmpty(username)){
            Toast toast=Toast.makeText(getApplicationContext(),"Enter username",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 250);
            toast.show();
            btn_login.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast toast=Toast.makeText(getApplicationContext(),"Enter password",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 250);
            toast.show();
            btn_login.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return;

        }
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_STAFF_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.e("Response",""+response);
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            String msg=jsonObject.getString("message");

                            if(status.equals("1")){
                                JSONArray jsonArray=jsonObject.getJSONArray("details");
                                for(int i=0;i <jsonArray.length();i++){
                                    JSONObject jsn=jsonArray.getJSONObject(i);
                                    String clientID=jsn.getString("clientID");
                                    String firstname=jsn.getString("firstname");
                                    String lastname=jsn.getString("lastname");
                                    String username=jsn.getString("username");
                                    String phoneNo=jsn.getString("phoneNo");
                                    String email=jsn.getString("email");
                                    String dateCreated=jsn.getString("dateCreated");
                                    String user_type=jsn.getString("user");
                                    session.loginUser(clientID,firstname,lastname,username,phoneNo,email,dateCreated,user_type);
                                }
                                Intent intent=new Intent(getApplicationContext(), Dashboard.class);
                                startActivity(intent);
                                Toast toast=Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP, 0, 250);
                                toast.show();
                                progressBar.setVisibility(View.GONE);
                                btn_login.setVisibility(View.VISIBLE);
                                finish();
                            }else if(status.equals("2")){
                                Toast toast=Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP, 0, 250);
                                toast.show();
                                progressBar.setVisibility(View.GONE);
                                btn_login.setVisibility(View.VISIBLE);
                            }else if(status.equals("0")) {
                                Toast toast=Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP, 0, 250);
                                toast.show();
                                progressBar.setVisibility(View.GONE);
                                btn_login.setVisibility(View.VISIBLE);
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            Toast toast=Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0, 250);
                            toast.show();
                            progressBar.setVisibility(View.GONE);
                            btn_login.setVisibility(View.VISIBLE);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
               Toast toast= Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,250);
              toast.show();
                progressBar.setVisibility(View.GONE);
                btn_login.setVisibility(View.VISIBLE);

            }
        })
        {
            @Override
            protected Map<String, String> getParams()throws AuthFailureError {
                Map<String,String> params= new HashMap<>();
                params.put("username",username);
                params.put("password",password);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
