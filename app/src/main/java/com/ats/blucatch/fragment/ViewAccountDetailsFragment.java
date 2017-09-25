package com.ats.blucatch.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.blucatch.R;
import com.ats.blucatch.activity.MainActivity;
import com.ats.blucatch.bean.Ledger;
import com.ats.blucatch.bean.LedgerData;
import com.ats.blucatch.utils.CheckNetwork;
import com.ats.blucatch.utils.InterfaceApi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewAccountDetailsFragment extends Fragment {

    private TextView tvDate, tvTitle, tvAmt, tvLabelDate, tvLabelTitle, tvLabelAmt;
    long accId;
    private ListView lvViewLedger;
    private ProgressDialog progressBar;
    private ArrayList<Ledger> ledgerArray = new ArrayList<>();
    private MyLedgerAdapter myLedgerAdapter;

    private int yyyy, mm, dd;
    private long fDate, tDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_account_details, container, false);
        Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
        Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");
        MainActivity.tvTitle.setText("Account Details");
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
        MainActivity.isAtAccDetails = true;
        MainActivity.isAtUserMaster = false;
        MainActivity.isAtAddUser = false;
        MainActivity.isAtExpMaster = false;
        MainActivity.isAtAddExp = false;
        MainActivity.isAtChangePass = false;
        MainActivity.isAtHomeTripExp = false;
        MainActivity.isAtHomeFishSell = false;

        accId = getArguments().getLong("Account_Id");
        String date_type = getArguments().getString("Date_Type");
        String view_type = getArguments().getString("View_Type");
        long fromDate = getArguments().getLong("From_Date");
        long toDate = getArguments().getLong("To_Date");
        long todaysDate = getArguments().getLong("Todays_Date");


        lvViewLedger = view.findViewById(R.id.lvAccDetails_ViewLedger);
        tvLabelDate = view.findViewById(R.id.tvAccdetails_LabelDate);
        tvLabelTitle = view.findViewById(R.id.tvAccdetails_LabelTitle);
        tvLabelAmt = view.findViewById(R.id.tvAccdetails_LabelAmt);

        tvLabelDate.setTypeface(boldFont);
        tvLabelTitle.setTypeface(boldFont);
        tvLabelAmt.setTypeface(boldFont);

        Calendar calendar = Calendar.getInstance();
        yyyy = calendar.get(Calendar.YEAR);
        mm = calendar.get(Calendar.MONTH);
        dd = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        fDate = calendar.getTimeInMillis();

        Calendar calendar1 = Calendar.getInstance();
        yyyy = calendar1.get(Calendar.YEAR);
        mm = calendar1.get(Calendar.MONTH);
        dd = calendar1.get(Calendar.DAY_OF_MONTH);
        calendar1.set(Calendar.MILLISECOND, 0);
        calendar1.set(Calendar.SECOND, 59);
        calendar1.set(Calendar.MINUTE, 59);
        calendar1.set(Calendar.HOUR_OF_DAY, 23);
        tDate = calendar1.getTimeInMillis();


        if (date_type.equalsIgnoreCase("today")) {
            getLedgerData(accId, date_type, view_type, fromDate, toDate);
        } else if (date_type.equalsIgnoreCase("fromTo")) {
            getLedgerData(accId, date_type, view_type, fromDate, toDate);
        }
//        getLedgerData(accId);
        return view;
    }


    public void getLedgerData(long id, final String date, final String view, final long fromDate, final long toDate) {

        if (CheckNetwork.isInternetAvailable(getContext())) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            InterfaceApi api = retrofit.create(InterfaceApi.class);
            Call<LedgerData> ledgerDataCall = api.allLedgerDataByAccId(id, id);

            progressBar = new ProgressDialog(getContext());
            progressBar.setCancelable(false);
            progressBar.setMessage("please wait....");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.setProgress(0);
            progressBar.setMax(100);
            progressBar.show();

            ledgerDataCall.enqueue(new Callback<LedgerData>() {
                @Override
                public void onResponse(Call<LedgerData> call, Response<LedgerData> response) {

                    if (response.body() != null) {
                        LedgerData data = response.body();
                        if (data.getErrorMessage().getError()) {
                            progressBar.dismiss();
                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
                            builder.setTitle("Error");
                            builder.setCancelable(false);
                            builder.setMessage("" + data.getErrorMessage());
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            android.app.AlertDialog dialog = builder.create();
                            dialog.show();

                            //Toast.makeText(getContext(), "unable to fetch account data.", Toast.LENGTH_SHORT).show();
                            Log.e("RESPONSE : ", " ERROR : " + data.getErrorMessage().getMessage());
                        } else {
                            ledgerArray.clear();
                            if (date.equalsIgnoreCase("today")) {
                                Log.e("DATE : ", " TODAY : -----------------------------");
                                for (int i = 0, j = 0; i < data.getLedger().size(); i++) {
                                    Log.e("DATE : ", " TODAY : -----------------------------\nFrom Date : " + fDate + "\nTo Date : " + tDate);
                                    if (data.getLedger().get(i).getTransactionDate() <= tDate && data.getLedger().get(i).getTransactionDate() >= fDate) {
                                        if (view.equalsIgnoreCase("All")) {
                                            Log.e("VIEW : ", " ALL : " + data.getLedger().get(i));
                                            ledgerArray.add(j, data.getLedger().get(i));
                                            j++;
                                        } else if (data.getLedger().get(i).getType().equalsIgnoreCase(view)) {
                                            Log.e("VIEW : ", "  : " + view + "   : " + data.getLedger().get(i));
                                            ledgerArray.add(j, data.getLedger().get(i));
                                            j++;
                                        }
                                    }
                                }
                            } else if (date.equalsIgnoreCase("fromTo")) {
                                Log.e("DATE : ", " From To Date ");
                                for (int i = 0, j = 0; i < data.getLedger().size(); i++) {
                                    Log.e("DATE : ", " TODAY : -----------------------------\nFrom Date : " + fromDate + "\nTo Date : " + toDate);
                                    if (data.getLedger().get(i).getTransactionDate() <= toDate && data.getLedger().get(i).getTransactionDate() >= fromDate) {
                                        if (view.equalsIgnoreCase("All")) {
                                            Log.e("VIEW : ", " ALL : " + data.getLedger().get(i));
                                            ledgerArray.add(j, data.getLedger().get(i));
                                            j++;
                                        } else if (data.getLedger().get(i).getType().equalsIgnoreCase(view)) {
                                            Log.e("VIEW : ", "  : " + view + "   : " + data.getLedger().get(i));
                                            ledgerArray.add(j, data.getLedger().get(i));
                                            j++;
                                        }
                                    }
                                }
                            }


                            //Log.e("RESPONSE : ", " DATA : " + data.getLedger());
                            if (ledgerArray.size() <= 0) {
                                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
                                builder.setTitle("");
                                builder.setCancelable(false);
                                builder.setMessage("No Entries Found !");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                android.app.AlertDialog dialog = builder.create();
                                dialog.show();
                            } else {
                                myLedgerAdapter = new MyLedgerAdapter(getContext(), ledgerArray);
                                lvViewLedger.setAdapter(myLedgerAdapter);
                                lvViewLedger.setTextFilterEnabled(true);
                            }
                            progressBar.dismiss();
                        }

                    } else {
                        progressBar.dismiss();
                        Toast.makeText(getContext(), "No Entries Found", Toast.LENGTH_SHORT).show();
                        Log.e("RESPONSE : ", " NO DATA");
                    }
                }

                @Override
                public void onFailure(Call<LedgerData> call, Throwable t) {
                    progressBar.dismiss();
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
                    builder.setTitle("Server Error");
                    builder.setCancelable(false);
                    builder.setMessage("No Entries Found !");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    android.app.AlertDialog dialog = builder.create();
                    dialog.show();
                    // Toast.makeText(getContext(), "unable to fetch account data! server error", Toast.LENGTH_SHORT).show();
                    Log.e("ON FAILURE : ", " ERROR : " + t.getMessage());
                }
            });


        } else {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
            builder.setTitle("Check Connectivity");
            builder.setCancelable(false);
            builder.setMessage("Please Connect to Internet");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            android.app.AlertDialog dialog = builder.create();
            dialog.show();
        }

    }


    public class MyLedgerAdapter extends BaseAdapter {

        private ArrayList<Ledger> originalValues;
        private ArrayList<Ledger> displayedValues;
        LayoutInflater inflater;

        public MyLedgerAdapter(Context context, ArrayList<Ledger> ledgerArrayList) {
            this.originalValues = ledgerArrayList;
            this.displayedValues = ledgerArrayList;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return displayedValues.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int position, View v, ViewGroup parent) {
            // LayoutInflater inflater = getActivity().getLayoutInflater();
            v = inflater.inflate(R.layout.custom_ledger_view, null);

            TextView tvDate = v.findViewById(R.id.tvAccdetails_Date);
            TextView tvExpName = v.findViewById(R.id.tvAccdetails_Title);
            TextView tvAmount = v.findViewById(R.id.tvAccdetails_Amt);
            TextView tvType = v.findViewById(R.id.tvAccDetails_Type);

            Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
            Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");

            tvDate.setTypeface(lightFont);
            tvExpName.setTypeface(lightFont);
            tvAmount.setTypeface(lightFont);
            tvType.setTypeface(lightFont);

            Date date = new Date(displayedValues.get(position).getTransactionDate());
            SimpleDateFormat fmtOut = new SimpleDateFormat("dd/MM/yyyy");
            String dtFormat = fmtOut.format(date);

            tvDate.setText("" + dtFormat);
            tvExpName.setText("" + displayedValues.get(position).getExpName());
            tvAmount.setText("" + displayedValues.get(position).getAmount());
            tvType.setText("" + displayedValues.get(position).getType());

            return v;
        }

        /*@Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    FilterResults results = new FilterResults();
                    ArrayList<Ledger> filteredArrayList = new ArrayList<Ledger>();
                    if (originalValues == null) {
                        originalValues = new ArrayList<Ledger>(displayedValues);
                    }

                    if (charSequence == null || charSequence.length() == 0) {
                        results.count = originalValues.size();
                        results.values = originalValues;
                    } else {
                        charSequence = charSequence.toString().toLowerCase();
                        for (int i = 0; i < originalValues.size(); i++) {
                            String accName = originalValues.get(i).getAccName();
                            String empType = originalValues.get(i).getEmpType();
                            String accType = originalValues.get(i).getAccType();
                            String mobile = originalValues.get(i).getEmpMobile();
                            String transcType = originalValues.get(i).getTransactionType();
                            if (accName.toLowerCase().startsWith(charSequence.toString()) || accType.toLowerCase().startsWith(charSequence.toString()) || empType.toLowerCase().startsWith(charSequence.toString()) || mobile.toLowerCase().startsWith(charSequence.toString()) || mobile.toLowerCase().startsWith(charSequence.toString()) || transcType.toLowerCase().startsWith(charSequence.toString())) {
                                filteredArrayList.add(new Account(originalValues.get(i).getAccId(), originalValues.get(i).getAccType(), originalValues.get(i).getAccName(), originalValues.get(i).getEmpType(), originalValues.get(i).getTandelAccId(), originalValues.get(i).getEmpMobile(), originalValues.get(i).getEmpDOB(), originalValues.get(i).getEmpAddress(), originalValues.get(i).getEmpAadhar(), originalValues.get(i).getEmpBankDetails(), originalValues.get(i).getEmpPerShare(), originalValues.get(i).getRemark(), originalValues.get(i).getIsUsed(), originalValues.get(i).getEmpBloodGroup(), originalValues.get(i).getEmergencyName1(), originalValues.get(i).getEmergencyMobile1(), originalValues.get(i).getEmergencyName2(), originalValues.get(i).getEmergencyMobile2(), originalValues.get(i).getOpeningBalance(), originalValues.get(i).getTransactionType(), originalValues.get(i).getCoId(), originalValues.get(i).getEnterDate(), originalValues.get(i).getEnterBy()));
                            }
                        }
                        results.count = filteredArrayList.size();
                        results.values = filteredArrayList;
                    }

                    return results;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    displayedValues = (ArrayList<Account>) filterResults.values;
                    notifyDataSetChanged();
                }
            };

            return filter;
        }*/
    }
}
