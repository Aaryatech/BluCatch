package com.ats.blucatch.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.blucatch.R;
import com.ats.blucatch.activity.MainActivity;
import com.ats.blucatch.utils.InterfaceApi;
import com.ats.blucatch.utils.MySpinnerAdapter;

import java.util.ArrayList;

public class NewEmergencyInfoFragment extends Fragment {

    private TextView tvLabelBloodGroup;
    private Spinner spBloodGroup;
    private TextInputLayout textPerson1, textPerson2, textMobile1, textMobile2;
    private EditText edPerson1, edMobile1, edPerson2, edMobile2;
    private Button btnSave, btnReset;

    private ArrayList<String> bgArray = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_emergency_info, container, false);
        Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
        Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");

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

        btnSave = view.findViewById(R.id.btnNewEmrg_Save);
        btnReset = view.findViewById(R.id.btnNewEmrg_Reset);

        tvLabelBloodGroup = view.findViewById(R.id.tvNewEmrg_LabelBloodGroup);

        spBloodGroup = view.findViewById(R.id.spNewEmrg_BloodGroup);

        textPerson1 = view.findViewById(R.id.textNewEmrg_Name1);
        textPerson2 = view.findViewById(R.id.textNewEmrg_Name2);
        textMobile1 = view.findViewById(R.id.textNewEmrg_Mobile1);
        textMobile2 = view.findViewById(R.id.textNewEmrg_Mobile2);

        edPerson1 = view.findViewById(R.id.edNewEmrg_Name1);
        edPerson2 = view.findViewById(R.id.edNewEmrg_Name2);
        edMobile1 = view.findViewById(R.id.edNewEmrg_Mobile1);
        edMobile2 = view.findViewById(R.id.edNewEmrg_Mobile2);

        tvLabelBloodGroup.setTypeface(lightFont);
        textPerson1.setTypeface(lightFont);
        textPerson2.setTypeface(lightFont);
        textMobile1.setTypeface(lightFont);
        textMobile2.setTypeface(lightFont);
        edPerson1.setTypeface(lightFont);
        edPerson2.setTypeface(lightFont);
        edMobile1.setTypeface(lightFont);
        edMobile2.setTypeface(lightFont);
        btnSave.setTypeface(lightFont);
        btnReset.setTypeface(lightFont);

        MySpinnerAdapter spAdapter = new MySpinnerAdapter(
                getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                bgArray);
        spBloodGroup.setAdapter(spAdapter);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getContext().getSharedPreferences(InterfaceApi.MY_PREF, Context.MODE_PRIVATE);
                String accountName = prefs.getString("Account_Name", "");
                String openingBal = prefs.getString("Opening_Balance", "");
                String userType = prefs.getString("User_Type", "");
                String mobile = prefs.getString("Mobile", "");
                String dob = prefs.getString("DOB", "");
                String address = prefs.getString("Address", "");
                String aadhar = prefs.getString("Aadhar", "");
                String bank = prefs.getString("Bank", "");
                String percent = prefs.getString("Percent", "");
                String remark = prefs.getString("Remark", "");
                String transactionType = prefs.getString("Transaction_Type", "");
                Log.e("ACCOUNT NAME : ", " " + accountName);
                Log.e("openingBal : ", " " + openingBal);
                Log.e("userType : ", " " + userType);
                Log.e("mobile : ", " " + mobile);
                Log.e("dob : ", " " + dob);
                Log.e("address : ", " " + address);
                Log.e("aadhar : ", " " + aadhar);
                Log.e("bank : ", " " + bank);
                Log.e("percent : ", " " + percent);
                Log.e("remark : ", " " + remark);
                Log.e("transactionType : ", " " + transactionType);

                if (accountName.equals("")) {
                    Toast.makeText(getContext(), "enter acc name", Toast.LENGTH_SHORT).show();
                }


            }
        });

        return view;
    }

}
