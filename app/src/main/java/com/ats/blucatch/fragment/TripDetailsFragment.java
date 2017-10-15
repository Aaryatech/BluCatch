package com.ats.blucatch.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.blucatch.R;
import com.ats.blucatch.activity.MainActivity;
import com.ats.blucatch.bean.TripData;
import com.ats.blucatch.bean.TripDisplay;
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

public class TripDetailsFragment extends Fragment {

    private EditText edSearch;
    static long boatId;
    static String boatName;
    private ProgressDialog progressBar;
    private ArrayList<TripDisplay> tripDisplayArray = new ArrayList<>();
    MyTripDetailsAdapter myAdapter;
    private ListView lvList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_details, container, false);
        Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
        Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");

        try {
            boatId = getArguments().getLong("Boat_Id");
            boatName = getArguments().getString("Boat_Name");
        } catch (Exception e) {
            e.printStackTrace();
        }

        MainActivity.tvTitle.setText("Trip Details- " + boatName);
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


        lvList = view.findViewById(R.id.lvBoatTripDetails_List);
        edSearch = view.findViewById(R.id.edTripDetails_Search);
        edSearch.setTypeface(lightFont);

        getAllTripDataByBoat(boatId);

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

    public void getAllTripDataByBoat(long id) {
        if (CheckNetwork.isInternetAvailable(getContext())) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            InterfaceApi api = retrofit.create(InterfaceApi.class);
            Call<TripData> tripDataCall = api.allTripDataByBoat(id);

            progressBar = new ProgressDialog(getContext());
            progressBar.setCancelable(false);
            progressBar.setMessage("please wait....");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.setProgress(0);
            progressBar.setMax(100);
            progressBar.show();

            tripDataCall.enqueue(new Callback<TripData>() {
                @Override
                public void onResponse(Call<TripData> call, Response<TripData> response) {
                    try {
                        if (response.body() != null) {

                            TripData data = response.body();
                            if (data.getErrorMessage().getError()) {
                                progressBar.dismiss();
                                // Toast.makeText(getContext(), "Unable to fetch data!", Toast.LENGTH_SHORT).show();
                                Log.e("ON RESPONSE : ", "ERROR : " + data.getErrorMessage().getMessage());
                            } else {
                                tripDisplayArray.clear();
                                for (int i = 0; i < data.getTripDisplay().size(); i++) {
                                    tripDisplayArray.add(i, data.getTripDisplay().get(i));
                                }
                                Log.e("ON RESPONSE : ", " DATA : " + tripDisplayArray);
                                myAdapter = new MyTripDetailsAdapter(getContext(), tripDisplayArray);
                                lvList.setAdapter(myAdapter);
                                progressBar.dismiss();
                            }
                        } else {
                            progressBar.dismiss();
                            Toast.makeText(getContext(), "Unable to fetch data!", Toast.LENGTH_SHORT).show();
                            Log.e("ON RESPONSE : ", "NO DATA");
                        }
                    } catch (Exception e) {
                        progressBar.dismiss();
                        Log.e("Exception : ", "" + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<TripData> call, Throwable t) {
                    progressBar.dismiss();
                    Toast.makeText(getContext(), "Unable to fetch data! server error", Toast.LENGTH_SHORT).show();
                    Log.e("ON Failure : ", "ERROR : " + t.getMessage());
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


    public class MyTripDetailsAdapter extends BaseAdapter implements Filterable {

        private ArrayList<TripDisplay> originalValues;
        private ArrayList<TripDisplay> displayedValues;
        LayoutInflater inflater;

        public MyTripDetailsAdapter(Context context, ArrayList<TripDisplay> tripDispArrayList) {
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
            TextView tvTripNo;
            TextView tvStatus;
            TextView tvBoatName;
            TextView tvTandelCrew;
            TextView tvStDate;
            TextView tvTripDays;
            TextView tvTripExp;
            TextView tvTripExpCount;
            TextView tvFishSell;
            TextView tvFishSellCount;

            ImageView ivpopup;
        }

        @Override
        public View getView(final int position, View v, ViewGroup parent) {
            MyTripDetailsAdapter.ViewHolder holder = null;

            if (v == null) {
                v = inflater.inflate(R.layout.custom_trip_master, null);
                holder = new MyTripDetailsAdapter.ViewHolder();
                holder.tvTripNo = v.findViewById(R.id.tvTripMaster_TripNo);
                holder.tvStatus = v.findViewById(R.id.tvTripMaster_Status);
                holder.tvBoatName = v.findViewById(R.id.tvTripMaster_BoatName);
                holder.tvTandelCrew = v.findViewById(R.id.tvTripMaster_TandelCrew);
                holder.tvStDate = v.findViewById(R.id.tvTripMaster_StDate);
                holder.tvTripDays = v.findViewById(R.id.tvTripMaster_TripDays);
                holder.tvTripExp = v.findViewById(R.id.tvTripMaster_TripExpenses);
                holder.tvTripExpCount = v.findViewById(R.id.tvTripMaster_TripExpCount);
                holder.tvFishSell = v.findViewById(R.id.tvTripMaster_FishSell);
                holder.tvFishSellCount = v.findViewById(R.id.tvTripMaster_FishSellCount);
                holder.ivpopup = v.findViewById(R.id.ivTripMasterPopUp);
                v.setTag(holder);
            } else {
                holder = (MyTripDetailsAdapter.ViewHolder) v.getTag();
            }


            Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
            Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");

            holder.tvTripNo.setTypeface(lightFont);
            holder.tvStatus.setTypeface(boldFont);
            holder.tvBoatName.setTypeface(lightFont);
            holder.tvTandelCrew.setTypeface(lightFont);
            holder.tvStDate.setTypeface(lightFont);
            holder.tvTripDays.setTypeface(lightFont);
            holder.tvTripExp.setTypeface(boldFont);
            holder.tvTripExpCount.setTypeface(lightFont);
            holder.tvFishSell.setTypeface(boldFont);
            holder.tvFishSellCount.setTypeface(lightFont);


            Date date1 = new Date(displayedValues.get(position).getTripStartDate());
            SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
            String dateText = df2.format(date1);
            System.out.println(dateText);

            Date date2 = new Date(displayedValues.get(position).getTripEndDate());

            holder.tvTripNo.setText("Trip: " + displayedValues.get(position).getTripId());
            holder.tvStatus.setText("" + displayedValues.get(position).getTripStatus());
            holder.tvBoatName.setText("" + displayedValues.get(position).getBoatName());

            String[] crewIdStringArray = displayedValues.get(position).getTripStaff().split(",");

            holder.tvTandelCrew.setText("" + displayedValues.get(position).getTandelName() + " + " + crewIdStringArray.length);
            holder.tvStDate.setText("" + dateText);
            holder.tvTripDays.setText("");
            holder.tvTripExpCount.setText("" + displayedValues.get(position).getExpenseCount() + "/-");
            holder.tvFishSellCount.setText("" + displayedValues.get(position).getFishSellCount() + "/-");

            holder.tvTripExp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment adf = new BoatTripExpensesViewFragment();
                    Bundle args = new Bundle();
                    args.putInt("Season_Id", displayedValues.get(position).getSeasonId());
                    args.putString("Boat_Name", displayedValues.get(position).getBoatName());
                    args.putLong("Trip_ID", displayedValues.get(position).getTripId());
                    args.putLong("Boat_ID", displayedValues.get(position).getBoatId());
                    adf.setArguments(args);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf).commit();

                }
            });

            holder.tvTripExpCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment adf = new BoatTripExpensesViewFragment();
                    Bundle args = new Bundle();
                    args.putInt("Season_Id", displayedValues.get(position).getSeasonId());
                    args.putString("Boat_Name", displayedValues.get(position).getBoatName());
                    args.putLong("Trip_ID", displayedValues.get(position).getTripId());
                    args.putLong("Boat_ID", displayedValues.get(position).getBoatId());
                    adf.setArguments(args);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf).commit();
                }
            });

            holder.tvFishSell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment adf = new BoatFishSellViewFragment();
                    Bundle args = new Bundle();
                    args.putInt("Season_Id", displayedValues.get(position).getSeasonId());
                    args.putLong("Boat_Id", displayedValues.get(position).getBoatId());
                    args.putLong("Trip_Id", displayedValues.get(position).getTripId());
                    args.putString("Auctioner_Name", displayedValues.get(position).getAuctionerName());
                    args.putString("Boat_Name", displayedValues.get(position).getBoatName());
                    adf.setArguments(args);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf).commit();

                }
            });

            holder.tvFishSellCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment adf = new BoatFishSellViewFragment();
                    Bundle args = new Bundle();
                    args.putInt("Season_Id", displayedValues.get(position).getSeasonId());
                    args.putLong("Boat_Id", displayedValues.get(position).getBoatId());
                    args.putLong("Trip_Id", displayedValues.get(position).getTripId());
                    args.putString("Auctioner_Name", displayedValues.get(position).getAuctionerName());
                    args.putString("Boat_Name", displayedValues.get(position).getBoatName());
                    adf.setArguments(args);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf).commit();

                }
            });


            holder.ivpopup.setVisibility(View.GONE);

            holder.ivpopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(getContext(), view);
                    popupMenu.getMenuInflater().inflate(R.menu.popup_trip_master, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            if (menuItem.getItemId() == R.id.item_trip_master_edit) {

                                Fragment adf = new EditTripFragment();
                                Bundle args = new Bundle();
                                args.putString("Trip_Boat_Name", displayedValues.get(position).getBoatName());
                                args.putString("Trip_Tandel_Name", displayedValues.get(position).getTandelName());
                                args.putString("Trip_Auctioner_Name", displayedValues.get(position).getAuctionerName());
                                args.putLong("Trip_Id", displayedValues.get(position).getTripId());
                                args.putLong("Trip_Start_Date", displayedValues.get(position).getTripStartDate());
                                args.putLong("Trip_End_Date", displayedValues.get(position).getTripEndDate());
                                args.putString("Trip_Staff_Count", displayedValues.get(position).getTripStaff());
                                args.putString("Trip_Status", displayedValues.get(position).getTripStatus());
                                adf.setArguments(args);
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf).commit();

                            } else if (menuItem.getItemId() == R.id.item_trip_master_delete) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                                builder.setTitle("Confirm Action");
                                builder.setMessage("Do you really want to delete trip?");
                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // deleteTripData(displayedValues.get(position).getTripId());
                                    }
                                });
                                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                                //Toast.makeText(getContext(), "deleted", Toast.LENGTH_SHORT).show();
                            }
                            return true;
                        }
                    });
                    //  setForceShowIcon(popupMenu);
                    popupMenu.show();
                }
            });
            return v;
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    FilterResults results = new FilterResults();
                    ArrayList<TripDisplay> filteredArrayList = new ArrayList<>();
                    if (originalValues == null) {
                        originalValues = new ArrayList<TripDisplay>(displayedValues);
                    }

                    if (charSequence == null || charSequence.length() == 0) {
                        results.count = originalValues.size();
                        results.values = originalValues;
                    } else {
                        charSequence = charSequence.toString().toLowerCase();
                        for (int i = 0; i < originalValues.size(); i++) {
                            String tripNo = "" + originalValues.get(i).getTripId();
                            String status = originalValues.get(i).getTripStatus();
                            String boatName = originalValues.get(i).getBoatName();
                            String tandelName = originalValues.get(i).getTandelName();
                            String auctionerName = originalValues.get(i).getAuctionerName();
                            if (tripNo.toLowerCase().startsWith(charSequence.toString()) || status.toLowerCase().startsWith(charSequence.toString()) || boatName.toLowerCase().startsWith(charSequence.toString()) || tandelName.toLowerCase().startsWith(charSequence.toString()) || auctionerName.toLowerCase().startsWith(charSequence.toString())) {
                                filteredArrayList.add(new TripDisplay(originalValues.get(i).getTripId(), originalValues.get(i).getBoatId(), originalValues.get(i).getTripStartDate(), originalValues.get(i).getTripEndDate(), originalValues.get(i).getTripTandelId(), originalValues.get(i).getTripAuctionerId(), originalValues.get(i).getTripStaff(), originalValues.get(i).getTripSettled(), originalValues.get(i).getTripStatus(), originalValues.get(i).getTripDelete(), originalValues.get(i).getBoatName(), originalValues.get(i).getTandelName(), originalValues.get(i).getAuctionerName(), originalValues.get(i).getCoId(), originalValues.get(i).getEnterDate(), originalValues.get(i).getEnterBy(), originalValues.get(i).getSeasonId(), originalValues.get(i).getExpenseCount(), originalValues.get(i).getFishSellCount()));
                            }
                        }
                        results.count = filteredArrayList.size();
                        results.values = filteredArrayList;
                    }

                    return results;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    displayedValues = (ArrayList<TripDisplay>) filterResults.values;
                    notifyDataSetChanged();
                }
            };

            return filter;
        }
    }
}
