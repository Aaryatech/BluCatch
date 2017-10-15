package com.ats.blucatch.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ListView;
import android.widget.TextView;

import com.ats.blucatch.R;
import com.ats.blucatch.activity.MainActivity;
import com.ats.blucatch.bean.TripExpensesData;
import com.ats.blucatch.bean.TripExpensesListData;
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

public class BoatTripExpensesViewFragment extends Fragment {

    //private TextView tvLabelTripNo, tvLabelExp, tvTripNo, tvExp, tvLabelDate, tvLabelTitle, tvDate1, tvDate2, tvName1, tvName2, tvDesc1, tvDesc2, tvAmt1, tvAmt2;
    private TextView tvLabelTripNo, tvLabelExp, tvTripNo, tvExp, tvLabelDate, tvLabelTitle, tvDate1, tvName1, tvDesc1, tvAmt1;
    private EditText edSearch;
    private FloatingActionButton fab;
    public static String boatName;
    public static long tripId, boatId;
    public static int seasonId;
    public ProgressDialog progressBar;
    private ArrayList<TripExpensesData> expensesDataArray = new ArrayList<>();
    private MyBoatTripExpAdapter myAdapter;

    private ListView lvTripExp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_boat_trip_expenses_view, container, false);
        Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
        Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");

        try {
            boatName = getArguments().getString("Boat_Name");
            tripId = getArguments().getLong("Trip_ID");
            boatId = getArguments().getLong("Boat_ID");
            seasonId = getArguments().getInt("Season_Id");
        } catch (Exception e) {
            Log.e("Bundle  : ", "no data found");
        }

        MainActivity.tvTitle.setText("Trip Expenses- " + boatName);
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
        MainActivity.isAtBoatTripExp = true;
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


        fab = view.findViewById(R.id.fabEnterExp);
        lvTripExp = view.findViewById(R.id.lvBoatTripExpense_List);

        edSearch = view.findViewById(R.id.edBoatTripExpenses_Search);
        tvLabelTripNo = view.findViewById(R.id.tvBoatTripExpense_LabelTripNo);
        tvLabelExp = view.findViewById(R.id.tvBoatTripExpense_LabelExpense);
        tvTripNo = view.findViewById(R.id.tvBoatTripExpense_TripNo);
        tvExp = view.findViewById(R.id.tvBoatTripExpense_Expense);

        edSearch.setTypeface(lightFont);
        tvLabelTripNo.setTypeface(boldFont);
        tvLabelExp.setTypeface(boldFont);
        tvTripNo.setTypeface(boldFont);
        tvExp.setTypeface(boldFont);

        tvTripNo.setText("" + tripId);

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


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment adf = new UserEnterTransactionFragment();
                Bundle args = new Bundle();
                args.putString("Expense_Type", "Trip");
                args.putString("User_Trip_Boat_Name", boatName);
                args.putLong("User_Trip_ID", tripId);
                args.putLong("User_Trip_Boat_ID", boatId);
                args.putInt("User_Trip_Season_Id", seasonId);
                adf.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf).commit();

            }
        });

        getTripExpensesData();

        return view;
    }

    public void getTripExpensesData() {
        if (CheckNetwork.isInternetAvailable(getContext())) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            final InterfaceApi api = retrofit.create(InterfaceApi.class);

            Call<TripExpensesListData> tripExpensesListDataCall = api.allTripWiseExpenses(tripId);

            progressBar = new ProgressDialog(getContext());
            progressBar.setCancelable(false);
            progressBar.setMessage("please wait....");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.setProgress(0);
            progressBar.setMax(100);
            progressBar.show();

            tripExpensesListDataCall.enqueue(new Callback<TripExpensesListData>() {
                @Override
                public void onResponse(Call<TripExpensesListData> call, Response<TripExpensesListData> response) {
                    try {
                        if (response.body() != null) {
                            TripExpensesListData data = response.body();
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

                                Log.e("ON RESPONSE : ", "ERROR : " + data.getErrorMessage().getMessage());

                            } else {
                                expensesDataArray.clear();
                                for (int i = 0; i < data.getTripExpensesData().size(); i++) {
                                    expensesDataArray.add(i, data.getTripExpensesData().get(i));
                                }
                                myAdapter = new MyBoatTripExpAdapter(getContext(), expensesDataArray);
                                lvTripExp.setAdapter(myAdapter);

                                tvExp.setText("" + data.getTotalAmount() + "/-");

                                progressBar.dismiss();

                            }

                        } else {
                            progressBar.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                            builder.setTitle("Error");
                            builder.setCancelable(false);
                            builder.setMessage("No Expenses Found!");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            Log.e("ON RESPONSE : ", "NO DATA");
                        }
                    } catch (Exception e) {
                        progressBar.dismiss();
                        Log.e("Exception : ", "" + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<TripExpensesListData> call, Throwable t) {
                    progressBar.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                    builder.setTitle("Error");
                    builder.setCancelable(false);
                    builder.setMessage("Server Error");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    Log.e("ON FAILURE : ", "ERROR : " + t.getMessage());
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


    public class MyBoatTripExpAdapter extends BaseAdapter implements Filterable {

        private ArrayList<TripExpensesData> originalValues;
        private ArrayList<TripExpensesData> displayedValues;
        LayoutInflater inflater;

        public MyBoatTripExpAdapter(Context context, ArrayList<TripExpensesData> tripDispArrayList) {
            this.originalValues = tripDispArrayList;
            this.displayedValues = tripDispArrayList;
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

        public class ViewHolder {
            TextView tvDate;
            TextView tvExpense;
            TextView tvRemark;
            TextView tvAmount;
        }

        @Override
        public View getView(final int position, View v, ViewGroup parent) {
            MyBoatTripExpAdapter.ViewHolder holder = null;

            if (v == null) {
                v = inflater.inflate(R.layout.custom_trip_expense_list, null);
                holder = new MyBoatTripExpAdapter.ViewHolder();
                holder.tvDate = v.findViewById(R.id.tvUserTripExp_Date1);
                holder.tvExpense = v.findViewById(R.id.tvUserTripExp_Name1);
                holder.tvRemark = v.findViewById(R.id.tvUserTripExp_Desc1);
                holder.tvAmount = v.findViewById(R.id.tvUserTripExp_Amt1);
                v.setTag(holder);
            } else {
                holder = (MyBoatTripExpAdapter.ViewHolder) v.getTag();
            }

            Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
            Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");

            holder.tvDate.setTypeface(boldFont);
            holder.tvExpense.setTypeface(boldFont);
            holder.tvAmount.setTypeface(boldFont);
            holder.tvRemark.setTypeface(lightFont);

            Date date1 = new Date(displayedValues.get(position).getExpDate());
            SimpleDateFormat df2 = new SimpleDateFormat("dd MMM yyyy");
            String dateText = df2.format(date1);
            System.out.println(dateText);

            holder.tvDate.setText("" + dateText);
            holder.tvExpense.setText("" + displayedValues.get(position).getExpName());
            holder.tvRemark.setText("" + displayedValues.get(position).getExpRemark());
            holder.tvAmount.setText("" + displayedValues.get(position).getExpAmount());

            return v;
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    FilterResults results = new FilterResults();
                    ArrayList<TripExpensesData> filteredArrayList = new ArrayList<>();
                    if (originalValues == null) {
                        originalValues = new ArrayList<TripExpensesData>(displayedValues);
                    }

                    if (charSequence == null || charSequence.length() == 0) {
                        results.count = originalValues.size();
                        results.values = originalValues;
                    } else {
                        charSequence = charSequence.toString().toLowerCase();
                        for (int i = 0; i < originalValues.size(); i++) {
                            String name = originalValues.get(i).getExpName();
                            String amount = "" + originalValues.get(i).getExpAmount();
                            if (name.toLowerCase().startsWith(charSequence.toString()) || amount.toLowerCase().startsWith(charSequence.toString())) {
                                filteredArrayList.add(new TripExpensesData(originalValues.get(i).getApproveStatus(), originalValues.get(i).getSenderId(), originalValues.get(i).getReceiverId(), originalValues.get(i).getExpRemark(), originalValues.get(i).getExpDate(), originalValues.get(i).getExpTripId(), originalValues.get(i).getExpId(), originalValues.get(i).getExpName(), originalValues.get(i).getExpAmount()));
                            }
                        }
                        results.count = filteredArrayList.size();
                        results.values = filteredArrayList;
                    }

                    return results;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    displayedValues = (ArrayList<TripExpensesData>) filterResults.values;
                    notifyDataSetChanged();
                }
            };

            return filter;
        }
    }


}
