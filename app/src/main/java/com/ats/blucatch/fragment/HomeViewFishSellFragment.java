package com.ats.blucatch.fragment;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.ats.blucatch.R;
import com.ats.blucatch.activity.MainActivity;

public class HomeViewFishSellFragment extends Fragment {

    private TextView tvAuctioner, tvLabelTripNo, tvLabelSell, tvTripNo, tvSell, tvLabelDate, tvLabelTitle, tvDate1, tvDate2, tvTitle1, tvTitle2, tvFish1, tvSize1, tvLabelQty1, tvQty1, tvAmt1;
    private EditText edSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_view_fish_sell, container, false);
        Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
        Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");

        MainActivity.tvTitle.setText("Fish Sell- Sagar");
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
        MainActivity.isAtHomeFishSell = true;


        tvAuctioner = view.findViewById(R.id.tvHomeFishSell_AuctName);
        tvLabelTripNo = view.findViewById(R.id.tvHomeFishSell_LabelTripNo);
        tvLabelSell = view.findViewById(R.id.tvHomeFishSell_LabelSell);
        tvTripNo = view.findViewById(R.id.tvHomeFishSell_TripNo);
        tvSell = view.findViewById(R.id.tvHomeFishSell_Sell);

        tvFish1 = view.findViewById(R.id.tvHomeFishSell_Fish1);
        tvSize1 = view.findViewById(R.id.tvHomeFishSell_Size1);
        tvLabelQty1 = view.findViewById(R.id.tvHomeFishSell_LabelQty1);
        tvQty1 = view.findViewById(R.id.tvHomeFishSell_Qty1);
        tvAmt1 = view.findViewById(R.id.tvHomeFishSell_Amt1);

        edSearch = view.findViewById(R.id.edHomeFishSell_Search);

        tvFish1.setTypeface(boldFont);
        tvSize1.setTypeface(boldFont);
        tvLabelQty1.setTypeface(lightFont);
        tvQty1.setTypeface(boldFont);
        tvAmt1.setTypeface(boldFont);

        tvAuctioner.setTypeface(boldFont);
        tvLabelTripNo.setTypeface(boldFont);
        tvLabelSell.setTypeface(boldFont);
        tvTripNo.setTypeface(boldFont);
        tvSell.setTypeface(boldFont);

        edSearch.setTypeface(lightFont);

        return view;
    }

}
