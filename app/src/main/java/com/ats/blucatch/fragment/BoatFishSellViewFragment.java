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
import com.ats.blucatch.bean.FishCatchDisplayList;
import com.ats.blucatch.bean.FishSellList;
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

public class BoatFishSellViewFragment extends Fragment {

    private TextView tvAuctioner, tvLabelTripNo, tvLabelSell, tvTripNo, tvSell, tvLabelDate, tvLabelTitle, tvDate1, tvDate2, tvTitle1, tvTitle2, tvFish1, tvSize1, tvLabelQty1, tvQty1, tvAmt1;
    private EditText edSearch;
    public static String boatName, auctName;
    public static long tripId, boatId;
    public static int seasonId;
    private FloatingActionButton fab;
    private ListView lvList;
    private ProgressDialog progressBarSell;

    private ArrayList<FishCatchDisplayList> catchArrayList = new ArrayList<>();

    private MyBoatTripFishSellAdapter fishSellAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_boat_fish_sell_view, container, false);
        Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
        Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");

        try {
            boatId = getArguments().getLong("Boat_Id");
            tripId = getArguments().getLong("Trip_Id");
            boatName = getArguments().getString("Boat_Name");
            auctName = getArguments().getString("Auctioner_Name");
            seasonId = getArguments().getInt("Season_Id");
        } catch (Exception e) {
            Log.e("Bundle  : ", "no data found");
        }
        MainActivity.tvTitle.setText("Fish Sell- " + boatName);
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


        fab = view.findViewById(R.id.fabBoatTripEnterFishExp);
        lvList = view.findViewById(R.id.lvBoatTripFishSell_List);

        tvAuctioner = view.findViewById(R.id.tvBoatTripFishSell_AuctName);
        tvLabelTripNo = view.findViewById(R.id.tvBoatTripFishSell_LabelTripNo);
        tvLabelSell = view.findViewById(R.id.tvBoatTripFishSell_LabelSell);
        tvTripNo = view.findViewById(R.id.tvBoatTripFishSell_TripNo);
        tvSell = view.findViewById(R.id.tvBoatTripFishSell_Sell);

        edSearch = view.findViewById(R.id.edBoatTripFishSell_Search);

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
                args.putString("Boat_Name", boatName);
                args.putLong("Trip_Id", tripId);
                args.putLong("Boat_Id", boatId);
                args.putInt("Season_Id", seasonId);
                adf.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf).commit();

            }
        });

        getFishSellData(tripId);

        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                fishSellAdapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return view;
    }


    public void getFishSellData(long tripId) {

        if (CheckNetwork.isInternetAvailable(getContext())) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            InterfaceApi api = retrofit.create(InterfaceApi.class);

            Call<FishSellList> fishSellListCall = api.getFishSellList(tripId);

            progressBarSell = new ProgressDialog(getContext());
            progressBarSell.setCancelable(false);
            progressBarSell.setMessage("please wait....");
            progressBarSell.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBarSell.setProgress(0);
            progressBarSell.setMax(100);
            progressBarSell.show();

            fishSellListCall.enqueue(new Callback<FishSellList>() {
                @Override
                public void onResponse(Call<FishSellList> call, Response<FishSellList> response) {
                    try {

                        if (response.body() != null) {
                            FishSellList data = response.body();
                            if (data.getErrorMessage().getError()) {
                                progressBarSell.dismiss();
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.AlertDialogTheme);
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
                                catchArrayList.clear();
                                for (int i = 0; i < data.getFishCatchDisplayList().size(); i++) {
                                    catchArrayList.add(i, data.getFishCatchDisplayList().get(i));
                                }

                                fishSellAdapter = new MyBoatTripFishSellAdapter(getContext(), catchArrayList);
                                lvList.setAdapter(fishSellAdapter);

                                tvAuctioner.setText("" + data.getAuctionerName());
                                tvSell.setText("" + data.getTotalSell() + "/-");

                                progressBarSell.dismiss();

                            }

                        } else {
                            progressBarSell.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.AlertDialogTheme);
                            builder.setCancelable(false);
                            builder.setMessage("No Data Found");
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
                        progressBarSell.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<FishSellList> call, Throwable t) {
                    progressBarSell.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.AlertDialogTheme);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.AlertDialogTheme);
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


    public class MyBoatTripFishSellAdapter extends BaseAdapter implements Filterable {

        private ArrayList<FishCatchDisplayList> originalValues;
        private ArrayList<FishCatchDisplayList> displayedValues;
        LayoutInflater inflater;

        public MyBoatTripFishSellAdapter(Context context, ArrayList<FishCatchDisplayList> catchDisplayLists) {
            this.originalValues = catchDisplayLists;
            this.displayedValues = catchDisplayLists;
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
            TextView tvFishName;
            TextView tvWeight;
            TextView tvQty;
            TextView tvAmount;
            TextView tvLabelQty;
        }

        @Override
        public View getView(final int position, View v, ViewGroup parent) {
            MyBoatTripFishSellAdapter.ViewHolder holder = null;

            if (v == null) {
                v = inflater.inflate(R.layout.custom_fish_sell_layout, null);
                holder = new MyBoatTripFishSellAdapter.ViewHolder();
                holder.tvDate = v.findViewById(R.id.tvUserFishSell_Date1);
                holder.tvFishName = v.findViewById(R.id.tvUserFishSell_Fish1);
                holder.tvWeight = v.findViewById(R.id.tvUserFishSell_Size1);
                holder.tvAmount = v.findViewById(R.id.tvUserFishSell_Amt1);
                holder.tvQty = v.findViewById(R.id.tvUserFishSell_Qty1);
                holder.tvLabelQty = v.findViewById(R.id.tvUserFishSell_LabelQty1);
                v.setTag(holder);
            } else {
                holder = (MyBoatTripFishSellAdapter.ViewHolder) v.getTag();
            }

            Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
            Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");

            holder.tvDate.setTypeface(boldFont);
            holder.tvFishName.setTypeface(boldFont);
            holder.tvAmount.setTypeface(boldFont);
            holder.tvWeight.setTypeface(lightFont);
            holder.tvQty.setTypeface(lightFont);
            holder.tvLabelQty.setTypeface(lightFont);

            Date date1 = new Date(displayedValues.get(position).getEnterDate());
            SimpleDateFormat df2 = new SimpleDateFormat("dd MMM yyyy");
            String dateText = df2.format(date1);
            System.out.println(dateText);

            holder.tvDate.setText(" " + dateText + " ");
            holder.tvFishName.setText("" + displayedValues.get(position).getFishName());
            holder.tvQty.setText("" + displayedValues.get(position).getQuantity());
            holder.tvAmount.setText("" + displayedValues.get(position).getTotal() + "/-");
            holder.tvWeight.setText("" + displayedValues.get(position).getWeight());

            return v;
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    FilterResults results = new FilterResults();
                    ArrayList<FishCatchDisplayList> filteredArrayList = new ArrayList<>();
                    if (originalValues == null) {
                        originalValues = new ArrayList<FishCatchDisplayList>(displayedValues);
                    }

                    if (charSequence == null || charSequence.length() == 0) {
                        results.count = originalValues.size();
                        results.values = originalValues;
                    } else {
                        charSequence = charSequence.toString().toLowerCase();
                        for (int i = 0; i < originalValues.size(); i++) {
                            String name = originalValues.get(i).getFishName();
                            String amount = "" + originalValues.get(i).getTotal();
                            if (name.toLowerCase().startsWith(charSequence.toString()) || amount.toLowerCase().startsWith(charSequence.toString())) {
                                filteredArrayList.add(new FishCatchDisplayList(originalValues.get(i).getCatchId(), originalValues.get(i).getTripId(), originalValues.get(i).getFishId(), originalValues.get(i).getFishName(), originalValues.get(i).getRate(), originalValues.get(i).getQuantity(), originalValues.get(i).getTotal(), originalValues.get(i).getWeight(), originalValues.get(i).getCoId(), originalValues.get(i).getEnterDate(), originalValues.get(i).getEnterBy()));
                            }
                        }
                        results.count = filteredArrayList.size();
                        results.values = filteredArrayList;
                    }

                    return results;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    displayedValues = (ArrayList<FishCatchDisplayList>) filterResults.values;
                    notifyDataSetChanged();
                }
            };

            return filter;
        }
    }
}
