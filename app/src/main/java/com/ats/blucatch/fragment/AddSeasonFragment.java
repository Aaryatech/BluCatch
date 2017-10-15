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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ats.blucatch.R;
import com.ats.blucatch.activity.MainActivity;
import com.ats.blucatch.bean.ErrorMessage;
import com.ats.blucatch.bean.Season;
import com.ats.blucatch.utils.CheckNetwork;
import com.ats.blucatch.utils.InterfaceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddSeasonFragment extends Fragment {

    private TextInputLayout textRemark;
    private EditText edRemark;
    private Button btnSave, btnReset;
    private ProgressDialog progressBar;
    private int userId, coId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_season, container, false);
        Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
        Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");

        MainActivity.tvTitle.setText("Add New Season");
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
        MainActivity.isAtSeasonMaster = false;
        MainActivity.isAtAddSeason = true;

        SharedPreferences pref = getContext().getSharedPreferences(InterfaceApi.MY_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        userId = pref.getInt("AppUserId", 0);
        coId = pref.getInt("AppCoId", 0);
        Log.e("Co_id : ", "" + coId);

        edRemark = view.findViewById(R.id.edAddSeason_Remark);
        textRemark = view.findViewById(R.id.textAddSeason_Remark);
        btnSave = view.findViewById(R.id.btnAddSeason_Save);
        btnReset = view.findViewById(R.id.btnAddSeason_Reset);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewSeason();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edRemark.setText("");
            }
        });
        return view;
    }

    public void addNewSeason() {
        if (CheckNetwork.isInternetAvailable(getContext())) {
            if (edRemark.getText().toString().trim().isEmpty()) {
                Toast.makeText(getContext(), "please enter remark first", Toast.LENGTH_SHORT).show();
                edRemark.requestFocus();
            } else {

                String remark = edRemark.getText().toString().trim();
                Season season = new Season(0, 0, remark, 0, coId, 0, userId);

                Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                InterfaceApi api = retrofit.create(InterfaceApi.class);

                Call<ErrorMessage> errorMessageCall = api.addSeason(season);

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
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                                    builder.setTitle("Error");
                                    builder.setCancelable(false);
                                    builder.setMessage("" + data.getMessage());
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            edRemark.setText("");
                                        }
                                    });
                                    AlertDialog dialog = builder.create();
                                    dialog.show();

                                    Log.e("ON RESPONSE : ", "ERROR : " + data.getMessage());

                                } else {
                                    progressBar.dismiss();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                                    builder.setTitle("Success");
                                    builder.setCancelable(false);
                                    builder.setMessage("new season added successfully.");
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            // resetData();
                                        }
                                    });
                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                }

                            } else {
                                progressBar.dismiss();
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                                builder.setCancelable(false);
                                builder.setMessage("failed to add new season");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        // resetData();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();

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
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                        builder.setTitle("Error");
                        builder.setCancelable(false);
                        builder.setMessage("Server error");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

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

}
