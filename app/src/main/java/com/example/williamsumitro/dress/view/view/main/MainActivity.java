package com.example.williamsumitro.dress.view.view.main;

import android.content.Context;
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
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.HomeFragment;
import com.example.williamsumitro.dress.view.SettingsFragment;
import com.example.williamsumitro.dress.view.view.MystoreFragment;
import com.example.williamsumitro.dress.view.view.Settings;
import com.example.williamsumitro.dress.view.view.authentication.Login;
import com.example.williamsumitro.dress.view.view.authentication.Register;
import com.example.williamsumitro.dress.view.view.order.fragment.OrderFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.main_nav) NavigationView navigationView;
    @BindView(R.id.main_drawerlayout) DrawerLayout drawerLayout;
    @BindView(R.id.main_appbar_toolbar) Toolbar toolbar;
    @BindView(R.id.main_appbar_frame) FrameLayout frameLayout;

    public static int navIndex = 0;

    private static final String MYSTORE = "MYSTORE";
    private static final String ORDER = "ORDER";
    private static final String HOME = "HOME";
    private static final String FAVORITESTORE = "FAVORITESTORE";
    private static final String PRODUCTDISCUSSION = "PRODUCTDISCUSSION";
    private static final String LOGOUT = "LOGOUT";
    private static final String WISHLIST = "WISHLIST";
    private static final String SETTINGS = "SETTINGS";
    public static String CURRENT = HOME;

    private boolean FragOnBackPress = true;
    private String[] activityTitles;
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler handler;
    private View headerLayout;
    private int CurrentSelectedPosition, coba;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Context context;
    private MenuItem activeMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        coba = 1;
        if(coba == 0){
            navigationView.getMenu().setGroupVisible(R.id.drawergroup_notlogin, true);
            headerLayout = navigationView.inflateHeaderView(R.layout.main_navheadernotlogin);
            setupNavigationView();
        }
        else{
            navigationView.getMenu().setGroupVisible(R.id.drawergroup_my, true);
            navigationView.getMenu().setGroupVisible(R.id.drawergroup_login, true);
            headerLayout = navigationView.inflateHeaderView(R.layout.main_navheaderlogin);
            setupNavigationView();
        }
        if (savedInstanceState == null) {
            navIndex = 0;
            CURRENT = HOME;
            loadHomeFragment();
        }

    }

    private void initView(){
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        handler = new Handler();
        context = this;
        activityTitles = getResources().getStringArray(R.array.nav_titles);
        fragmentManager = getSupportFragmentManager();
    }
    private void setupNavigationView(){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.drawer_mystore:
                        navIndex = 0;
                        CURRENT = MYSTORE;
                        break;
                    case R.id.drawer_home:
                        navIndex = 1;
                        CURRENT = HOME;
                        break;
                    case R.id.drawer_order:
                        navIndex = 2;
                        CURRENT = ORDER;
                        break;
//                    case R.id.nav_movies:
//                        navIndex = 3;
//                        CURRENT = TAG_MOVIES;
//                        break;
//                    case R.id.nav_notifications:
//                        navIndex = 3;
//                        CURRENT = TAG_NOTIFICATIONS;
//                        break;
//                    case R.id.nav_settings:
//                        navIndex = 4;
//                        CURRENT = TAG_SETTINGS;
//                        break;
//                    case R.id.nav_about_us:
//                        // launch new intent instead of loading fragment
//                        startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
//                        drawer.closeDrawers();
//                        return true;
//                    case R.id.nav_privacy_policy:
//                        // launch new intent instead of loading fragment
//                        startActivity(new Intent(MainActivity.this, PrivacyPolicyActivity.class));
//                        drawer.closeDrawers();
//                        return true;
                    default:
                        navIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
            }

    private void initanim(Intent intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slideright, R.anim.fadeout);
        context.startActivity(intent);
    }

    private void initFragmentManager(){
        CurrentSelectedPosition = 1;
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_appbar_frame, new HomeFragment()).commit();
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
    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navIndex).setChecked(true);
    }
    private void loadHomeFragment() {
    // selecting appropriate nav menu item
    selectNavMenu();

    // set toolbar title
    setToolbarTitle();

    // if user select the current navigation menu again, don't do anything
    // just close the navigation drawer
    if (getSupportFragmentManager().findFragmentByTag(CURRENT) != null) {
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

    // If mPendingRunnable is not null, then add to the message queue
    if (mPendingRunnable != null) {
        handler.post(mPendingRunnable);
    }


    //Closing drawer on item click
    drawerLayout.closeDrawers();

    // refresh toolbar menu
    invalidateOptionsMenu();
}
    private Fragment getHomeFragment() {
        switch (navIndex) {
            case 0:
                MystoreFragment mystoreFragment = new MystoreFragment();
                return mystoreFragment;
            case 1:
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 2:
                // movies fragment
                OrderFragment orderFragment = new OrderFragment();
                return orderFragment;
            case 3:
                // settings fragment
                SettingsFragment settingsFragment = new SettingsFragment();
                return settingsFragment;
            default:
                return new HomeFragment();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
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
