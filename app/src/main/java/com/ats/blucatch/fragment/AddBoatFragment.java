package com.ats.blucatch.fragment;


import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.blucatch.R;
import com.ats.blucatch.activity.MainActivity;
import com.ats.blucatch.bean.AccountData;
import com.ats.blucatch.bean.Boat;
import com.ats.blucatch.bean.ErrorMessage;
import com.ats.blucatch.utils.CheckNetwork;
import com.ats.blucatch.utils.InterfaceApi;
import com.ats.blucatch.utils.MySpinnerAdapter;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddBoatFragment extends Fragment {

    int userId, coId;
    private TextView tvLabelRegOwner, tvLabelRegTandel, tvLabelRegAuctioner, tvLabelFishingTypes, tvLabelBoatStatus, tvLabelBoatSize, tvOwnerId, tvTandelId, tvAuctionerId;
    private TextInputLayout textBoatName, textRegNo, textPurDate, textBoatSize, textLicNo, textLicFrom, textLicTo, textInsureFrom, textInsureTo, textEngineType, textHPRating, textL, textB, textH;
    private EditText edBoatName, edRegNo, edPurchaseDate, edBoatSize, edLicNo, edLicFrom, edLicTo, edInsureFrom, edInsureTo, edEngineType, edHPRating, edL, edB, edH;
    private Spinner spRegOwner, spRegTandel, spRegAuctioner, spFishingType, spBoatStatus;
    private Button btnSave, btnReset;

    private ArrayList<String> boatStatusArray = new ArrayList<>();
    private ArrayList<String> ownerArray = new ArrayList<>();
    private ArrayList<Long> ownerIdArray = new ArrayList<>();
    private ArrayList<String> tandelArray = new ArrayList<>();
    private ArrayList<Long> tandelIdArray = new ArrayList<>();
    private ArrayList<String> auctionerArray = new ArrayList<>();
    private ArrayList<Long> auctionerIdArray = new ArrayList<>();
    private ArrayList<String> fishingTypeArray = new ArrayList<>();

    private ProgressDialog progressBar;

    private int purchaseYear, purchaseMonth, purchaseDay, licFromYear, licFromMonth, licFromDay, licToYear, licToMonth, licToDay, incFromYear, incFromMonth, incFromDay, incToYear, incToMonth, incToDay;
    private long purchaseMillis, licFromMillis, licToMillis, incFromMillis, incToMillis, todaysMillis;
    private int yyyy, mm, dd;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_boat, container, false);
        Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
        Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");
        MainActivity.tvTitle.setText("Add New Boat");
        MainActivity.tvTitle.setTypeface(boldFont);

        MainActivity.isAtHome = false;
        MainActivity.isAtFishMaster = false;
        MainActivity.isAtAddFish = false;
        MainActivity.isAtEditFish = false;
        MainActivity.isAtBoatMaster = false;
        MainActivity.isAtAddBoat = true;
        MainActivity.isAtEditBoat = false;
        MainActivity.isAtTripDetails = false;
        MainActivity.isAtTripMaster = false;
        MainActivity.isAtAddTrip = false;
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

        boatStatusArray.clear();
        boatStatusArray.add(0, "Select Boat Status");
        boatStatusArray.add(1, "Live");
        boatStatusArray.add(2, "Ready to go");
        boatStatusArray.add(3, "Under Maintenance");
        boatStatusArray.add(4, "Not in Use");

        fishingTypeArray.clear();
        fishingTypeArray.add(0, "Select Fishing Type");
        fishingTypeArray.add(1, "Type 1");

        tvOwnerId = view.findViewById(R.id.tvAddBoat_OwnerId);
        tvAuctionerId = view.findViewById(R.id.tvAddBoat_AuctionerId);
        tvTandelId = view.findViewById(R.id.tvAddBoat_TandelId);

        textBoatName = view.findViewById(R.id.textAddBoat_BoatName);
        textRegNo = view.findViewById(R.id.textAddBoat_RegNo);
        textPurDate = view.findViewById(R.id.textAddBoat_PurDate);
        textLicNo = view.findViewById(R.id.textAddBoat_LicenseNo);
        textLicFrom = view.findViewById(R.id.textAddBoat_LicFrom);
        textLicTo = view.findViewById(R.id.textAddBoat_Lic_To);
        textInsureFrom = view.findViewById(R.id.textAddBoat_InsureFrom);
        textInsureTo = view.findViewById(R.id.textAddBoat_InsureTo);
        textEngineType = view.findViewById(R.id.textAddBoat_EngineType);
        textHPRating = view.findViewById(R.id.textAddBoat_HPRating);
        textL = view.findViewById(R.id.textAddBoat_BoatSize_L);
        textB = view.findViewById(R.id.textAddBoat_BoatSize_B);
        textH = view.findViewById(R.id.textAddBoat_BoatSize_H);

        edBoatName = view.findViewById(R.id.edAddBoat_BoatName);
        edRegNo = view.findViewById(R.id.edAddBoat_RegNo);
        edPurchaseDate = view.findViewById(R.id.edAddBoat_PurDate);
        edLicNo = view.findViewById(R.id.edAddBoat_LicenseNo);
        edLicFrom = view.findViewById(R.id.edAddBoat_LicFrom);
        edLicTo = view.findViewById(R.id.edAddBoat_LicTo);
        edInsureFrom = view.findViewById(R.id.edAddBoat_InsureFrom);
        edInsureTo = view.findViewById(R.id.edAddBoat_InsureTo);
        edEngineType = view.findViewById(R.id.edAddBoat_EngineType);
        edHPRating = view.findViewById(R.id.edAddBoat_HPRating);
        edL = view.findViewById(R.id.edAddBoat_BoatSize_L);
        edB = view.findViewById(R.id.edAddBoat_BoatSize_B);
        edH = view.findViewById(R.id.edAddBoat_BoatSize_H);

        tvLabelBoatSize = view.findViewById(R.id.tvAddBoat_LabelBoatSize);
        tvLabelRegOwner = view.findViewById(R.id.tvAddBoat_LabelRegOwner);
        tvLabelRegTandel = view.findViewById(R.id.tvAddBoat_LabelRegTandel);
        tvLabelRegAuctioner = view.findViewById(R.id.tvAddBoat_LabelRegAuctioner);
        tvLabelFishingTypes = view.findViewById(R.id.tvAddBoat_LabelFishingType);
        tvLabelBoatStatus = view.findViewById(R.id.tvAddBoat_LabelBoatStatus);

        spRegOwner = view.findViewById(R.id.spAddBoat_RegOwner);
        spRegTandel = view.findViewById(R.id.spAddBoat_RegTandel);
        spRegAuctioner = view.findViewById(R.id.spAddBoat_RegAuctioner);
        spFishingType = view.findViewById(R.id.spAddBoat_FishingType);
        spBoatStatus = view.findViewById(R.id.spAddBoat_BoatStatus);

        btnSave = view.findViewById(R.id.btnAddBoat_Save);
        btnReset = view.findViewById(R.id.btnAddBoat_Reset);

        textBoatName.setTypeface(lightFont);
        textRegNo.setTypeface(lightFont);
        textPurDate.setTypeface(lightFont);
        textLicNo.setTypeface(lightFont);
        textLicFrom.setTypeface(lightFont);
        textLicTo.setTypeface(lightFont);
        textInsureFrom.setTypeface(lightFont);
        textInsureTo.setTypeface(lightFont);
        textEngineType.setTypeface(lightFont);
        textHPRating.setTypeface(lightFont);
        textL.setTypeface(lightFont);
        textB.setTypeface(lightFont);
        textH.setTypeface(lightFont);

        edBoatName.setTypeface(lightFont);
        edRegNo.setTypeface(lightFont);
        edPurchaseDate.setTypeface(lightFont);
        edLicNo.setTypeface(lightFont);
        edLicFrom.setTypeface(lightFont);
        edLicTo.setTypeface(lightFont);
        edInsureFrom.setTypeface(lightFont);
        edInsureTo.setTypeface(lightFont);
        edEngineType.setTypeface(lightFont);
        edHPRating.setTypeface(lightFont);
        edL.setTypeface(lightFont);
        edB.setTypeface(lightFont);
        edH.setTypeface(lightFont);

        tvLabelBoatSize.setTypeface(lightFont);
        tvLabelRegOwner.setTypeface(lightFont);
        tvLabelRegTandel.setTypeface(lightFont);
        tvLabelRegAuctioner.setTypeface(lightFont);
        tvLabelFishingTypes.setTypeface(lightFont);
        tvLabelBoatStatus.setTypeface(lightFont);

        btnSave.setTypeface(lightFont);
        btnReset.setTypeface(lightFont);

        Calendar calendar = Calendar.getInstance();
        yyyy = calendar.get(Calendar.YEAR);
        mm = calendar.get(Calendar.MONTH);
        dd = calendar.get(Calendar.DAY_OF_MONTH);
        purchaseMillis = calendar.getTimeInMillis();
        licFromMillis = calendar.getTimeInMillis();
        licToMillis = calendar.getTimeInMillis();
        incFromMillis = calendar.getTimeInMillis();
        incToMillis = calendar.getTimeInMillis();
        todaysMillis = calendar.getTimeInMillis();
        edPurchaseDate.setText(dd + " / " + (mm + 1) + " / " + yyyy);
        edLicFrom.setText(dd + " / " + (mm + 1) + " / " + yyyy);
        edLicTo.setText(dd + " / " + (mm + 1) + " / " + yyyy);
        edInsureFrom.setText(dd + " / " + (mm + 1) + " / " + yyyy);
        edInsureTo.setText(dd + " / " + (mm + 1) + " / " + yyyy);

        edPurchaseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), purchaseDateListener, yyyy, mm, dd);
                dialog.show();
            }
        });

        edLicFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), licFromDateListener, yyyy, mm, dd);
                dialog.show();
            }
        });

        edLicTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), licToDateListener, yyyy, mm, dd);
                dialog.show();
            }
        });

        edInsureFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), incFromDateListener, yyyy, mm, dd);
                dialog.show();
            }
        });

        edInsureTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), incToDateListener, yyyy, mm, dd);
                dialog.show();
            }
        });

        getSpinnerDataforBoat();
        setSpinnerAdapter();


        spRegOwner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tvOwnerId.setText("" + ownerIdArray.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spRegTandel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tvTandelId.setText("" + tandelIdArray.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spRegAuctioner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tvAuctionerId.setText("" + auctionerIdArray.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewBoat();
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


    public void setSpinnerAdapter() {
        MySpinnerAdapter spAdapterOwner = new MySpinnerAdapter(
                getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                ownerArray);
        spRegOwner.setAdapter(spAdapterOwner);

        MySpinnerAdapter spAdapterTandel = new MySpinnerAdapter(
                getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                tandelArray);
        spRegTandel.setAdapter(spAdapterTandel);

        MySpinnerAdapter spAdapterAuctioner = new MySpinnerAdapter(
                getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                auctionerArray);
        spRegAuctioner.setAdapter(spAdapterAuctioner);

        MySpinnerAdapter spAdapterFishingType = new MySpinnerAdapter(
                getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                fishingTypeArray);
        spFishingType.setAdapter(spAdapterFishingType);

        MySpinnerAdapter spAdapterBoatStatus = new MySpinnerAdapter(
                getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                boatStatusArray);
        spBoatStatus.setAdapter(spAdapterBoatStatus);
    }

    public void getSpinnerDataforBoat() {
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
                                for (int i = 0, j = 0, k = 0, a = 0; i < data.getAccount().size(); i++) {
                                    if (data.getAccount().get(i).getEmpType().equalsIgnoreCase("Owner")) {
                                        ownerArray.add(j, data.getAccount().get(i).getAccName());
                                        ownerIdArray.add(j, data.getAccount().get(i).getAccId());
                                        j++;
                                    }
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
                                        ownerArray);
                                spRegOwner.setAdapter(spAdapterOwner);

                                MySpinnerAdapter spAdapterTandel = new MySpinnerAdapter(
                                        getContext(),
                                        android.R.layout.simple_spinner_dropdown_item,
                                        tandelArray);
                                spRegTandel.setAdapter(spAdapterTandel);

                                MySpinnerAdapter spAdapterAuctioner = new MySpinnerAdapter(
                                        getContext(),
                                        android.R.layout.simple_spinner_dropdown_item,
                                        auctionerArray);
                                spRegAuctioner.setAdapter(spAdapterAuctioner);

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
    }

    public void addNewBoat() {

        if (CheckNetwork.isInternetAvailable(getContext())) {

            if (edBoatName.getText().toString().trim().isEmpty()) {
                Toast.makeText(getContext(), "please enter boat name", Toast.LENGTH_SHORT).show();
                edBoatName.requestFocus();
            } else if (edRegNo.getText().toString().trim().isEmpty()) {
                Toast.makeText(getContext(), "please enter boat registeration number", Toast.LENGTH_SHORT).show();
                edBoatName.requestFocus();
            } else if (edPurchaseDate.getText().toString().trim().isEmpty()) {
                Toast.makeText(getContext(), "please select boat purchase date", Toast.LENGTH_SHORT).show();
                edPurchaseDate.requestFocus();
            } else if (purchaseMillis >= todaysMillis) {
                Toast.makeText(getContext(), "please select valid purchase date", Toast.LENGTH_SHORT).show();
                edPurchaseDate.requestFocus();
            } else if (edL.getText().toString().trim().isEmpty()) {
                Toast.makeText(getContext(), "please enter boat length", Toast.LENGTH_SHORT).show();
                edL.requestFocus();
            } else if (edB.getText().toString().trim().isEmpty()) {
                Toast.makeText(getContext(), "please enter boat breadth", Toast.LENGTH_SHORT).show();
                edB.requestFocus();
            } else if (edH.getText().toString().trim().isEmpty()) {
                Toast.makeText(getContext(), "please enter boat height", Toast.LENGTH_SHORT).show();
                edH.requestFocus();
            } else if (spFishingType.getSelectedItemPosition() == 0) {
                Toast.makeText(getContext(), "please select fishing type", Toast.LENGTH_SHORT).show();
                spFishingType.requestFocus();
            } else if (edLicNo.getText().toString().trim().isEmpty()) {
                Toast.makeText(getContext(), "please enter boat license number", Toast.LENGTH_SHORT).show();
                edLicNo.requestFocus();
            } else if (edLicFrom.getText().toString().trim().isEmpty()) {
                Toast.makeText(getContext(), "please select license from date", Toast.LENGTH_SHORT).show();
                edLicFrom.requestFocus();
            } else if (licFromMillis >= todaysMillis) {
                Toast.makeText(getContext(), "please select valid license from date", Toast.LENGTH_SHORT).show();
                edLicFrom.requestFocus();
            } else if (edLicTo.getText().toString().trim().isEmpty()) {
                Toast.makeText(getContext(), "please select license to date", Toast.LENGTH_SHORT).show();
                edLicTo.requestFocus();
            } else if (licFromMillis >= licToMillis) {
                Toast.makeText(getContext(), "license from date is not valid", Toast.LENGTH_SHORT).show();
                edLicFrom.requestFocus();
            } else if (edInsureFrom.getText().toString().trim().isEmpty()) {
                Toast.makeText(getContext(), "please select insurance from date", Toast.LENGTH_SHORT).show();
                edInsureFrom.requestFocus();
            } else if (incFromMillis >= todaysMillis) {
                Toast.makeText(getContext(), "please select valid insurance from date", Toast.LENGTH_SHORT).show();
                edLicFrom.requestFocus();
            } else if (edInsureTo.getText().toString().trim().isEmpty()) {
                Toast.makeText(getContext(), "please select insurance to", Toast.LENGTH_SHORT).show();
                edInsureTo.requestFocus();
            } else if (incFromMillis >= incToMillis) {
                Toast.makeText(getContext(), "insurance from date is not valid", Toast.LENGTH_SHORT).show();
                edLicFrom.requestFocus();
            } else if (edEngineType.getText().toString().trim().isEmpty()) {
                Toast.makeText(getContext(), "please enter engine type", Toast.LENGTH_SHORT).show();
                edEngineType.requestFocus();
            } else if (edHPRating.getText().toString().trim().isEmpty()) {
                Toast.makeText(getContext(), "please enter boat HP rating", Toast.LENGTH_SHORT).show();
                edHPRating.requestFocus();
            } else if (spBoatStatus.getSelectedItemPosition() == 0) {
                Toast.makeText(getContext(), "please select boat status", Toast.LENGTH_SHORT).show();
                spBoatStatus.requestFocus();
            } else if (tvOwnerId.getText().toString().trim().isEmpty()) {
                Toast.makeText(getContext(), "please select boat Owner", Toast.LENGTH_SHORT).show();
                spRegOwner.requestFocus();
            } else if (tvTandelId.getText().toString().trim().isEmpty()) {
                Toast.makeText(getContext(), "please select boat tandel", Toast.LENGTH_SHORT).show();
                spRegTandel.requestFocus();
            } else if (tvAuctionerId.getText().toString().trim().isEmpty()) {
                Toast.makeText(getContext(), "please select boat auctioner", Toast.LENGTH_SHORT).show();
                spRegAuctioner.requestFocus();
            } else {
                String boatName = edBoatName.getText().toString().trim();
                String regNo = edRegNo.getText().toString().trim();
                long ownerId = Long.parseLong(tvOwnerId.getText().toString().trim());
                long tandelId = Long.parseLong(tvTandelId.getText().toString().trim());
                long auctionerId = Long.parseLong(tvAuctionerId.getText().toString().trim());
                int length = Integer.parseInt(edL.getText().toString().trim());
                int breadth = Integer.parseInt(edB.getText().toString().trim());
                int height = Integer.parseInt(edH.getText().toString().trim());
                String fishingType = spFishingType.getSelectedItem().toString();
                String licNumber = edLicNo.getText().toString().trim();
                String engineType = edEngineType.getText().toString().trim();
                int hpRating = Integer.parseInt(edHPRating.getText().toString().trim());
                String boatStatus = spBoatStatus.getSelectedItem().toString();

                Boat boat = new Boat(boatName, ownerId, regNo, purchaseMillis, length, breadth, height, fishingType, licNumber, licFromMillis, licToMillis, incFromMillis, incToMillis, engineType, hpRating, 0, tandelId, auctionerId, boatStatus, 0, coId, 0, userId);

                Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                InterfaceApi api = retrofit.create(InterfaceApi.class);

                Call<ErrorMessage> errorMessageCall = api.addBoat(boat);

                progressBar = new ProgressDialog(getContext());
                progressBar.setCancelable(false);
                progressBar.setMessage("please wait....");
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressBar.setProgress(0);
                progressBar.setMax(100);
                progressBar.show();

                errorMessageCall.enqueue(new Callback<ErrorMessage>() {
                    @Override
                    public void onResponse(Call<ErrorMessage> call, Response<ErrorMessage> response) {
                        try {
                            if (response.body() != null) {
                                ErrorMessage data = response.body();
                                if (data.getError()) {
                                    progressBar.dismiss();
                                    Toast.makeText(getContext(), "Unable to add boat!", Toast.LENGTH_SHORT).show();
                                    Log.e("ON RESPONSE : ", "ERROR : " + data.getMessage());

                                } else {
                                    progressBar.dismiss();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                                    builder.setTitle("Success");
                                    builder.setCancelable(false);
                                    builder.setMessage("New boat added successfully.");
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            resetData();
                                            getSpinnerDataforBoat();
                                            setSpinnerAdapter();
                                        }
                                    });
                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                }

                            } else {
                                progressBar.dismiss();
                                Toast.makeText(getContext(), "Unable to add boat!", Toast.LENGTH_SHORT).show();
                                Log.e("ON RESPONSE : ", "NO DATA");
                            }
                        } catch (Exception e) {
                            progressBar.dismiss();
                            Log.e("Exception : ", "" + e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<ErrorMessage> call, Throwable t) {
                        progressBar.dismiss();
                        Toast.makeText(getContext(), "Unable to add boat!", Toast.LENGTH_SHORT).show();
                        Log.e("ON FAILURE : ", "ERROR : " + t.getMessage());
                    }
                });

            }
        } else {
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

    }

    public void resetData() {

        edBoatName.setText("");
        edRegNo.setText("");
        tvOwnerId.setText("");
        tvTandelId.setText("");
        tvAuctionerId.setText("");
        edL.setText("");
        edB.setText("");
        edH.setText("");
        spFishingType.setSelection(0);
        edLicNo.setText("");
        edEngineType.setText("");
        edHPRating.setText("");
        spBoatStatus.setSelection(0);
        tvOwnerId.setText("");
        tvTandelId.setText("");
        tvAuctionerId.setText("");
        edPurchaseDate.setText("");
        edLicFrom.setText("");
        edLicTo.setText("");
        edInsureFrom.setText("");
        edInsureTo.setText("");
    }

    private DatePickerDialog.OnDateSetListener purchaseDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            purchaseYear = year;
            purchaseMonth = month + 1;
            purchaseDay = dayOfMonth;
            edPurchaseDate.setText(purchaseDay + " / " + purchaseMonth + " / " + purchaseYear);

            Calendar calendar = Calendar.getInstance();
            calendar.set(purchaseYear, purchaseMonth - 1, purchaseDay);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR, 0);
            purchaseMillis = calendar.getTimeInMillis();
        }
    };

    private DatePickerDialog.OnDateSetListener licFromDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            licFromYear = year;
            licFromMonth = month + 1;
            licFromDay = dayOfMonth;
            edLicFrom.setText(licFromDay + " / " + licFromMonth + " / " + licFromYear);

            Calendar calendar = Calendar.getInstance();
            calendar.set(licFromYear, licFromMonth - 1, licFromDay);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR, 0);
            licFromMillis = calendar.getTimeInMillis();
        }
    };

    private DatePickerDialog.OnDateSetListener licToDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            licToYear = year;
            licToMonth = month + 1;
            licToDay = dayOfMonth;
            edLicTo.setText(licToDay + " / " + licToMonth + " / " + licToYear);

            Calendar calendar = Calendar.getInstance();
            calendar.set(licToYear, licToMonth - 1, licToDay);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR, 0);
            licToMillis = calendar.getTimeInMillis();
        }
    };

    private DatePickerDialog.OnDateSetListener incFromDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            incFromYear = year;
            incFromMonth = month + 1;
            incFromDay = dayOfMonth;
            edInsureFrom.setText(incFromDay + " / " + incFromMonth + " / " + incFromYear);

            Calendar calendar = Calendar.getInstance();
            calendar.set(incFromYear, incFromMonth - 1, incFromDay);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR, 0);
            incFromMillis = calendar.getTimeInMillis();
        }
    };

    private DatePickerDialog.OnDateSetListener incToDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            incToYear = year;
            incToMonth = month + 1;
            incToDay = dayOfMonth;
            edInsureTo.setText(incToDay + " / " + incToMonth + " / " + incToYear);

            Calendar calendar = Calendar.getInstance();
            calendar.set(incToYear, incToMonth - 1, incToDay);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR, 0);
            incToMillis = calendar.getTimeInMillis();
        }
    };

}
