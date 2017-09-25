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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.blucatch.R;
import com.ats.blucatch.activity.MainActivity;
import com.ats.blucatch.bean.BoatData;
import com.ats.blucatch.bean.BoatDisp;
import com.ats.blucatch.bean.ErrorMessage;
import com.ats.blucatch.utils.CheckNetwork;
import com.ats.blucatch.utils.InterfaceApi;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BoatMasterFragment extends Fragment {

    private EditText edSearch;
    private TextView tvBoatName1, tvLabelTandel1, tvLabelAuctioner1, tvTandel1, tvAuctioner, tvStatus1;
    private ImageView ivPopup1;
    private FloatingActionButton fab;
    private LinearLayout llBoat1;
    private ProgressDialog progressBar;
    private ListView lvBoatMaster;
    private ArrayAdapter<BoatDisp> adapter;
    private MyAdapter myAdapter;

    private ArrayList<Integer> boatIdArray = new ArrayList<>();
    private ArrayList<String> boatNameArray = new ArrayList<>();
    private ArrayList<Integer> ownerIdArray = new ArrayList<>();
    private ArrayList<String> regNoArray = new ArrayList<>();
    private ArrayList<Long> purchaseDateArray = new ArrayList<>();
    private ArrayList<Integer> lengthArray = new ArrayList<>();
    private ArrayList<Integer> breadthArray = new ArrayList<>();
    private ArrayList<Integer> heightArray = new ArrayList<>();
    private ArrayList<String> fishingTypeArray = new ArrayList<>();
    private ArrayList<String> licNoArray = new ArrayList<>();
    private ArrayList<Long> licFromDateArray = new ArrayList<>();
    private ArrayList<Long> licToDateArray = new ArrayList<>();
    private ArrayList<Long> incFromDateArray = new ArrayList<>();
    private ArrayList<Long> incToDateArray = new ArrayList<>();
    private ArrayList<String> engineTypeArray = new ArrayList<>();
    private ArrayList<Integer> hpRatingArray = new ArrayList<>();
    private ArrayList<Integer> isUsedArray = new ArrayList<>();
    private ArrayList<Integer> tandelIdArray = new ArrayList<>();
    private ArrayList<Integer> auctionerIdArray = new ArrayList<>();
    private ArrayList<String> boatStatusArray = new ArrayList<>();
    private ArrayList<Integer> blockStatusArray = new ArrayList<>();
    private ArrayList<String> ownerNameArray = new ArrayList<>();
    private ArrayList<String> tandelNameArray = new ArrayList<>();
    private ArrayList<String> auctionerNameArray = new ArrayList<>();

    private ArrayList<BoatDisp> boatDispArray = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_boat_master, container, false);
        Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
        Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");

        MainActivity.tvTitle.setText("Boat Master");
        MainActivity.tvTitle.setTypeface(boldFont);

        MainActivity.isAtHome = false;
        MainActivity.isAtFishMaster = false;
        MainActivity.isAtAddFish = false;
        MainActivity.isAtEditFish = false;
        MainActivity.isAtBoatMaster = true;
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
        MainActivity.isAtAccDetails = false;
        MainActivity.isAtUserMaster = false;
        MainActivity.isAtAddUser = false;
        MainActivity.isAtExpMaster = false;
        MainActivity.isAtAddExp = false;
        MainActivity.isAtChangePass = false;
        MainActivity.isAtHomeTripExp = false;
        MainActivity.isAtHomeFishSell = false;

        fab = view.findViewById(R.id.fabBoatMaster);
        lvBoatMaster = view.findViewById(R.id.lvBoatMaster);

        edSearch = view.findViewById(R.id.edBoatMaster_Search);
        edSearch.setTypeface(lightFont);

        getAllBoatData();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new AddBoatFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
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


    public void getAllBoatData() {
        if (CheckNetwork.isInternetAvailable(getContext())) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            InterfaceApi api = retrofit.create(InterfaceApi.class);
            Call<BoatData> boatDataCall = api.allBoatData();

            progressBar = new ProgressDialog(getContext());
            progressBar.setCancelable(false);
            progressBar.setMessage("please wait....");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.setProgress(0);
            progressBar.setMax(100);
            progressBar.show();

            boatDataCall.enqueue(new Callback<BoatData>() {
                @Override
                public void onResponse(Call<BoatData> call, Response<BoatData> response) {
                    if (response.body() == null) {
                        progressBar.dismiss();
                        Log.e("On Response : ", " NO DATA");
                        Toast.makeText(getContext(), "unable to fetch data!", Toast.LENGTH_SHORT).show();
                    } else {
                        BoatData data = response.body();
                        if (data.getErrorMessage().getError()) {
                            progressBar.dismiss();
                            Log.e("On Response : ", " ERROR : " + data.getErrorMessage().getMessage());
                            Toast.makeText(getContext(), "unable to fetch data!", Toast.LENGTH_SHORT).show();
                        } else {

                            for (int i = 0; i < data.getBoatDisp().size(); i++) {
                                boatDispArray.add(i, data.getBoatDisp().get(i));
                            }
                            Log.e("ON RESPONSE : ", "DATA : " + boatDispArray);
                            myAdapter = new MyAdapter(getContext(), boatDispArray);
                            lvBoatMaster.setAdapter(myAdapter);
                            progressBar.dismiss();
                        }


                    }
                }

                @Override
                public void onFailure(Call<BoatData> call, Throwable t) {
                    Log.e("ON Failure : ", " ERROR : " + t.getMessage());
                    Toast.makeText(getContext(), "unable to fetch data!", Toast.LENGTH_SHORT).show();
                    progressBar.dismiss();
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


    private void blockUnblockBoatData(long boatId) {
        if (CheckNetwork.isInternetAvailable(getContext())) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            final InterfaceApi api = retrofit.create(InterfaceApi.class);

            Call<ErrorMessage> errorMessageCall = api.blockUnblockBoat(boatId);

            progressBar = new ProgressDialog(getContext());
            progressBar.setCancelable(false);
            progressBar.setMessage("please wait....");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.setProgress(0);
            progressBar.setMax(100);
            progressBar.show();

            errorMessageCall.enqueue(new Callback<ErrorMessage>() {
                @Override
                public void onResponse(Call<ErrorMessage> call, Response<ErrorMessage> response) {
                    if (response.body() != null) {
                        ErrorMessage data = response.body();
                        if (data.getError()) {
                            progressBar.dismiss();
                            //Toast.makeText(getContext(), "Unable to block / unblock boat!", Toast.LENGTH_SHORT).show();
                            Log.e("ON RESPONSE : ", "ERROR : " + data.getMessage());
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

                        } else {
                            progressBar.dismiss();
                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
                            builder.setTitle("Success");
                            builder.setCancelable(false);
                            builder.setMessage("" + data.getMessage());
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    clearArrayList();
                                    getAllBoatData();
                                    myAdapter.notifyDataSetChanged();
                                    edSearch.setText("");
                                }
                            });
                            android.app.AlertDialog dialog = builder.create();
                            dialog.show();
                        }

                    } else {
                        progressBar.dismiss();
                        Toast.makeText(getContext(), "Unable to block / unblock boat!", Toast.LENGTH_SHORT).show();
                        Log.e("ON RESPONSE : ", "NO DATA");
                    }
                }

                @Override
                public void onFailure(Call<ErrorMessage> call, Throwable t) {
                    progressBar.dismiss();
                    Toast.makeText(getContext(), "Unable to block / unblock boat!", Toast.LENGTH_SHORT).show();
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


    public void deleteBoatData(long id) {
        if (CheckNetwork.isInternetAvailable(getContext())) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            final InterfaceApi api = retrofit.create(InterfaceApi.class);

            Call<ErrorMessage> errorMessageCall = api.deleteBoat(id);

            progressBar = new ProgressDialog(getContext());
            progressBar.setCancelable(false);
            progressBar.setMessage("please wait....");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.setProgress(0);
            progressBar.setMax(100);
            progressBar.show();

            errorMessageCall.enqueue(new Callback<ErrorMessage>() {
                @Override
                public void onResponse(Call<ErrorMessage> call, Response<ErrorMessage> response) {
                    if (response.body() != null) {
                        ErrorMessage data = response.body();
                        if (data.getError()) {
                            progressBar.dismiss();
                            Toast.makeText(getContext(), "Unable to delete boat!", Toast.LENGTH_SHORT).show();
                            Log.e("ON RESPONSE : ", "ERROR : " + data.getMessage());

                        } else {
                            progressBar.dismiss();
                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
                            builder.setTitle("Success");
                            builder.setCancelable(false);
                            builder.setMessage("Boat deleted successfully.");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    clearArrayList();
                                    getAllBoatData();
                                    myAdapter.notifyDataSetChanged();
                                    edSearch.setText("");
                                }
                            });
                            android.app.AlertDialog dialog = builder.create();
                            dialog.show();
                        }

                    } else {
                        progressBar.dismiss();
                        Toast.makeText(getContext(), "Unable to delete boat!", Toast.LENGTH_SHORT).show();
                        Log.e("ON RESPONSE : ", "NO DATA");
                    }
                }

                @Override
                public void onFailure(Call<ErrorMessage> call, Throwable t) {
                    progressBar.dismiss();
                    Toast.makeText(getContext(), "Unable to delete boat!", Toast.LENGTH_SHORT).show();
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
        boatDispArray.clear();
    }


    public class MyAdapter extends BaseAdapter implements Filterable {

        private ArrayList<BoatDisp> originalValues;
        private ArrayList<BoatDisp> displayedValues;
        LayoutInflater inflater;

        public MyAdapter(Context context, ArrayList<BoatDisp> boatArrayList) {
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
            v = inflater.inflate(R.layout.custom_boat_master, null);

            LinearLayout llBoat = v.findViewById(R.id.llBoatMaster_Item);
            TextView tvBoatStatus = v.findViewById(R.id.tvBoatMaster_status1);
            TextView tvBoatName = v.findViewById(R.id.tvBoatMaster_BoatName1);
            TextView tvTandel = v.findViewById(R.id.tvBoatMaster_LabelTandel1);
            TextView tvAuctioner = v.findViewById(R.id.tvBoatMaster_LabelAuctioner1);
            TextView tvLabelTandel = v.findViewById(R.id.tvBoatMaster_TandelName1);
            TextView tvLabelAuctioner = v.findViewById(R.id.tvBoatMaster_Auctioner1);
            TextView tvBlockStatus = v.findViewById(R.id.tvBoatMaster_BlockStatus);
            ImageView ivpopup = v.findViewById(R.id.ivBoatMaster_PopUp1);

            Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
            Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");

            tvBoatStatus.setTypeface(boldFont);
            tvBoatName.setTypeface(boldFont);
            tvTandel.setTypeface(boldFont);
            tvAuctioner.setTypeface(boldFont);
            tvLabelTandel.setTypeface(lightFont);
            tvLabelAuctioner.setTypeface(lightFont);
            tvBlockStatus.setTypeface(boldFont);

            tvBoatStatus.setText(" " + displayedValues.get(position).getBoatStatus() + " ");
            tvBoatName.setText(displayedValues.get(position).getBoatName());
            tvTandel.setText(displayedValues.get(position).getTandelName());
            tvAuctioner.setText(displayedValues.get(position).getAuctionerName());
            if (displayedValues.get(position).getBlockStatus() == 0) {
                tvBlockStatus.setText("");
            } else if (displayedValues.get(position).getBlockStatus() == 1) {
                tvBlockStatus.setText("Blocked");
            } else {
                tvBlockStatus.setText("");
            }

            llBoat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment adf = new ViewBoatDetailsFragment();
                    Bundle args = new Bundle();
                    args.putLong("Boat_Id", displayedValues.get(position).getBoatId());
                    args.putString("Boat_Name", displayedValues.get(position).getBoatName());
                    args.putString("Boat_Owner", displayedValues.get(position).getOwnerName());
                    args.putString("Boat_Tandel", displayedValues.get(position).getTandelName());
                    args.putString("Boat_Auctioner", displayedValues.get(position).getAuctionerName());
                    args.putString("Boat_RegNo", displayedValues.get(position).getRegNo());
                    args.putLong("Boat_Purchase_Date", displayedValues.get(position).getBoatPurchaseDate());
                    args.putInt("Boat_Length", displayedValues.get(position).getBoatLenght());
                    args.putInt("Boat_Breadth", displayedValues.get(position).getBoatBreadth());
                    args.putInt("Boat_Height", displayedValues.get(position).getBoatHeight());
                    args.putString("Boat_Fishing_type", displayedValues.get(position).getFishingType());
                    args.putString("Boat_LicNo", displayedValues.get(position).getBoatLicNo());
                    args.putLong("Boat_LicFrom", displayedValues.get(position).getBoatLicFromDt());
                    args.putLong("Boat_LicTo", displayedValues.get(position).getBoatLicToDt());
                    args.putLong("Boat_IncFrom", displayedValues.get(position).getBoatInsuranceFromDt());
                    args.putLong("Boat_IncTo", displayedValues.get(position).getBoatInsuranceToDt());
                    args.putString("Boat_Engine_Type", displayedValues.get(position).getBoatEngineType());
                    args.putInt("Boat_HP_Rating", displayedValues.get(position).getBoatHPRating());
                    args.putString("Boat_Status", displayedValues.get(position).getBoatStatus());

                    adf.setArguments(args);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf).commit();

                }
            });

            ivpopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(getContext(), view);
                    popupMenu.getMenuInflater().inflate(R.menu.popup_boat_master, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            if (menuItem.getItemId() == R.id.item_boat_edit) {
                                Fragment adf = new EditBoatFragment();
                                Bundle args = new Bundle();
                                args.putLong("Boat_Id", displayedValues.get(position).getBoatId());
                                args.putString("Boat_Name", displayedValues.get(position).getBoatName());
                                args.putString("Boat_Owner", displayedValues.get(position).getOwnerName());
                                args.putString("Boat_Tandel", displayedValues.get(position).getTandelName());
                                args.putString("Boat_Auctioner", displayedValues.get(position).getAuctionerName());
                                args.putString("Boat_RegNo", displayedValues.get(position).getRegNo());
                                args.putLong("Boat_Purchase_Date", displayedValues.get(position).getBoatPurchaseDate());
                                args.putInt("Boat_Length", displayedValues.get(position).getBoatLenght());
                                args.putInt("Boat_Breadth", displayedValues.get(position).getBoatBreadth());
                                args.putInt("Boat_Height", displayedValues.get(position).getBoatHeight());
                                args.putString("Boat_Fishing_type", displayedValues.get(position).getFishingType());
                                args.putString("Boat_LicNo", displayedValues.get(position).getBoatLicNo());
                                args.putLong("Boat_LicFrom", displayedValues.get(position).getBoatLicFromDt());
                                args.putLong("Boat_LicTo", displayedValues.get(position).getBoatLicToDt());
                                args.putLong("Boat_IncFrom", displayedValues.get(position).getBoatInsuranceFromDt());
                                args.putLong("Boat_IncTo", displayedValues.get(position).getBoatInsuranceToDt());
                                args.putString("Boat_Engine_Type", displayedValues.get(position).getBoatEngineType());
                                args.putInt("Boat_HP_Rating", displayedValues.get(position).getBoatHPRating());
                                args.putString("Boat_Status", displayedValues.get(position).getBoatStatus());

                                adf.setArguments(args);
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf).commit();

                            } else if (menuItem.getItemId() == R.id.item_boat_block_unblock) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle("Confirm Action");
                                if (displayedValues.get(position).getBlockStatus() == 0) {
                                    builder.setMessage("Do you really want to block boat?");
                                } else if (displayedValues.get(position).getBlockStatus() == 1) {
                                    builder.setMessage("Do you really want to unblock boat?");
                                } else {
                                    builder.setMessage("Do you really want to block / unblock boat?");
                                }
                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        blockUnblockBoatData(displayedValues.get(position).getBoatId());
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
                                //Toast.makeText(getContext(), "Block / Unblock", Toast.LENGTH_SHORT).show();
                            } else if (menuItem.getItemId() == R.id.item_boat_trip_details) {
                                Fragment adf = new TripDetailsFragment();
                                Bundle args = new Bundle();
                                args.putLong("Boat_Id", displayedValues.get(position).getBoatId());
                                adf.setArguments(args);
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf).commit();
                            } else if (menuItem.getItemId() == R.id.item_boat_expenses) {
                                Fragment adf = new BoatExpensesFragment();
                                Bundle args = new Bundle();
                                args.putLong("Boat_Id", displayedValues.get(position).getBoatId());
                                args.putString("Boat_Name", displayedValues.get(position).getBoatName());
                                adf.setArguments(args);
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf).commit();
                            } else if (menuItem.getItemId() == R.id.item_boat_delete) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle("Confirm Action");
                                builder.setMessage("Do you really want to delete boat?");
                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        deleteBoatData(displayedValues.get(position).getBoatId());
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
                    ArrayList<BoatDisp> filteredArrayList = new ArrayList<>();
                    if (originalValues == null) {
                        originalValues = new ArrayList<BoatDisp>(displayedValues);
                    }

                    if (charSequence == null || charSequence.length() == 0) {
                        results.count = originalValues.size();
                        results.values = originalValues;
                    } else {
                        charSequence = charSequence.toString().toLowerCase();
                        for (int i = 0; i < originalValues.size(); i++) {
                            String status = originalValues.get(i).getBoatStatus();
                            String boatName = originalValues.get(i).getBoatName();
                            String tandelName = originalValues.get(i).getTandelName();
                            String auctionerName = originalValues.get(i).getAuctionerName();
                            if (status.toLowerCase().startsWith(charSequence.toString()) || boatName.toLowerCase().startsWith(charSequence.toString()) || tandelName.toLowerCase().startsWith(charSequence.toString()) || auctionerName.toLowerCase().startsWith(charSequence.toString())) {
                                filteredArrayList.add(new BoatDisp(originalValues.get(i).getBoatId(), originalValues.get(i).getBoatName(), originalValues.get(i).getBoatOwner(), originalValues.get(i).getRegNo(), originalValues.get(i).getBoatPurchaseDate(), originalValues.get(i).getBoatLenght(), originalValues.get(i).getBoatBreadth(), originalValues.get(i).getBoatHeight(), originalValues.get(i).getFishingType(), originalValues.get(i).getBoatLicNo(), originalValues.get(i).getBoatLicFromDt(), originalValues.get(i).getBoatLicToDt(), originalValues.get(i).getBoatInsuranceFromDt(), originalValues.get(i).getBoatInsuranceToDt(), originalValues.get(i).getBoatEngineType(), originalValues.get(i).getBoatHPRating(), originalValues.get(i).getBoatIsUsed(), originalValues.get(i).getBoatTandel(), originalValues.get(i).getBoatAuctioner(), originalValues.get(i).getBoatStatus(), originalValues.get(i).getBlockStatus(), originalValues.get(i).getTandelName(), originalValues.get(i).getAuctionerName(), originalValues.get(i).getOwnerName(), originalValues.get(i).getCoId(), originalValues.get(i).getEnterDate(), originalValues.get(i).getEnterBy()));
                            }
                        }
                        results.count = filteredArrayList.size();
                        results.values = filteredArrayList;
                    }

                    return results;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    displayedValues = (ArrayList<BoatDisp>) filterResults.values;
                    notifyDataSetChanged();
                }
            };

            return filter;
        }
    }
}
