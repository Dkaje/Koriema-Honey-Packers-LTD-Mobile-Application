package com.example.Varsani.Clients;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.Varsani.ConfirmPayment;
import com.example.Varsani.HelpOrderProduct;
import com.example.Varsani.HowToLogin;
import com.example.Varsani.R;

public class Help extends AppCompatActivity {
    TextView howtologin,howtoorder,confirmpayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);


        howtologin= findViewById(R.id.howtologin);
        howtoorder= findViewById(R.id.howtoorder);
        confirmpayment = findViewById(R.id.confirmpayment);

        howtologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HowToLogin.class);
                startActivity(intent);
            }
        });


        howtoorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HelpOrderProduct.class);
                startActivity(intent);
            }
        });

        confirmpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ConfirmPayment.class);
                startActivity(intent);
            }
        });


    }
}
