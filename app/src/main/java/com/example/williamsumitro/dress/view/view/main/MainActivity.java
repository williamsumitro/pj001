package com.example.williamsumitro.dress.view.view.main;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
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
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.HomeFragment;
import com.example.williamsumitro.dress.view.SettingsFragment;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.MystoreFragment;
import com.example.williamsumitro.dress.view.view.Settings;
import com.example.williamsumitro.dress.view.view.authentication.Login;
import com.example.williamsumitro.dress.view.view.authentication.Register;
import com.example.williamsumitro.dress.view.view.bag.activity.ShoppingBag;
import com.example.williamsumitro.dress.view.view.favoritestore.fragment.FavoriteStoreFragment;
import com.example.williamsumitro.dress.view.view.order.fragment.OrderFragment;
import com.example.williamsumitro.dress.view.view.search.activity.Search;
import com.example.williamsumitro.dress.view.view.store.activity.MyStore;
import com.example.williamsumitro.dress.view.view.wishlist.fragment.WishlistFragment;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    public static  MainActivity mainactivity;
    public static final String EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X";
    public static final String EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y";
    private int revealX;
    private int revealY;

    @BindView(R.id.main_nav) NavigationView navigationView;
    @BindView(R.id.main_drawerlayout) DrawerLayout drawerLayout;
    @BindView(R.id.main_appbar_toolbar) Toolbar toolbar;
    @BindView(R.id.main_appbar_frame) FrameLayout frameLayout;

    public static int navIndex = 1;
    private static final String MYSTORE = "MYSTORE";
    private static final String ORDER = "ORDER";
    private static final String HOME = "HOME";
    private static final String FAVORITESTORE = "FAVORITESTORE";
    private static final String PRODUCTDISCUSSION = "PRODUCTDISCUSSION";
    private static final String LOGOUT = "LOGOUT";
    private static final String WISHLIST = "WISHLIST";
    private static final String SETTINGS = "SETTINGS";
    private static final String HELP = "HELP";
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
    private SessionManagement sessionManagement;
    private String jwt="", name, email;
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initSession();
        setupNavigationView();
        if (savedInstanceState == null) {
            navIndex = 1;
            CURRENT = HOME;
            loadHomeFragment();
        }
        loadNavBar();

        initTransition(savedInstanceState);
    }
    private void initSession(){
        HashMap<String, String> user = sessionManagement.getUserDetails();
        jwt = user.get(SessionManagement.JWT);
        name = user.get(SessionManagement.KEY_NAME);
        email = user.get(SessionManagement.KEY_EMAIL);
    }

    private void loadNavBar() {
        headerLayout = navigationView.inflateHeaderView(R.layout.main_navheaderlogin);
        View header = navigationView.getHeaderView(0);
        RelativeLayout containerlogin = (RelativeLayout) header.findViewById(R.id.nav_header_containerlogin);
        RelativeLayout containernotlogin = (RelativeLayout) header.findViewById(R.id.nav_header_containernotlogin);
        TextView nama = (TextView) header.findViewById(R.id.nav_header_name);
        CircleImageView image = (CircleImageView) header.findViewById(R.id.nav_header_image);
        if (jwt == null){
            containernotlogin.setVisibility(View.VISIBLE);
            containerlogin.setVisibility(View.GONE);
        }else {
            containernotlogin.setVisibility(View.GONE);
            containerlogin.setVisibility(View.VISIBLE);
        }
        containernotlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Login.class);
                initanim(intent);
            }
        });
        Menu menu = navigationView.getMenu();
        MenuItem home = menu.findItem(R.id.drawer_home);
        MenuItem settings = menu.findItem(R.id.drawer_settings);
        MenuItem help = menu.findItem(R.id.drawer_help);
        MenuItem mystore = menu.findItem(R.id.drawer_mystore);
        MenuItem order = menu.findItem(R.id.drawer_order);
        MenuItem wishlist = menu.findItem(R.id.drawer_wishlist);
        MenuItem favstore = menu.findItem(R.id.drawer_favoritestore);
        MenuItem discussion = menu.findItem(R.id.drawer_productdiscussion);
        MenuItem logout = menu.findItem(R.id.drawer_logout);
        if(jwt == null){
            home.setVisible(true);
            settings.setVisible(true);
            help.setVisible(true);
            mystore.setVisible(false);
            order.setVisible(false);
            wishlist.setVisible(false);
            favstore.setVisible(false);
            discussion.setVisible(false);
            logout.setVisible(false);
        }else {
            home.setVisible(true);
            settings.setVisible(true);
            help.setVisible(true);
            mystore.setVisible(true);
            order.setVisible(true);
            wishlist.setVisible(true);
            favstore.setVisible(true);
            discussion.setVisible(true);
            logout.setVisible(true);
        }
    }

    private void initView(){
        mainactivity = this;
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        handler = new Handler();
        context = this;
        activityTitles = getResources().getStringArray(R.array.nav_titles);
        fragmentManager = getSupportFragmentManager();
        sessionManagement = new SessionManagement(getApplicationContext());
    }
    private void setupNavigationView(){

            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.drawer_mystore:
                            Intent intent = new Intent(context, MyStore.class);
                            initanim(intent);
                            drawerLayout.closeDrawers();
                            return true;
                        case R.id.drawer_home:
                            navIndex = 1;
                            CURRENT = HOME;
                            break;
                        case R.id.drawer_order:
                            navIndex = 2;
                            CURRENT = ORDER;
                            break;
                        case R.id.drawer_wishlist:
                            navIndex = 3;
                            CURRENT = WISHLIST;
                            break;
                        case R.id.drawer_favoritestore:
                            navIndex = 4;
                            CURRENT = FAVORITESTORE;
                            break;
                        case R.id.drawer_productdiscussion:
                            navIndex = 5;
                            CURRENT = PRODUCTDISCUSSION;
                            break;
                        case R.id.drawer_settings:
                            navIndex = 6;
                            CURRENT = SETTINGS;
                            break;
                        case R.id.drawer_help:
                            navIndex = 7;
                            CURRENT = HELP;
                            break;
                        case R.id.drawer_logout:
                            initDialog("", 10);
                            break;
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
        overridePendingTransition(R.anim.slideright, R.anim.fadeout);
        context.startActivity(intent);
    }

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
                OrderFragment orderFragment = new OrderFragment();
                return orderFragment;
            case 3:
                WishlistFragment wishlistFragment = new WishlistFragment();
                return wishlistFragment;
            case 4:
                FavoriteStoreFragment favoriteStoreFragment = new FavoriteStoreFragment();
                return favoriteStoreFragment;
            case 5:

            case 6:
                SettingsFragment settingsFragment = new SettingsFragment();
                return settingsFragment;
            case 7:

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
        // automatically handle clicks on the Home/Up button1, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_bag) {
            Intent intent = new Intent(this, ShoppingBag.class);
            initanim(intent);
            return true;
        }else if(id == R.id.menu_search){
            Intent intent = new Intent(this, Search.class);
            initanim(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void initTransition(Bundle savedInstanceState){
        final Intent intent = getIntent();
        if (savedInstanceState == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_X) &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_Y)) {
            revealX = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_X, 0);
            revealY = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_Y, 0);
            ViewTreeObserver viewTreeObserver = frameLayout.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        revealActivity(revealX, revealY);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            frameLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                    }
                });
            }else {
                frameLayout.setVisibility(View.VISIBLE);
            }
        }
    }
    protected void revealActivity(int x, int y) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            float finalRadius = (float) (Math.max(frameLayout.getWidth(), frameLayout.getHeight()) * 1.1);

            // create the animator for this view (the start radius is zero)
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(frameLayout, x, y, 0, finalRadius);
            circularReveal.setDuration(400);
            circularReveal.setInterpolator(new AccelerateInterpolator());

            // make the view visible and start the animation
            frameLayout.setVisibility(View.VISIBLE);
            circularReveal.start();
        } else {
            finish();
        }
    }

    protected void unRevealActivity() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            finish();
        } else {
            float finalRadius = (float) (Math.max(frameLayout.getWidth(), frameLayout.getHeight()) * 1.1);
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(
                    frameLayout, revealX, revealY, finalRadius, 0);

            circularReveal.setDuration(400);
            circularReveal.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    frameLayout.setVisibility(View.INVISIBLE);
                    finish();
                }
            });


            circularReveal.start();
        }
    }
    private void initDialog(String message, int stats){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_dialog);
        TextView status = (TextView) dialog.findViewById(R.id.customdialog_tvStatus);
        TextView detail = (TextView) dialog.findViewById(R.id.customdialog_tvDetail);
        ImageView imageView = (ImageView) dialog.findViewById(R.id.customdialog_img);
        Button buttonok = (Button) dialog.findViewById(R.id.customdialog_btnok);
        Button buttoncancel = (Button) dialog.findViewById(R.id.customdialog_btncancel);
        if(stats == 0){
            status.setText("Oops!");
            detail.setText(message);
            imageView.setImageResource(R.drawable.emoji_oops);
            buttonok.setBackgroundResource(R.drawable.button1_red);
            buttonok.setText("Try Again");
            buttonok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
        else if(stats == 1){
            status.setText("Registered Success!");
            detail.setText(message);
            imageView.setImageResource(R.drawable.emoji_success);
            buttonok.setBackgroundResource(R.drawable.button1_green);
            buttonok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    startActivity(new Intent(context, Login.class));
                    finish();
                }
            });
            dialog.show();
        }
        else if (stats == 3){
            status.setText("Uh Oh!");
            detail.setText("There is a problem with internet connection or the server");
            imageView.setImageResource(R.drawable.emoji_cry);
            buttonok.setBackgroundResource(R.drawable.button1_red);
            buttonok.setText("Try Again");
            buttonok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
        else if (stats == 10){
            buttoncancel.setVisibility(View.VISIBLE);
            status.setText("Are you sure want to logout ?");
            detail.setText(message);
            imageView.setImageResource(R.drawable.emoji_smile);
            buttonok.setBackgroundResource(R.drawable.button1_green);
            buttoncancel.setBackgroundResource(R.drawable.button1_red);
            buttonok.setText("Ok");
            buttoncancel.setText("Cancel");
            buttonok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    sessionManagement.logoutUser();
                    finish();
                }
            });
            buttoncancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    navigationView.getMenu().getItem(8).setChecked(false);
//                    navigationView.setCheckedItem(R.id.drawer_home);
                    navigationView.getMenu().getItem(navIndex).setChecked(true);
                }
            });
            dialog.show();
        }
    }
}
