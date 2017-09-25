package com.ats.blucatch.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.blucatch.R;
import com.ats.blucatch.activity.MainActivity;
import com.ats.blucatch.bean.BoatData;
import com.ats.blucatch.bean.ErrorMessage;
import com.ats.blucatch.bean.TripData;
import com.ats.blucatch.bean.TripDisplay;
import com.ats.blucatch.utils.CheckNetwork;
import com.ats.blucatch.utils.InterfaceApi;
import com.ats.blucatch.utils.MySpinnerAdapter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TripMasterFragment extends Fragment {

    private ListView lvTripMaster;
    private TextView tvTripNo, tvStatus, tvBoatName, tvTandelCrew, tvStDate, tvTripDays, tvTripExp, tvTripExpCount, tvFishSell, tvFishSellCount;
    private EditText edSearch;
    private FloatingActionButton fab;
    private ImageView ivPopUp, ivFilter;
    private LinearLayout llFilter;
    private Spinner spBoat;
    boolean flag = false;
    private ProgressDialog progressBar, progressBar1, progressBar3;
    private ArrayAdapter<TripDisplay> adapter;

    private ArrayList<String> boatArray = new ArrayList<>();
    private ArrayList<Long> boatIdArray = new ArrayList<>();
    private ArrayList<TripDisplay> tripDisplayArray = new ArrayList<>();

    private MyAdapter myAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_master, container, false);
        Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
        Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");

        MainActivity.tvTitle.setText("Trip Master");
        MainActivity.tvTitle.setTypeface(boldFont);

        MainActivity.isAtHome = false;
        MainActivity.isAtFishMaster = false;
        MainActivity.isAtAddFish = false;
        MainActivity.isAtEditFish = false;
        MainActivity.isAtBoatMaster = false;
        MainActivity.isAtAddBoat = false;
        MainActivity.isAtEditBoat = false;
        MainActivity.isAtTripDetails = false;
        MainActivity.isAtTripMaster = true;
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

        lvTripMaster = view.findViewById(R.id.lvTripMaster);
        lvTripMaster.setScrollingCacheEnabled(false);

        spBoat = view.findViewById(R.id.spTripMaster_Boat);


        edSearch = view.findViewById(R.id.edTripMaster_search);
        edSearch.setTypeface(lightFont);

        fab = view.findViewById(R.id.fabTripMaster);
        ivPopUp = view.findViewById(R.id.ivTripMasterPopUp);

        ivFilter = view.findViewById(R.id.ivTripMaster_Filter);
        llFilter = view.findViewById(R.id.llTripMasterFilter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new AddTripFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }
        });

        ivFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == false) {
                    flag = true;
                    llFilter.setVisibility(View.VISIBLE);
                } else {
                    flag = false;
                    llFilter.setVisibility(View.GONE);
                }
            }
        });


      /*  tvTripExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new TripExpenseViewFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }
        });
        tvTripExpCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new TripExpenseViewFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }
        });

        tvFishSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new TripFishSellViewFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }
        });

        tvFishSellCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new TripFishSellViewFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }
        });*/

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

        getSpinnerDataforBoat();
        getAllTripData(0);
        spinnerListener();

        return view;
    }


    public static void setForceShowIcon(PopupMenu popupMenu) {
        try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void getAllTripData(final long boatId) {
        if (CheckNetwork.isInternetAvailable(getContext())) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            InterfaceApi api = retrofit.create(InterfaceApi.class);
            Call<TripData> tripDataCall = api.allTripData();

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
                    if (response.body() != null) {

                        TripData data = response.body();
                        if (data.getErrorMessage().getError()) {
                            progressBar.dismiss();
                            Toast.makeText(getContext(), "Unable to fetch data!", Toast.LENGTH_SHORT).show();
                            Log.e("ON RESPONSE : ", "ERROR : " + data.getErrorMessage().getMessage());
                        } else {
                            tripDisplayArray.clear();
                            if (boatId == 0) {
                                for (int i = 0; i < data.getTripDisplay().size(); i++) {
                                    tripDisplayArray.add(i, data.getTripDisplay().get(i));
                                }
                            } else {
                                for (int i = 0, j = 0; i < data.getTripDisplay().size(); i++) {
                                    if (data.getTripDisplay().get(i).getBoatId() == boatId) {
                                        tripDisplayArray.add(j, data.getTripDisplay().get(i));
                                        j++;
                                    }
                                }
                            }
                            Log.e("ON RESPONSE : ", " DATA : " + tripDisplayArray);
                            myAdapter = new MyAdapter(getContext(), tripDisplayArray);
                            lvTripMaster.setAdapter(myAdapter);
                            progressBar.dismiss();
                        }

                    } else {
                        progressBar.dismiss();
                        Toast.makeText(getContext(), "Unable to fetch data!", Toast.LENGTH_SHORT).show();
                        Log.e("ON RESPONSE : ", "NO DATA");
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


    public static long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }


    public void getSpinnerDataforBoat() {
        if (CheckNetwork.isInternetAvailable(getContext())) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            InterfaceApi api = retrofit.create(InterfaceApi.class);
            Call<BoatData> boatDataCall = api.allBoatData();

            progressBar1 = new ProgressDialog(getContext());
            progressBar1.setCancelable(false);
            progressBar1.setMessage("please wait....");
            progressBar1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar1.setProgress(0);
            progressBar1.setMax(100);
            progressBar1.show();

            boatDataCall.enqueue(new Callback<BoatData>() {
                @Override
                public void onResponse(Call<BoatData> call, Response<BoatData> response) {

                    if (response.body() != null) {
                        BoatData data = response.body();
                        if (data.getErrorMessage().getError()) {
                            progressBar1.dismiss();
                            Log.e("RESPONSE : ", " ERROR : " + data.getErrorMessage().getMessage());
                        } else {
                            boatArray.clear();
                            boatIdArray.clear();
                            boatArray.add(0, "All");
                            boatIdArray.add(0, 0l);
                            for (int i = 0; i < data.getBoatDisp().size(); i++) {
                                boatArray.add((i + 1), data.getBoatDisp().get(i).getBoatName());
                                boatIdArray.add((i + 1), data.getBoatDisp().get(i).getBoatId());
                            }

                            Log.e("RESPONSE : ", " DATA : " + data.getBoatDisp());
                            MySpinnerAdapter spAdapterOwner = new MySpinnerAdapter(
                                    getContext(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    boatArray);
                            spBoat.setAdapter(spAdapterOwner);


                            progressBar1.dismiss();
                        }

                    } else {
                        progressBar1.dismiss();
                        Log.e("RESPONSE : ", " NO DATA");
                    }
                }

                @Override
                public void onFailure(Call<BoatData> call, Throwable t) {
                    progressBar1.dismiss();
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

    public void spinnerListener() {
        spBoat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getAllTripData(boatIdArray.get(i));
                Log.e("BOAT ID : ", " : " + boatIdArray.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void deleteTripData(long id) {
        if (CheckNetwork.isInternetAvailable(getContext())) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            final InterfaceApi api = retrofit.create(InterfaceApi.class);

            Call<ErrorMessage> errorMessageCall = api.deleteTrip(id);

            progressBar3 = new ProgressDialog(getContext());
            progressBar3.setCancelable(false);
            progressBar3.setMessage("please wait....");
            progressBar3.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar3.setProgress(0);
            progressBar3.setMax(100);
            progressBar3.show();

            errorMessageCall.enqueue(new Callback<ErrorMessage>() {
                @Override
                public void onResponse(Call<ErrorMessage> call, Response<ErrorMessage> response) {
                    if (response.body() != null) {
                        ErrorMessage data = response.body();
                        if (data.getError()) {
                            progressBar3.dismiss();
                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
                            builder.setTitle("Error");
                            builder.setCancelable(false);
                            builder.setMessage("" + data.getMessage());
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            android.app.AlertDialog dialog = builder.create();
                            dialog.show();
                            //Toast.makeText(getContext(), "unable to delete trip!", Toast.LENGTH_SHORT).show();
                            Log.e("ON RESPONSE : ", "ERROR : " + data.getMessage());

                        } else {
                            progressBar3.dismiss();
                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
                            builder.setTitle("Success");
                            builder.setCancelable(false);
                            builder.setMessage("Trip deleted successfully.");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    clearArrayList();
                                    getAllTripData(0);
                                    myAdapter.notifyDataSetChanged();
                                    edSearch.setText("");
                                }
                            });
                            android.app.AlertDialog dialog = builder.create();
                            dialog.show();
                        }

                    } else {
                        progressBar3.dismiss();
                        Toast.makeText(getContext(), "Unable to delete trip!", Toast.LENGTH_SHORT).show();
                        Log.e("ON RESPONSE : ", "NO DATA");
                    }
                }

                @Override
                public void onFailure(Call<ErrorMessage> call, Throwable t) {
                    progressBar3.dismiss();
                    Toast.makeText(getContext(), "Unable to delete trip! server error", Toast.LENGTH_SHORT).show();
                    Log.e("ON FAILURE : ", "ERROR : " + t.getMessage());
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

    private void clearArrayList() {
        tripDisplayArray.clear();
    }


    public class MyAdapter extends BaseAdapter implements Filterable {

        private ArrayList<TripDisplay> originalValues;
        private ArrayList<TripDisplay> displayedValues;
        LayoutInflater inflater;

        public MyAdapter(Context context, ArrayList<TripDisplay> tripDispArrayList) {
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
            ViewHolder holder = null;

            if (v == null) {
                v = inflater.inflate(R.layout.custom_trip_master, null);
                holder = new ViewHolder();
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
                holder = (ViewHolder) v.getTag();
            }

          /*  v = inflater.inflate(R.layout.custom_trip_master, null);

            TextView tvTripNo = v.findViewById(R.id.tvTripMaster_TripNo);
            TextView tvStatus = v.findViewById(R.id.tvTripMaster_Status);
            TextView tvBoatName = v.findViewById(R.id.tvTripMaster_BoatName);
            TextView tvTandelCrew = v.findViewById(R.id.tvTripMaster_TandelCrew);
            TextView tvStDate = v.findViewById(R.id.tvTripMaster_StDate);
            TextView tvTripDays = v.findViewById(R.id.tvTripMaster_TripDays);
            TextView tvTripExp = v.findViewById(R.id.tvTripMaster_TripExpenses);
            TextView tvTripExpCount = v.findViewById(R.id.tvTripMaster_TripExpCount);
            TextView tvFishSell = v.findViewById(R.id.tvTripMaster_FishSell);
            TextView tvFishSellCount = v.findViewById(R.id.tvTripMaster_FishSellCount);

            ImageView ivpopup = v.findViewById(R.id.ivTripMasterPopUp);
*/
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

           /* tvTripNo.setTypeface(lightFont);
            tvStatus.setTypeface(boldFont);
            tvBoatName.setTypeface(lightFont);
            tvTandelCrew.setTypeface(lightFont);
            tvStDate.setTypeface(lightFont);
            tvTripDays.setTypeface(lightFont);
            tvTripExp.setTypeface(boldFont);
            tvTripExpCount.setTypeface(lightFont);
            tvFishSell.setTypeface(boldFont);
            tvFishSellCount.setTypeface(lightFont);*/

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


                                   /* Fragment fragment = new EditTripFragment();
                                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                    ft.replace(R.id.content_frame, fragment);
                                    ft.commit();*/
                            } else if (menuItem.getItemId() == R.id.item_trip_master_delete) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle("Confirm Action");
                                builder.setMessage("Do you really want to delete trip?");
                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        deleteTripData(displayedValues.get(position).getTripId());
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
                    setForceShowIcon(popupMenu);
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
                                filteredArrayList.add(new TripDisplay(originalValues.get(i).getTripId(), originalValues.get(i).getBoatId(), originalValues.get(i).getTripStartDate(), originalValues.get(i).getTripEndDate(), originalValues.get(i).getTripTandelId(), originalValues.get(i).getTripAuctionerId(), originalValues.get(i).getTripStaff(), originalValues.get(i).getTripSettled(), originalValues.get(i).getTripStatus(), originalValues.get(i).getTripDelete(), originalValues.get(i).getBoatName(), originalValues.get(i).getTandelName(), originalValues.get(i).getAuctionerName(), originalValues.get(i).getCoId(), originalValues.get(i).getEnterDate(), originalValues.get(i).getEnterBy()));
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



