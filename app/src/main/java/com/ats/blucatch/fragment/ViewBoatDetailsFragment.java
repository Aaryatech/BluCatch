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

import java.text.SimpleDateFormat;
import java.util.Date;

public class ViewBoatDetailsFragment extends Fragment {

    private TextView tvLabelName, tvLabelRegno, tvLabelRegOwner, tvLabelRegTandel, tvLabelAuctioner, tvLabelPurchaseDate, tvLabelBoatSize, tvLabelFishingType, tvLabelLicNo, tvLabelLicFrom, tvLabelLicTo, tvLabelIncFrom, tvLabelIncTo, tvLabelEngineType, tvLabelHPRating, tvLabelBoatStatus;
    private TextView tvName, tvRegno, tvRegOwner, tvRegTandel, tvAuctioner, tvPurchaseDate, tvBoatSize, tvFishingType, tvLicNo, tvLicFrom, tvLicTo, tvIncFrom, tvIncTo, tvEngineType, tvHPRating, tvBoatStatus, tvLabelLicDetails, tvLabelIncDetails;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_boat_details, container, false);
        Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
        Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");

        MainActivity.tvTitle.setText("Boat Details");
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
        MainActivity.isAtBoatDetails = true;
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


        tvLabelName = view.findViewById(R.id.tvBoatDetails_LabelBoatName);
        tvLabelRegno = view.findViewById(R.id.tvBoatDetails_LabelRegNo);
        tvLabelRegOwner = view.findViewById(R.id.tvBoatDetails_LabelRegOwner);
        tvLabelRegTandel = view.findViewById(R.id.tvBoatDetails_LabelRegTandel);
        tvLabelAuctioner = view.findViewById(R.id.tvBoatDetails_LabelRegAuctioner);
        tvLabelPurchaseDate = view.findViewById(R.id.tvBoatDetails_LabelPurdate);
        tvLabelBoatSize = view.findViewById(R.id.tvBoatDetails_LabelBoatSize);
        tvLabelFishingType = view.findViewById(R.id.tvBoatDetails_LabelFishingType);
        tvLabelLicNo = view.findViewById(R.id.tvBoatDetails_LabelLicNo);
        tvLabelLicFrom = view.findViewById(R.id.tvBoatDetails_LabelLicFrom);
        tvLabelLicTo = view.findViewById(R.id.tvBoatDetails_LabelLicTo);
        tvLabelIncFrom = view.findViewById(R.id.tvBoatDetails_LabelIncFrom);
        tvLabelIncTo = view.findViewById(R.id.tvBoatDetails_LabelIncTo);
        tvLabelEngineType = view.findViewById(R.id.tvBoatDetails_LabelEngType);
        tvLabelHPRating = view.findViewById(R.id.tvBoatDetails_LabelHPRating);
        tvLabelBoatStatus = view.findViewById(R.id.tvBoatDetails_LabelBoatStatus);

        tvName = view.findViewById(R.id.tvBoatDetails_BoatName);
        tvRegno = view.findViewById(R.id.tvBoatDetails_RegNo);
        tvRegOwner = view.findViewById(R.id.tvBoatDetails_RegOwner);
        tvRegTandel = view.findViewById(R.id.tvBoatDetails_RegTandel);
        tvAuctioner = view.findViewById(R.id.tvBoatDetails_RegAuctioner);
        tvPurchaseDate = view.findViewById(R.id.tvBoatDetails_PurDate);
        tvBoatSize = view.findViewById(R.id.tvBoatDetails_BoatSize);
        tvFishingType = view.findViewById(R.id.tvBoatDetails_FishingType);
        tvLicNo = view.findViewById(R.id.tvBoatDetails_LicNo);
        tvLicFrom = view.findViewById(R.id.tvBoatDetails_LicFrom);
        tvLicTo = view.findViewById(R.id.tvBoatDetails_LicTo);
        tvIncFrom = view.findViewById(R.id.tvBoatDetails_IncFrom);
        tvIncTo = view.findViewById(R.id.tvBoatDetails_IncTo);
        tvEngineType = view.findViewById(R.id.tvBoatDetails_EngType);
        tvHPRating = view.findViewById(R.id.tvBoatDetails_HPRating);
        tvBoatStatus = view.findViewById(R.id.tvBoatDetails_BoatStatus);
        tvLabelLicDetails = view.findViewById(R.id.tvBoatDetails_LabelLicDetails);
        tvLabelIncDetails = view.findViewById(R.id.tvBoatDetails_LabelIncDetails);

        tvLabelLicDetails.setTypeface(boldFont);
        tvLabelIncDetails.setTypeface(boldFont);

        tvLabelName.setTypeface(lightFont);
        tvLabelRegno.setTypeface(lightFont);
        tvLabelRegOwner.setTypeface(lightFont);
        tvLabelRegTandel.setTypeface(lightFont);
        tvLabelAuctioner.setTypeface(lightFont);
        tvLabelPurchaseDate.setTypeface(lightFont);
        tvLabelBoatSize.setTypeface(lightFont);
        tvLabelFishingType.setTypeface(lightFont);
        tvLabelLicNo.setTypeface(lightFont);
        tvLabelLicFrom.setTypeface(lightFont);
        tvLabelLicTo.setTypeface(lightFont);
        tvLabelIncFrom.setTypeface(lightFont);
        tvLabelIncTo.setTypeface(lightFont);
        tvLabelEngineType.setTypeface(lightFont);
        tvLabelHPRating.setTypeface(lightFont);
        tvLabelBoatStatus.setTypeface(boldFont);

        tvName.setTypeface(lightFont);
        tvRegno.setTypeface(lightFont);
        tvRegOwner.setTypeface(lightFont);
        tvRegTandel.setTypeface(lightFont);
        tvAuctioner.setTypeface(lightFont);
        tvPurchaseDate.setTypeface(lightFont);
        tvBoatSize.setTypeface(lightFont);
        tvFishingType.setTypeface(lightFont);
        tvLicNo.setTypeface(lightFont);
        tvLicFrom.setTypeface(lightFont);
        tvLicTo.setTypeface(lightFont);
        tvIncFrom.setTypeface(lightFont);
        tvIncTo.setTypeface(lightFont);
        tvEngineType.setTypeface(lightFont);
        tvHPRating.setTypeface(lightFont);
        tvBoatStatus.setTypeface(lightFont);

        String bName = getArguments().getString("Boat_Name");
        String bOwner = getArguments().getString("Boat_Owner");
        String bTandel = getArguments().getString("Boat_Tandel");
        String bAuctioner = getArguments().getString("Boat_Auctioner");
        String bRegno = getArguments().getString("Boat_RegNo");
        long bPurchaseDate = getArguments().getLong("Boat_Purchase_Date");
        int bLength = getArguments().getInt("Boat_Length");
        int bBreadth = getArguments().getInt("Boat_Breadth");
        int bHeight = getArguments().getInt("Boat_Height");
        String bFishingType = getArguments().getString("Boat_Fishing_type");
        String bLicNo = getArguments().getString("Boat_LicNo");
        long bLicFrom = getArguments().getLong("Boat_LicFrom");
        long bLicTo = getArguments().getLong("Boat_LicTo");
        long bIncFrom = getArguments().getLong("Boat_IncFrom");
        long bIncTo = getArguments().getLong("Boat_IncTo");
        String bEngineType = getArguments().getString("Boat_Engine_Type");
        String bStatus = getArguments().getString("Boat_Status");
        int bHPRating = getArguments().getInt("Boat_HP_Rating");

        tvName.setText("" + bName);
        tvRegno.setText("" + bRegno);
        tvRegOwner.setText("" + bOwner);
        tvRegTandel.setText("" + bTandel);
        tvAuctioner.setText("" + bAuctioner);

        Date dt = new Date(bPurchaseDate);
        SimpleDateFormat formatter = new SimpleDateFormat("dd / M / yyyy");
        String curDate = formatter.format(dt);
        tvPurchaseDate.setText(curDate);

        tvBoatSize.setText("" + bLength + " x " + bBreadth + " x " + bHeight);
        tvFishingType.setText("" + bFishingType);
        tvLicNo.setText("" + bLicNo);

        Date licFrom = new Date(bLicFrom);
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd / M / yyyy");
        String licFromDate = formatter1.format(licFrom);
        tvLicFrom.setText("" + licFromDate);

        Date licTo = new Date(bLicTo);
        SimpleDateFormat formatter2 = new SimpleDateFormat("dd / M / yyyy");
        String licToDate = formatter2.format(licTo);
        tvLicTo.setText("" + licToDate);

        Date incFrom = new Date(bIncFrom);
        SimpleDateFormat formatter3 = new SimpleDateFormat("dd / M / yyyy");
        String incFromDate = formatter3.format(incFrom);
        tvIncFrom.setText("" + incFromDate);

        Date incTo = new Date(bIncTo);
        SimpleDateFormat formatter4 = new SimpleDateFormat("dd / M / yyyy");
        String incToDate = formatter4.format(incTo);
        tvIncTo.setText("" + incToDate);

        tvEngineType.setText("" + bEngineType);
        tvHPRating.setText("" + bHPRating);
        tvBoatStatus.setText("" + bStatus);


        return view;
    }

}
