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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.blucatch.R;
import com.ats.blucatch.activity.MainActivity;
import com.ats.blucatch.bean.AccountData;
import com.ats.blucatch.bean.ErrorMessage;
import com.ats.blucatch.bean.User;
import com.ats.blucatch.utils.CheckNetwork;
import com.ats.blucatch.utils.InterfaceApi;
import com.ats.blucatch.utils.MySpinnerAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddNewUserFragment extends Fragment {

    int userId, coId;
    private TextView tvLabelUser, tvUserType, tvUserName, tvMobile, tvAccId;
    private Spinner spUser;
    private EditText edPass, edConfirmPass, edUserType, edUserName, edMobile;
    private TextInputLayout textPass, textConfirmPass, textUserType, textUserName, textMobile;
    private Button btnSave, btnReset;
    private ProgressDialog progressBar, progressBar1;

    private ArrayList<Long> accIdArray = new ArrayList<>();
    private ArrayList<String> accTypeArray = new ArrayList<>();
    private ArrayList<String> accNameArray = new ArrayList<>();
    private ArrayList<String> empTypeArray = new ArrayList<>();
    private ArrayList<String> empMobileArray = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_user, container, false);
        Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
        Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");
        MainActivity.tvTitle.setText("Add New User");
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
        MainActivity.isAtAddUser = true;
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

        tvLabelUser = view.findViewById(R.id.tvAddUser_LabelUser);

        spUser = view.findViewById(R.id.spAddUser_User);
        tvAccId = view.findViewById(R.id.tvAddUser_accId);

        textPass = view.findViewById(R.id.textAddUser_Password);
        textConfirmPass = view.findViewById(R.id.textAddUser_CPassword);
        textUserType = view.findViewById(R.id.textAddUser_UserType);
        textUserName = view.findViewById(R.id.textAddUser_UserName);
        textMobile = view.findViewById(R.id.textAddUser_Mobile);

        edPass = view.findViewById(R.id.edAddUser_Password);
        edConfirmPass = view.findViewById(R.id.edAddUser_CPassword);
        edUserType = view.findViewById(R.id.edAddUser_UserType);
        edUserName = view.findViewById(R.id.edAddUser_UserName);
        edMobile = view.findViewById(R.id.edAddUser_Mobile);

        btnSave = view.findViewById(R.id.btnAddUser_Save);
        btnReset = view.findViewById(R.id.btnAddUser_Reset);

        tvLabelUser.setTypeface(lightFont);
        textPass.setTypeface(lightFont);
        textConfirmPass.setTypeface(lightFont);
        edPass.setTypeface(lightFont);
        edConfirmPass.setTypeface(lightFont);
        btnSave.setTypeface(lightFont);
        btnReset.setTypeface(lightFont);
        textUserType.setTypeface(lightFont);
        textUserName.setTypeface(lightFont);
        textMobile.setTypeface(lightFont);
        edUserType.setTypeface(lightFont);
        edUserName.setTypeface(lightFont);
        edMobile.setTypeface(lightFont);

        getUserListFromAccount();

        spUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Toast.makeText(getContext(), "POSITION : " + i, Toast.LENGTH_SHORT).show();
                edUserType.setText(empTypeArray.get(i));
                tvAccId.setText("" + accIdArray.get(i));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewUser();
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

    public void getUserListFromAccount() {
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
                                for (int i = 0, j = 0; i < data.getAccount().size(); i++) {
                                    if (!data.getAccount().get(i).getAccType().equalsIgnoreCase("Transaction")) {
                                        accIdArray.add(j, data.getAccount().get(i).getAccId());
                                        accTypeArray.add(j, data.getAccount().get(i).getAccType());
                                        accNameArray.add(j, data.getAccount().get(i).getAccName());
                                        empTypeArray.add(j, data.getAccount().get(i).getEmpType());
                                        empMobileArray.add(j, data.getAccount().get(i).getEmpMobile());
                                        j++;
                                    }
                                }
                                Log.e("RESPONSE : ", " DATA : " + data.getAccount());
                                MySpinnerAdapter spAdapterUser = new MySpinnerAdapter(
                                        getContext(),
                                        android.R.layout.simple_spinner_dropdown_item,
                                        accNameArray);
                                spUser.setAdapter(spAdapterUser);

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

    public void addNewUser() {
        if (CheckNetwork.isInternetAvailable(getContext())) {

            if (tvAccId.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Please select user", Toast.LENGTH_SHORT).show();
                spUser.requestFocus();
            } else if (edUserName.getText().toString().trim().isEmpty()) {
                Toast.makeText(getContext(), "please enter user name", Toast.LENGTH_SHORT).show();
                edUserName.requestFocus();
            } else if (edPass.getText().toString().trim().isEmpty()) {
                Toast.makeText(getContext(), "please enter password", Toast.LENGTH_SHORT).show();
                edPass.requestFocus();
            } else if (edConfirmPass.getText().toString().trim().isEmpty()) {
                Toast.makeText(getContext(), "please enter confirm password", Toast.LENGTH_SHORT).show();
                edConfirmPass.requestFocus();
            } else if (!edPass.getText().toString().trim().equals(edConfirmPass.getText().toString().trim())) {
                Toast.makeText(getContext(), "password not matched", Toast.LENGTH_SHORT).show();
            } else {
                long accId = Long.parseLong(tvAccId.getText().toString().trim());
                String userName = edUserName.getText().toString().trim();
                String mobile = edMobile.getText().toString().trim();
                String userAccType = edUserType.getText().toString().trim();
                String pass = edPass.getText().toString().trim();
                int isUsed = 0;

                User user = new User(accId, userName, pass, userAccType, 0, 0, coId, 0, userId);

                Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                InterfaceApi api = retrofit.create(InterfaceApi.class);

                Call<ErrorMessage> errorMessageCall = api.addUser(user);

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
                                    //Toast.makeText(getContext(), "Unable to add user.", Toast.LENGTH_SHORT).show();
                                    Log.e("ON RESPONSE : ", "ERROR : " + data.getMessage());

                                } else {
                                    progressBar1.dismiss();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                                    builder.setTitle("Success");
                                    builder.setCancelable(false);
                                    builder.setMessage("New user added successfully.");
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
                                progressBar1.dismiss();
                                Toast.makeText(getContext(), "Unable to add user.", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getContext(), "Unable to add user.", Toast.LENGTH_SHORT).show();
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
        edPass.setText("");
        edConfirmPass.setText("");
        edUserName.setText("");
        spUser.setSelection(0);
        spUser.requestFocus();
        getUserListFromAccount();

    }

}
