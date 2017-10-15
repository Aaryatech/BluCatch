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
import com.ats.blucatch.bean.NotificationBean;
import com.ats.blucatch.utils.CheckNetwork;
import com.ats.blucatch.utils.InterfaceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddNotification extends Fragment {

    private TextInputLayout textTitle, textDesc;
    private EditText edTitle, edDesc;
    private Button btnSend, btnReset;
    private ProgressDialog progressBar;
    int userId, coId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_notification, container, false);
        Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
        Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");
        MainActivity.tvTitle.setText("New Notification");
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
        MainActivity.isAtAddNotification = true;

        try {
            SharedPreferences pref = getContext().getSharedPreferences(InterfaceApi.MY_PREF, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            userId = pref.getInt("AppUserId", 0);
            coId = pref.getInt("AppCoId", 0);
            Log.e("Co_id : ", "" + coId);
        } catch (Exception e) {
            Log.e("Exception : ", "" + e.getMessage());
        }

        textTitle = view.findViewById(R.id.textAddNotification_Title);
        textDesc = view.findViewById(R.id.textAddNotification_Desc);
        edTitle = view.findViewById(R.id.edAddNotification_Title);
        edDesc = view.findViewById(R.id.edAddNotification_Desc);
        btnSend = view.findViewById(R.id.btnAddNotification_Send);
        btnReset = view.findViewById(R.id.btnAddNotification_Reset);

        textTitle.setTypeface(lightFont);
        textDesc.setTypeface(lightFont);
        edTitle.setTypeface(lightFont);
        edDesc.setTypeface(lightFont);
        btnSend.setTypeface(lightFont);
        btnReset.setTypeface(lightFont);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewNotification();
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

    public void addNewNotification() {
        if (CheckNetwork.isInternetAvailable(getContext())) {

            if (edTitle.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "please insert title", Toast.LENGTH_SHORT).show();
                edTitle.requestFocus();
            } else if (edDesc.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "please insert description", Toast.LENGTH_SHORT).show();
                edDesc.requestFocus();
            } else {
                String title = edTitle.getText().toString().trim();
                String desc = edDesc.getText().toString().trim();

                NotificationBean notificationBean = new NotificationBean(0, title, desc, coId);

                Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                InterfaceApi api = retrofit.create(InterfaceApi.class);

                Call<ErrorMessage> errorMessageCall = api.addNotification(notificationBean);

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
                                    Toast.makeText(getContext(), "Unable to send notification!", Toast.LENGTH_SHORT).show();
                                    Log.e("ON RESPONSE : ", "ERROR : " + data.getMessage());

                                } else {
                                    progressBar.dismiss();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                                    builder.setTitle("Success");
                                    builder.setCancelable(false);
                                    builder.setMessage("Notification send successfully.");
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
                                progressBar.dismiss();
                                Toast.makeText(getContext(), "Unable to send notification!", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getContext(), "Unable to send notification!", Toast.LENGTH_SHORT).show();
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
        edTitle.setText("");
        edDesc.setText("");
    }
}
