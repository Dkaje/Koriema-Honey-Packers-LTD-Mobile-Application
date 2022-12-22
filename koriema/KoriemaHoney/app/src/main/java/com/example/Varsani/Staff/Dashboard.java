package com.example.Varsani.Staff;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.Varsani.Clients.Feedback;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.MainActivity;
import com.example.Varsani.R;
import com.example.Varsani.Staff.Driver.ArrivedOrders;
import com.example.Varsani.Staff.Driver.AssignedOrders;
import com.example.Varsani.Staff.Driver.DeliveredOrders;
import com.example.Varsani.Staff.Finance.ApprovedOrders;
import com.example.Varsani.Staff.Finance.NewOrders;
import com.example.Varsani.Staff.ShippingMrg.OrdersToShip;
import com.example.Varsani.Staff.ShippingMrg.ShippingOrders;
import com.example.Varsani.Staff.Store_mrg.RequestItems;
import com.example.Varsani.Staff.Store_mrg.ViewStock;
import com.example.Varsani.utils.SessionHandler;
import com.google.android.material.navigation.NavigationView;

public class Dashboard extends AppCompatActivity {
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View contextView;
    private SessionHandler session;
    private UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_bar);
        setSupportActionBar(toolbar);

        contextView = findViewById(android.R.id.content);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);


        session=new SessionHandler(getApplicationContext());
        user=session.getUserDetails();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,
                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };


        navigationView.setNavigationItemSelectedListener( new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Intent hm = new Intent( getApplicationContext(), Dashboard.class );
                        startActivity( hm );
                        break;
                    case R.id.nav_new_orders:
                        Intent n = new Intent( getApplicationContext(), NewOrders.class );
                        startActivity( n );
                        break;
                    case R.id.nav_approvedOrders:
                        Intent a = new Intent( getApplicationContext(), ApprovedOrders.class );
                        startActivity( a );
                        break;
                    case R.id.nav_orders_to_shipp:
                        Intent as = new Intent( getApplicationContext(), OrdersToShip.class );
                        startActivity( as );
                        break;
                    case R.id.nav_staff_feedback:
                        Intent fb = new Intent( getApplicationContext(), StaffFeedback.class );
                        startActivity(fb);
                        break;

                    case R.id.nav_shipping_orders:
                        Intent so = new Intent( getApplicationContext(), ShippingOrders.class );
                        startActivity( so );
                        break;
                    case R.id.nav_assigned_orders:
                        Intent ao = new Intent( getApplicationContext(), AssignedOrders.class );
                        startActivity( ao );
                        break;
                    case R.id.nav_arrived_orders:
                        Intent aro = new Intent( getApplicationContext(), ArrivedOrders.class );
                        startActivity( aro );
                        break;
                    case R.id.nav_delivered_orders:
                        Intent d = new Intent( getApplicationContext(), DeliveredOrders.class );
                        startActivity( d );
                        break;
                    case R.id.nav_stock:
                        Intent st = new Intent( getApplicationContext(), ViewStock.class );
                        startActivity( st);
                        break;
                    case R.id.nav_supplies:
                        Intent im = new Intent( getApplicationContext(), RequestItems.class );
                        startActivity( im);
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

    public void alertLogout(){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage("Do you want to logout?");
        alertDialog.setCancelable(false);
        alertDialog.setButton("Yes logout", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                session.logoutUser();
              Toast toast=  Toast.makeText( getApplicationContext(),"You are logged out",Toast.LENGTH_SHORT );
              toast.setGravity(Gravity.TOP,0,250);
                      toast.show();

                      Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                      startActivity(intent);
                finish();
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


    private int k = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent e) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            oNBackPressedExitApp();
            return true;
        }

        return super.onKeyDown(keyCode, e);
    }

    @Override
    protected void onStart() {
        super.onStart();
        k = 0;
    }
    public void oNBackPressedExitApp(){

        k++;
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Exit Koriema");
        alertDialog.setIcon(R.drawable.ic_notifications);
        alertDialog.setMessage("Do you really want to exit?");
        alertDialog.setCancelable(false);

        if(k == 1) {
            alertDialog.setButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    Intent homeScreenIntent = new Intent(Intent.ACTION_MAIN);
                    homeScreenIntent.addCategory(Intent.CATEGORY_HOME);
                    homeScreenIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(homeScreenIntent);
                    return;
                }
            });
            alertDialog.setButton2("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    k = 0;
                    return;
                }
            });
            alertDialog.show();
        }
    }
    public void check(){

        navigationView.getMenu().findItem(R.id.nav_new_orders).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_approvedOrders).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_orders_to_shipp).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_shipping_orders).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_assigned_orders).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_arrived_orders).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_delivered_orders).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_stock).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_supplies).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_staff_feedback).setVisible(false);

        if(session.isLoggedIn()) {

            if (user.getUser_type().equals("Finance")) {

                navigationView.getMenu().findItem(R.id.nav_new_orders).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_approvedOrders).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_staff_feedback).setVisible(true);

            } else if (user.getUser_type().equals("Shipping manager")) {
                navigationView.getMenu().findItem(R.id.nav_orders_to_shipp).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_shipping_orders).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_staff_feedback).setVisible(true);

            } else if (user.getUser_type().equals("Driver")) {
                navigationView.getMenu().findItem(R.id.nav_assigned_orders).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_arrived_orders).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_delivered_orders).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_staff_feedback).setVisible(true);
            } else if (user.getUser_type().equals("Inventory Manager")) {
                navigationView.getMenu().findItem(R.id.nav_stock).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_supplies).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_staff_feedback).setVisible(true);

            }
        }
    }
}
