package com.ats.blucatch.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ats.blucatch.R;
import com.ats.blucatch.fragment.AccountMasterFragment;
import com.ats.blucatch.fragment.AddNotification;
import com.ats.blucatch.fragment.BoatExpensesFragment;
import com.ats.blucatch.fragment.BoatMasterFragment;
import com.ats.blucatch.fragment.ChangePasswordFragment;
import com.ats.blucatch.fragment.ExpenseMasterFragment;
import com.ats.blucatch.fragment.FishMasterFragment;
import com.ats.blucatch.fragment.HomeFragment;
import com.ats.blucatch.fragment.NotificationFragment;
import com.ats.blucatch.fragment.SeasonMasterFragment;
import com.ats.blucatch.fragment.TripDetailsFragment;
import com.ats.blucatch.fragment.TripMasterFishSellFragment;
import com.ats.blucatch.fragment.TripMasterFragment;
import com.ats.blucatch.fragment.TripMasterViewTripExpFragment;
import com.ats.blucatch.fragment.UserHomeFragment;
import com.ats.blucatch.fragment.UserMasterFragment;
import com.ats.blucatch.fragment.UserTripExpenseFragment;
import com.ats.blucatch.fragment.UserViewLedgerFragment;
import com.ats.blucatch.fragment.ViewLedgerFragment;
import com.ats.blucatch.utils.InterfaceApi;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static boolean isAtHome = true, isAtFishMaster = false, isAtAddFish = false, isAtEditFish = false, isAtBoatMaster = false, isAtAddBoat = false, isAtEditBoat = false, isAtTripDetails = false, isAtTripMaster = false, isAtAddTrip = false, isAtEditTrip = false, isAtBoatExpense = false, isAtBoatDetails = false, isAtBoatTripExp = false, isAtNotification = false, isAtTripExpView = false, isAtTripFishSell = false, isAtAccMaster = false, isAtAddAcc = false, isAtViewLedger = false, isAtAccDetails = false, isAtUserMaster = false, isAtAddUser = false, isAtExpMaster = false, isAtAddExp = false, isAtChangePass = false, isAtHomeTripExp = false, isAtHomeFishSell = false, isAtAddNewAcc = false, isAtBoatEnterExp = false, isAtSeasonMaster = false, isAtAddSeason = false, isAtTripMasterTripExp = false, isAtTripMasterFishSell = false, isAtTripMasterEnterTripExp = false, isAtTripMasterEnterFishSell = false, isAtAddNotification = false, isAtUserViewLedger = false, isAtUserAccDetails = false;
    public static boolean isAtUserTripExp = false, isAtUserFishSell = false, isAtUserEnterTransaction = false;

    private LinearLayout llAdminMenu, llUserMenu, llManagerMenu;
    private TextView tvMenuAddSeason, tvMenuAddAccount, tvMenuUserMgmt, tvMenuBoatMaster, tvMenuFishMaster, tvMenuTripMaster, tvMenuShowExpenses, tvMenuViewLedger, tvMenuChangePassword, tvMenuLogout, tvMenuSendNotification, tvMenuTransactionMaster, tvUserLogout, tvUserChangePass, tvManagerBoatMaster, tvManagerTripMaster, tvManagerFishMaster, tvManagerChangePass, tvManagerViewLedger, tvManagerLogout, tvUserViewLedger, tvMenuTransaction;
    public static TextView tvTitle;

    String appUserType;
    int appUserId, appCoId;
    long appAccId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Typeface boldFont = Typeface.createFromAsset(this.getAssets(), "SofiaProBold.otf");

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setTypeface(boldFont);

        llAdminMenu = (LinearLayout) findViewById(R.id.llAdminMenu);
        llUserMenu = (LinearLayout) findViewById(R.id.llUserMenu);
        llManagerMenu = (LinearLayout) findViewById(R.id.llManagerMenu);

        SharedPreferences pref = getApplicationContext().getSharedPreferences(InterfaceApi.MY_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        appUserId = pref.getInt("AppUserId", 0);
        appAccId = pref.getLong("AppAccId", 0);
        appUserType = pref.getString("AppUserType", "");
        appCoId = pref.getInt("AppCoId", 0);

        Log.e("user id : ", "" + appUserId);
        Log.e("acc id : ", "" + appAccId);
        Log.e("user type : ", "" + appUserType);
        Log.e("Co id : ", "" + appCoId);
        if (appUserId <= 0) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

        if (appUserType.equalsIgnoreCase("Admin")) {
            llAdminMenu.setVisibility(View.VISIBLE);
            llUserMenu.setVisibility(View.GONE);
            llManagerMenu.setVisibility(View.GONE);
            Fragment fragment = new HomeFragment();

            if (fragment != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }
        } else if (appUserType.equalsIgnoreCase("Tandel")) {
            llAdminMenu.setVisibility(View.GONE);
            llUserMenu.setVisibility(View.VISIBLE);
            llManagerMenu.setVisibility(View.GONE);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new UserHomeFragment());
            ft.commit();
        } else if (appUserType.equalsIgnoreCase("Manager")) {
            llAdminMenu.setVisibility(View.GONE);
            llUserMenu.setVisibility(View.GONE);
            llManagerMenu.setVisibility(View.VISIBLE);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new UserHomeFragment());
            ft.commit();
        } else if (appUserType.equalsIgnoreCase("Auctioner")) {
            llAdminMenu.setVisibility(View.GONE);
            llUserMenu.setVisibility(View.VISIBLE);
            llManagerMenu.setVisibility(View.GONE);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new UserHomeFragment());
            ft.commit();
        } else {
            llAdminMenu.setVisibility(View.GONE);
            llManagerMenu.setVisibility(View.GONE);
            llUserMenu.setVisibility(View.VISIBLE);
        }

        tvMenuAddSeason = (TextView) findViewById(R.id.tvMenuAddSeason);
        tvMenuAddAccount = (TextView) findViewById(R.id.tvMenuAddAccount);
        tvMenuUserMgmt = (TextView) findViewById(R.id.tvMenuUserMgmt);
        tvMenuBoatMaster = (TextView) findViewById(R.id.tvMenuBoatMaster);
        tvMenuFishMaster = (TextView) findViewById(R.id.tvMenuFishMaster);
        tvMenuTripMaster = (TextView) findViewById(R.id.tvMenuTripMaster);
        tvMenuShowExpenses = (TextView) findViewById(R.id.tvMenuShowExpenses);
        tvMenuViewLedger = (TextView) findViewById(R.id.tvMenuViewLedger);
        tvMenuChangePassword = (TextView) findViewById(R.id.tvMenuChangePassword);
        tvMenuLogout = (TextView) findViewById(R.id.tvMenuLogout);
        tvMenuSendNotification = (TextView) findViewById(R.id.tvMenuSendNotification);
        tvMenuTransactionMaster = (TextView) findViewById(R.id.tvMenuTransactionMaster);

        tvUserLogout = (TextView) findViewById(R.id.tvMenuUserLogout);
        tvUserChangePass = (TextView) findViewById(R.id.tvMenuUserChangePassword);
        tvUserViewLedger = (TextView) findViewById(R.id.tvMenuUserViewLedger);

        tvManagerBoatMaster = (TextView) findViewById(R.id.tvMenuManagerBoatMaster);
        tvManagerFishMaster = (TextView) findViewById(R.id.tvMenuManagerFishMaster);
        tvManagerTripMaster = (TextView) findViewById(R.id.tvMenuManagerTripMaster);
        tvManagerChangePass = (TextView) findViewById(R.id.tvMenuManagerChangePass);
        tvManagerViewLedger = (TextView) findViewById(R.id.tvMenuManagerViewLedger);
        tvManagerLogout = (TextView) findViewById(R.id.tvMenuManagerLogout);

        tvMenuViewLedger.setVisibility(View.GONE);
        //tvMenuShowExpenses.setVisibility(View.GONE);
       // tvMenuTransactionMaster.setVisibility(View.GONE);

        tvMenuAddSeason.setTypeface(boldFont);
        tvMenuAddAccount.setTypeface(boldFont);
        tvMenuUserMgmt.setTypeface(boldFont);
        tvMenuBoatMaster.setTypeface(boldFont);
        tvMenuFishMaster.setTypeface(boldFont);
        tvMenuTripMaster.setTypeface(boldFont);
        tvMenuShowExpenses.setTypeface(boldFont);
        tvMenuViewLedger.setTypeface(boldFont);
        tvMenuChangePassword.setTypeface(boldFont);
        tvMenuLogout.setTypeface(boldFont);
        tvMenuSendNotification.setTypeface(boldFont);
        tvMenuTransactionMaster.setTypeface(boldFont);


        tvMenuAddSeason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new SeasonMasterFragment();
                if (fragment != null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.commit();
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        tvMenuAddAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new AccountMasterFragment();
                if (fragment != null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.commit();
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        tvMenuUserMgmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new UserMasterFragment();
                if (fragment != null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.commit();
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        tvMenuShowExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new ExpenseMasterFragment();
                if (fragment != null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.commit();
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        tvMenuFishMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new FishMasterFragment();
                if (fragment != null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.commit();
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        tvMenuBoatMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new BoatMasterFragment();
                if (fragment != null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.commit();
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        tvMenuTripMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new TripMasterFragment();
                if (fragment != null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.commit();
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        tvMenuChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ChangePasswordFragment();
                if (fragment != null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.commit();
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        tvMenuLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogTheme);
                builder.setTitle("Logout from Application ?");
                builder.setMessage("Do you want to logout ?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences pref = getApplicationContext().getSharedPreferences(InterfaceApi.MY_PREF, MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.remove("AppUserId");
                        editor.remove("AppAccId");
                        editor.remove("AppUserType");
                        editor.commit();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        tvMenuSendNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new AddNotification();
                if (fragment != null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.commit();
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        tvUserChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ChangePasswordFragment();
                if (fragment != null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.commit();
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        tvUserLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogTheme);
                builder.setTitle("Logout from Application ?");
                builder.setMessage("Do you want to logout ?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences pref = getApplicationContext().getSharedPreferences(InterfaceApi.MY_PREF, MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.remove("AppUserId");
                        editor.remove("AppAccId");
                        editor.remove("AppUserType");
                        editor.commit();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        tvUserViewLedger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new UserViewLedgerFragment();
                if (fragment != null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.commit();
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        tvManagerBoatMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new BoatMasterFragment();
                if (fragment != null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.commit();
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        tvManagerFishMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new FishMasterFragment();
                if (fragment != null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.commit();
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        tvManagerTripMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new TripMasterFragment();
                if (fragment != null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.commit();
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        tvManagerChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ChangePasswordFragment();
                if (fragment != null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.commit();
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        tvManagerViewLedger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new UserViewLedgerFragment();
                if (fragment != null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.commit();
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });


        tvManagerLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogTheme);
                builder.setTitle("Logout from Application ?");
                builder.setMessage("Do you want to logout ?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences pref = getApplicationContext().getSharedPreferences(InterfaceApi.MY_PREF, MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.remove("AppUserId");
                        editor.remove("AppAccId");
                        editor.remove("AppUserType");
                        editor.commit();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.mipmap.menu_icon);
        toggle.syncState();

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {


            if (isAtHome) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogTheme);
                builder.setTitle("Confirm Action");
                builder.setMessage("Do you really want to exit?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            } else if (isAtFishMaster) {
                if (appUserType.equalsIgnoreCase("Admin")) {
                    llAdminMenu.setVisibility(View.VISIBLE);
                    llUserMenu.setVisibility(View.GONE);
                    llManagerMenu.setVisibility(View.GONE);
                    Fragment fragment = new HomeFragment();

                    if (fragment != null) {
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, fragment);
                        ft.commit();
                    }
                } else if (appUserType.equalsIgnoreCase("Tandel")) {
                    llAdminMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.VISIBLE);
                    llManagerMenu.setVisibility(View.GONE);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new UserHomeFragment());
                    ft.commit();
                } else if (appUserType.equalsIgnoreCase("Manager")) {
                    llAdminMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.GONE);
                    llManagerMenu.setVisibility(View.VISIBLE);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new UserHomeFragment());
                    ft.commit();
                } else if (appUserType.equalsIgnoreCase("Auctioner")) {
                    llAdminMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.VISIBLE);
                    llManagerMenu.setVisibility(View.GONE);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new UserHomeFragment());
                    ft.commit();
                } else {
                    llAdminMenu.setVisibility(View.GONE);
                    llManagerMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.VISIBLE);
                }

            } else if (isAtAddFish) {
                Fragment fragment = new FishMasterFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            } else if (isAtEditFish) {
                Fragment fragment = new FishMasterFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            } else if (isAtBoatMaster) {
                if (appUserType.equalsIgnoreCase("Admin")) {
                    llAdminMenu.setVisibility(View.VISIBLE);
                    llUserMenu.setVisibility(View.GONE);
                    llManagerMenu.setVisibility(View.GONE);
                    Fragment fragment = new HomeFragment();

                    if (fragment != null) {
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, fragment);
                        ft.commit();
                    }
                } else if (appUserType.equalsIgnoreCase("Tandel")) {
                    llAdminMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.VISIBLE);
                    llManagerMenu.setVisibility(View.GONE);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new UserHomeFragment());
                    ft.commit();
                } else if (appUserType.equalsIgnoreCase("Manager")) {
                    llAdminMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.GONE);
                    llManagerMenu.setVisibility(View.VISIBLE);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new UserHomeFragment());
                    ft.commit();
                } else if (appUserType.equalsIgnoreCase("Auctioner")) {
                    llAdminMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.VISIBLE);
                    llManagerMenu.setVisibility(View.GONE);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new UserHomeFragment());
                    ft.commit();
                } else {
                    llAdminMenu.setVisibility(View.GONE);
                    llManagerMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.VISIBLE);
                }

            } else if (isAtAddBoat) {
                Fragment fragment = new BoatMasterFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            } else if (isAtEditBoat) {
                Fragment fragment = new BoatMasterFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            } else if (isAtTripDetails) {
                Fragment fragment = new BoatMasterFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            } else if (isAtTripMaster) {
                if (appUserType.equalsIgnoreCase("Admin")) {
                    llAdminMenu.setVisibility(View.VISIBLE);
                    llUserMenu.setVisibility(View.GONE);
                    llManagerMenu.setVisibility(View.GONE);
                    Fragment fragment = new HomeFragment();

                    if (fragment != null) {
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, fragment);
                        ft.commit();
                    }
                } else if (appUserType.equalsIgnoreCase("Tandel")) {
                    llAdminMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.VISIBLE);
                    llManagerMenu.setVisibility(View.GONE);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new UserHomeFragment());
                    ft.commit();
                } else if (appUserType.equalsIgnoreCase("Manager")) {
                    llAdminMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.GONE);
                    llManagerMenu.setVisibility(View.VISIBLE);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new UserHomeFragment());
                    ft.commit();
                } else if (appUserType.equalsIgnoreCase("Auctioner")) {
                    llAdminMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.VISIBLE);
                    llManagerMenu.setVisibility(View.GONE);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new UserHomeFragment());
                    ft.commit();
                } else {
                    llAdminMenu.setVisibility(View.GONE);
                    llManagerMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.VISIBLE);
                }

            } else if (isAtAddTrip) {
                Fragment fragment = new TripMasterFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            } else if (isAtEditTrip) {
                Fragment fragment = new TripMasterFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            } else if (isAtBoatExpense) {
                Fragment fragment = new BoatMasterFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            } else if (isAtBoatDetails) {
                Fragment fragment = new BoatMasterFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            } else if (isAtBoatTripExp) {
                Fragment fragment = new TripDetailsFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            } else if (isAtNotification) {
                if (appUserType.equalsIgnoreCase("Admin")) {
                    llAdminMenu.setVisibility(View.VISIBLE);
                    llUserMenu.setVisibility(View.GONE);
                    llManagerMenu.setVisibility(View.GONE);
                    Fragment fragment = new HomeFragment();

                    if (fragment != null) {
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, fragment);
                        ft.commit();
                    }
                } else if (appUserType.equalsIgnoreCase("Tandel")) {
                    llAdminMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.VISIBLE);
                    llManagerMenu.setVisibility(View.GONE);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new UserHomeFragment());
                    ft.commit();
                } else if (appUserType.equalsIgnoreCase("Manager")) {
                    llAdminMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.GONE);
                    llManagerMenu.setVisibility(View.VISIBLE);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new UserHomeFragment());
                    ft.commit();
                } else if (appUserType.equalsIgnoreCase("Auctioner")) {
                    llAdminMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.VISIBLE);
                    llManagerMenu.setVisibility(View.GONE);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new UserHomeFragment());
                    ft.commit();
                } else {
                    llAdminMenu.setVisibility(View.GONE);
                    llManagerMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.VISIBLE);
                }

            } else if (isAtTripExpView) {
                Fragment fragment = new TripMasterFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            } else if (isAtTripFishSell) {
                Fragment fragment = new TripMasterFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            } else if (isAtAccMaster) {
                Fragment fragment = new HomeFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            } else if (isAtAddAcc) {
                Fragment fragment = new AccountMasterFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            } else if (isAtViewLedger) {
                Fragment fragment = new AccountMasterFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            } else if (isAtAccDetails) {
                Fragment fragment = new ViewLedgerFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            } else if (isAtUserMaster) {
                if (appUserType.equalsIgnoreCase("Admin")) {
                    llAdminMenu.setVisibility(View.VISIBLE);
                    llUserMenu.setVisibility(View.GONE);
                    llManagerMenu.setVisibility(View.GONE);
                    Fragment fragment = new HomeFragment();

                    if (fragment != null) {
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, fragment);
                        ft.commit();
                    }
                } else if (appUserType.equalsIgnoreCase("Tandel")) {
                    llAdminMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.VISIBLE);
                    llManagerMenu.setVisibility(View.GONE);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new UserHomeFragment());
                    ft.commit();
                } else if (appUserType.equalsIgnoreCase("Manager")) {
                    llAdminMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.GONE);
                    llManagerMenu.setVisibility(View.VISIBLE);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new UserHomeFragment());
                    ft.commit();
                } else if (appUserType.equalsIgnoreCase("Auctioner")) {
                    llAdminMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.VISIBLE);
                    llManagerMenu.setVisibility(View.GONE);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new UserHomeFragment());
                    ft.commit();
                } else {
                    llAdminMenu.setVisibility(View.GONE);
                    llManagerMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.VISIBLE);
                }

            } else if (isAtAddUser) {
                Fragment fragment = new UserMasterFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            } else if (isAtExpMaster) {
                if (appUserType.equalsIgnoreCase("Admin")) {
                    llAdminMenu.setVisibility(View.VISIBLE);
                    llUserMenu.setVisibility(View.GONE);
                    llManagerMenu.setVisibility(View.GONE);
                    Fragment fragment = new HomeFragment();

                    if (fragment != null) {
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, fragment);
                        ft.commit();
                    }
                } else if (appUserType.equalsIgnoreCase("Tandel")) {
                    llAdminMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.VISIBLE);
                    llManagerMenu.setVisibility(View.GONE);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new UserHomeFragment());
                    ft.commit();
                } else if (appUserType.equalsIgnoreCase("Manager")) {
                    llAdminMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.GONE);
                    llManagerMenu.setVisibility(View.VISIBLE);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new UserHomeFragment());
                    ft.commit();
                } else if (appUserType.equalsIgnoreCase("Auctioner")) {
                    llAdminMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.VISIBLE);
                    llManagerMenu.setVisibility(View.GONE);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new UserHomeFragment());
                    ft.commit();
                } else {
                    llAdminMenu.setVisibility(View.GONE);
                    llManagerMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.VISIBLE);
                }

            } else if (isAtAddExp) {
                Fragment fragment = new ExpenseMasterFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            } else if (isAtChangePass) {

                if (appUserType.equalsIgnoreCase("Admin")) {
                    llAdminMenu.setVisibility(View.VISIBLE);
                    llUserMenu.setVisibility(View.GONE);
                    llManagerMenu.setVisibility(View.GONE);
                    Fragment fragment = new HomeFragment();

                    if (fragment != null) {
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, fragment);
                        ft.commit();
                    }
                } else if (appUserType.equalsIgnoreCase("Tandel")) {
                    llAdminMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.VISIBLE);
                    llManagerMenu.setVisibility(View.GONE);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new UserHomeFragment());
                    ft.commit();
                } else if (appUserType.equalsIgnoreCase("Manager")) {
                    llAdminMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.GONE);
                    llManagerMenu.setVisibility(View.VISIBLE);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new UserHomeFragment());
                    ft.commit();
                } else if (appUserType.equalsIgnoreCase("Auctioner")) {
                    llAdminMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.VISIBLE);
                    llManagerMenu.setVisibility(View.GONE);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new UserHomeFragment());
                    ft.commit();
                } else {
                    llAdminMenu.setVisibility(View.GONE);
                    llManagerMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.VISIBLE);
                }

            } else if (isAtHomeTripExp) {
                Fragment fragment = new HomeFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            } else if (isAtHomeFishSell) {
                Fragment fragment = new HomeFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            } else if (isAtAddNewAcc) {
                Fragment fragment = new AccountMasterFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            } else if (isAtUserTripExp) {
                if (appUserType.equalsIgnoreCase("Admin")) {
                    llAdminMenu.setVisibility(View.VISIBLE);
                    llUserMenu.setVisibility(View.GONE);
                    llManagerMenu.setVisibility(View.GONE);
                    Fragment fragment = new HomeFragment();

                    if (fragment != null) {
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, fragment);
                        ft.commit();
                    }
                } else if (appUserType.equalsIgnoreCase("Tandel")) {
                    llAdminMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.VISIBLE);
                    llManagerMenu.setVisibility(View.GONE);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new UserHomeFragment());
                    ft.commit();
                } else if (appUserType.equalsIgnoreCase("Manager")) {
                    llAdminMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.GONE);
                    llManagerMenu.setVisibility(View.VISIBLE);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new UserHomeFragment());
                    ft.commit();
                } else if (appUserType.equalsIgnoreCase("Auctioner")) {
                    llAdminMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.VISIBLE);
                    llManagerMenu.setVisibility(View.GONE);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new UserHomeFragment());
                    ft.commit();
                } else {
                    llAdminMenu.setVisibility(View.GONE);
                    llManagerMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.VISIBLE);
                }
            } else if (isAtUserFishSell) {
                if (appUserType.equalsIgnoreCase("Admin")) {
                    llAdminMenu.setVisibility(View.VISIBLE);
                    llUserMenu.setVisibility(View.GONE);
                    llManagerMenu.setVisibility(View.GONE);
                    Fragment fragment = new HomeFragment();

                    if (fragment != null) {
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, fragment);
                        ft.commit();
                    }
                } else if (appUserType.equalsIgnoreCase("Tandel")) {
                    llAdminMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.VISIBLE);
                    llManagerMenu.setVisibility(View.GONE);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new UserHomeFragment());
                    ft.commit();
                } else if (appUserType.equalsIgnoreCase("Manager")) {
                    llAdminMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.GONE);
                    llManagerMenu.setVisibility(View.VISIBLE);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new UserHomeFragment());
                    ft.commit();
                } else if (appUserType.equalsIgnoreCase("Auctioner")) {
                    llAdminMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.VISIBLE);
                    llManagerMenu.setVisibility(View.GONE);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new UserHomeFragment());
                    ft.commit();
                } else {
                    llAdminMenu.setVisibility(View.GONE);
                    llManagerMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.VISIBLE);
                }
            } else if (isAtUserEnterTransaction) {
                Fragment fragment = new UserTripExpenseFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            } else if (isAtBoatEnterExp) {
                Fragment fragment = new BoatExpensesFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            } else if (isAtAddSeason) {
                Fragment fragment = new SeasonMasterFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            } else if (isAtSeasonMaster) {
                if (appUserType.equalsIgnoreCase("Admin")) {
                    llAdminMenu.setVisibility(View.VISIBLE);
                    llUserMenu.setVisibility(View.GONE);
                    llManagerMenu.setVisibility(View.GONE);
                    Fragment fragment = new HomeFragment();

                    if (fragment != null) {
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, fragment);
                        ft.commit();
                    }
                } else if (appUserType.equalsIgnoreCase("Tandel")) {
                    llAdminMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.VISIBLE);
                    llManagerMenu.setVisibility(View.GONE);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new UserHomeFragment());
                    ft.commit();
                } else if (appUserType.equalsIgnoreCase("Manager")) {
                    llAdminMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.GONE);
                    llManagerMenu.setVisibility(View.VISIBLE);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new UserHomeFragment());
                    ft.commit();
                } else if (appUserType.equalsIgnoreCase("Auctioner")) {
                    llAdminMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.VISIBLE);
                    llManagerMenu.setVisibility(View.GONE);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new UserHomeFragment());
                    ft.commit();
                } else {
                    llAdminMenu.setVisibility(View.GONE);
                    llManagerMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.VISIBLE);
                }
            } else if (isAtTripMasterTripExp) {
                Fragment fragment = new TripMasterFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            } else if (isAtTripMasterFishSell) {
                Fragment fragment = new TripMasterFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            } else if (isAtTripMasterEnterTripExp) {
                Fragment fragment = new TripMasterViewTripExpFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            } else if (isAtTripMasterEnterFishSell) {
                Fragment fragment = new TripMasterFishSellFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            } else if (isAtAddNotification) {
                if (appUserType.equalsIgnoreCase("Admin")) {
                    llAdminMenu.setVisibility(View.VISIBLE);
                    llUserMenu.setVisibility(View.GONE);
                    llManagerMenu.setVisibility(View.GONE);
                    Fragment fragment = new HomeFragment();

                    if (fragment != null) {
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, fragment);
                        ft.commit();
                    }
                } else if (appUserType.equalsIgnoreCase("Tandel")) {
                    llAdminMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.VISIBLE);
                    llManagerMenu.setVisibility(View.GONE);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new UserHomeFragment());
                    ft.commit();
                } else if (appUserType.equalsIgnoreCase("Manager")) {
                    llAdminMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.GONE);
                    llManagerMenu.setVisibility(View.VISIBLE);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new UserHomeFragment());
                    ft.commit();
                } else if (appUserType.equalsIgnoreCase("Auctioner")) {
                    llAdminMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.VISIBLE);
                    llManagerMenu.setVisibility(View.GONE);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new UserHomeFragment());
                    ft.commit();
                } else {
                    llAdminMenu.setVisibility(View.GONE);
                    llManagerMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.VISIBLE);
                }
            } else if (isAtUserViewLedger) {
                if (appUserType.equalsIgnoreCase("Admin")) {
                    llAdminMenu.setVisibility(View.VISIBLE);
                    llUserMenu.setVisibility(View.GONE);
                    llManagerMenu.setVisibility(View.GONE);
                    Fragment fragment = new HomeFragment();

                    if (fragment != null) {
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, fragment);
                        ft.commit();
                    }
                } else if (appUserType.equalsIgnoreCase("Tandel")) {
                    llAdminMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.VISIBLE);
                    llManagerMenu.setVisibility(View.GONE);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new UserHomeFragment());
                    ft.commit();
                } else if (appUserType.equalsIgnoreCase("Manager")) {
                    llAdminMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.GONE);
                    llManagerMenu.setVisibility(View.VISIBLE);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new UserHomeFragment());
                    ft.commit();
                } else if (appUserType.equalsIgnoreCase("Auctioner")) {
                    llAdminMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.VISIBLE);
                    llManagerMenu.setVisibility(View.GONE);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new UserHomeFragment());
                    ft.commit();
                } else {
                    llAdminMenu.setVisibility(View.GONE);
                    llManagerMenu.setVisibility(View.GONE);
                    llUserMenu.setVisibility(View.VISIBLE);
                }
            } else if (isAtUserAccDetails) {
                Fragment fragment = new UserViewLedgerFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_notification) {
            Fragment fragment = new NotificationFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
