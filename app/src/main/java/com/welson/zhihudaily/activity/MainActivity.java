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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.welson.zhihudaily.R;
import com.welson.zhihudaily.contract.MainContract;
import com.welson.zhihudaily.data.Themes;
import com.welson.zhihudaily.fragment.CommonFragment;
import com.welson.zhihudaily.fragment.HomeFragment;
import com.welson.zhihudaily.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity implements MainContract.View{

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FrameLayout mainLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    private FragmentManager fragmentManager;
    private HomeFragment homeFragment;
    private CommonFragment commonFragment;
    private MainPresenter presenter;
    private boolean isHomeFragment = true;
    private Themes themes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        initFragment();
        switchFragment(isHomeFragment);
    }

    @Override
    public void onResume(){
        super.onResume();
        initData();
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

    private void initData(){
        presenter = new MainPresenter(this);
        presenter.requestData();
    }

    private void initFragment(){
        homeFragment = new HomeFragment();
        commonFragment = new CommonFragment();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_layout,homeFragment);
        transaction.add(R.id.fragment_layout,commonFragment);
        transaction.commit();
    }

    private void switchFragment(boolean isHomeFragment){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (isHomeFragment){
            transaction.show(homeFragment);
            transaction.hide(commonFragment);
        }else {
            transaction.show(commonFragment);
            transaction.hide(homeFragment);
        }
        transaction.commit();
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

    @Override
    public void showThemeSuccess(Themes themes) {
        this.themes = themes;
        int group = navigationView.getMenu().getItem(0).getGroupId();
        Log.d("dingyl","group is : " + group);
        Log.d("dingyl","navigation_group is : " + R.id.navigation_group);
        for (int i = 0;i<themes.getOthers().size();i++){
            navigationView.getMenu().add(group,themes.getOthers().get(i).getId(),i,themes.getOthers().get(i).getName());
        }
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {

    }
}
