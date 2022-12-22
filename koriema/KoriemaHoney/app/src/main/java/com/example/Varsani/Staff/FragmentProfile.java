package com.example.Varsani.Staff;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Varsani.Clients.Login;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;
import com.example.Varsani.utils.SessionHandler;

public class FragmentProfile extends Fragment {
    private SessionHandler session;
    private UserModel user;
    private TextView txv_name, txv_phoneNo, txv_email,
            txv_username,txv_role;
    private Button btnlogout;

    public static FragmentProfile newInstance() {
        return new FragmentProfile();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);


        txv_name = root.findViewById(R.id.txv_name);
        txv_username =root. findViewById(R.id.txv_username);
        txv_phoneNo =root. findViewById(R.id.txv_phoneNo);
        txv_email =root. findViewById(R.id.txv_email);
        txv_role =root. findViewById(R.id.txv_role);
        btnlogout = root.findViewById(R.id.logout_btn);

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertLogout();
            }
        });

        session = new SessionHandler(getContext());
        user = session.getUserDetails();

        txv_email.setText( user.getEmail());
        txv_name.setText("Name " + user.getFirstname() + " " + user.getLastname());
        txv_phoneNo.setText(user.getPhoneNo());
        txv_username.setText("Username " + user.getUsername());
        txv_role.setText(user.getUser_type());
        return  root;
    }

    public void alertLogout(){
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setMessage("Do you want to logout?");
        alertDialog.setCancelable(false);
        alertDialog.setButton("Yes logout", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                session.logoutUser();
                Toast.makeText( getContext(),"Welcome Back Next time",Toast.LENGTH_SHORT ).show();
                Intent intent = new Intent(getContext(), Login.class);
                startActivity(intent);
                return;
            }
        });
        alertDialog.setButton2("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

                return;
            }
        });
        alertDialog.show();
    }



}
