package com.ats.blucatch.fragment;


import android.app.DatePickerDialog;
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
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ats.blucatch.R;
import com.ats.blucatch.activity.MainActivity;
import com.ats.blucatch.utils.InterfaceApi;

import java.util.Calendar;

public class UserViewLedgerFragment extends Fragment {

    private RadioGroup rgDate, rgType;
    private RadioButton rbCurrentPeriod, rbFromToDate, rbAll, rbCr, rbDr;
    private Button btnShow;
    private TextInputLayout textFromDate, textToDate;
    private EditText edFromDate, edToDate;
    private LinearLayout llDate;
    private TextView tvAccName;
    static String accName;
    String dateType, viewType;

    private int fromYear, fromMonth, fromDay, toYear, toMonth, toDay;
    private long fromMillis, toMillis, todaysMillis;
    private int yyyy, mm, dd;

    int userId, coId;
    long accId;
    String appUserType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_view_ledger, container, false);
        Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
        Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");
        MainActivity.tvTitle.setText("View Ledger");
        MainActivity.tvTitle.setTypeface(boldFont);

        MainActivity.isAtHome = false;
        MainActivity.isAtUserEnterTransaction = false;
        MainActivity.isAtUserFishSell = false;
        MainActivity.isAtUserTripExp = false;
        MainActivity.isAtUserViewLedger = true;
        MainActivity.isAtUserAccDetails = false;


        try {
            SharedPreferences pref = getContext().getSharedPreferences(InterfaceApi.MY_PREF, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            userId = pref.getInt("AppUserId", 0);
            coId = pref.getInt("AppCoId", 0);
            accId = pref.getLong("AppAccId", 0);
            appUserType = pref.getString("AppUserType", "");
            Log.e("Co_id : ", "" + coId);
        } catch (Exception e) {
            Log.e("Exception : ", "" + e.getMessage());
        }


        tvAccName = view.findViewById(R.id.tvUserViewLedger_AccName);
        // tvAccName.setText("" + accName);

        rgDate = view.findViewById(R.id.rgUserViewLedger_Date);
        rgType = view.findViewById(R.id.rgUserViewLedger_Type);

        rbCurrentPeriod = view.findViewById(R.id.rbUserViewLedger_CurrentPeriod);
        rbCurrentPeriod.setChecked(true);

        rbFromToDate = view.findViewById(R.id.rbUserViewLedger_FromToDate);
        rbAll = view.findViewById(R.id.rbUserViewLedger_All);
        rbAll.setChecked(true);

        rbCr = view.findViewById(R.id.rbUserViewLedger_Cr);
        rbDr = view.findViewById(R.id.rbUserViewLedger_Dr);

        btnShow = view.findViewById(R.id.btnUserViewLedger_Show);
        llDate = view.findViewById(R.id.llUserViewLedgerDate);

        textFromDate = view.findViewById(R.id.textUserViewLedger_FromDate);
        textToDate = view.findViewById(R.id.textUserViewLedger_ToDate);

        edFromDate = view.findViewById(R.id.edUserViewLedger_FromDate);
        edToDate = view.findViewById(R.id.edUserViewLedger_ToDate);

        tvAccName.setTypeface(boldFont);
        rbCurrentPeriod.setTypeface(lightFont);
        rbFromToDate.setTypeface(lightFont);
        rbAll.setTypeface(lightFont);
        rbCr.setTypeface(lightFont);
        rbDr.setTypeface(lightFont);

        btnShow.setTypeface(lightFont);

        textFromDate.setTypeface(lightFont);
        textToDate.setTypeface(lightFont);

        edFromDate.setTypeface(lightFont);
        edToDate.setTypeface(lightFont);

        Calendar calendar = Calendar.getInstance();
        yyyy = calendar.get(Calendar.YEAR);
        mm = calendar.get(Calendar.MONTH);
        dd = calendar.get(Calendar.DAY_OF_MONTH);
        fromMillis = calendar.getTimeInMillis();
        toMillis = calendar.getTimeInMillis();
        todaysMillis = calendar.getTimeInMillis();
        edFromDate.setText(dd + " / " + (mm + 1) + " / " + yyyy);
        edToDate.setText(dd + " / " + (mm + 1) + " / " + yyyy);


        rbFromToDate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    llDate.setVisibility(View.VISIBLE);
                } else {
                    llDate.setVisibility(View.GONE);
                }
            }
        });

        edFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), fromDateListener, yyyy, mm, dd);
                dialog.show();
            }
        });

        edToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), toDateListener, yyyy, mm, dd);
                dialog.show();
            }
        });


        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (rbCurrentPeriod.isChecked()) {
                    dateType = "today";
                } else if (rbFromToDate.isChecked()) {
                    dateType = "fromTo";
                }

                if (rbAll.isChecked()) {
                    viewType = "All";
                } else if (rbCr.isChecked()) {
                    viewType = "Cr";
                } else if (rbDr.isChecked()) {
                    viewType = "Dr";
                }

                Fragment adf = new UserViewAccountDetailsFragment();
                Bundle args = new Bundle();
                args.putLong("Account_Id", accId);
                args.putString("Date_Type", dateType);
                args.putString("View_Type", viewType);
                args.putLong("From_Date", fromMillis);
                args.putLong("To_Date", toMillis);
                args.putLong("Todays_Date", todaysMillis);
                adf.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf).commit();
            }
        });

        return view;
    }


    private DatePickerDialog.OnDateSetListener fromDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            fromYear = year;
            fromMonth = month + 1;
            fromDay = dayOfMonth;
            edFromDate.setText(fromDay + " / " + fromMonth + " / " + fromYear);

            Calendar calendar = Calendar.getInstance();
            calendar.set(fromYear, fromMonth - 1, fromDay);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            fromMillis = calendar.getTimeInMillis();
        }
    };


    private DatePickerDialog.OnDateSetListener toDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            toYear = year;
            toMonth = month + 1;
            toDay = dayOfMonth;
            edToDate.setText(toDay + " / " + toMonth + " / " + toYear);

            Calendar calendar = Calendar.getInstance();
            calendar.set(toYear, toMonth - 1, toDay);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            toMillis = calendar.getTimeInMillis();
        }
    };

}
