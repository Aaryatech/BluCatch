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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class EditUserFragment extends Fragment {

    long bAccId;
    int userId, coId;
    private TextView tvLabelUser, tvUserType, tvUserName, tvMobile, tvAccId;
    private Spinner spUser;
    private EditText edPass, edConfirmPass, edUserType, edUserName, edMobile;
    private TextInputLayout textPass, textConfirmPass, textUserType, textUserName, textMobile;
    private Button btnUpdate;
    private ProgressDialog progressBar;

    private ArrayList<Long> accIdArray = new ArrayList<>();
    private ArrayList<String> accTypeArray = new ArrayList<>();
    private ArrayList<String> accNameArray = new ArrayList<>();
    private ArrayList<String> empTypeArray = new ArrayList<>();
    private ArrayList<String> empMobileArray = new ArrayList<>();
    int uId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_user, container, false);
        Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
        Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");
        MainActivity.tvTitle.setText("Edit User");
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

        SharedPreferences pref = getContext().getSharedPreferences(InterfaceApi.MY_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        userId = pref.getInt("AppUserId", 0);
        coId = pref.getInt("AppCoId", 0);
        Log.e("Co_id : ", "" + coId);

        tvLabelUser = view.findViewById(R.id.tvEditUser_LabelUser);

        spUser = view.findViewById(R.id.spEditUser_User);
        spUser.setEnabled(false);
        tvAccId = view.findViewById(R.id.tvEditUser_accId);

        textPass = view.findViewById(R.id.textEditUser_Password);
        textConfirmPass = view.findViewById(R.id.textEditUser_CPassword);
        textUserType = view.findViewById(R.id.textEditUser_UserType);
        textUserName = view.findViewById(R.id.textEditUser_UserName);
        textMobile = view.findViewById(R.id.textEditUser_Mobile);

        edPass = view.findViewById(R.id.edEditUser_Password);
        edConfirmPass = view.findViewById(R.id.edEditUser_CPassword);
        edUserType = view.findViewById(R.id.edEditUser_UserType);
        edUserName = view.findViewById(R.id.edEditUser_UserName);
        edMobile = view.findViewById(R.id.edEditUser_Mobile);

        btnUpdate = view.findViewById(R.id.btnEditUser_Update);

        tvLabelUser.setTypeface(lightFont);
        textPass.setTypeface(lightFont);
        textConfirmPass.setTypeface(lightFont);
        edPass.setTypeface(lightFont);
        edConfirmPass.setTypeface(lightFont);
        btnUpdate.setTypeface(lightFont);
        textUserType.setTypeface(lightFont);
        textUserName.setTypeface(lightFont);
        textMobile.setTypeface(lightFont);
        edUserType.setTypeface(lightFont);
        edUserName.setTypeface(lightFont);
        edMobile.setTypeface(lightFont);

        String bUserName = getArguments().getString("User_Name");
        String bPassword = getArguments().getString("User_Password");
        final int bUserId = getArguments().getInt("User_Id");
        bAccId = getArguments().getLong("User_Acc_Id");

        edUserName.setText("" + bUserName);
        edPass.setText("" + bPassword);

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


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editUserData(bUserId);
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

                    if (response.body() != null) {
                        AccountData data = response.body();
                        if (data.getErrorMessage().getError()) {
                            progressBar.dismiss();
                            Log.e("RESPONSE : ", " ERROR : " + data.getErrorMessage().getMessage());
                        } else {
                            for (int i = 0, k = 0; i < data.getAccount().size(); i++) {
                                if (!data.getAccount().get(i).getAccType().equalsIgnoreCase("Transaction")) {
                                    accIdArray.add(k, data.getAccount().get(i).getAccId());
                                    accTypeArray.add(k, data.getAccount().get(i).getAccType());
                                    accNameArray.add(k, data.getAccount().get(i).getAccName());
                                    empTypeArray.add(k, data.getAccount().get(i).getEmpType());
                                    empMobileArray.add(k, data.getAccount().get(i).getEmpMobile());
                                }
                            }
                            Log.e("RESPONSE : ", " DATA : " + data.getAccount());
                            MySpinnerAdapter spAdapterUser = new MySpinnerAdapter(
                                    getContext(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    accNameArray);
                            spUser.setAdapter(spAdapterUser);

                            String accName = null;
                            for (int j = 0; j < accIdArray.size(); j++) {
                                if (bAccId == accIdArray.get(j)) {
                                    accName = accNameArray.get(j);
                                    break;
                                }
                            }
                            ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, accNameArray);
                            mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spUser.setAdapter(mAdapter);
                            if (!accName.equals(null)) {
                                int spinnerPosition = mAdapter.getPosition(accName);
                                spUser.setSelection(spinnerPosition);
                            }

                            progressBar.dismiss();

                           /* String name = getArguments().getString("User_Name");
                            String pass = getArguments().getString("User_Password");
                            uId = getArguments().getInt("User_Id");

                            Log.e("Bundle Data : ", " Name : " + name + "\nPass : " + pass + "\nuser Id : " + uId);

                            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, accNameArray);
                            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spUser.setAdapter(adapter1);
                            if (!name.equals(null)) {
                                int spinnerPosition = adapter1.getPosition(name);
                                spUser.setSelection(spinnerPosition);
                            }*/
                        }

                    } else {
                        progressBar.dismiss();
                        Log.e("RESPONSE : ", " NO DATA");
                    }
                }

                @Override
                public void onFailure(Call<AccountData> call, Throwable t) {
                    progressBar.dismiss();
                    Log.e("ON FAILURE : ", " ERROR : " + t.getMessage());
                }
            });


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


    public void editUserData(int id) {
        if (CheckNetwork.isInternetAvailable(getContext())) {

            Log.e("----------USER ID : ", " ----------------: " + id);

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
                int accId = Integer.parseInt(tvAccId.getText().toString().trim());
                String userName = edUserName.getText().toString().trim();
                String userAccType = edUserType.getText().toString().trim();
                String pass = edPass.getText().toString().trim();
                int isUsed = 0;

                User user = new User(accId, userName, pass, userAccType, 0, 0, coId, 0, userId);

                Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                InterfaceApi api = retrofit.create(InterfaceApi.class);

                Call<ErrorMessage> errorMessageCall = api.editUser(id, user);

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
                                Toast.makeText(getContext(), "Unable to update user.", Toast.LENGTH_SHORT).show();
                                Log.e("ON RESPONSE : ", "ERROR : " + data.getMessage());

                            } else {
                                progressBar.dismiss();
                                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
                                builder.setTitle("Success");
                                builder.setCancelable(false);
                                builder.setMessage("user updated successfully.");
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
                            Toast.makeText(getContext(), "Unable to update user.", Toast.LENGTH_SHORT).show();
                            Log.e("ON RESPONSE : ", "NO DATA");
                        }
                    }

                    @Override
                    public void onFailure(Call<ErrorMessage> call, Throwable t) {
                        progressBar.dismiss();
                        Toast.makeText(getContext(), "Unable to update user.", Toast.LENGTH_SHORT).show();
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
        edPass.setText("");
        edConfirmPass.setText("");
        spUser.requestFocus();

    }
}
