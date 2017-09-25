package com.ats.blucatch.fragment;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.ats.blucatch.R;
import com.ats.blucatch.activity.MainActivity;

public class UserFishSellFragment extends Fragment {

    private TextView tvAuctioner, tvLabelTripNo, tvLabelSell, tvTripNo, tvSell, tvLabelDate, tvLabelTitle, tvDate1, tvDate2, tvTitle1, tvTitle2, tvFish1, tvSize1, tvLabelQty1, tvQty1, tvAmt1;
    private EditText edSearch;
    public static String boatName, auctName;
    public static long tripId, boatId;
    private FloatingActionButton fab;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_fish_sell, container, false);
        Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
        Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");

        try {
            boatId = getArguments().getLong("User_Trip_Boat_ID");
            tripId = getArguments().getLong("User_Trip_ID");
            boatName = getArguments().getString("User_Trip_Boat_Name");
            auctName = getArguments().getString("User_Trip_Auctioner_Name");
        } catch (Exception e) {
            Log.e("Bundle  : ", "no data found");
        }
        MainActivity.tvTitle.setText("Fish Sell- " + boatName);
        MainActivity.tvTitle.setTypeface(boldFont);

        MainActivity.isAtHome = false;
        MainActivity.isAtUserEnterTransaction = false;
        MainActivity.isAtUserFishSell = true;
        MainActivity.isAtUserTripExp = false;

        fab = view.findViewById(R.id.fabEnterFishExp);

        tvAuctioner = view.findViewById(R.id.tvUserFishSell_AuctName);
        tvLabelTripNo = view.findViewById(R.id.tvUserFishSell_LabelTripNo);
        tvLabelSell = view.findViewById(R.id.tvUserFishSell_LabelSell);
        tvTripNo = view.findViewById(R.id.tvUserFishSell_TripNo);
        tvSell = view.findViewById(R.id.tvUserFishSell_Sell);

        tvFish1 = view.findViewById(R.id.tvUserFishSell_Fish1);
        tvSize1 = view.findViewById(R.id.tvUserFishSell_Size1);
        tvLabelQty1 = view.findViewById(R.id.tvUserFishSell_LabelQty1);
        tvQty1 = view.findViewById(R.id.tvUserFishSell_Qty1);
        tvAmt1 = view.findViewById(R.id.tvUserFishSell_Amt1);

        edSearch = view.findViewById(R.id.edUserFishSell_Search);

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

        tvAuctioner.setText("" + auctName);
        tvTripNo.setText("" + tripId);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment adf = new UserEnterFishSellFragment();
                Bundle args = new Bundle();
                args.putString("Expense_Type", "Fish");
                args.putString("User_Trip_Boat_Name", boatName);
                args.putLong("User_Trip_ID", tripId);
                args.putLong("User_Trip_Boat_ID", boatId);
                adf.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf).commit();

            }
        });

        return view;
    }

}
