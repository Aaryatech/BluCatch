package com.ats.blucatch.fragment;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ats.blucatch.R;
import com.ats.blucatch.activity.MainActivity;

public class ViewExpenseLedgerFragment extends Fragment {

    private TextView tvExpName;
    private RadioButton rbCurrent, rbFromTo;
    private EditText edFromDate, edToDate;
    private TextInputLayout textFromDate, textToDate;
    private Button btnShow;
    private LinearLayout llDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_view_expense_ledger, container, false);
        Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
        Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");
        MainActivity.tvTitle.setText("Expense Ledger");
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


        tvExpName = view.findViewById(R.id.tvViewExpLedger_ExpName);
        rbCurrent = view.findViewById(R.id.rbViewExpLedger_CurrentPeriod);
        rbFromTo = view.findViewById(R.id.rbViewExpLedger_FromToDate);
        edFromDate = view.findViewById(R.id.edViewExpLedger_FromDate);
        edToDate = view.findViewById(R.id.edViewExpLedger_ToDate);
        textFromDate = view.findViewById(R.id.textViewExpLedger_FromDate);
        textToDate = view.findViewById(R.id.textViewExpLedger_ToDate);
        btnShow = view.findViewById(R.id.btnViewExpLedger_Show);
        llDate = view.findViewById(R.id.llViewExpLedgerDate);

        tvExpName.setTypeface(boldFont);
        rbCurrent.setTypeface(lightFont);
        rbFromTo.setTypeface(lightFont);
        edFromDate.setTypeface(lightFont);
        edToDate.setTypeface(lightFont);
        textFromDate.setTypeface(lightFont);
        textToDate.setTypeface(lightFont);
        btnShow.setTypeface(lightFont);

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new ViewExpenseDetailsFragment());
                ft.commit();
            }
        });

        rbFromTo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    llDate.setVisibility(View.VISIBLE);
                } else {
                    llDate.setVisibility(View.GONE);
                }
            }
        });
        return view;
    }

}
