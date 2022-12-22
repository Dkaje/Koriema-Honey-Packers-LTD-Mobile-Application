package com.example.Varsani;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.Varsani.Clients.ChooseUser;
import com.example.Varsani.Clients.Help;
import com.example.Varsani.Clients.Search;
import com.example.Varsani.Clients.home.HomeFragment;
import com.example.Varsani.Staff.Dashboard;
import com.example.Varsani.Staff.SelectLogin;
import com.example.Varsani.Clients.About;
import com.example.Varsani.Clients.Adapters.AdapterProducts;
import com.example.Varsani.Clients.CartItems;
import com.example.Varsani.Clients.Feedback;
import com.example.Varsani.Clients.Help_in;
import com.example.Varsani.Clients.Login;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.Clients.Orders_hist;
import com.example.Varsani.Clients.Profile;
import com.example.Varsani.Clients.Register;
import com.example.Varsani.R;
import com.example.Varsani.Suppliers.MyRequests;
import com.example.Varsani.Suppliers.RegSuppliers;
import com.example.Varsani.Suppliers.Requests;
import com.example.Varsani.utils.SessionHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {


    private AppBarConfiguration mAppBarConfiguration;
    private NavigationView navigationView;
    private  DrawerLayout drawer;
    private UserModel user;
    private SessionHandler session;
     AdapterProducts adapterProducts;
     HomeFragment homeFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_bar);
        setSupportActionBar( toolbar );
//        contextView = findViewById(android.R.id.content);
        drawer = findViewById( R.id.drawer_layout );
        navigationView = findViewById( R.id.nav_view );
        FloatingActionButton fab = findViewById(R.id.fab);

        session=new SessionHandler(getApplicationContext());
        user=session.getUserDetails();

        homeFragment=new HomeFragment();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(session.isLoggedIn()){
                    Intent in=new Intent(getApplicationContext(), CartItems.class);
                    startActivity(in);
                }else{
                    Snackbar.make(view, "You must login to view cart", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });
        fab.setImageResource(R.drawable.ic_shopping_cart_white);
        if(session.isLoggedIn()){

            if(user.getUser_type().equals("Client")||user.getUser_type().equals("Supplier")){
               // DO NOTHING
            }else{
                Intent in=new Intent(getApplicationContext(), Dashboard.class);
                startActivity(in);
                finish();
            }
        }



        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        navigationView.setNavigationItemSelectedListener( new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Intent in = new Intent( getApplicationContext(), MainActivity.class );
                        startActivity( in );
                        break;
                    case R.id.nav_profile:
                        Intent p=new Intent(getApplicationContext(), Profile.class);
                        startActivity(p);
                        break;
                    case R.id.nav_request:
                        Intent nr=new Intent(getApplicationContext(), MyRequests.class);
                        startActivity(nr);
                        break;
                    case R.id.nav_register:
                        Intent intent=new Intent(getApplicationContext(), Register.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_reg_supplier:
                        Intent ent=new Intent(getApplicationContext(), RegSuppliers.class);
                        startActivity(ent);
                        break;
                     case R.id.nav_login:
                        Intent i=new Intent(getApplicationContext(), Login.class);
                        startActivity(i);
                        break;
                    case R.id.nav_orders:
                        if(user.getUser_type().equals("Client")){
                            Intent o=new Intent(getApplicationContext(), Orders_hist.class);
                            startActivity(o);
                        }else{
                            Intent o=new Intent(getApplicationContext(), Requests.class);
                            startActivity(o);
                        }

                        break;
                    case R.id.nav_feedback:
                        Intent fbs=new Intent(getApplicationContext(), Feedback.class);
                        startActivity(fbs);
                        break;
                    case R.id.nav_staff_login:
                        Intent s=new Intent(getApplicationContext(), SelectLogin.class);
                        startActivity(s);
                        break;
                    case R.id.nav_help:
                        Intent he=new Intent(getApplicationContext(), Help.class);
                        startActivity(he);
                        break;
                    case R.id.nav_about:
                        Intent ab=new Intent(getApplicationContext(), About.class);
                        startActivity(ab);
                        break;

                    case R.id.nav_logout:
                        alertLogout();
                        break;
                }
                drawer.closeDrawer( GravityCompat.START,true);
                return false;
            }

        } );
        drawer.closeDrawers();

        check();
    }




    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public void alertLogout(){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage("Do you want to logout?");
        alertDialog.setCancelable(false);
        alertDialog.setButton("Yes logout", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                session.logoutUser();
                Toast.makeText( getApplicationContext(),"You are logged out",Toast.LENGTH_SHORT ).show();
                finish();
                startActivity(getIntent());
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

    public void check(){

        navigationView.getMenu().findItem(R.id.nav_profile).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_feedback).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_orders).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_request).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_reg_supplier).setVisible(false);

        if(session.isLoggedIn()){

            navigationView.getMenu().findItem(R.id.nav_register).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_reg_supplier).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_staff_login).setVisible(false);

            if(user.getUser_type().equals("Client")){

                navigationView.getMenu().findItem(R.id.nav_profile).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_feedback).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_orders).setVisible(true);
            }
               if(user.getUser_type().equals("Supplier")){

                navigationView.getMenu().findItem(R.id.nav_profile).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_feedback).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_request).setVisible(true);
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent s=new Intent(getApplicationContext(), Search.class);
                startActivity(s);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
