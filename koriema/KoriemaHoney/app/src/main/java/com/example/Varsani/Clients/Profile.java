package com.example.Varsani.Clients;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.Varsani.utils.SessionHandler;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;

public class Profile extends AppCompatActivity {

    private SessionHandler session;
    private UserModel user;
    private TextView txv_name,txv_phoneNo, txv_email,
            txv_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txv_name=findViewById(R.id.txv_name);
        txv_username=findViewById(R.id.txv_username);
        txv_phoneNo=findViewById(R.id.txv_phoneNo);
        txv_email =findViewById(R.id.txv_email);

        session=new SessionHandler(getApplicationContext());
        user=session.getUserDetails();

        getSupportActionBar().setTitle(user.getUser_type());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txv_email.setText("Email "+user.getEmail());
        txv_name.setText("Name "+user.getFirstname()+" "+user.getLastname());
        txv_phoneNo.setText("Phone No "+user.getPhoneNo());
        txv_username.setText("Username "+user.getUsername());
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
