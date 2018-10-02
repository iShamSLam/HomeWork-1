package com.example.user.homework;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static TextView nh_log;
    public static TextView nh_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        nh_log = header.findViewById(R.id.nh_log);
        nh_email = header.findViewById(R.id.nh_email);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.nav_say_hello: {
                FragmentTransaction helloTrans = getSupportFragmentManager()
                        .beginTransaction();
                helloTrans.replace(R.id.fragm, new SayHelloFragment());
                helloTrans.addToBackStack("back1");
                helloTrans.commit();
                break;
            }
            case R.id.nav_god_bless: {
                FragmentTransaction blessTrans = getSupportFragmentManager()
                        .beginTransaction();
                blessTrans.replace(R.id.fragm, new SayGodBless());
                blessTrans.addToBackStack("back2");
                blessTrans.commit();
                break;
            }
            case R.id.nav_set_data: {
                FragmentTransaction profileTrans = getSupportFragmentManager()
                        .beginTransaction();
                profileTrans.replace(R.id.fragm, new ProfileFragment());
                profileTrans.addToBackStack("back3");
                profileTrans.commit();
                break;
            }
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

