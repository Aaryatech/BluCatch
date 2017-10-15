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

public class EditFishFragment extends Fragment {

    int coId, userId;
    private TextView tvLabelFishSize, tvLabelRate, tvLabelFishType;
    private EditText edFishName, edMinRate, edMaxRate;
    private TextInputLayout textFishName, textMinRate, textMaxRate;
    private Button btnSave, btnReset;
    private Spinner spFishSize, spFishType;
    private ArrayList<String> spFishSizeArray = new ArrayList<>();
    private ArrayList<String> spFishTypeArray = new ArrayList<>();
    private ProgressDialog progressBar;
    private int fishId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_fish, container, false);
        MainActivity.tvTitle.setText("Edit Fish");

        MainActivity.isAtHome = false;
        MainActivity.isAtFishMaster = false;
        MainActivity.isAtAddFish = false;
        MainActivity.isAtEditFish = true;
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

        try {
            SharedPreferences pref = getContext().getSharedPreferences(InterfaceApi.MY_PREF, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            userId = pref.getInt("AppUserId", 0);
            coId = pref.getInt("AppCoId", 0);
            Log.e("Co_id : ", "" + coId);
        } catch (Exception e) {
            Log.e("Exception : ", "" + e.getMessage());
        }

        tvLabelFishSize = view.findViewById(R.id.tvLabelEditFish_FishSize);
        tvLabelRate = view.findViewById(R.id.tvLabelEditFish_Rate);

        edFishName = view.findViewById(R.id.edEditFish_FishName);
        edMinRate = view.findViewById(R.id.edEditFish_MinRate);
        edMaxRate = view.findViewById(R.id.edEditFish_MaxRate);

        textFishName = view.findViewById(R.id.textEditFish_FishName);
        textMinRate = view.findViewById(R.id.textEditFish_MinRate);
        textMaxRate = view.findViewById(R.id.textEditFish_MaxRate);

        btnSave = view.findViewById(R.id.btnEditFish_Save);
//        btnReset = view.findViewById(R.id.btnEditFish_Reset);

        tvLabelFishSize.setTypeface(lightFont);
        tvLabelRate.setTypeface(lightFont);

        edFishName.setTypeface(lightFont);
        edMinRate.setTypeface(lightFont);
        edMaxRate.setTypeface(lightFont);

        textFishName.setTypeface(lightFont);
        textMinRate.setTypeface(lightFont);
        textMaxRate.setTypeface(lightFont);

        btnSave.setTypeface(lightFont);
//        btnReset.setTypeface(lightFont);

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

        spFishType = view.findViewById(R.id.spEditFishType);
        ArrayAdapter<String> spTypeAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, spFishTypeArray);
        spFishType.setAdapter(spTypeAdapter);

        spFishSize = view.findViewById(R.id.spEditFishSize);
        ArrayAdapter<String> spAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, spFishSizeArray);
        spFishSize.setAdapter(spAdapter);

        String fishName = getArguments().getString("Fish_Name");
        Double minRate = getArguments().getDouble("Min_Rate");
        Double maxRate = getArguments().getDouble("Max_Rate");
        String fishType = getArguments().getString("Fish_Type");
        String fishSize = getArguments().getString("Fish_Size");
        fishId = getArguments().getInt("Fish_Id");

        edFishName.setText(fishName);
        edMinRate.setText("" + minRate);
        edMaxRate.setText("" + maxRate);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, spFishTypeArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFishType.setAdapter(adapter);
        if (!fishType.equals(null)) {
            int spinnerPosition = adapter.getPosition(fishType);
            spFishType.setSelection(spinnerPosition);
        }

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, spFishSizeArray);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFishSize.setAdapter(adapter2);
        if (!fishSize.equals(null)) {
            int spinnerPosition = adapter2.getPosition(fishSize);
            spFishSize.setSelection(spinnerPosition);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editFish();

            }
        });

        return view;
    }

    public void editFish() {
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

                Call<ErrorMessage> errorMessageCall = api.editFish(fishId, fish);

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
                                    Toast.makeText(getContext(), "unable to edit fish!", Toast.LENGTH_SHORT).show();
                                    Log.e("ON RESPONSE : ", "ERROR : " + data.getMessage());

                                } else {
                                    progressBar.dismiss();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                                    builder.setTitle("Success");
                                    builder.setCancelable(false);
                                    builder.setMessage("Fish updated successfully.");
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
                                progressBar.dismiss();
                                Toast.makeText(getContext(), "unable to edit fish!", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getContext(), "unable to edit fish! server error", Toast.LENGTH_SHORT).show();
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
        edFishName.setText("");
        edMaxRate.setText("");
        edMinRate.setText("");
        spFishSize.setSelection(0);
        spFishType.setSelection(0);
    }


}
