package com.example.williamsumitro.dress.view.view.main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.BagResponse;
import com.example.williamsumitro.dress.view.model.StoreResponse;
import com.example.williamsumitro.dress.view.model.UserResponse;
import com.example.williamsumitro.dress.view.model.UserDetails;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.view.Test;
import com.example.williamsumitro.dress.view.view.authentication.Unauthorized;
import com.example.williamsumitro.dress.view.view.home.fragment.HomeFragment;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.authentication.Login;
import com.example.williamsumitro.dress.view.view.bag.activity.ShoppingBag;
import com.example.williamsumitro.dress.view.view.favoritestore.fragment.FavoriteStoreFragment;
import com.example.williamsumitro.dress.view.view.purchase.fragment.PurchaseFragment;
import com.example.williamsumitro.dress.view.view.request.fragment.RFQFragment;
import com.example.williamsumitro.dress.view.view.sellerpanel.fragment.NoStoreFragment;
import com.example.williamsumitro.dress.view.view.sellerpanel.fragment.SellerPanelFragment;
import com.example.williamsumitro.dress.view.view.profile.activity.Profile;
import com.example.williamsumitro.dress.view.view.search.activity.Search;
import com.example.williamsumitro.dress.view.view.wallet.fragment.WalletFragment;
import com.example.williamsumitro.dress.view.view.wishlist.fragment.WishlistFragment;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static MainActivity mainactivity;

    @BindView(R.id.main_nav) NavigationView navigationView;
    @BindView(R.id.main_drawerlayout) DrawerLayout drawerLayout;
    @BindView(R.id.main_appbar_toolbar) Toolbar toolbar;
    @BindView(R.id.main_appbar_frame) FrameLayout frameLayout;

    public static int navIndex = 2;
    private static final String HOME = "HOME";
    private static final String FAVORITESTORE = "FAVORITESTORE";
    private static final String WISHLIST = "WISHLIST";
    private static final String HELP = "HELP";
    private static final String PURCHASE = "PURCHASE";
    private static final String SELLERPANEL = "SELLERPANEL";
    private static final String NOSTORE = "NOSTORE";
    private static final String MYWALLET = "MYWALLET";
    private static final String RFQ = "RFQ";
    public static String CURRENT = HOME;

    private String[] activityTitles;
    private Handler handler;
    private View headerLayout;
    private FragmentManager fragmentManager;
    private Context context;
    private SessionManagement sessionManagement;
    private String token ="", total_qty="0";
    private apiService service;
    private Boolean status = false;
    private TextView itemcount;
    private ProgressDialog progressDialog;
    private SweetAlertDialog sweetAlertDialog;
    private final static String CHECKOUT = "CHECKOUT";
    private final static String FILEUPLOAD = "FILEUPLOAD";
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        loadNavBar();
        Intent getintent = getIntent();
        setupNavigationView();
        if (getintent.hasExtra(CHECKOUT)){
            navIndex = 4;
            CURRENT = PURCHASE;
            loadHomeFragment();
        }
        else if (getintent.hasExtra(FILEUPLOAD)){
            navIndex = 1;
            CURRENT = SELLERPANEL;
            loadHomeFragment();
        }
        else{
            if (savedInstanceState == null) {
                navIndex = 2;
                CURRENT = HOME;
                loadHomeFragment();
            }
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
        progressDialog = new ProgressDialog(context);
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
    }
    private void api_getauthuser(){
        progressDialog.setMessage("Loading ...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        service = apiUtils.getAPIService();
        service.req_get_auth_user(token).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.code()==200){
                    UserDetails userDetails = response.body().getUserDetails();
                    View header = navigationView.getHeaderView(0);
                    TextView nama = (TextView) header.findViewById(R.id.nav_header_name);
                    CircleImageView image = (CircleImageView) header.findViewById(R.id.nav_header_image);
                    nama.setText(userDetails.getFullName());
                    if (userDetails.getGender().toLowerCase().equals("m"))
                        Picasso.with(context).load(userDetails.getAvatar()).placeholder(R.drawable.man).into(image);
                    else
                        Picasso.with(context).load(userDetails.getAvatar()).placeholder(R.drawable.woman1).into(image);
                    progressDialog.dismiss();
                }
                else if (response.code()==500){
                    progressDialog.dismiss();
                    Intent intent = new Intent(context, Unauthorized.class);
                    initanim(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                initDialog(0);
                progressDialog.dismiss();
            }
        });
    }
    private void loadNavBar() {
        headerLayout = navigationView.inflateHeaderView(R.layout.main_navheaderlogin);
        View header = navigationView.getHeaderView(0);

        RelativeLayout containerlogin = (RelativeLayout) header.findViewById(R.id.nav_header_containerlogin);
        RelativeLayout containernotlogin = (RelativeLayout) header.findViewById(R.id.nav_header_containernotlogin);

        containernotlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Login.class);
                initanim(intent);
            }
        });
        Menu menu = navigationView.getMenu();
        MenuItem wallet = menu.findItem(R.id.drawer_wallet);
        MenuItem home = menu.findItem(R.id.drawer_home);
        MenuItem openingrequest = menu.findItem(R.id.drawer_openrequest);
        MenuItem help = menu.findItem(R.id.drawer_help);
        MenuItem sellerpanel = menu.findItem(R.id.drawer_sellerpanel);
        MenuItem order = menu.findItem(R.id.drawer_order);
        MenuItem wishlist = menu.findItem(R.id.drawer_wishlist);
        MenuItem favstore = menu.findItem(R.id.drawer_favoritestore);
        MenuItem logout = menu.findItem(R.id.drawer_logout);

        if(token == null){
            containernotlogin.setVisibility(View.VISIBLE);
            containerlogin.setVisibility(View.GONE);
            wallet.setVisible(false);
            home.setVisible(true);
            help.setVisible(true);
            openingrequest.setVisible(false);
            sellerpanel.setVisible(false);
            order.setVisible(false);
            wishlist.setVisible(false);
            favstore.setVisible(false);
            logout.setVisible(false);
        }else {
            api_getauthuser();
            containernotlogin.setVisibility(View.GONE);
            containerlogin.setVisibility(View.VISIBLE);
            wallet.setVisible(true);
            home.setVisible(true);
            help.setVisible(true);
            openingrequest.setVisible(true);
            sellerpanel.setVisible(true);
            order.setVisible(true);
            wishlist.setVisible(true);
            favstore.setVisible(true);
            logout.setVisible(true);
        }
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Profile.class);
                initanim(intent);
            }
        });
    }
    private void api_hasstore(){
        service = apiUtils.getAPIService();
        service.req_get_user_store(token).enqueue(new Callback<StoreResponse>() {
            @Override
            public void onResponse(Call<StoreResponse> call, Response<StoreResponse> response) {
                if(response.code()==200){
                    status = response.body().getHaveStore();
                    if(status){
                        navIndex = 1;
                        CURRENT = SELLERPANEL;
                        loadHomeFragment();

                    }else {
                        navIndex = 1;
                        CURRENT = NOSTORE;
                        loadHomeFragment();
                    }
                }
            }

            @Override
            public void onFailure(Call<StoreResponse> call, Throwable t) {
                initDialog(0);
            }
        });
    }

    private void setupNavigationView(){
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.drawer_wallet:
                            navIndex = 0;
                            CURRENT = MYWALLET;
                            break;
                        case R.id.drawer_sellerpanel:
                            api_hasstore();
                            break;
                        case R.id.drawer_home:
                            navIndex = 2;
                            CURRENT = HOME;
                            break;
                        case R.id.drawer_openrequest:
                            navIndex = 3;
                            CURRENT = RFQ;
                            break;
                        case R.id.drawer_order:
                            navIndex = 4;
                            CURRENT = PURCHASE;
                            break;
                        case R.id.drawer_wishlist:
                            navIndex = 5;
                            CURRENT = WISHLIST;
                            break;
                        case R.id.drawer_favoritestore:
                            navIndex = 6;
                            CURRENT = FAVORITESTORE;
                            break;
                        case R.id.drawer_help:
                            navIndex = 7;
                            CURRENT = HELP;
                            break;
                        case R.id.drawer_logout:
                            initDialog(2);
                            break;
                        default:
                            navIndex = 2;
                    }
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
        context.startActivity(intent);
        overridePendingTransition(R.anim.slideright, R.anim.fadeout);
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navIndex).setChecked(true);
    }
    private void loadHomeFragment() {
        selectNavMenu();
        setToolbarTitle();
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

        // If mPendingRunnable is not null, then add1 to the message queue
        if (mPendingRunnable != null) {
            handler.post(mPendingRunnable);
        }
        drawerLayout.closeDrawers();
        invalidateOptionsMenu();
    }
    private Fragment getHomeFragment() {
        switch (navIndex) {
            case 0 :
                WalletFragment walletFragment = new WalletFragment();
                return walletFragment;
            case 1 :
                if (CURRENT == NOSTORE){
                    NoStoreFragment noStoreFragment = new NoStoreFragment();
                    return noStoreFragment;
                }
                if (CURRENT == SELLERPANEL) {
                    SellerPanelFragment sellerPanelFragment = new SellerPanelFragment();
                    return sellerPanelFragment;
                }
            case 2:
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 3:
                RFQFragment rfqFragment = new RFQFragment();
                return rfqFragment;
            case 4:
                PurchaseFragment purchaseFragment = new PurchaseFragment();
                return purchaseFragment;
            case 5:
                WishlistFragment wishlistFragment = new WishlistFragment();
                return wishlistFragment;
            case 6:
                FavoriteStoreFragment favoriteStoreFragment = new FavoriteStoreFragment();
                return favoriteStoreFragment;
            case 7:
                Intent intent = new Intent(context, Test.class);
                initanim(intent);
            default:
                return new HomeFragment();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        if (token==null){
            menu.findItem(R.id.menu_notification).setVisible(false);
            menu.findItem(R.id.menu_bag).setVisible(false);
        }
        else {
            final MenuItem menuItem = menu.findItem(R.id.menu_bag);
            View actionView = MenuItemCompat.getActionView(menuItem);
            itemcount = (TextView) actionView.findViewById(R.id.customshoppingbag_cart_badge);
            actionView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShoppingBag.class);
                    initanim(intent);
                }
            });
            api_viewshoppingbag();
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_search){
            Intent intent = new Intent(this, Search.class);
            initanim(intent);
        }else if(id == R.id.menu_notification_purchase){
            navIndex = 4;
            CURRENT = PURCHASE;
            loadHomeFragment();
        }else if(id == R.id.menu_notification_request){
        }

        return super.onOptionsItemSelected(item);
    }
    private void setupBadge(){
        int qtytot = 0;
        if (total_qty!=null)
            qtytot = Integer.parseInt(total_qty);
        if (qtytot==0){
            if (itemcount.getVisibility() != View.GONE)
                itemcount.setVisibility(View.GONE);
        }
        else {
            itemcount.setText(String.valueOf(Math.min(qtytot, 99)));
            if (itemcount.getVisibility() != View.VISIBLE)
                itemcount.setVisibility(View.VISIBLE);
        }
    }
    private void api_viewshoppingbag() {
        service = apiUtils.getAPIService();
        service.req_view_shopping_bag(token).enqueue(new Callback<BagResponse>() {
            @Override
            public void onResponse(Call<BagResponse> call, Response<BagResponse> response) {
                if (response.code()==200){
                    total_qty = response.body().getTotalQty();
                    setupBadge();
                }
            }
            @Override
            public void onFailure(Call<BagResponse> call, Throwable t) {

            }
        });
    }
    private void initDialog(int stats){
        if (stats == 0){
            sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setCanceledOnTouchOutside(false);
            sweetAlertDialog.setTitleText("Sorry")
                    .setContentText("There is a problem with internet connection or the server")
                    .setConfirmText("Try Again")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                            Intent restart = new Intent(context, MainActivity.class);
                            initanim(restart);
                            sweetAlertDialog.dismiss();
                        }
                    });
            if(!((Activity) context).isFinishing())
                sweetAlertDialog.show();
//                prettyDialog.show();
        }
        else if (stats == 2){
            sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setCanceledOnTouchOutside(false);
            sweetAlertDialog.setTitleText("Logout")
                    .setContentText("Are sure you want to logout ?")
                    .setConfirmText("Yes")
                    .setCancelText("No")
                    .showCancelButton(true)
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    })
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.setTitleText("Logout")
                                    .setContentText("See you again !")
                                    .setConfirmText("OK")
                                    .showCancelButton(false)
                                    .setCancelClickListener(null)
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            finish();
                                            sessionManagement.logoutUser();
                                            sweetAlertDialog.dismiss();
                                        }
                                    })
                                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        }
                    })
                    .show();
//            prettyDialog.setTitle("")
//                    .setMessage("")
//                    .setIcon(R.drawable.pdlg_icon_info)
//                    .setIconTint(R.color.pdlg_color_red)
//                    .addButton("Yes",
//                            R.color.pdlg_color_white,
//                            R.color.pdlg_color_green, new PrettyDialogCallback() {
//                                @Override
//                                public void onClick() {
//                                    prettyDialog.dismiss();
//                                }
//                            })
//                    .addButton("No",
//                            R.color.pdlg_color_white,
//                            R.color.pdlg_color_red, new PrettyDialogCallback() {
//                                @Override
//                                public void onClick() {
//                                    prettyDialog.dismiss();
//                                    navigationView.getMenu().getItem(8).setChecked(false);
//                                    navigationView.getMenu().getItem(navIndex).setChecked(true);
//                                }
//                            })
//                    .setAnimationEnabled(true)
//                    .show();
        }
    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toasty.info(this, "Please click BACK again to exit", Toast.LENGTH_SHORT, true).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
