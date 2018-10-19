package com.welson.zhihudaily.activity;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.welson.zhihudaily.R;
import com.welson.zhihudaily.adapter.NavigationListAdapter;
import com.welson.zhihudaily.contract.MainContract;
import com.welson.zhihudaily.data.NewsStory;
import com.welson.zhihudaily.data.ThemeContent;
import com.welson.zhihudaily.data.ThemeData;
import com.welson.zhihudaily.data.Themes;
import com.welson.zhihudaily.fragment.CommonFragment;
import com.welson.zhihudaily.fragment.HomeFragment;
import com.welson.zhihudaily.presenter.MainPresenter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainContract.View{

    private static final String TAG = MainActivity.class.getSimpleName();
    private DrawerLayout drawerLayout;
    //private NavigationView navigationView;
    private FrameLayout mainLayout;
    public Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    private ListView navigationViewList;
    private View navigationHeaderView;
    private NavigationListAdapter adapter;
    private FragmentManager fragmentManager;
    private HomeFragment homeFragment;
    private CommonFragment commonFragment;
    private MainPresenter presenter;
    private boolean isHomeFragment = true;
    private ArrayList<ThemeData> themeDatas;
    private int commonId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment();
        switchFragment(isHomeFragment);
        initView();
        initData();
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    private void initView(){
        toolbar = findViewById(R.id.main_toolbar);
        toolbar.setTitle(getString(R.string.home_toolbar_title));
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        //navigationView = findViewById(R.id.navigation_view);
        mainLayout = findViewById(R.id.fragment_layout);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerToggle.syncState();
        drawerLayout.setDrawerListener(drawerToggle);
        navigationViewList = findViewById(R.id.navigation_list);
        navigationHeaderView = getLayoutInflater().inflate(R.layout.navigation_header,null,false);
    }

    private void initListener(){
        /*navigationView.setCheckedItem(R.id.navigation_home);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });*/
        navigationViewList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0)
                    return;
                if (i != 1){
                    drawerLayout.closeDrawers();
                    commonId = themeDatas.get(i - 2).getId();
                    toolbar.setTitle(themeDatas.get(i - 2).getName());
                    isHomeFragment = false;
                    switchFragment(isHomeFragment);
                }else {
                    drawerLayout.closeDrawers();
                    isHomeFragment = true;
                    toolbar.setTitle(getString(R.string.home_toolbar_title));
                    switchFragment(isHomeFragment);
                }
            }
        });
    }

    private void initData(){
        presenter = new MainPresenter(this);
        presenter.requestData();
        themeDatas = new ArrayList<>();
        adapter = new NavigationListAdapter(this,themeDatas);
        navigationViewList.setAdapter(adapter);
        navigationViewList.addHeaderView(navigationHeaderView);
        navigationViewList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        navigationViewList.setItemChecked(1,true);
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
            commonFragment.setId(commonId);
            transaction.show(commonFragment);
            transaction.hide(homeFragment);
        }
        transaction.commit();
        invalidateOptionsMenu(); //调用onPrepare menu
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.home_toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        //Log.d("dingyl","onPrepareOptionsMenu");
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
        themeDatas.clear();
        themeDatas.addAll(themes.getOthers());
        adapter.notifyDataSetChanged();
        initListener();
        /*int group = navigationView.getMenu().getItem(0).getGroupId();
        Log.d("dingyl","group is : " + group);
        Log.d("dingyl","navigation_group is : " + R.id.navigation_group);
        for (int i = 0;i<themes.getOthers().size();i++){
            navigationView.getMenu().add(group,themes.getOthers().get(i).getId(),i,themes.getOthers().get(i).getName());
        }*/
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        presenter.removeAllDisposable();
    }

    @Override
    public void onBackPressed(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

}
