package com.ats.blucatch.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.blucatch.R;
import com.ats.blucatch.activity.MainActivity;
import com.ats.blucatch.bean.AccountData;
import com.ats.blucatch.bean.AllSpinnerDataForTrip;
import com.ats.blucatch.bean.BoatData;
import com.ats.blucatch.bean.ErrorMessage;
import com.ats.blucatch.bean.Trip;
import com.ats.blucatch.utils.CheckNetwork;
import com.ats.blucatch.utils.InterfaceApi;
import com.ats.blucatch.utils.MySpinnerAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddTripFragment extends Fragment {

    private TextInputLayout textStartDate, textDays, textEndDate, textTandel, textAuctioner, textStaffCount;
    private EditText edStartDate, edDays, edEndDate, edTandel, edAuctioner, edStaffCount;
    private Button btnSave, btnReset;
    private RadioButton rbLive, rbClosed;
    private TextView tvBoatStatus, tvBoatName, tvTandelName, tvAuctionerName, tvBoatId, tvTandelId, tvAuctionerId;
    private Spinner spBoatName, spTandelName, spAuctionerName;
    private ProgressDialog progressBar, progressBar1, progressBar2, progressBar3;
    private ArrayList<String> boatArray = new ArrayList<>();
    private ArrayList<Long> boatIdArray = new ArrayList<>();
    private ArrayList<Long> tandelIdArray = new ArrayList<>();
    private ArrayList<Long> auctionerIdArray = new ArrayList<>();
    private ArrayList<String> tandelArray = new ArrayList<>();
    private ArrayList<String> auctionerArray = new ArrayList<>();

    private ArrayList<String> staffNameArray = new ArrayList<>();
    private ArrayList<Long> staffIdArray = new ArrayList<>();


    String status = "Live";
    int coId, userId;


    String[] data = {"abc", "xyz", "pqr"};
    String temp = "";
    ArrayList<Integer> mSelectedItems = new ArrayList();
    ArrayList<Long> tempIds = new ArrayList<>();
    ArrayList<String> tempCrewName = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_trip, container, false);
        Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
        Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");
        MainActivity.tvTitle.setText("Add New Trip");
        MainActivity.tvTitle.setTypeface(boldFont);

        MainActivity.isAtHome = false;
        MainActivity.isAtFishMaster = false;
        MainActivity.isAtAddFish = false;
        MainActivity.isAtEditFish = false;
        MainActivity.isAtBoatMaster = false;
        MainActivity.isAtAddBoat = false;
        MainActivity.isAtEditBoat = false;
        MainActivity.isAtTripDetails = false;
        MainActivity.isAtTripMaster = false;
        MainActivity.isAtAddTrip = true;
        MainActivity.isAtEditTrip = false;
        MainActivity.isAtBoatExpense = false;
        MainActivity.isAtBoatDetails = false;
        MainActivity.isAtBoatTripExp = false;
        MainActivity.isAtNotification = false;
        MainActivity.isAtTripExpView = false;
        MainActivity.isAtTripFishSell = false;
        MainActivity.isAtAccMaster = false;
        MainActivity.isAtAddAcc = false;
        MainActivity.isAtViewLedger = false;
        MainActivity.isAtAccDetails = false;
        MainActivity.isAtUserMaster = false;
        MainActivity.isAtAddUser = false;
        MainActivity.isAtExpMaster = false;
        MainActivity.isAtAddExp = false;
        MainActivity.isAtChangePass = false;
        MainActivity.isAtHomeTripExp = false;
        MainActivity.isAtHomeFishSell = false;

        try {
            SharedPreferences pref = getContext().getSharedPreferences(InterfaceApi.MY_PREF, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            userId = pref.getInt("AppUserId", 0);
            coId = pref.getInt("AppCoId", 0);
            Log.e("Co_id : ", "" + coId);
        } catch (Exception e) {
            Log.e("Exception : ", "" + e.getMessage());
        }

        spBoatName = view.findViewById(R.id.spAddTrip_BoatName);
        tvBoatName = view.findViewById(R.id.tvLabelAddTrip_BoatName);
        spTandelName = view.findViewById(R.id.spAddTrip_TandelName);
        tvTandelName = view.findViewById(R.id.tvLabelAddTrip_TandelName);
        spAuctionerName = view.findViewById(R.id.spAddTrip_AuctionerName);
        tvAuctionerName = view.findViewById(R.id.tvLabelAddTrip_AuctionerName);
        tvBoatId = view.findViewById(R.id.tvAddTrip_BoatId);
        tvTandelId = view.findViewById(R.id.tvAddTrip_TandelId);
        tvAuctionerId = view.findViewById(R.id.tvAddTrip_AuctionerId);

        textStartDate = view.findViewById(R.id.textAddTrip_StartDate);
        textEndDate = view.findViewById(R.id.textAddTrip_EndDate);
        textStaffCount = view.findViewById(R.id.textAddTrip_StaffCount);

        edStartDate = view.findViewById(R.id.edAddTrip_StartDate);
        edEndDate = view.findViewById(R.id.edAddTrip_EndDate);
        edStaffCount = view.findViewById(R.id.edAddTrip_StaffCount);

        btnSave = view.findViewById(R.id.btnAddTrip_Save);
        btnReset = view.findViewById(R.id.btnAddTrip_Reset);

        rbLive = view.findViewById(R.id.rbAddTrip_Live);
        rbClosed = view.findViewById(R.id.rbAddTrip_Closed);

        tvBoatStatus = view.findViewById(R.id.tvLabelAddTrip_TripStatus);

        tvBoatName.setTypeface(lightFont);
        tvTandelName.setTypeface(lightFont);
        tvAuctionerName.setTypeface(lightFont);
        textStartDate.setTypeface(lightFont);
        textEndDate.setTypeface(lightFont);
        textStaffCount.setTypeface(lightFont);

        edStartDate.setTypeface(lightFont);
        edEndDate.setTypeface(lightFont);
        edStaffCount.setTypeface(lightFont);

        btnSave.setTypeface(lightFont);
        btnReset.setTypeface(lightFont);

        rbLive.setTypeface(lightFont);
        rbClosed.setTypeface(lightFont);

        tvBoatStatus.setTypeface(lightFont);

        edStartDate.setKeyListener(null);
        edEndDate.setKeyListener(null);

        rbLive.setChecked(true);


        edStaffCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                staffDialog();
            }
        });

        getSpinnerDataforBoat();
        // getSpinnerDataforName();
        getSpinnerDataforAccount();
        spinnerListener();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewTrip();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetData();
            }
        });

        return view;
    }

    public void addNewTrip() {
        try {
            if (CheckNetwork.isInternetAvailable(getContext())) {

                if (tvBoatId.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "please select boat", Toast.LENGTH_SHORT).show();
                    spBoatName.requestFocus();
                } else if (tvTandelId.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "please select tandel", Toast.LENGTH_SHORT).show();
                    spTandelName.requestFocus();
                } else if (tvAuctionerId.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "please select auctioner", Toast.LENGTH_SHORT).show();
                    spAuctionerName.requestFocus();
                }
//            else if (edStaffCount.getText().toString().isEmpty()) {
//                Toast.makeText(getContext(), "please select staff", Toast.LENGTH_SHORT).show();
//                edStaffCount.requestFocus();
//            }
                else {
                    long boatId = Integer.parseInt(tvBoatId.getText().toString().trim());
                    long tanId = Integer.parseInt(tvTandelId.getText().toString().trim());
                    long aucId = Integer.parseInt(tvAuctionerId.getText().toString().trim());
                    String staffCount = "";
                    if (rbLive.isChecked()) {
                        status = "Live";
                    } else if (rbClosed.isChecked()) {
                        status = "Closed";
                    }
                    String staffIds = "";
                    if (tempIds.size() > 0) {
                        for (int m = 0; m < tempIds.size(); m++) {
                            staffCount += tempIds.get(m) + ",";
                        }
                        Log.e("Staff Parameter : ", "" + staffCount);
                        staffIds = staffCount.substring(0, staffCount.length() - 1);
                        Log.e("Staff Parameter : ", " After Trip : " + staffIds);
                    }
                    Trip trip = new Trip(boatId, 0, 0, tanId, aucId, staffIds, 0, status, 0, coId, 0, userId, 0);

                    Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    InterfaceApi api = retrofit.create(InterfaceApi.class);

                    Call<ErrorMessage> errorMessageCall = api.addTrip(trip);

                    progressBar2 = new ProgressDialog(getContext());
                    progressBar2.setCancelable(false);
                    progressBar2.setMessage("please wait....");
                    progressBar2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressBar2.setProgress(0);
                    progressBar2.setMax(100);
                    progressBar2.show();

                    errorMessageCall.enqueue(new Callback<ErrorMessage>() {
                        @Override
                        public void onResponse(Call<ErrorMessage> call, Response<ErrorMessage> response) {
                            try {
                                if (response.body() != null) {
                                    ErrorMessage data = response.body();
                                    if (data.getError()) {
                                        progressBar2.dismiss();
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                                        builder.setTitle("Error");
                                        builder.setCancelable(false);
                                        builder.setMessage("" + data.getMessage());
                                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                resetData();
                                            }
                                        });
                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                        // Toast.makeText(getContext(), "unable to add trip!", Toast.LENGTH_SHORT).show();
                                        Log.e("ON RESPONSE : ", "ERROR : " + data.getMessage());

                                    } else {
                                        progressBar2.dismiss();
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                                        builder.setTitle("Success");
                                        builder.setCancelable(false);
                                        builder.setMessage("New Trip added successfully.");
                                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                resetData();
                                            }
                                        });
                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                    }

                                } else {
                                    progressBar2.dismiss();
                                    Toast.makeText(getContext(), "unable to add trip!", Toast.LENGTH_SHORT).show();
                                    Log.e("ON RESPONSE : ", "NO DATA");
                                }
                            } catch (Exception e) {
                                progressBar2.dismiss();
                                Log.e("Exception : ", "" + e.getMessage());
                            }
                        }

                        @Override
                        public void onFailure(Call<ErrorMessage> call, Throwable t) {
                            progressBar2.dismiss();
                            Toast.makeText(getContext(), "unable to add trip! server error", Toast.LENGTH_SHORT).show();
                            Log.e("ON FAILURE : ", "ERROR : " + t.getMessage());
                        }
                    });

                }
            } else

            {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                builder.setTitle("Check Connectivity");
                builder.setCancelable(false);
                builder.setMessage("Please Connect to Internet");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        } catch (Exception e) {
            Log.e("Exception e : ", "" + e.getMessage());
        }

    }

    private void resetData() {
        edStaffCount.setText("");
        rbLive.setChecked(true);
        spBoatName.setSelection(0);
        spTandelName.setSelection(0);
        spAuctionerName.setSelection(0);
        getSpinnerDataforBoat();
        getSpinnerDataforAccount();
    }

    public void getSpinnerDataforBoat() {
        if (CheckNetwork.isInternetAvailable(getContext())) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            InterfaceApi api = retrofit.create(InterfaceApi.class);
            Call<BoatData> boatDataCall = api.allBoatData();

            //  progressBar1 = new ProgressDialog(getContext());
            // progressBar1.setCancelable(false);
            // progressBar1.setMessage("please wait....");
            //  progressBar1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            //   progressBar1.setProgress(0);
            //   progressBar1.setMax(100);
            //   progressBar1.show();

            boatDataCall.enqueue(new Callback<BoatData>() {
                @Override
                public void onResponse(Call<BoatData> call, Response<BoatData> response) {
                    try {
                        if (response.body() != null) {
                            BoatData data = response.body();
                            if (data.getErrorMessage().getError()) {
                                //  progressBar1.dismiss();
                                Log.e("RESPONSE : ", " ERROR : " + data.getErrorMessage().getMessage());
                            } else {
                                boatArray.clear();
                                boatIdArray.clear();
                                for (int i = 0, j = 0; i < data.getBoatDisp().size(); i++) {
                                    if (data.getBoatDisp().get(i).getBlockStatus() != 1 && data.getBoatDisp().get(i).getBoatStatus().equalsIgnoreCase("Ready to go")) {
                                        boatArray.add(j, data.getBoatDisp().get(i).getBoatName());
                                        boatIdArray.add(j, data.getBoatDisp().get(i).getBoatId());
                                        j++;
                                    }
                                }

                                Log.e("RESPONSE : ", " DATA : " + data.getBoatDisp());
                                MySpinnerAdapter spAdapterOwner = new MySpinnerAdapter(
                                        getContext(),
                                        android.R.layout.simple_spinner_dropdown_item,
                                        boatArray);
                                spBoatName.setAdapter(spAdapterOwner);
                                //    progressBar1.dismiss();
                            }

                        } else {
                            //    progressBar1.dismiss();
                            Log.e("RESPONSE : ", " NO DATA");
                        }
                    } catch (Exception e) {
                        Log.e("Exception : ", "" + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<BoatData> call, Throwable t) {
                    //  progressBar1.dismiss();
                    Log.e("ON FAILURE : ", " ERROR : " + t.getMessage());
                }
            });


        } else {
           /* AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.AlertDialogTheme);
            builder.setTitle("Check Connectivity");
            builder.setCancelable(false);
            builder.setMessage("Please Connect to Internet");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();*/
            Log.e("Add Trip : ", " NO Internet");
        }
    }

    public void getSpinnerDataforName() {
        if (CheckNetwork.isInternetAvailable(getContext())) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            InterfaceApi api = retrofit.create(InterfaceApi.class);
            Call<AccountData> accountDataCall = api.allAccountData();

            progressBar = new ProgressDialog(getContext());
            progressBar.setCancelable(false);
            progressBar.setMessage("please wait....");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.setProgress(0);
            progressBar.setMax(100);
            progressBar.show();

            accountDataCall.enqueue(new Callback<AccountData>() {
                @Override
                public void onResponse(Call<AccountData> call, Response<AccountData> response) {
                    try {
                        if (response.body() != null) {
                            AccountData data = response.body();
                            if (data.getErrorMessage().getError()) {
                                progressBar.dismiss();
                                Log.e("RESPONSE : ", " ERROR : " + data.getErrorMessage().getMessage());
                            } else {
                                for (int i = 0, k = 0, a = 0; i < data.getAccount().size(); i++) {
                                    if (data.getAccount().get(i).getEmpType().equalsIgnoreCase("Tandel")) {
                                        tandelArray.add(k, data.getAccount().get(i).getAccName());
                                        tandelIdArray.add(k, data.getAccount().get(i).getAccId());
                                        k++;
                                    }
                                    if (data.getAccount().get(i).getEmpType().equalsIgnoreCase("Auctioner")) {
                                        auctionerArray.add(a, data.getAccount().get(i).getAccName());
                                        auctionerIdArray.add(a, data.getAccount().get(i).getAccId());
                                        a++;
                                    }
                                }

                                Log.e("RESPONSE : ", " DATA : " + data.getAccount());
                                MySpinnerAdapter spAdapterOwner = new MySpinnerAdapter(
                                        getContext(),
                                        android.R.layout.simple_spinner_dropdown_item,
                                        tandelArray);
                                spTandelName.setAdapter(spAdapterOwner);

                                MySpinnerAdapter spAdapterTandel = new MySpinnerAdapter(
                                        getContext(),
                                        android.R.layout.simple_spinner_dropdown_item,
                                        auctionerArray);
                                spAuctionerName.setAdapter(spAdapterTandel);

                                progressBar.dismiss();
                            }

                        } else {
                            progressBar.dismiss();
                            Log.e("RESPONSE : ", " NO DATA");
                        }
                    } catch (Exception e) {
                        progressBar.dismiss();
                        Log.e("Exception : ", "" + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<AccountData> call, Throwable t) {
                    progressBar.dismiss();
                    Log.e("ON FAILURE : ", " ERROR : " + t.getMessage());
                }
            });


        } else {
           /* AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.AlertDialogTheme);
            builder.setTitle("Check Connectivity");
            builder.setCancelable(false);
            builder.setMessage("Please Connect to Internet");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();*/
            Log.e("Add Trip : ", " NO Internet");
        }
    }

    public void getSpinnerDataForStaff(final long tandelId) {
        if (CheckNetwork.isInternetAvailable(getContext())) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            InterfaceApi api = retrofit.create(InterfaceApi.class);
            Call<AccountData> accountDataCall = api.allAccountData();

//            progressBar3 = new ProgressDialog(getContext());
//            progressBar3.setCancelable(false);
//            progressBar3.setMessage("please wait....");
//            progressBar3.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressBar3.setProgress(0);
//            progressBar3.setMax(100);
//            progressBar3.show();

            accountDataCall.enqueue(new Callback<AccountData>() {
                @Override
                public void onResponse(Call<AccountData> call, Response<AccountData> response) {
                    try {
                        if (response.body() != null) {
                            AccountData data = response.body();
                            if (data.getErrorMessage().getError()) {
                                // progressBar3.dismiss();
                                Log.e("RESPONSE : ", " ERROR : " + data.getErrorMessage().getMessage());
                            } else {
                                staffNameArray.clear();
                                staffIdArray.clear();
                                for (int i = 0, j = 0; i < data.getAccount().size(); i++) {
                                    if (tandelId == data.getAccount().get(i).getTandelAccId()) {
                                        staffNameArray.add(j, data.getAccount().get(i).getAccName());
                                        staffIdArray.add(j, data.getAccount().get(i).getAccId());
                                    }
                                }
                                Log.e("RESPONSE : ", " CREW DATA : " + staffNameArray);

                                // progressBar3.dismiss();
                            }

                        } else {
                            // progressBar3.dismiss();
                            Log.e("RESPONSE : ", " NO DATA");
                        }
                    } catch (Exception e) {
                        Log.e("Exception : ", "" + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<AccountData> call, Throwable t) {
                    // progressBar3.dismiss();
                    Log.e("ON FAILURE : ", " ERROR : " + t.getMessage());
                }
            });


        } else {
            Log.e("Connection : ", " NO Internet Connection");
        }
    }

    public void spinnerListener() {
        spBoatName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tvBoatId.setText("" + boatIdArray.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spTandelName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tvTandelId.setText("" + tandelIdArray.get(i));
                edStaffCount.setText("");
                tempCrewName.clear();
                tempIds.clear();
                getSpinnerDataForStaff(tandelIdArray.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spAuctionerName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tvAuctionerId.setText("" + auctionerIdArray.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void staffDialog() {

        String[] staffArray = new String[staffNameArray.size()];
        staffArray = staffNameArray.toArray(staffArray);

        mSelectedItems.clear();
        tempCrewName.clear();
        tempIds.clear();

        AlertDialog.Builder ab = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
        ab.setTitle("Select Crew Members")
                .setMultiChoiceItems(staffArray, null, new DialogInterface.OnMultiChoiceClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            mSelectedItems.add(which);
                        } else if (mSelectedItems.contains(which)) {
                            mSelectedItems.remove(Integer.valueOf(which));
                        }
                    }

                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // Toast.makeText(getContext(), "" + mSelectedItems, Toast.LENGTH_SHORT).show();
                for (int i = 0, j = 0; i < mSelectedItems.size(); i++) {
                    tempIds.add(i, staffIdArray.get(mSelectedItems.get(i)));
                    tempCrewName.add(i, staffNameArray.get(mSelectedItems.get(i)));
                }
                if (tempCrewName.size() <= 0) {
                    edStaffCount.setText("");
                } else {
                    edStaffCount.setText("" + tempCrewName);
                }

                Log.e("Crew Ids : ", " : " + tempIds);
                Log.e("Crew Name : ", " : " + tempCrewName);
            }
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        ab.create();
        ab.show();

    }

    public void getSpinnerDataforAccount() {
        if (CheckNetwork.isInternetAvailable(getContext())) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            InterfaceApi api = retrofit.create(InterfaceApi.class);
            Call<AllSpinnerDataForTrip> allSpinnerDataForTripCall = api.allSpinnerDataForTrip();

            progressBar = new ProgressDialog(getContext());
            progressBar.setCancelable(false);
            progressBar.setMessage("please wait....");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.setProgress(0);
            progressBar.setMax(100);
            progressBar.show();

            allSpinnerDataForTripCall.enqueue(new Callback<AllSpinnerDataForTrip>() {
                @Override
                public void onResponse(Call<AllSpinnerDataForTrip> call, Response<AllSpinnerDataForTrip> response) {
                    try {
                        if (response.body() != null) {
                            AllSpinnerDataForTrip data = response.body();
                            if (data.getErrorMessage().getError()) {
                                progressBar.dismiss();
                                // Log.e("RESPONSE : ", " ERROR : " + data.getErrorMessage().getMessage());
                            } else {
                                tandelIdArray.clear();
                                tandelArray.clear();
                                auctionerIdArray.clear();
                                auctionerArray.clear();
                                for (int i = 0; i < data.getTandelSpinner().size(); i++) {
                                    tandelArray.add(i, data.getTandelSpinner().get(i).getTandelName());
                                    tandelIdArray.add(i, data.getTandelSpinner().get(i).getTandelId());
                                }
                                for (int i = 0; i < data.getAuctionerSpinner().size(); i++) {
                                    auctionerArray.add(i, data.getAuctionerSpinner().get(i).getAuctionerName());
                                    auctionerIdArray.add(i, data.getAuctionerSpinner().get(i).getAuctionerId());
                                }

                                MySpinnerAdapter spAdapterOwner = new MySpinnerAdapter(
                                        getContext(),
                                        android.R.layout.simple_spinner_dropdown_item,
                                        tandelArray);
                                spTandelName.setAdapter(spAdapterOwner);

                                MySpinnerAdapter spAdapterTandel = new MySpinnerAdapter(
                                        getContext(),
                                        android.R.layout.simple_spinner_dropdown_item,
                                        auctionerArray);
                                spAuctionerName.setAdapter(spAdapterTandel);

                                progressBar.dismiss();
                            }

                        } else {
                            progressBar.dismiss();
                            Log.e("RESPONSE : ", " NO DATA");
                        }
                    } catch (Exception e) {
                        progressBar.dismiss();
                        Log.e("Exception : ", " " + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<AllSpinnerDataForTrip> call, Throwable t) {
                    progressBar.dismiss();
                    Log.e("ON FAILURE : ", " ERROR : " + t.getMessage());
                }
            });


        } else {
           /* AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.AlertDialogTheme);
            builder.setTitle("Check Connectivity");
            builder.setCancelable(false);
            builder.setMessage("Please Connect to Internet");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();*/

            Log.e("Add Trip : ", " NO Internet");
        }
    }
}
