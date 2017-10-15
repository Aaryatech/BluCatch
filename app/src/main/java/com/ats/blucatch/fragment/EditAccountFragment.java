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
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditAccountFragment extends Fragment {

    int userId, coId;
    private RadioButton rbUser, rbTransaction;
    private LinearLayout llUser, llTransaction;
    private TextInputLayout textAcc, textOpenBal, textMobile, textDOB, textAddress, textAadhar, textBankAcc, textPercent, textRemark, textEmerName1, textEmerName2, textEmerMobile1, textEmerMobile2;
    private EditText edAcc, edOpenBal, edMobile, edDOB, edAddress, edAadhar, edBanlAcc, edPercent, edRemark, edEmerName1, edEmerName2, edEmerMobile1, edEmerMobile2;
    private Spinner spUserType, spTransType, spBloodGrp, spUserSubType;
    private TextView tvLabelType, tvLabelUserType, tvLabelTransType, tvLabelBloodGrp, tvLabelUserSubType;
    private Button btnUpdate;

    private ArrayList<String> userArray = new ArrayList<>();
    private ArrayList<String> transactionArray = new ArrayList<>();
    private ArrayList<String> bgArray = new ArrayList<>();
    private ArrayList<Long> tandelIdArray = new ArrayList<>();
    private ArrayList<Long> managerIdArray = new ArrayList<>();
    private ArrayList<String> tandelArray = new ArrayList<>();
    private ArrayList<String> managerArray = new ArrayList<>();

    private long dob, userSubTypeId, accId;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_account, container, false);
        Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
        Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");

        MainActivity.tvTitle.setText("Edit Account");
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

        try {
            SharedPreferences pref = getContext().getSharedPreferences(InterfaceApi.MY_PREF, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            userId = pref.getInt("AppUserId", 0);
            coId = pref.getInt("AppCoId", 0);
            Log.e("Co_id : ", "" + coId);
        } catch (Exception e) {
            Log.e("Exception : ", "" + e.getMessage());
        }


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

        btnUpdate = view.findViewById(R.id.btnEditAcc_Update);
        // btnReset = view.findViewById(R.id.btnEditAcc_Reset);

        rbUser = view.findViewById(R.id.rbEditAcc_User);
        rbTransaction = view.findViewById(R.id.rbEditAcc_Transaction);

        llUser = view.findViewById(R.id.llEditAcc_User);
        llTransaction = view.findViewById(R.id.llEditAcc_Transaction);

        textAcc = view.findViewById(R.id.textEditAcc_AccName);
        textOpenBal = view.findViewById(R.id.textEditAcc_OpenBalance);
        textMobile = view.findViewById(R.id.textEditAcc_Mobile);
        textDOB = view.findViewById(R.id.textEditAcc_DOB);
        textAddress = view.findViewById(R.id.textEditAcc_Address);
        textAadhar = view.findViewById(R.id.textEditAcc_Aadhar);
        textBankAcc = view.findViewById(R.id.textEditAcc_BankAcc);
        textPercent = view.findViewById(R.id.textEditAcc_Percent);
        textRemark = view.findViewById(R.id.textEditAcc_Remark);
        textEmerName1 = view.findViewById(R.id.textEditAcc_Name1);
        textEmerName2 = view.findViewById(R.id.textEditAcc_Name2);
        textEmerMobile1 = view.findViewById(R.id.textEditAcc_Mobile1);
        textEmerMobile2 = view.findViewById(R.id.textEditAcc_Mobile2);

        edAcc = view.findViewById(R.id.edEditAcc_AccName);
        edOpenBal = view.findViewById(R.id.edEditAcc_OpenBalance);
        edMobile = view.findViewById(R.id.edEditAcc_Mobile);
        edDOB = view.findViewById(R.id.edEditAcc_DOB);
        edAddress = view.findViewById(R.id.edEditAcc_Address);
        edAadhar = view.findViewById(R.id.edEditAcc_Aadhar);
        edBanlAcc = view.findViewById(R.id.edEditAcc_BankAcc);
        edPercent = view.findViewById(R.id.edEditAcc_Percent);
        edRemark = view.findViewById(R.id.edEditAcc_Remark);
        edEmerName1 = view.findViewById(R.id.edEditAcc_Name1);
        edEmerName2 = view.findViewById(R.id.edEditAcc_Name2);
        edEmerMobile1 = view.findViewById(R.id.edEditAcc_Mobile1);
        edEmerMobile2 = view.findViewById(R.id.edEditAcc_Mobile2);

        Calendar calendar = Calendar.getInstance();
        yyyy = calendar.get(Calendar.YEAR);
        mm = calendar.get(Calendar.MONTH);
        dd = calendar.get(Calendar.DAY_OF_MONTH);
        todayMillis = calendar.getTimeInMillis();
        formDateMillis = calendar.getTimeInMillis();
        edDOB.setText(dd + " / " + (mm + 1) + " / " + yyyy);

        Log.e("TODAY MILLIS : ", " : " + todayMillis);

        edDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), dateListener, yyyy, mm, dd);
                dialog.show();
            }
        });

        spBloodGrp = view.findViewById(R.id.spEditAcc_BloodGroup);
        spUserType = view.findViewById(R.id.spEditAcc_UserType);
        spTransType = view.findViewById(R.id.spEditAcc_TransType);
        spUserSubType = view.findViewById(R.id.spEditAcc_UserSubType);
        tvLabelUserSubType = view.findViewById(R.id.tvEditAcc_LabelUserSubType);

        getSpinnerDataforTandel_manager();

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

        tvLabelType = view.findViewById(R.id.tvEditAcc_LabelType);
        tvLabelUserType = view.findViewById(R.id.tvEditAcc_LabelUserType);
        tvLabelTransType = view.findViewById(R.id.tvEditAcc_TransType);
        tvLabelBloodGrp = view.findViewById(R.id.tvEditAcc_LabelBloodGroup);

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

        btnUpdate.setTypeface(lightFont);
//        btnReset.setTypeface(lightFont);


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

        String bAccName = "", bUserType = "", bMobile = "", bAddress = "", bAadhar = "", bBank = "", bRemark = "", bEmrgName1 = "", bEmrgName2 = "", bEmrgMobile1 = "", bEmrgMobile2 = "", bBloodGrp = "", bTransType = "", bAccType = "";
        double bOpneBal = 0;
        int bPercent = 0;
        long bSubTypeId = 0;
        long bdob = 0;
        try {
            accId = getArguments().getLong("Account_Id");
            bAccName = getArguments().getString("Account_Name");
            bUserType = getArguments().getString("User_Type");
            bSubTypeId = getArguments().getLong("User_Sub_Type");
            bMobile = getArguments().getString("Account_Mobile");
            bdob = getArguments().getLong("Account_DOB");
            bAddress = getArguments().getString("Account_Address");
            bAadhar = getArguments().getString("Account_Aadhar");
            bBank = getArguments().getString("Account_Bank");
            bRemark = getArguments().getString("Account_Remark");
            bEmrgName1 = getArguments().getString("Account_Emrg_Name1");
            bEmrgName2 = getArguments().getString("Account_Emrg_Name2");
            bEmrgMobile1 = getArguments().getString("Account_Emrg_Mobile1");
            bEmrgMobile2 = getArguments().getString("Account_Emrg_Mobile2");
            bBloodGrp = getArguments().getString("Account_BloodGrp");
            bTransType = getArguments().getString("Account_Transaction");
            bAccType = getArguments().getString("Account_Type");
            bOpneBal = getArguments().getDouble("Account_OpenBal");
            bPercent = getArguments().getInt("Account_Percent");
        } catch (Exception e) {
            Log.e("Exception : ", "" + e.getMessage());
        }


        if (bAccType.equalsIgnoreCase("User")) {
            rbUser.setChecked(true);
            rbTransaction.setEnabled(false);
            edAcc.setText(bAccName);
            edOpenBal.setText("" + bOpneBal);
            edMobile.setText(bMobile);

            formDateMillis = bdob;
            Date dt = new Date(bdob);
            SimpleDateFormat formatter = new SimpleDateFormat("dd / M / yyyy");
            String curDate = formatter.format(dt);
            edDOB.setText(curDate);

            edAddress.setText(bAddress);
            edAadhar.setText(bAadhar);
            edBanlAcc.setText(bBank);
            edPercent.setText("" + bPercent);
            edEmerName1.setText(bEmrgName1);
            edEmerName2.setText(bEmrgName2);
            edEmerMobile1.setText(bEmrgMobile1);
            edEmerMobile2.setText(bEmrgMobile2);
            edRemark.setText(bRemark);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, userArray);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spUserType.setAdapter(adapter);
            if (!bUserType.equals(null)) {
                int spinnerPosition = adapter.getPosition(bUserType);
                spUserType.setSelection(spinnerPosition);
            }

            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, bgArray);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spBloodGrp.setAdapter(adapter1);
            if (!bUserType.equals(null)) {
                int spinnerPosition = adapter1.getPosition(bBloodGrp);
                spBloodGrp.setSelection(spinnerPosition);
            }

            if (bUserType.equalsIgnoreCase("Tandel")) {
                Log.e("--------------", "Tandel" + managerArray.size());

                for (int j = 0; j < managerArray.size(); j++) {
                    Log.e("manager name : ", " : " + managerArray.get(j));
                }


            } else if (bUserType.equalsIgnoreCase("Crew")) {
                Log.e("--------------", "Crew");
            }


        } else {
            rbTransaction.setChecked(true);
            rbUser.setEnabled(false);
            edAcc.setText("Transaction");
            edOpenBal.setText("" + bOpneBal);
            edRemark.setText(bRemark);

            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, transactionArray);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spTransType.setAdapter(adapter1);
            if (!bTransType.equals(null)) {
                int spinnerPosition = adapter1.getPosition(bTransType);
                spTransType.setSelection(spinnerPosition);
            }

        }

        final long finalBSubTypeId = bSubTypeId;
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

                    Log.e("--------------", "User Sub Type Id : " + finalBSubTypeId);
                    String name = null;
                    for (int j = 0; j < managerIdArray.size(); j++) {
                        if (finalBSubTypeId == managerIdArray.get(j)) {
                            name = managerArray.get(j);
                            break;
                        }
                    }
                    Log.e("MANAGER NAME : ", " : " + name);
                    ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, managerArray);
                    mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spUserSubType.setAdapter(mAdapter);
                    if (!name.equals(null)) {
                        int spinnerPosition = mAdapter.getPosition(name);
                        spUserSubType.setSelection(spinnerPosition);
                    }


                } else if (spUserType.getSelectedItem().toString().equalsIgnoreCase("Crew")) {
                    tvLabelUserSubType.setVisibility(View.VISIBLE);
                    spUserSubType.setVisibility(View.VISIBLE);
                    tvLabelUserSubType.setText("Tandel");
                    MySpinnerAdapter spAdapterTandel = new MySpinnerAdapter(
                            getContext(),
                            android.R.layout.simple_spinner_dropdown_item,
                            tandelArray);
                    spUserSubType.setAdapter(spAdapterTandel);

                    Log.e("--------------", "User Sub Type Id : " + finalBSubTypeId);
                    String name = null;
                    for (int j = 0; j < tandelIdArray.size(); j++) {
                        if (finalBSubTypeId == tandelIdArray.get(j)) {
                            name = tandelArray.get(j);
                            break;
                        }
                    }
                    Log.e("Tandel NAME : ", " : " + name);
                    ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, tandelArray);
                    mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spUserSubType.setAdapter(mAdapter);
                    if (!name.equals(null)) {
                        int spinnerPosition = mAdapter.getPosition(name);
                        spUserSubType.setSelection(spinnerPosition);
                    }


                } else {
                    tvLabelUserSubType.setVisibility(View.GONE);
                    spUserSubType.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
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
                                    editAccount(accId, account);
                                }
                            } else if (spUserType.getSelectedItem().toString().equalsIgnoreCase("Crew")) {
                                if (spUserSubType.getSelectedItemPosition() == 0) {
                                    Toast.makeText(getContext(), "please select tandel", Toast.LENGTH_SHORT).show();
                                    spUserSubType.requestFocus();
                                } else {
                                    userSubTypeId = tandelIdArray.get(spUserSubType.getSelectedItemPosition());

                                    Account account = new Account("User", accName, userType, userSubTypeId, mobile, formDateMillis, addr, aadhar, bank, percent, remark, 0, bloodGrp, emrgName1, emrgMobile1, emrgName2, emrgMobile2, openBal, "na", coId, 0, userId);
                                    editAccount(accId, account);
                                }
                            } else {
                                Account account = new Account("User", accName, userType, userSubTypeId, mobile, formDateMillis, addr, aadhar, bank, percent, remark, 0, bloodGrp, emrgName1, emrgMobile1, emrgName2, emrgMobile2, openBal, "na", coId, 0, userId);
                                editAccount(accId, account);
                            }
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
                            transactionType = "na";

                            Account account = new Account("Transaction", accName, userType, 0, mobile, 0, addr, aadhar, bank, percent, remark, 0, bloodGrp, emrgName1, emrgMobile1, emrgName2, emrgMobile2, openBal, transactionType, coId, 0, userId);
                            editAccount(accId, account);
                        }
                    }
                }
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
        rbTransaction.setChecked(false);
        rbUser.setChecked(false);
        llUser.setVisibility(View.GONE);
        llTransaction.setVisibility(View.GONE);
    }

    public void editAccount(long aId, Account account) {
        if (CheckNetwork.isInternetAvailable(getContext())) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            InterfaceApi api = retrofit.create(InterfaceApi.class);

            Call<ErrorMessage> errorMessageCall = api.editAccount(aId, account);

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
                                Log.e("ON RESPONSE : ", "ERROR : " + data.getMessage());

                            } else {
                                progressBar.dismiss();
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                                builder.setTitle("Success");
                                builder.setCancelable(false);
                                builder.setMessage("Account updated successfully.");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        //  resetData();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }

                        } else {
                            progressBar.dismiss();
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
                    Log.e("ON FAILURE : ", "ERROR : " + t.getMessage());
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