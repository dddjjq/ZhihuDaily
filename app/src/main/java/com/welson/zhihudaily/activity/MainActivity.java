package com.welson.zhihudaily.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.welson.zhihudaily.R;
import com.welson.zhihudaily.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FrameLayout mainLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    private FragmentManager fragmentManager;
    private HomeFragment homeFragment;
    private boolean isHomeFragment = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        initFragment();
    }

    private void initView(){
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        mainLayout = findViewById(R.id.fragment_layout);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerToggle.syncState();
        drawerLayout.setDrawerListener(drawerToggle);
    }

    private void initListener(){
        navigationView.setCheckedItem(R.id.navigation_home);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void initFragment(){
        homeFragment = new HomeFragment();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_layout,homeFragment);
        transaction.commit();
    }

    private void switchFragment(boolean isHomeFragment){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.home_toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        if (isHomeFragment){
            menu.findItem(R.id.mine_news).setVisible(true);
            menu.findItem(R.id.home_menu_more).setVisible(true);
            menu.findItem(R.id.add_to_mine).setVisible(false);
        }else {
            menu.findItem(R.id.mine_news).setVisible(false);
            menu.findItem(R.id.home_menu_more).setVisible(false);
            menu.findItem(R.id.add_to_mine).setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }
}
