package com.ats.blucatch.fragment;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ats.blucatch.R;
import com.ats.blucatch.activity.MainActivity;

public class BoatExpensesFragment extends Fragment {

    private TextView tvTotalAmt, tvLabelDate, tvDate, tvLabelExpType, tvExpType, tvLabelExpName, tvExpName, tvLabelDesc, tvDesc, tvLabelAmt, tvAmt, tvExpDate1, tvExpDate2, tvName1, tvName2, tvDesc1, tvDesc2, tvAmt1, tvAmt2;
    public static String boatName;
    public static long boatId;
    private FloatingActionButton fab;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_boat_expenses, container, false);
        Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
        Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");

        try {
            boatId = getArguments().getLong("Boat_Id");
            boatName = getArguments().getString("Boat_Name");
        } catch (Exception e) {
            e.printStackTrace();
        }

        MainActivity.tvTitle.setText("Boat Expenses- " + boatName);
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
        MainActivity.isAtBoatExpense = true;
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

        fab = view.findViewById(R.id.fabBoatExpense);

        tvTotalAmt = view.findViewById(R.id.tvBoatExp_TotalAmt);

        tvTotalAmt.setTypeface(boldFont);

        tvExpDate1 = view.findViewById(R.id.tvBoatExp_ExpDate1);
        tvExpDate2 = view.findViewById(R.id.tvBoatExp_ExpDate2);
        tvName1 = view.findViewById(R.id.tvBoatExp_ExpName1);
        tvName2 = view.findViewById(R.id.tvBoatExp_ExpName2);
        tvDesc1 = view.findViewById(R.id.tvBoatExp_ExpDesc1);
        tvDesc2 = view.findViewById(R.id.tvBoatExp_ExpDesc2);
        tvAmt1 = view.findViewById(R.id.tvBoatExp_ExpAmt1);
        tvAmt2 = view.findViewById(R.id.tvBoatExp_ExpAmt2);

        tvExpDate1.setTypeface(lightFont);
        tvExpDate2.setTypeface(lightFont);
        tvName1.setTypeface(boldFont);
        tvName2.setTypeface(boldFont);
        tvDesc1.setTypeface(lightFont);
        tvDesc2.setTypeface(lightFont);
        tvAmt1.setTypeface(boldFont);
        tvAmt2.setTypeface(boldFont);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment adf = new BoatEnterTransactionFragment();
                Bundle args = new Bundle();
                args.putLong("Boat_Id", boatId);
                args.putString("Boat_Name", boatName);
                adf.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf).commit();
            }
        });
        return view;
    }

}
