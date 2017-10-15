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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.blucatch.R;
import com.ats.blucatch.activity.MainActivity;
import com.ats.blucatch.bean.ErrorMessage;
import com.ats.blucatch.bean.Expense;
import com.ats.blucatch.utils.CheckNetwork;
import com.ats.blucatch.utils.InterfaceApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddNewExpenseFragment extends Fragment {

    int userId, coId;
    private TextInputLayout textExpName;
    private EditText edExpName, edValues;
    private TextView tvLabelExpType, tvLabelAccType, tvLabelEntryType, tvLabelPhoto, tvlabelEntryAccess, tvDispValues;
    private Spinner spExpType, spAccType, spEntryType, spEntryAccess;
    private RadioButton rbYes, rbNo;
    private Button btnSave, btnReset, btnAddValues;
    private ProgressDialog progressBar, progressBar1;
    int photoStatus;

    private LinearLayout llComboValues;

    private ArrayList<String> expTypeArray = new ArrayList<>();
    private ArrayList<String> accTypeArray = new ArrayList<>();
    private ArrayList<String> entryTypeArray = new ArrayList<>();
    private ArrayList<String> entryAccessArray = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_expense, container, false);
        Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
        Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");
        MainActivity.tvTitle.setText("Add New Expense");
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
        MainActivity.isAtAddExp = true;
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

        expTypeArray.clear();
        expTypeArray.add(0, "Select Type");
        expTypeArray.add(1, "Boat Consumable Expense");
        expTypeArray.add(2, "Trip Expense");
        expTypeArray.add(3, "Selling Expense");
        expTypeArray.add(4, "Boat Material Purchase");

        accTypeArray.clear();
        accTypeArray.add(0, "Select Type");
        accTypeArray.add(1, "Owner's Expenses");
        accTypeArray.add(2, "Common Expenses");
        accTypeArray.add(3, "Tandel Expenses");

        entryTypeArray.clear();
        entryTypeArray.add(0, "Select Type");
        entryTypeArray.add(1, "Rate-Qty-Total");
        entryTypeArray.add(2, "Rate-Qty-Total-Misc");
        entryTypeArray.add(3, "Amount");
        entryTypeArray.add(4, "Type-Amount");

        entryAccessArray.clear();
        entryAccessArray.add(0, "Select User");
        entryAccessArray.add(1, "Admin");
        entryAccessArray.add(2, "Manager");
        entryAccessArray.add(3, "Auctioner");
        entryAccessArray.add(4, "Tandel");

        tvDispValues = view.findViewById(R.id.tvAddExp_tvDispValues);
        edValues = view.findViewById(R.id.edAddExp_Values);
        btnAddValues = view.findViewById(R.id.btnAddExp_AddValues);
        llComboValues = view.findViewById(R.id.llAddExp_ComboValues);

        textExpName = view.findViewById(R.id.textAddExp_ExpName);
        edExpName = view.findViewById(R.id.edAddExp_ExpName);

        tvLabelExpType = view.findViewById(R.id.tvAddExp_LabelExpType);
        tvLabelEntryType = view.findViewById(R.id.tvAddExp_LabelEntryType);
        tvlabelEntryAccess = view.findViewById(R.id.tvAddExp_LabelEntryAccess);
        tvLabelPhoto = view.findViewById(R.id.tvAddExp_LabelPhoto);
        tvLabelAccType = view.findViewById(R.id.tvAddExp_LabelAccType);

        spExpType = view.findViewById(R.id.spAddExp_ExpType);
        spEntryType = view.findViewById(R.id.spAddExp_EntryType);
        spEntryAccess = view.findViewById(R.id.spAddExp_EntryAccess);
        spAccType = view.findViewById(R.id.spAddExp_AccType);

        rbYes = view.findViewById(R.id.rbAddExp_PhotoYes);
        rbNo = view.findViewById(R.id.rbAddExp_PhotoNo);

        rbNo.setChecked(true);
        photoStatus = 1;

        btnSave = view.findViewById(R.id.btnAddExp_Save);
        btnReset = view.findViewById(R.id.btnAddExp_Reset);

        tvDispValues.setTypeface(lightFont);
        edValues.setTypeface(lightFont);
        textExpName.setTypeface(lightFont);
        edExpName.setTypeface(lightFont);
        tvLabelExpType.setTypeface(lightFont);
        tvLabelEntryType.setTypeface(lightFont);
        tvlabelEntryAccess.setTypeface(lightFont);
        tvLabelPhoto.setTypeface(lightFont);
        tvLabelAccType.setTypeface(lightFont);
        rbYes.setTypeface(lightFont);
        rbNo.setTypeface(lightFont);
        btnSave.setTypeface(lightFont);
        btnReset.setTypeface(lightFont);

        ArrayAdapter<String> expTypeAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, expTypeArray);
        spExpType.setAdapter(expTypeAdapter);

        ArrayAdapter<String> accTypeAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, accTypeArray);
        spAccType.setAdapter(accTypeAdapter);

        ArrayAdapter<String> entryTypeAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, entryTypeArray);
        spEntryType.setAdapter(entryTypeAdapter);

        ArrayAdapter<String> entryAccessAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, entryAccessArray);
        spEntryAccess.setAdapter(entryAccessAdapter);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addExpeneseData();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetData();
            }
        });

        btnAddValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edValues.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "please enter values", Toast.LENGTH_SHORT).show();
                } else {
                    tvDispValues.setText(tvDispValues.getText().toString().toString() + edValues.getText().toString().trim() + ",");
                    edValues.setText("");
                }
            }
        });

        spEntryType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spEntryType.getSelectedItem().toString().equalsIgnoreCase("Type-Amount")) {
                    llComboValues.setVisibility(View.VISIBLE);
                    edValues.setText("");
                    tvDispValues.setText("");
                } else {
                    llComboValues.setVisibility(View.GONE);
                    edValues.setText("");
                    tvDispValues.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    public void addExpeneseData() {

        if (CheckNetwork.isInternetAvailable(getContext())) {

            if (edExpName.getText().toString().trim().isEmpty()) {
                edExpName.requestFocus();
                Toast.makeText(getContext(), "please enter expense name", Toast.LENGTH_SHORT).show();
            } else if (spExpType.getSelectedItemPosition() == 0) {
                spExpType.requestFocus();
                Toast.makeText(getContext(), "please select expense type", Toast.LENGTH_SHORT).show();
            } else if (spAccType.getSelectedItemPosition() == 0) {
                spAccType.requestFocus();
                Toast.makeText(getContext(), "please select expense account type", Toast.LENGTH_SHORT).show();
            } else if (spEntryType.getSelectedItemPosition() == 0) {
                spEntryType.requestFocus();
                Toast.makeText(getContext(), "please select expense entry type", Toast.LENGTH_SHORT).show();
            } else if (spEntryAccess.getSelectedItemPosition() == 0) {
                spEntryAccess.requestFocus();
                Toast.makeText(getContext(), "please select expense entry access to", Toast.LENGTH_SHORT).show();
            } else {
                if (spEntryType.getSelectedItem().toString().equalsIgnoreCase("Type-Amount")) {
                    if (tvDispValues.getText().toString().isEmpty()) {
                        edValues.requestFocus();
                        Toast.makeText(getContext(), "please enter type values", Toast.LENGTH_SHORT).show();
                    } else {
                        String expName = edExpName.getText().toString().trim();
                        String expType = spExpType.getSelectedItem().toString();
                        String accType = spAccType.getSelectedItem().toString();
                        String entryType = spEntryType.getSelectedItem().toString();
                        String entryAccessTo = spEntryAccess.getSelectedItem().toString();

                        String values = tvDispValues.getText().toString().trim();
                        String comboValues = values.substring(0, values.length() - 1);

                        if (rbYes.isChecked()) {
                            photoStatus = 0;
                        } else if (rbNo.isChecked()) {
                            photoStatus = 1;
                        }

                        Expense expense = new Expense(expName, expType, accType, entryType, photoStatus, entryAccessTo, 0, coId, 0, userId, comboValues);

                        Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        InterfaceApi api = retrofit.create(InterfaceApi.class);

                        Call<ErrorMessage> errorMessageCall = api.addExpenses(expense);

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
                                            Toast.makeText(getContext(), "unable to add expense!", Toast.LENGTH_SHORT).show();
                                            Log.e("ON RESPONSE : ", "ERROR : " + data.getMessage());

                                        } else {
                                            progressBar.dismiss();
                                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                                            builder.setTitle("Success");
                                            builder.setCancelable(false);
                                            builder.setMessage("New expense added successfully.");
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
                                        Toast.makeText(getContext(), "unable to add expense!", Toast.LENGTH_SHORT).show();
                                        Log.e("ON RESPONSE : ", "NO DATA");
                                    }
                                } catch (Exception e) {
                                    progressBar.dismiss();
                                    Log.e("Exception : ", "" + e.getMessage());
                                }
                            }

                            @Override
                            public void onFailure(Call<ErrorMessage> call, Throwable t) {
                                Toast.makeText(getContext(), "unable to add expense! server error", Toast.LENGTH_SHORT).show();
                                progressBar.dismiss();
                                Log.e("ON FAILURE : ", "ERROR : " + t.getMessage());
                            }
                        });

                    }
                } else {
                    String expName = edExpName.getText().toString().trim();
                    String expType = spExpType.getSelectedItem().toString();
                    String accType = spAccType.getSelectedItem().toString();
                    String entryType = spEntryType.getSelectedItem().toString();
                    String entryAccessTo = spEntryAccess.getSelectedItem().toString();

                    if (rbYes.isChecked()) {
                        photoStatus = 0;
                    } else if (rbNo.isChecked()) {
                        photoStatus = 1;
                    }

                    Expense expense = new Expense(expName, expType, accType, entryType, photoStatus, entryAccessTo, 0, coId, 0, userId, "");

                    Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    InterfaceApi api = retrofit.create(InterfaceApi.class);

                    Call<ErrorMessage> errorMessageCall = api.addExpenses(expense);

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
                                        Toast.makeText(getContext(), "unable to add expense!", Toast.LENGTH_SHORT).show();
                                        Log.e("ON RESPONSE : ", "ERROR : " + data.getMessage());

                                    } else {
                                        progressBar1.dismiss();
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                                        builder.setTitle("Success");
                                        builder.setCancelable(false);
                                        builder.setMessage("New expense added successfully.");
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
                                    Toast.makeText(getContext(), "unable to add expense!", Toast.LENGTH_SHORT).show();
                                    Log.e("ON RESPONSE : ", "NO DATA");
                                }
                            } catch (Exception e) {
                                progressBar1.dismiss();
                                Log.e("Exception : ", "" + e.getMessage());
                            }
                        }

                        @Override
                        public void onFailure(Call<ErrorMessage> call, Throwable t) {
                            Toast.makeText(getContext(), "unable to add expense! server error", Toast.LENGTH_SHORT).show();
                            progressBar1.dismiss();
                            Log.e("ON FAILURE : ", "ERROR : " + t.getMessage());
                        }
                    });
                }


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

    private void resetData() {
        edExpName.setText("");
        spExpType.setSelection(0);
        spAccType.setSelection(0);
        spEntryType.setSelection(0);
        spEntryAccess.setSelection(0);
        rbNo.setChecked(true);
        edValues.setText("");
        tvDispValues.setText("");
    }

}
