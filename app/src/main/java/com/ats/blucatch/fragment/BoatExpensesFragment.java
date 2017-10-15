package com.ats.blucatch.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.blucatch.R;
import com.ats.blucatch.activity.MainActivity;
import com.ats.blucatch.bean.BoatWiseExpense;
import com.ats.blucatch.bean.BoatWiseExpenseData;
import com.ats.blucatch.utils.CheckNetwork;
import com.ats.blucatch.utils.InterfaceApi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BoatExpensesFragment extends Fragment {

    private TextView tvTotalAmt, tvLabelDate, tvDate, tvLabelExpType, tvExpType, tvLabelExpName, tvExpName, tvLabelDesc, tvDesc, tvLabelAmt, tvAmt, tvExpDate1, tvExpDate2, tvName1, tvName2, tvDesc1, tvDesc2, tvAmt1, tvAmt2;
    public static String boatName;
    public static long boatId;
    private FloatingActionButton fab;
    private ProgressDialog progressBar;
    private ListView lvList;
    private EditText edSearch;

    private ArrayList<BoatWiseExpense> expenseArrayList = new ArrayList<>();

    BoatExpenseAdapter myAdapter;


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

        edSearch = view.findViewById(R.id.edBoatExpenses_Search);
        lvList = view.findViewById(R.id.lvBoatExp_List);
        fab = view.findViewById(R.id.fabBoatExpense);
        tvTotalAmt = view.findViewById(R.id.tvBoatExp_TotalAmt);
        tvTotalAmt.setTypeface(boldFont);

        getBoatExpensesData(boatId);

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

        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                myAdapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }


    public void getBoatExpensesData(long id) {
        if (CheckNetwork.isInternetAvailable(getContext())) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            final InterfaceApi api = retrofit.create(InterfaceApi.class);

            Call<BoatWiseExpenseData> boatWiseExpenseDataCall = api.allBoatExpenses(id);

            progressBar = new ProgressDialog(getContext());
            progressBar.setCancelable(false);
            progressBar.setMessage("please wait....");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.setProgress(0);
            progressBar.setMax(100);
            progressBar.show();

            boatWiseExpenseDataCall.enqueue(new Callback<BoatWiseExpenseData>() {
                @Override
                public void onResponse(Call<BoatWiseExpenseData> call, Response<BoatWiseExpenseData> response) {
                    try {
                        if (response.body() != null) {
                            BoatWiseExpenseData data = response.body();
                            if (data.getErrorMessage().getError()) {
                                progressBar.dismiss();
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                                builder.setCancelable(false);
                                builder.setMessage("" + data.getErrorMessage().getMessage());
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            } else {
                                expenseArrayList.clear();
                                for (int i = 0; i < data.getBoatWiseExpenses().size(); i++) {
                                    expenseArrayList.add(i, data.getBoatWiseExpenses().get(i));
                                }

                                if (data.getTotalExpense() == 0) {
                                    tvTotalAmt.setText("");
                                } else {
                                    tvTotalAmt.setText("" + data.getTotalExpense() + "/-");
                                }

                                myAdapter = new BoatExpenseAdapter(getContext(), expenseArrayList);
                                lvList.setAdapter(myAdapter);
                                progressBar.dismiss();
                            }

                        } else {
                            progressBar.dismiss();
                            Toast.makeText(getContext(), "No Expenses Found", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        progressBar.dismiss();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<BoatWiseExpenseData> call, Throwable t) {
                    progressBar.dismiss();
                    Log.e("onFailure : ", "" + t.getMessage());
                    Toast.makeText(getContext(), "Server Error!", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
            builder.setTitle("Check Connectivity");
            builder.setCancelable(false);
            builder.setMessage("Please Connect to Internet");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }


    public class BoatExpenseAdapter extends BaseAdapter implements Filterable {

        private ArrayList<BoatWiseExpense> originalValues;
        private ArrayList<BoatWiseExpense> displayedValues;
        LayoutInflater inflater;

        public BoatExpenseAdapter(Context context, ArrayList<BoatWiseExpense> boatArrayList) {
            this.originalValues = boatArrayList;
            this.displayedValues = boatArrayList;
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
            v = inflater.inflate(R.layout.custom_boat_expense_layout, null);

            LinearLayout llBoatExp = v.findViewById(R.id.llBoatExp_Layout);
            TextView tvExpName = v.findViewById(R.id.tvBoatExp_ExpName);
            TextView tvExpDate = v.findViewById(R.id.tvBoatExp_ExpDate);
            TextView tvExpRemark = v.findViewById(R.id.tvBoatExp_ExpRemark);
            TextView tvExpAmt = v.findViewById(R.id.tvBoatExp_ExpAmt);
            TextView tvExpType = v.findViewById(R.id.tvBoatExp_ExpType);

            Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
            Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");

            tvExpName.setTypeface(boldFont);
            tvExpDate.setTypeface(boldFont);
            tvExpRemark.setTypeface(lightFont);
            tvExpAmt.setTypeface(boldFont);
            tvExpType.setTypeface(lightFont);

            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
            Date dt = new Date();
            dt.setTime(displayedValues.get(position).getTransactionDate());
            String textDate = sdf.format(dt);

            tvExpType.setText("( " + displayedValues.get(position).getExpType() + " )");
            tvExpName.setText("" + displayedValues.get(position).getExpName());
            tvExpDate.setText(" " + textDate + " ");
            tvExpRemark.setText("" + displayedValues.get(position).getRemark());
            tvExpAmt.setText("" + displayedValues.get(position).getAmount());

            if (displayedValues.get(position).getIsAuthorised() == 0) {
                llBoatExp.setBackgroundColor(Color.parseColor("#d1f9d0"));
            } else if (displayedValues.get(position).getIsAuthorised() == 1) {
                llBoatExp.setBackgroundColor(Color.parseColor("#f9d1d0"));
            } else if (displayedValues.get(position).getIsAuthorised() == 2) {
                llBoatExp.setBackgroundColor(Color.parseColor("#ffffff"));
            }


            return v;
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    FilterResults results = new FilterResults();
                    ArrayList<BoatWiseExpense> filteredArrayList = new ArrayList<>();
                    if (originalValues == null) {
                        originalValues = new ArrayList<BoatWiseExpense>(displayedValues);
                    }

                    if (charSequence == null || charSequence.length() == 0) {
                        results.count = originalValues.size();
                        results.values = originalValues;
                    } else {
                        charSequence = charSequence.toString().toLowerCase();
                        for (int i = 0; i < originalValues.size(); i++) {
                            String name = originalValues.get(i).getExpName();
                            String remark = originalValues.get(i).getRemark();
                            String type = originalValues.get(i).getExpType();
                            String amount = String.valueOf(originalValues.get(i).getAmount());
                            if (name.toLowerCase().startsWith(charSequence.toString()) || remark.toLowerCase().startsWith(charSequence.toString()) || type.toLowerCase().startsWith(charSequence.toString()) || amount.toLowerCase().startsWith(charSequence.toString())) {
                                filteredArrayList.add(new BoatWiseExpense(originalValues.get(i).getTrId(), originalValues.get(i).getExpId(), originalValues.get(i).getExpType(), originalValues.get(i).getExpName(), originalValues.get(i).getBoatId(), originalValues.get(i).getBoatName(), originalValues.get(i).getSenderAccId(), originalValues.get(i).getSenderAccName(), originalValues.get(i).getReceiverAccId(), originalValues.get(i).getReceiverAccName(), originalValues.get(i).getAmount(), originalValues.get(i).getRate(), originalValues.get(i).getQuantity(), originalValues.get(i).getTotal(), originalValues.get(i).getAddedAmount(), originalValues.get(i).getTransactionDate(), originalValues.get(i).getIsAuthorised(), originalValues.get(i).getAutRemark(), originalValues.get(i).getRejRemark(), originalValues.get(i).getAutDate(), originalValues.get(i).getRejDate(), originalValues.get(i).getRemark()));
                            }
                        }
                        results.count = filteredArrayList.size();
                        results.values = filteredArrayList;
                    }

                    return results;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    displayedValues = (ArrayList<BoatWiseExpense>) filterResults.values;
                    notifyDataSetChanged();
                }
            };

            return filter;
        }
    }
}
