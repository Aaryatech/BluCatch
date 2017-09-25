package com.ats.blucatch.fragment;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ats.blucatch.R;
import com.ats.blucatch.activity.MainActivity;

public class TripExpenseViewFragment extends Fragment {

    private TextView tvLabelTripNo, tvLabelExp, tvTripNo, tvExp, tvLabelDate, tvLabelTitle, tvDate1, tvDate2, tvName1, tvName2, tvDesc1, tvDesc2, tvAmt1, tvAmt2;
    private FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_expense_view, container, false);
        Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
        Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");

        MainActivity.tvTitle.setText("Trip Expense- Sagar");
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
        MainActivity.isAtTripExpView = true;
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


       // fab = view.findViewById(R.id.fabEnterExp);

        tvLabelTripNo = view.findViewById(R.id.tvTripExpenseView_LabelTripNo);
        tvLabelExp = view.findViewById(R.id.tvTripExpenseView_LabelExpense);
        tvTripNo = view.findViewById(R.id.tvTripExpenseView_TripNo);
        tvExp = view.findViewById(R.id.tvTripExpenseView_Expense);

        tvDate1 = view.findViewById(R.id.tvTripExpenseView_Date1);
        tvDate2 = view.findViewById(R.id.tvTripExpenseView_Date2);
        tvName1 = view.findViewById(R.id.tvTripExpenseView_Name1);
        tvName2 = view.findViewById(R.id.tvTripExpenseView_Name2);
        tvDesc1 = view.findViewById(R.id.tvTripExpenseView_Desc1);
        tvDesc2 = view.findViewById(R.id.tvTripExpenseView_Desc2);
        tvAmt1 = view.findViewById(R.id.tvTripExpenseView_Amt1);
        tvAmt2 = view.findViewById(R.id.tvTripExpenseView_Amt2);

        tvDate1.setTypeface(boldFont);
        tvDate2.setTypeface(boldFont);
        tvName1.setTypeface(boldFont);
        tvName2.setTypeface(boldFont);
        tvDesc1.setTypeface(lightFont);
        tvDesc2.setTypeface(lightFont);
        tvAmt1.setTypeface(boldFont);
        tvAmt2.setTypeface(boldFont);

        tvLabelTripNo.setTypeface(boldFont);
        tvLabelExp.setTypeface(boldFont);
        tvTripNo.setTypeface(boldFont);
        tvExp.setTypeface(boldFont);



        return view;
    }

}
