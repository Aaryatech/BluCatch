package com.ats.blucatch.fragment;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ats.blucatch.R;
import com.ats.blucatch.activity.MainActivity;

public class ViewExpenseDetailsFragment extends Fragment {

    //    private TextView tvDate, tvExp, tvAmt;
    private TextView tvDate, tvTitle, tvAmt, tvLabelDate, tvLabelTitle, tvLabelAmt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_expense_details, container, false);
        Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
        Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");
        MainActivity.tvTitle.setText("Expense Details");
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



        tvDate = view.findViewById(R.id.tvExpDetails_Date1);
        tvTitle = view.findViewById(R.id.tvExpDetails_Title1);
        tvAmt = view.findViewById(R.id.tvExpDetails_Amt1);
        tvLabelDate = view.findViewById(R.id.tvExpDetails_LabelDate);
        tvLabelTitle = view.findViewById(R.id.tvExpDetails_LabelTitle);
        tvLabelAmt = view.findViewById(R.id.tvExpDetails_LabelAmt);

        tvDate.setTypeface(lightFont);
        tvTitle.setTypeface(lightFont);
        tvAmt.setTypeface(lightFont);
        tvLabelDate.setTypeface(boldFont);
        tvLabelTitle.setTypeface(boldFont);
        tvLabelAmt.setTypeface(boldFont);

        return view;
    }

}
