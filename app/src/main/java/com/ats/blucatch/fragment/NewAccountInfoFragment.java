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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.blucatch.R;
import com.ats.blucatch.activity.MainActivity;
import com.ats.blucatch.bean.Account;
import com.ats.blucatch.bean.AccountData;
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

public class NewAccountInfoFragment extends Fragment {

    int userId, coId;
    private RadioGroup rgType;
    private RadioButton rbUser, rbTransaction;
    private LinearLayout llUser, llTransaction;
    private TextInputLayout textAcc, textOpenBal, textMobile, textDOB, textAddress, textAadhar, textBankAcc, textPercent, textRemark, textEmerName1, textEmerName2, textEmerMobile1, textEmerMobile2;
    private EditText edAcc, edOpenBal, edMobile, edDOB, edAddress, edAadhar, edBanlAcc, edPercent, edRemark, edEmerName1, edEmerName2, edEmerMobile1, edEmerMobile2;
    private Spinner spUserType, spTransType, spBloodGrp, spUserSubType;
    private TextView tvLabelType, tvLabelUserType, tvLabelTransType, tvLabelBloodGrp, tvLabelUserSubType;
    private Button btnSave, btnReset;

    private ArrayList<String> userArray = new ArrayList<>();
    private ArrayList<String> transactionArray = new ArrayList<>();
    private ArrayList<String> bgArray = new ArrayList<>();
    private ArrayList<Long> tandelIdArray = new ArrayList<>();
    private ArrayList<Long> managerIdArray = new ArrayList<>();
    private ArrayList<String> tandelArray = new ArrayList<>();
    private ArrayList<String> managerArray = new ArrayList<>();

    private long dob, userSubTypeId;
    private String accName, userType, mobile, addr, aadhar, bank, remark, transactionType, bloodGrp, emrgName1, emrgName2, emrgMobile1, emrgMobile2;
    private double openBal;
    private Integer percent;
    private ProgressDialog progressBar, progressBar1;

    private int valYear, valueMonth, valueDay;
    private int valYearSecond, valueMonthSecond, valueDaySecond;
    private long todayMillis, formDateMillis;
    private int yyyy, mm, dd;
    private int YYYY, MMM, DD;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_account_info, container, false);
        Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
        Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");

        MainActivity.tvTitle.setText("Add New Account");
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
        MainActivity.isAtAddAcc = true;
        MainActivity.isAtViewLedger = false;
        MainActivity.isAtAccDetails = false;
        MainActivity.isAtUserMaster = false;
        MainActivity.isAtAddUser = false;
        MainActivity.isAtExpMaster = false;
        MainActivity.isAtAddExp = false;
        MainActivity.isAtChangePass = false;
        MainActivity.isAtHomeTripExp = false;
        MainActivity.isAtHomeFishSell = false;
        MainActivity.isAtAddNewAcc = true;

        SharedPreferences pref = getContext().getSharedPreferences(InterfaceApi.MY_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        userId = pref.getInt("AppUserId", 0);
        coId = pref.getInt("AppCoId", 0);
        Log.e("Co_id : ", "" + coId);

        userArray.clear();
        userArray.add(0, "Select User Type");
        userArray.add(1, "Owner");
        userArray.add(2, "Tandel");
        userArray.add(3, "Manager");
        userArray.add(4, "Auctioner");
        userArray.add(5, "Crew");
        userArray.add(6, "Other");

        bgArray.clear();
        bgArray.add(0, "Select Blood Group");
        bgArray.add(1, "A+");
        bgArray.add(2, "A-");
        bgArray.add(3, "B+");
        bgArray.add(4, "B-");
        bgArray.add(5, "AB+");
        bgArray.add(6, "AB-");
        bgArray.add(7, "O+");
        bgArray.add(8, "O-");

        transactionArray.clear();
        transactionArray.add(0, "Select Transaction Type");
        transactionArray.add(1, "Cash In Hand");
        transactionArray.add(2, "Bank");
        transactionArray.add(3, "Trip Income");
        transactionArray.add(4, "Capital Account");

        rgType = view.findViewById(R.id.rgNewAcc_Type);

        btnSave = view.findViewById(R.id.btnNewAcc_Save);
        btnReset = view.findViewById(R.id.btnNewAcc_Reset);

        rbUser = view.findViewById(R.id.rbNewAcc_User);
        rbTransaction = view.findViewById(R.id.rbNewAcc_Transaction);

        llUser = view.findViewById(R.id.llNewAcc_User);
        llTransaction = view.findViewById(R.id.llNewAcc_Transaction);

        textAcc = view.findViewById(R.id.textNewAcc_AccName);
        textOpenBal = view.findViewById(R.id.textNewAcc_OpenBalance);
        textMobile = view.findViewById(R.id.textNewAcc_Mobile);
        textDOB = view.findViewById(R.id.textNewAcc_DOB);
        textAddress = view.findViewById(R.id.textNewAcc_Address);
        textAadhar = view.findViewById(R.id.textNewAcc_Aadhar);
        textBankAcc = view.findViewById(R.id.textNewAcc_BankAcc);
        textPercent = view.findViewById(R.id.textNewAcc_Percent);
        textRemark = view.findViewById(R.id.textNewAcc_Remark);
        textEmerName1 = view.findViewById(R.id.textNewAcc_Name1);
        textEmerName2 = view.findViewById(R.id.textNewAcc_Name2);
        textEmerMobile1 = view.findViewById(R.id.textNewAcc_Mobile1);
        textEmerMobile2 = view.findViewById(R.id.textNewAcc_Mobile2);

        edAcc = view.findViewById(R.id.edNewAcc_AccName);
        edOpenBal = view.findViewById(R.id.edNewAcc_OpenBalance);
        edMobile = view.findViewById(R.id.edNewAcc_Mobile);
        edDOB = view.findViewById(R.id.edNewAcc_DOB);
        edAddress = view.findViewById(R.id.edNewAcc_Address);
        edAadhar = view.findViewById(R.id.edNewAcc_Aadhar);
        edBanlAcc = view.findViewById(R.id.edNewAcc_BankAcc);
        edPercent = view.findViewById(R.id.edNewAcc_Percent);
        edRemark = view.findViewById(R.id.edNewAcc_Remark);
        edEmerName1 = view.findViewById(R.id.edNewAcc_Name1);
        edEmerName2 = view.findViewById(R.id.edNewAcc_Name2);
        edEmerMobile1 = view.findViewById(R.id.edNewAcc_Mobile1);
        edEmerMobile2 = view.findViewById(R.id.edNewAcc_Mobile2);

        Calendar calendar = Calendar.getInstance();
        yyyy = calendar.get(Calendar.YEAR);
        mm = calendar.get(Calendar.MONTH);
        dd = calendar.get(Calendar.DAY_OF_MONTH);
        todayMillis = calendar.getTimeInMillis();
        formDateMillis = calendar.getTimeInMillis();
        edDOB.setText(dd + " / " + (mm + 1) + " / " + yyyy);

        spBloodGrp = view.findViewById(R.id.spNewAcc_BloodGroup);
        spUserType = view.findViewById(R.id.spNewAcc_UserType);
        spTransType = view.findViewById(R.id.spNewAcc_TransType);
        spUserSubType = view.findViewById(R.id.spNewAcc_UserSubType);
        tvLabelUserSubType = view.findViewById(R.id.tvNewAcc_LabelUserSubType);

        edDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), dateListener, yyyy, mm, dd);
                dialog.show();
            }
        });

        MySpinnerAdapter spAdapterUser = new MySpinnerAdapter(
                getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                userArray);
        spUserType.setAdapter(spAdapterUser);

        MySpinnerAdapter spAdapterTransaction = new MySpinnerAdapter(
                getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                transactionArray);
        spTransType.setAdapter(spAdapterTransaction);

        MySpinnerAdapter spAdapterBloodGrp = new MySpinnerAdapter(
                getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                bgArray);
        spBloodGrp.setAdapter(spAdapterBloodGrp);

        tvLabelType = view.findViewById(R.id.tvNewAcc_LabelType);
        tvLabelUserType = view.findViewById(R.id.tvNewAcc_LabelUserType);
        tvLabelTransType = view.findViewById(R.id.tvNewAcc_TransType);
        tvLabelBloodGrp = view.findViewById(R.id.tvNewAcc_LabelBloodGroup);

        textAcc.setTypeface(lightFont);
        textOpenBal.setTypeface(lightFont);
        textMobile.setTypeface(lightFont);
        textDOB.setTypeface(lightFont);
        textAddress.setTypeface(lightFont);
        textAadhar.setTypeface(lightFont);
        textBankAcc.setTypeface(lightFont);
        textPercent.setTypeface(lightFont);
        textRemark.setTypeface(lightFont);
        textEmerName1.setTypeface(lightFont);
        textEmerName2.setTypeface(lightFont);
        textEmerMobile1.setTypeface(lightFont);
        textEmerMobile2.setTypeface(lightFont);

        edAcc.setTypeface(lightFont);
        edOpenBal.setTypeface(lightFont);
        edMobile.setTypeface(lightFont);
        edDOB.setTypeface(lightFont);
        edAddress.setTypeface(lightFont);
        edAadhar.setTypeface(lightFont);
        edBanlAcc.setTypeface(lightFont);
        edPercent.setTypeface(lightFont);
        edRemark.setTypeface(lightFont);
        edEmerName1.setTypeface(lightFont);
        edEmerName2.setTypeface(lightFont);
        edEmerMobile1.setTypeface(lightFont);
        edEmerMobile2.setTypeface(lightFont);

        tvLabelType.setTypeface(lightFont);
        tvLabelUserType.setTypeface(lightFont);
        tvLabelTransType.setTypeface(lightFont);
        tvLabelBloodGrp.setTypeface(lightFont);

        rbUser.setTypeface(lightFont);
        rbTransaction.setTypeface(lightFont);

        btnSave.setTypeface(lightFont);
        btnReset.setTypeface(lightFont);


        rbUser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    llUser.setVisibility(View.VISIBLE);
                    llTransaction.setVisibility(View.GONE);
                    edAcc.setText("");
                }
            }
        });

        rbTransaction.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    llUser.setVisibility(View.GONE);
                    llTransaction.setVisibility(View.VISIBLE);
                    edAcc.setText("");
                }
            }
        });

        getSpinnerDataforTandel_manager();

        spUserType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spUserType.getSelectedItem().toString().equalsIgnoreCase("Tandel")) {
                    tvLabelUserSubType.setVisibility(View.VISIBLE);
                    spUserSubType.setVisibility(View.VISIBLE);
                    tvLabelUserSubType.setText("Manager");
                    MySpinnerAdapter spAdapterManager = new MySpinnerAdapter(
                            getContext(),
                            android.R.layout.simple_spinner_dropdown_item,
                            managerArray);
                    spUserSubType.setAdapter(spAdapterManager);

                } else if (spUserType.getSelectedItem().toString().equalsIgnoreCase("Crew")) {
                    tvLabelUserSubType.setVisibility(View.VISIBLE);
                    spUserSubType.setVisibility(View.VISIBLE);
                    tvLabelUserSubType.setText("Tandel");
                    MySpinnerAdapter spAdapterTandel = new MySpinnerAdapter(
                            getContext(),
                            android.R.layout.simple_spinner_dropdown_item,
                            tandelArray);
                    spUserSubType.setAdapter(spAdapterTandel);
                } else {
                    tvLabelUserSubType.setVisibility(View.GONE);
                    spUserSubType.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!rbUser.isChecked() && !rbTransaction.isChecked()) {
                    Toast.makeText(getContext(), "Please select Account Type", Toast.LENGTH_SHORT).show();

                } else {

                    if (rbUser.isChecked()) {
                        if (edAcc.getText().toString().isEmpty()) {
                            Toast.makeText(getContext(), "please enter account name", Toast.LENGTH_SHORT).show();
                            edAcc.requestFocus();
                        } else if (edOpenBal.getText().toString().isEmpty()) {
                            Toast.makeText(getContext(), "please enter opening balance", Toast.LENGTH_SHORT).show();
                            edOpenBal.requestFocus();
                        } else if (spUserType.getSelectedItemPosition() == 0) {
                            Toast.makeText(getContext(), "please select user type", Toast.LENGTH_SHORT).show();
                            spUserType.requestFocus();
                        } else if (edMobile.getText().toString().isEmpty()) {
                            Toast.makeText(getContext(), "please enter Mobile number", Toast.LENGTH_SHORT).show();
                            edMobile.requestFocus();
                        } else if (edMobile.getText().length() != 10) {
                            Toast.makeText(getContext(), "please enter 10 digit Mobile number", Toast.LENGTH_SHORT).show();
                            edMobile.requestFocus();
                        } else if (formDateMillis >= todayMillis) {
                            Toast.makeText(getContext(), "please enter valid date of birth", Toast.LENGTH_SHORT).show();
                            edDOB.requestFocus();
                        } else if (edAddress.getText().toString().isEmpty()) {
                            Toast.makeText(getContext(), "please enter address", Toast.LENGTH_SHORT).show();
                            edAddress.requestFocus();
                        } else if (edAadhar.getText().toString().isEmpty()) {
                            Toast.makeText(getContext(), "please enter aadhar number", Toast.LENGTH_SHORT).show();
                            edAadhar.requestFocus();
                        } else if (edAadhar.getText().length() != 12) {
                            Toast.makeText(getContext(), "please enter valid aadhar number", Toast.LENGTH_SHORT).show();
                            edAadhar.requestFocus();
                        } else if (edBanlAcc.getText().toString().isEmpty()) {
                            Toast.makeText(getContext(), "please enter bank details", Toast.LENGTH_SHORT).show();
                            edBanlAcc.requestFocus();
                        } else if (edPercent.getText().toString().isEmpty()) {
                            Toast.makeText(getContext(), "please enter percentage share", Toast.LENGTH_SHORT).show();
                            edPercent.requestFocus();
                        } else if (spBloodGrp.getSelectedItemPosition() == 0) {
                            Toast.makeText(getContext(), "please select blood group", Toast.LENGTH_SHORT).show();
                            spBloodGrp.requestFocus();
                        } else if (edEmerName1.getText().toString().isEmpty()) {
                            Toast.makeText(getContext(), "please enter emergency name 1", Toast.LENGTH_SHORT).show();
                            edEmerName1.requestFocus();
                        } else if (edEmerMobile1.getText().toString().isEmpty()) {
                            Toast.makeText(getContext(), "please enter emergency mobile number 1", Toast.LENGTH_SHORT).show();
                            edEmerMobile1.requestFocus();
                        } else if (edEmerMobile1.getText().length() != 10) {
                            Toast.makeText(getContext(), "please enter 10 digit Mobile number", Toast.LENGTH_SHORT).show();
                            edEmerMobile1.requestFocus();
                        } else if (edEmerName2.getText().toString().isEmpty()) {
                            Toast.makeText(getContext(), "please enter emergency name 2", Toast.LENGTH_SHORT).show();
                            edEmerName2.requestFocus();
                        } else if (edEmerMobile2.getText().toString().isEmpty()) {
                            Toast.makeText(getContext(), "please enter emergency mobile number 2", Toast.LENGTH_SHORT).show();
                            edEmerMobile2.requestFocus();
                        } else if (edEmerMobile2.getText().length() != 10) {
                            Toast.makeText(getContext(), "please enter 10 digit Mobile number", Toast.LENGTH_SHORT).show();
                            edEmerMobile2.requestFocus();
                        } else if (edRemark.getText().toString().isEmpty()) {
                            Toast.makeText(getContext(), "please enter remark", Toast.LENGTH_SHORT).show();
                            edRemark.requestFocus();
                        } else {
                            accName = edAcc.getText().toString().trim();
                            openBal = Double.parseDouble(edOpenBal.getText().toString().trim());
                            userType = spUserType.getSelectedItem().toString();
                            mobile = edMobile.getText().toString().trim();
                            addr = edAddress.getText().toString().trim();
                            aadhar = edAadhar.getText().toString().trim();
                            bank = edBanlAcc.getText().toString().trim();
                            percent = Integer.parseInt(edPercent.getText().toString().trim());
                            bloodGrp = spBloodGrp.getSelectedItem().toString();
                            emrgName1 = edEmerName1.getText().toString().trim();
                            emrgName2 = edEmerName2.getText().toString().trim();
                            emrgMobile1 = edEmerMobile1.getText().toString().trim();
                            emrgMobile2 = edEmerMobile2.getText().toString().trim();
                            remark = edRemark.getText().toString().trim();
                            transactionType = "na";
                            userSubTypeId = 0;


                            if (spUserType.getSelectedItem().toString().equalsIgnoreCase("Tandel")) {
                                if (spUserSubType.getSelectedItemPosition() == 0) {
                                    Toast.makeText(getContext(), "please select manager", Toast.LENGTH_SHORT).show();
                                    spUserSubType.requestFocus();
                                } else {
                                    userSubTypeId = managerIdArray.get(spUserSubType.getSelectedItemPosition());

                                    Account account = new Account("User", accName, userType, userSubTypeId, mobile, formDateMillis, addr, aadhar, bank, percent, remark, 0, bloodGrp, emrgName1, emrgMobile1, emrgName2, emrgMobile2, openBal, "na", coId, 0, userId);
                                    addNewAccount(account);
                                    //Toast.makeText(getContext(), "success : " + account, Toast.LENGTH_SHORT).show();
                                }
                            } else if (spUserType.getSelectedItem().toString().equalsIgnoreCase("Crew")) {
                                if (spUserSubType.getSelectedItemPosition() == 0) {
                                    Toast.makeText(getContext(), "please select tandel", Toast.LENGTH_SHORT).show();
                                    spUserSubType.requestFocus();
                                } else {
                                    userSubTypeId = tandelIdArray.get(spUserSubType.getSelectedItemPosition());

                                    Account account = new Account("User", accName, userType, userSubTypeId, mobile, formDateMillis, addr, aadhar, bank, percent, remark, 0, bloodGrp, emrgName1, emrgMobile1, emrgName2, emrgMobile2, openBal, "na", coId, 0, userId);
                                    addNewAccount(account);
                                    //Toast.makeText(getContext(), "success : " + account, Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Account account = new Account("User", accName, userType, userSubTypeId, mobile, formDateMillis, addr, aadhar, bank, percent, remark, 0, bloodGrp, emrgName1, emrgMobile1, emrgName2, emrgMobile2, openBal, "na", coId, 0, userId);
                                addNewAccount(account);
                                //Toast.makeText(getContext(), "success : " + account, Toast.LENGTH_SHORT).show();
                            }


//                            Account account = new Account("User", accName, userType, 0, mobile, 654654546455l, addr, aadhar, bank, percent, "na", "na", "na", 1, remark, 0, bloodGrp, emrgName1, emrgMobile1, emrgName2, emrgMobile2, openBal, transactionType,1,0,0);
//                            addNewAccount(account);
                        }
                    } else if (rbTransaction.isChecked()) {
                        if (edAcc.getText().toString().isEmpty()) {
                            Toast.makeText(getContext(), "please enter account name", Toast.LENGTH_SHORT).show();
                            edAcc.requestFocus();
                        } else if (edOpenBal.getText().toString().isEmpty()) {
                            Toast.makeText(getContext(), "please enter opening balance", Toast.LENGTH_SHORT).show();
                            edOpenBal.requestFocus();
                        } else if (edRemark.getText().toString().isEmpty()) {
                            Toast.makeText(getContext(), "please enter remark", Toast.LENGTH_SHORT).show();
                            edRemark.requestFocus();
                        } else {
                            accName = edAcc.getText().toString().trim();
                            openBal = Double.parseDouble(edOpenBal.getText().toString().trim());
                            userType = "na";
                            mobile = "na";
                            addr = "na";
                            aadhar = "na";
                            bank = "na";
                            percent = 0;
                            bloodGrp = "na";
                            emrgName1 = "na";
                            emrgName2 = "na";
                            emrgMobile1 = "na";
                            emrgMobile2 = "na";
                            remark = edRemark.getText().toString().trim();
                            //transactionType = spTransType.getSelectedItem().toString();
                            transactionType = "na";

                            Account account = new Account("Transaction", accName, userType, 0, mobile, 0, addr, aadhar, bank, percent, remark, 0, bloodGrp, emrgName1, emrgMobile1, emrgName2, emrgMobile2, openBal, transactionType, coId, 0, userId);
                            addNewAccount(account);
                            //Toast.makeText(getContext(), "success : " + account, Toast.LENGTH_SHORT).show();
//                            Account account = new Account("Transaction", accName, userType, 0, mobile, 546545465456l, addr, aadhar, bank, percent, "na", "na", "na", 1, remark, 0, bloodGrp, emrgName1, emrgMobile1, emrgName2, emrgMobile2, openBal, transactionType);
//                            addNewAccount(account);
                        }
                    }
                }
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

    public void resetData() {
        edAcc.setText("");
        edOpenBal.setText("");
        spUserType.setSelection(0);
        edMobile.setText("");
        edDOB.setText("");
        edAddress.setText("");
        edAadhar.setText("");
        edBanlAcc.setText("");
        edPercent.setText("");
        spBloodGrp.setSelection(0);
        edEmerName1.setText("");
        edEmerName2.setText("");
        edEmerMobile1.setText("");
        edEmerMobile2.setText("");
        edRemark.setText("");
        spTransType.setSelection(0);
        rgType.clearCheck();
        llUser.setVisibility(View.GONE);
        llTransaction.setVisibility(View.GONE);
    }


    public void addNewAccount(Account account) {
        if (CheckNetwork.isInternetAvailable(getContext())) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            InterfaceApi api = retrofit.create(InterfaceApi.class);

            Call<ErrorMessage> errorMessageCall = api.addAccount(account);

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
                            builder.setMessage("New Account added successfully.");
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


    public void getSpinnerDataforTandel_manager() {
        if (CheckNetwork.isInternetAvailable(getContext())) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            InterfaceApi api = retrofit.create(InterfaceApi.class);
            Call<AccountData> accountDataCall = api.allAccountData();

            progressBar1 = new ProgressDialog(getContext());
            progressBar1.setCancelable(false);
            progressBar1.setMessage("please wait....");
            progressBar1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar1.setProgress(0);
            progressBar1.setMax(100);
            progressBar1.show();

            accountDataCall.enqueue(new Callback<AccountData>() {
                @Override
                public void onResponse(Call<AccountData> call, Response<AccountData> response) {

                    if (response.body() != null) {
                        AccountData data = response.body();
                        if (data.getErrorMessage().getError()) {
                            progressBar1.dismiss();
                            Log.e("RESPONSE : ", " ERROR : " + data.getErrorMessage().getMessage());
                        } else {
                            tandelArray.clear();
                            tandelIdArray.clear();
                            managerArray.clear();
                            managerIdArray.clear();
                            tandelIdArray.add(0, 0l);
                            tandelArray.add(0, "Select Tandel");
                            managerIdArray.add(0, 0l);
                            managerArray.add(0, "Select Manager");

                            for (int i = 0, k = 1, a = 1; i < data.getAccount().size(); i++) {
                                if (data.getAccount().get(i).getEmpType().equalsIgnoreCase("Tandel")) {
                                    tandelArray.add(k, data.getAccount().get(i).getAccName());
                                    tandelIdArray.add(k, data.getAccount().get(i).getAccId());
                                    k++;
                                }
                                if (data.getAccount().get(i).getEmpType().equalsIgnoreCase("Manager")) {
                                    managerArray.add(a, data.getAccount().get(i).getAccName());
                                    managerIdArray.add(a, data.getAccount().get(i).getAccId());
                                    a++;
                                }
                            }

                            Log.e("RESPONSE : ", " Tandel Array : " + tandelArray);
                            Log.e("RESPONSE : ", " Manager Array : " + managerArray);
/*
                            MySpinnerAdapter spAdapterTandel = new MySpinnerAdapter(
                                    getContext(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    tandelArray);
                            spUserSubType.setAdapter(spAdapterTandel);

                            Log.e("RESPONSE : ", " Manager Array : " + managerArray);
                            MySpinnerAdapter spAdapterManager = new MySpinnerAdapter(
                                    getContext(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    managerArray);
                            spUserSubType.setAdapter(spAdapterManager);
*/

                            progressBar1.dismiss();
                        }

                    } else {
                        progressBar1.dismiss();
                        Log.e("RESPONSE : ", " NO DATA");
                    }
                }

                @Override
                public void onFailure(Call<AccountData> call, Throwable t) {
                    progressBar1.dismiss();
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

    private DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            valYear = year;
            valueMonth = month + 1;
            valueDay = dayOfMonth;
            edDOB.setText(valueDay + " / " + valueMonth + " / " + valYear);

            Calendar calendar = Calendar.getInstance();
            calendar.set(valYear, valueMonth - 1, valueDay);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR, 0);
            formDateMillis = calendar.getTimeInMillis();

        }
    };
}
