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
import android.widget.ArrayAdapter;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditBoatFragment extends Fragment {

    int userId, coId;
    private TextView tvLabelRegOwner, tvLabelRegTandel, tvLabelRegAuctioner, tvLabelFishingTypes, tvLabelBoatStatus, tvLabelBoatSize, tvOwnerId, tvAuctionerId, tvTandelId;
    private TextInputLayout textBoatName, textRegNo, textPurDate, textBoatSize, textLicNo, textLicFrom, textLicTo, textInsureFrom, textInsureTo, textEngineType, textHPRating, textL, textB, textH;
    private EditText edBoatName, edRegNo, edPurchaseDate, edBoatSize, edLicNo, edLicFrom, edLicTo, edInsureFrom, edInsureTo, edEngineType, edHPRating, edL, edB, edH;
    private Spinner spRegOwner, spRegTandel, spRegAuctioner, spFishingType, spBoatStatus;
    private Button btnUpdate;

    private ArrayList<String> boatStatusArray = new ArrayList<>();
    private ArrayList<String> ownerArray = new ArrayList<>();
    private ArrayList<Long> ownerIdArray = new ArrayList<>();
    private ArrayList<String> tandelArray = new ArrayList<>();
    private ArrayList<Long> tandelIdArray = new ArrayList<>();
    private ArrayList<String> auctionerArray = new ArrayList<>();
    private ArrayList<Long> auctionerIdArray = new ArrayList<>();
    private ArrayList<String> fishingTypeArray = new ArrayList<>();
    private ProgressDialog progressBar, progressBar1;

    long boatId;


    private int purchaseYear, purchaseMonth, purchaseDay, licFromYear, licFromMonth, licFromDay, licToYear, licToMonth, licToDay, incFromYear, incFromMonth, incFromDay, incToYear, incToMonth, incToDay;
    private long purchaseMillis, licFromMillis, licToMillis, incFromMillis, incToMillis, todaysMillis;
    private int yyyy, mm, dd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_boat, container, false);
        Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
        Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");

        MainActivity.tvTitle.setText("Edit Boat");
        MainActivity.tvTitle.setTypeface(boldFont);

        MainActivity.isAtHome = false;
        MainActivity.isAtFishMaster = false;
        MainActivity.isAtAddFish = false;
        MainActivity.isAtEditFish = false;
        MainActivity.isAtBoatMaster = false;
        MainActivity.isAtAddBoat = false;
        MainActivity.isAtEditBoat = true;
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

        tvOwnerId = view.findViewById(R.id.tvEditBoat_ownerId);
        tvTandelId = view.findViewById(R.id.tvEditBoat_tandelId);
        tvAuctionerId = view.findViewById(R.id.tvEditBoat_auctionerId);
        textBoatName = view.findViewById(R.id.textEditBoat_BoatName);
        textRegNo = view.findViewById(R.id.textEditBoat_RegNo);
        textPurDate = view.findViewById(R.id.textEditBoat_PurDate);
        textLicNo = view.findViewById(R.id.textEditBoat_LicenseNo);
        textLicFrom = view.findViewById(R.id.textEditBoat_LicFrom);
        textLicTo = view.findViewById(R.id.textEditBoat_Lic_To);
        textInsureFrom = view.findViewById(R.id.textEditBoat_InsureFrom);
        textInsureTo = view.findViewById(R.id.textEditBoat_InsureTo);
        textEngineType = view.findViewById(R.id.textEditBoat_EngineType);
        textHPRating = view.findViewById(R.id.textEditBoat_HPRating);
        textL = view.findViewById(R.id.textEditBoat_BoatSize_L);
        textB = view.findViewById(R.id.textEditBoat_BoatSize_B);
        textH = view.findViewById(R.id.textEditBoat_BoatSize_H);

        edBoatName = view.findViewById(R.id.edEditBoat_BoatName);
        edRegNo = view.findViewById(R.id.edEditBoat_RegNo);
        edPurchaseDate = view.findViewById(R.id.edEditBoat_PurDate);
        edLicNo = view.findViewById(R.id.edEditBoat_LicenseNo);
        edLicFrom = view.findViewById(R.id.edEditBoat_LicFrom);
        edLicTo = view.findViewById(R.id.edEditBoat_LicTo);
        edInsureFrom = view.findViewById(R.id.edEditBoat_InsureFrom);
        edInsureTo = view.findViewById(R.id.edEditBoat_InsureTo);
        edEngineType = view.findViewById(R.id.edEditBoat_EngineType);
        edHPRating = view.findViewById(R.id.edEditBoat_HPRating);
        edL = view.findViewById(R.id.edEditBoat_BoatSize_L);
        edB = view.findViewById(R.id.edEditBoat_BoatSize_B);
        edH = view.findViewById(R.id.edEditBoat_BoatSize_H);

        tvLabelRegOwner = view.findViewById(R.id.tvEditBoat_LabelRegOwner);
        tvLabelRegTandel = view.findViewById(R.id.tvEditBoat_LabelRegTandel);
        tvLabelRegAuctioner = view.findViewById(R.id.tvEditBoat_LabelRegAuctioner);
        tvLabelFishingTypes = view.findViewById(R.id.tvEditBoat_LabelFishingType);
        tvLabelBoatStatus = view.findViewById(R.id.tvEditBoat_LabelBoatStatus);
        tvLabelBoatSize = view.findViewById(R.id.tvEditBoat_LabelBoatSize);

        spRegOwner = view.findViewById(R.id.spEditBoat_RegOwner);
        spRegTandel = view.findViewById(R.id.spEditBoat_RegTandel);
        spRegAuctioner = view.findViewById(R.id.spEditBoat_RegAuctioner);
        spFishingType = view.findViewById(R.id.spEditBoat_FishingType);
        spBoatStatus = view.findViewById(R.id.spEditBoat_BoatStatus);

        btnUpdate = view.findViewById(R.id.btnEditBoat_Update);

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
        textH.setTypeface(lightFont);
        textB.setTypeface(lightFont);

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

        tvLabelRegOwner.setTypeface(lightFont);
        tvLabelRegTandel.setTypeface(lightFont);
        tvLabelRegAuctioner.setTypeface(lightFont);
        tvLabelFishingTypes.setTypeface(lightFont);
        tvLabelBoatStatus.setTypeface(lightFont);
        tvLabelBoatSize.setTypeface(lightFont);

        btnUpdate.setTypeface(lightFont);

        Calendar calendar = Calendar.getInstance();
        yyyy = calendar.get(Calendar.YEAR);
        mm = calendar.get(Calendar.MONTH);
        dd = calendar.get(Calendar.DAY_OF_MONTH);
        todaysMillis = calendar.getTimeInMillis();

        boatId = getArguments().getLong("Boat_Id");
        String bName = getArguments().getString("Boat_Name");
        String bOwner = getArguments().getString("Boat_Owner");
        String bTandel = getArguments().getString("Boat_Tandel");
        String bAuctioner = getArguments().getString("Boat_Auctioner");
        String bRegno = getArguments().getString("Boat_RegNo");
        long bPurchaseDate = getArguments().getLong("Boat_Purchase_Date");
        int bLength = getArguments().getInt("Boat_Length");
        int bBreadth = getArguments().getInt("Boat_Breadth");
        int bHeight = getArguments().getInt("Boat_Height");
        String bFishingType = getArguments().getString("Boat_Fishing_type");
        String bLicNo = getArguments().getString("Boat_LicNo");
        long bLicFrom = getArguments().getLong("Boat_LicFrom");
        long bLicTo = getArguments().getLong("Boat_LicTo");
        long bIncFrom = getArguments().getLong("Boat_IncFrom");
        long bIncTo = getArguments().getLong("Boat_IncTo");
        String bEngineType = getArguments().getString("Boat_Engine_Type");
        String bStatus = getArguments().getString("Boat_Status");
        int bHPRating = getArguments().getInt("Boat_HP_Rating");

        edBoatName.setText("" + bName);
        edRegNo.setText("" + bRegno);

        Date purchaseDate = new Date(bPurchaseDate);
        SimpleDateFormat formatter = new SimpleDateFormat("dd / M / yyyy");
        String pDate = formatter.format(purchaseDate);
        edPurchaseDate.setText("" + pDate);
        purchaseMillis = bPurchaseDate;

        edLicNo.setText("" + bLicNo);

        Date licFromDate = new Date(bLicFrom);
        SimpleDateFormat sdfLicFrom = new SimpleDateFormat("dd / M / yyyy");
        String licFrom = sdfLicFrom.format(licFromDate);
        edLicFrom.setText("" + licFrom);
        licFromMillis = bLicFrom;

        Date licToDate = new Date(bLicTo);
        SimpleDateFormat sdfLicTo = new SimpleDateFormat("dd / M / yyyy");
        String licTo = sdfLicTo.format(licToDate);
        edLicTo.setText("" + licTo);
        licToMillis = bLicTo;

        Date incFromDate = new Date(bIncFrom);
        SimpleDateFormat sdfIncFrom = new SimpleDateFormat("dd / M / yyyy");
        String incFrom = sdfIncFrom.format(incFromDate);
        edInsureFrom.setText("" + incFrom);
        incFromMillis = bIncFrom;

        Date incToDate = new Date(bIncTo);
        SimpleDateFormat sdfIncTo = new SimpleDateFormat("dd / M / yyyy");
        String incTo = sdfIncFrom.format(incToDate);
        edInsureTo.setText("" + incTo);
        incToMillis = bIncTo;

        edEngineType.setText("" + bEngineType);
        edHPRating.setText("" + bHPRating);
        edL.setText("" + bLength);
        edB.setText("" + bBreadth);
        edH.setText("" + bHeight);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, boatStatusArray);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBoatStatus.setAdapter(adapter1);
        if (!bStatus.equals(null)) {
            int spinnerPosition = adapter1.getPosition(bStatus);
            spBoatStatus.setSelection(spinnerPosition);
        }

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, fishingTypeArray);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFishingType.setAdapter(adapter2);
        if (!bFishingType.equals(null)) {
            int spinnerPosition = adapter2.getPosition(bFishingType);
            spFishingType.setSelection(spinnerPosition);
        }


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
        spinnerListener();


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editBoatData(boatId);
            }
        });

        return view;
    }

    public void spinnerListener() {
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

                                String bOwner = getArguments().getString("Boat_Owner");
                                String bTandel = getArguments().getString("Boat_Tandel");
                                String bAuctioner = getArguments().getString("Boat_Auctioner");

                                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, ownerArray);
                                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spRegOwner.setAdapter(adapter1);
                                if (!bOwner.equals(null)) {
                                    int spinnerPosition = adapter1.getPosition(bOwner);
                                    spRegOwner.setSelection(spinnerPosition);
                                }

                                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, tandelArray);
                                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spRegTandel.setAdapter(adapter2);
                                if (!bTandel.equals(null)) {
                                    int spinnerPosition = adapter2.getPosition(bTandel);
                                    spRegTandel.setSelection(spinnerPosition);
                                }

                                ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, auctionerArray);
                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spRegAuctioner.setAdapter(adapter3);
                                if (!bAuctioner.equals(null)) {
                                    int spinnerPosition = adapter3.getPosition(bAuctioner);
                                    spRegAuctioner.setSelection(spinnerPosition);
                                }

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

    public void editBoatData(long id) {
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
                Log.e("BOAT : ", " : " + boat);

                Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                InterfaceApi api = retrofit.create(InterfaceApi.class);

                Call<ErrorMessage> errorMessageCall = api.editBoat(id, boat);

                progressBar1 = new ProgressDialog(getContext());
                progressBar1.setCancelable(false);
                progressBar1.setMessage("please wait....");
                progressBar1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressBar1.setProgress(0);
                progressBar1.setMax(100);
                progressBar1.show();

                errorMessageCall.enqueue(new Callback<ErrorMessage>() {
                    @Override
                    public void onResponse(Call<ErrorMessage> call, Response<ErrorMessage> response) {
                        try {
                            if (response.body() != null) {
                                ErrorMessage data = response.body();
                                if (data.getError()) {
                                    progressBar1.dismiss();
                                    Toast.makeText(getContext(), "Unable to update boat!", Toast.LENGTH_SHORT).show();
                                    Log.e("ON RESPONSE : ", "ERROR : " + data.getMessage());

                                } else {
                                    progressBar1.dismiss();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                                    builder.setTitle("Success");
                                    builder.setCancelable(false);
                                    builder.setMessage("Boat updated successfully.");
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                }

                            } else {
                                progressBar1.dismiss();
                                Toast.makeText(getContext(), "Unable to update boat!", Toast.LENGTH_SHORT).show();
                                Log.e("ON RESPONSE : ", "NO DATA");
                            }
                        } catch (Exception e) {
                            progressBar1.dismiss();
                            Log.e("Exception : ", "" + e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<ErrorMessage> call, Throwable t) {
                        progressBar1.dismiss();
                        Toast.makeText(getContext(), "Unable to update boat!", Toast.LENGTH_SHORT).show();
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
