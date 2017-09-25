package com.ats.blucatch.fragment;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.ats.blucatch.R;
import com.ats.blucatch.activity.MainActivity;

public class TripDetailsFragment extends Fragment {

    private TextView tvTripNo, tvStatus, tvBoatName, tvTandelCrew, tvStDate, tvTripDays, tvTripExp, tvTripExpCount, tvFishSell, tvFishSellCount;
    private EditText edSearch;
    static long boatId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_details, container, false);
        Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
        Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");

        MainActivity.tvTitle.setText("Trip Details- Sagar");
        MainActivity.tvTitle.setTypeface(boldFont);

        MainActivity.isAtHome = false;
        MainActivity.isAtFishMaster = false;
        MainActivity.isAtAddFish = false;
        MainActivity.isAtEditFish = false;
        MainActivity.isAtBoatMaster = false;
        MainActivity.isAtAddBoat = false;
        MainActivity.isAtEditBoat = false;
        MainActivity.isAtTripDetails = true;
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

        try {
            boatId = getArguments().getLong("Boat_Id");
        } catch (Exception e) {
            e.printStackTrace();
        }


        edSearch = view.findViewById(R.id.edTripDetails_Search);
        edSearch.setTypeface(lightFont);

        tvTripNo = view.findViewById(R.id.tvTripDetails_TripNo);
        tvStatus = view.findViewById(R.id.tvTripDetails_Status);
        tvBoatName = view.findViewById(R.id.tvTripDetails_BoatName);
        tvTandelCrew = view.findViewById(R.id.tvTripDetails_TandelCrew);
        tvStDate = view.findViewById(R.id.tvTripDetails_StDate);
        tvTripDays = view.findViewById(R.id.tvTripDetails_TripDays);
        tvTripExp = view.findViewById(R.id.tvTripDetails_TripExpenses);
        tvTripExpCount = view.findViewById(R.id.tvTripDetails_TripExpCount);
        tvFishSell = view.findViewById(R.id.tvTripDetails_FishSell);
        tvFishSellCount = view.findViewById(R.id.tvTripDetails_FishSellCount);

        tvTripNo.setTypeface(lightFont);
        tvStatus.setTypeface(boldFont);
        tvBoatName.setTypeface(lightFont);
        tvTandelCrew.setTypeface(lightFont);
        tvStDate.setTypeface(lightFont);
        tvTripDays.setTypeface(lightFont);
        tvTripExp.setTypeface(boldFont);
        tvTripExpCount.setTypeface(lightFont);
        tvFishSell.setTypeface(boldFont);
        tvFishSellCount.setTypeface(lightFont);


        tvTripExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new BoatTripExpensesViewFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }
        });

        tvTripExpCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new BoatTripExpensesViewFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }
        });

        tvFishSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new BoatFishSellViewFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }
        });

        tvFishSellCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new BoatFishSellViewFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }
        });
        return view;
    }

}
