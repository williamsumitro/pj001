package com.example.williamsumitro.dress.view.view.main;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.HomeFragment;
import com.example.williamsumitro.dress.view.view.authentication.Login;
import com.example.williamsumitro.dress.view.view.authentication.Register;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.main_nav) NavigationView navigationView;
    @BindView(R.id.main_drawerlayout) DrawerLayout drawerLayout;
    @BindView(R.id.main_appbar_toolbar) Toolbar toolbar;
    @BindView(R.id.main_appbar_frame) FrameLayout frameLayout;

    public static int navIndex = 1;

    private static final String HOME = "HOME";
    private static final String WISHLIST = "WISHLIST";
    private static final String SETTINGS = "SETTINGS";
    public static String CURRENT = HOME;

    private boolean FragOnBackPress = true;
    private String[] titles;
    private Handler handler;
    private View headerLayout;
    private int CurrentSelectedPosition, coba;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setupNavigationView();
        coba = 0;
        if(coba == 0){
            navigationView.getMenu().setGroupVisible(R.id.drawergroup_notlogin, true);
            headerLayout = navigationView.inflateHeaderView(R.layout.main_navheadernotlogin);
        }
        else{
            navigationView.getMenu().setGroupVisible(R.id.drawergroup_login, true);
            headerLayout = navigationView.inflateHeaderView(R.layout.main_navheaderlogin);
        }
        if(savedInstanceState == null){
            navIndex = 1;
            CURRENT = HOME;
            loadHomeFragment();
        }
    }

    private void initView(){
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        handler = new Handler();
        titles = getResources().getStringArray(R.array.nav_titles);

    }
    private void setupNavigationView(){
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        if (coba == 0){
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                // This method will trigger on item Click of navigation menu
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.drawer_notloginhome:
                            navIndex = 1;
                            CURRENT = HOME;
                            break;
                        case R.id.drawer_login:
                            navigationView.getMenu().getItem(1).setChecked(true);
                            Intent loginintent = new Intent(MainActivity.this, Login.class);
                            loginintent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            loginintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(loginintent);
                            overridePendingTransition(R.anim.slideright, R.anim.fadeout);
                            drawerLayout.closeDrawers();
                            return true;
                        case R.id.drawer_register:
                            navigationView.getMenu().getItem(1).setChecked(true);
                            Intent registerintent = new Intent(MainActivity.this, Register.class);
                            registerintent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            registerintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(registerintent);
                            overridePendingTransition(R.anim.slideright, R.anim.fadeout);
                            drawerLayout.closeDrawers();
                            return true;
                        default:
                            navIndex = 1;
                    }
                    //Checking if the item is in checked state or not, if not make it in checked state
                    if (item.isChecked()) {
                        item.setChecked(false);
                    } else {
                        item.setChecked(true);
                    }
                    item.setChecked(true);

                    loadHomeFragment();

                    return true;
                }
            });
        }
        else {

        }
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer){
            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                navIndex = 1;
                navigationView.getMenu().getItem(1).setChecked(true);
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };
        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }
    private void loadHomeFragment(){
        navigationView.getMenu().getItem(1).setChecked(true);
        navigationView.getMenu().getItem(navIndex).setChecked(true);
        getSupportActionBar().setTitle(titles[navIndex]);
        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT) != null){
            drawerLayout.closeDrawers();
            return;
        }
        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.main_appbar_frame, fragment, CURRENT);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };
        if (mPendingRunnable != null) {
            handler.post(mPendingRunnable);
        }
        drawerLayout.closeDrawers();
        invalidateOptionsMenu();
    }
    private Fragment getHomeFragment(){
        switch (navIndex){
            case 0 :
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
//            case 1 :
//                WishlistFragment wishlistFragment = new WishlistFragment();
//                return wishlistFragment;
//            case 2 :
//                SettingsFragment settingsFragment = new SettingsFragment();
//                return settingsFragment;
            default:
                return new HomeFragment();
        }
    }
//    private void loadNavHeader() {
//        // name, website
//        txtName.setText("Ravi Tamada");
//        txtWebsite.setText("www.androidhive.info");
//
//        // loading header background image
//        Glide.with(this).load(urlNavHeaderBg)
//                .crossFade()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(imgNavHeaderBg);
//
//        // Loading profile image
//        Glide.with(this).load(urlProfileImg)
//                .crossFade()
//                .thumbnail(0.5f)
//                .bitmapTransform(new CircleTransform(this))
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(imgProfile);
//
//        // showing dot next to notifications label
//        navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot);
//    }


    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawers();
            return;
        }
        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (FragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navIndex != 0) {
                navIndex = 0;
                CURRENT = HOME;
                loadHomeFragment();
                return;
            }
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(navIndex == 0)
            getMenuInflater().inflate(R.menu.main, menu);
        // when fragment is notifications, load the menu created for notifications
//        if (navItemIndex == 3) {
//            getMenuInflater().inflate(R.menu.notifications, menu);
//        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.menu_logout) {
//            Toast.makeText(getApplicationContext(), "Logout user!", Toast.LENGTH_LONG).show();
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
