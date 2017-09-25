package com.ats.blucatch.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.blucatch.R;
import com.ats.blucatch.activity.MainActivity;
import com.ats.blucatch.bean.ErrorMessage;
import com.ats.blucatch.bean.Fish;
import com.ats.blucatch.utils.CheckNetwork;
import com.ats.blucatch.utils.InterfaceApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddFishFragment extends Fragment {

    int userId, coId;
    private TextView tvLabelFishSize, tvLabelRate, tvLabelFishType;
    private EditText edFishName, edMinRate, edMaxRate;
    private TextInputLayout textFishName, textMinRate, textMaxRate;
    private Button btnSave, btnReset;
    private Spinner spFishSize, spFishType;
    private ArrayList<String> spFishSizeArray = new ArrayList<>();
    private ArrayList<String> spFishTypeArray = new ArrayList<>();
    private ProgressDialog progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_fish, container, false);
        MainActivity.tvTitle.setText("Add Fish");
        MainActivity.isAtHome = false;
        MainActivity.isAtFishMaster = false;
        MainActivity.isAtAddFish = true;
        MainActivity.isAtEditFish = false;
        MainActivity.isAtBoatMaster = false;
        MainActivity.isAtAddBoat = false;
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

        Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
        Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");

        MainActivity.tvTitle.setTypeface(boldFont);

        SharedPreferences pref = getContext().getSharedPreferences(InterfaceApi.MY_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        userId = pref.getInt("AppUserId", 0);
        coId = pref.getInt("AppCoId", 0);
        Log.e("Co_id : ", "" + coId);

        tvLabelFishSize = view.findViewById(R.id.tvLabelAddFish_FishSize);
        tvLabelFishType = view.findViewById(R.id.tvLabelAddFish_FishType);
        tvLabelRate = view.findViewById(R.id.tvLabelAddFish_Rate);

        edFishName = view.findViewById(R.id.edAddFish_FishName);
        edMinRate = view.findViewById(R.id.edAddFish_MinRate);
        edMaxRate = view.findViewById(R.id.edAddFish_MaxRate);

        textFishName = view.findViewById(R.id.textAddFish_FishName);
        textMinRate = view.findViewById(R.id.textAddFish_MinRate);
        textMaxRate = view.findViewById(R.id.textAddFish_MaxRate);

        btnSave = view.findViewById(R.id.btnAddFish_Save);
        btnReset = view.findViewById(R.id.btnAddFish_Reset);

        tvLabelFishSize.setTypeface(lightFont);
        tvLabelFishType.setTypeface(lightFont);
        tvLabelRate.setTypeface(lightFont);

        edFishName.setTypeface(lightFont);
        edMinRate.setTypeface(lightFont);
        edMaxRate.setTypeface(lightFont);

        textFishName.setTypeface(lightFont);
        textMinRate.setTypeface(lightFont);
        textMaxRate.setTypeface(lightFont);

        btnSave.setTypeface(lightFont);
        btnReset.setTypeface(lightFont);

        spFishSizeArray.clear();
        spFishSizeArray.add(0, "Select Fish Size");
        spFishSizeArray.add(1, "Above 5 kg");
        spFishSizeArray.add(2, "3 to 5 kg");
        spFishSizeArray.add(3, "2 to 3 kg");
        spFishSizeArray.add(4, "1 to 2 kg");
        spFishSizeArray.add(5, "Below 1 kg");

        spFishTypeArray.clear();
        spFishTypeArray.add(0, "Select Fish Type");
        spFishTypeArray.add(1, "Normal");
        spFishTypeArray.add(2, "Premium");
        spFishTypeArray.add(3, "Jackpot");

        spFishType = view.findViewById(R.id.spFishType);
        ArrayAdapter<String> spTypeAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, spFishTypeArray);
        spFishType.setAdapter(spTypeAdapter);

        spFishSize = view.findViewById(R.id.spFishSize);
        ArrayAdapter<String> spAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, spFishSizeArray);
        spFishSize.setAdapter(spAdapter);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFish();
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

    public void addFish() {
        if (CheckNetwork.isInternetAvailable(getContext())) {
            if (edFishName.getText().toString().trim().isEmpty()) {
                Toast.makeText(getContext(), "please enter fish name", Toast.LENGTH_SHORT).show();
                edFishName.requestFocus();
            } else if (spFishType.getSelectedItemPosition() == 0) {
                Toast.makeText(getContext(), "please select fish type", Toast.LENGTH_SHORT).show();
                spFishType.requestFocus();
            } else if (spFishSize.getSelectedItemPosition() == 0) {
                Toast.makeText(getContext(), "please select fish size", Toast.LENGTH_SHORT).show();
                spFishSize.requestFocus();
            } else if (edMinRate.getText().toString().trim().isEmpty()) {
                Toast.makeText(getContext(), "please enter min rate", Toast.LENGTH_SHORT).show();
                edMinRate.requestFocus();
            } else if (edMaxRate.getText().toString().trim().isEmpty()) {
                Toast.makeText(getContext(), "please enter max rate", Toast.LENGTH_SHORT).show();
                edMaxRate.requestFocus();
            } else {

                String fishName = edFishName.getText().toString().trim();
                String fishType = spFishType.getSelectedItem().toString();
                double minRate = Double.parseDouble(edMinRate.getText().toString().trim());
                double maxRate = Double.parseDouble(edMaxRate.getText().toString().trim());
                String fishSize = spFishSize.getSelectedItem().toString();
                int isUsed = 0;

                Fish fish = new Fish(fishName, fishType, isUsed, fishSize, minRate, maxRate, coId, 0, userId);

                Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                InterfaceApi api = retrofit.create(InterfaceApi.class);

                Call<ErrorMessage> errorMessageCall = api.addFish(fish);

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
                        if (response.body() != null) {
                            ErrorMessage data = response.body();
                            if (data.getError()) {
                                progressBar.dismiss();
                                Log.e("ON RESPONSE : ", "ERROR : " + data.getMessage());

                            } else {
                                progressBar.dismiss();
                                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
                                builder.setTitle("Success");
                                builder.setCancelable(false);
                                builder.setMessage("New fish added successfully.");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        resetData();
                                    }
                                });
                                android.app.AlertDialog dialog = builder.create();
                                dialog.show();
                            }

                        } else {
                            progressBar.dismiss();
                            Log.e("ON RESPONSE : ", "NO DATA");
                        }
                    }

                    @Override
                    public void onFailure(Call<ErrorMessage> call, Throwable t) {
                        progressBar.dismiss();
                        Log.e("ON FAILURE : ", "ERROR : " + t.getMessage());
                    }
                });

            }
        } else {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
            builder.setTitle("Check Connectivity");
            builder.setCancelable(false);
            builder.setMessage("Please Connect to Internet");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            android.app.AlertDialog dialog = builder.create();
            dialog.show();
        }

    }

    public void resetData() {
        edFishName.setText("");
        edMaxRate.setText("");
        edMinRate.setText("");
        spFishSize.setSelection(0);
        spFishType.setSelection(0);
    }

}
