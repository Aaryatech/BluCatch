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
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.blucatch.R;
import com.ats.blucatch.activity.MainActivity;
import com.ats.blucatch.bean.ErrorMessage;
import com.ats.blucatch.bean.Fish;
import com.ats.blucatch.bean.FishData;
import com.ats.blucatch.db.MySqliteHelper;
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

public class FishMasterFragment extends Fragment {

    MySqliteHelper db;
    private FloatingActionButton fab;
    private ImageView ivPopup1, ivPopup2;
    private TextView tvFishName1, tvFishName2, tvLabelFishName1, tvLabelFishName2, tvLabelMinRate1, tvLabelMinRate2, tvMinRate1, tvMinRate2, tvLabelMaxRate1, tvLabelMaxRate2, tvMaxRate1, tvMaxRate2, tvLabelFishSize1, tvFishSize1, tvFishSize2, tvLabelFishSize2, tvLabelRate1, tvLabelRate2;
    private EditText edSearch;
    private ProgressDialog progressBar, progressBar1;
    private ListView lvFishMaster;

    private ArrayList<Integer> fishIdArray = new ArrayList<>();
    private ArrayList<String> fishNameArray = new ArrayList<>();
    private ArrayList<Double> minRateArray = new ArrayList<>();
    private ArrayList<Double> maxRateArray = new ArrayList<>();
    private ArrayList<String> fishTypeArray = new ArrayList<>();
    private ArrayList<String> fishSizeArray = new ArrayList<>();

    private ArrayList<Fish> fishArray = new ArrayList<>();

    private ArrayAdapter<String> adapter;
    private MyFishAdapter adapter1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fish_master, container, false);
        MainActivity.isAtHome = false;
        MainActivity.isAtFishMaster = true;
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
        MainActivity.isAtAccDetails = false;
        MainActivity.isAtUserMaster = false;
        MainActivity.isAtAddUser = false;
        MainActivity.isAtExpMaster = false;
        MainActivity.isAtAddExp = false;
        MainActivity.isAtChangePass = false;
        MainActivity.isAtHomeTripExp = false;
        MainActivity.isAtHomeFishSell = false;


        Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
        Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");

        MainActivity.tvTitle.setText("Fish Master");
        MainActivity.tvTitle.setTypeface(boldFont);

        edSearch = view.findViewById(R.id.edFishMaster_search);

        lvFishMaster = view.findViewById(R.id.lvFishMaster);

        fab = view.findViewById(R.id.fabFishMaster);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new AddFishFragment();
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
                adapter1.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        getFishData();

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

    public void getFishData() {

        //db = new MySqliteHelper(getContext());
        if (CheckNetwork.isInternetAvailable(getContext())) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            InterfaceApi api = retrofit.create(InterfaceApi.class);
            Call<FishData> fishDataCall = api.allFishData();

            progressBar = new ProgressDialog(getContext());
            progressBar.setCancelable(false);
            progressBar.setMessage("please wait....");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.setProgress(0);
            progressBar.setMax(100);
            progressBar.show();

            fishDataCall.enqueue(new Callback<FishData>() {
                @Override
                public void onResponse(Call<FishData> call, Response<FishData> response) {
                    try {
                        if (response.body() != null) {

                            FishData data = response.body();
                            if (data.getErrorMessage().getError()) {
                                progressBar.dismiss();
                                Toast.makeText(getContext(), "unable to fetch data!", Toast.LENGTH_SHORT).show();
                                Log.e("ON RESPONSE : ", " ERROR :  " + data.getErrorMessage().getMessage());
                            } else {
                                //db.deleteFish();
                                for (int i = 0; i < data.getFish().size(); i++) {
                                    //db.addFishToSqlite(data.getFish().get(i));
                                    fishArray.add(i, data.getFish().get(i));
                                }
                                Log.e("ON RESPONSE : ", " DATA :  " + fishArray);
                                adapter1 = new MyFishAdapter(getContext(), fishArray);
                                lvFishMaster.setAdapter(adapter1);
                                progressBar.dismiss();

                            }

                        } else {
                            progressBar.dismiss();
                            Toast.makeText(getContext(), "unable to fetch data!", Toast.LENGTH_SHORT).show();
                            Log.e("ON RESPONSE : ", " NO DATA ");
                        }
                    } catch (Exception e) {
                        progressBar.dismiss();
                        Log.e("Exception : ", "" + e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<FishData> call, Throwable t) {
                    progressBar.dismiss();
                    Toast.makeText(getContext(), "unable to fetch data! server error", Toast.LENGTH_SHORT).show();
                    Log.e("ON FAILURE : ", " ERROR : " + t.getMessage());
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

    public void deleteFishData(int id) {
        if (CheckNetwork.isInternetAvailable(getContext())) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            final InterfaceApi api = retrofit.create(InterfaceApi.class);

            Call<ErrorMessage> errorMessageCall = api.deleteFish(id);

            progressBar1 = new ProgressDialog(getContext());
            progressBar1.setCancelable(false);
            progressBar1.setMessage("please wait....");
            progressBar1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar1.setProgress(0);
            progressBar1.setMax(100);
            progressBar1.show();

            errorMessageCall.enqueue(new Callback<ErrorMessage>() {
                @Override
                public void onResponse(Call<ErrorMessage> call, Response<ErrorMessage> response) {
                    try {
                        if (response.body() != null) {
                            ErrorMessage data = response.body();
                            if (data.getError()) {
                                progressBar1.dismiss();
                                Log.e("ON RESPONSE : ", "ERROR : " + data.getMessage());

                            } else {
                                progressBar1.dismiss();
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                                builder.setTitle("Success");
                                builder.setCancelable(false);
                                builder.setMessage("Fish deleted successfully.");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        clearArrayList();
                                        getFishData();
                                        //setAdapterData();
                                        adapter1.notifyDataSetChanged();
                                        edSearch.setText("");
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }

                        } else {
                            progressBar1.dismiss();
                            Toast.makeText(getContext(), "Unable to delete fish!", Toast.LENGTH_SHORT).show();
                            Log.e("ON RESPONSE : ", "NO DATA");
                        }
                    } catch (Exception e) {
                        progressBar1.dismiss();
                        Log.e("Exception : ", "" + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ErrorMessage> call, Throwable t) {
                    progressBar1.dismiss();
                    Toast.makeText(getContext(), "Unable to delete fish!", Toast.LENGTH_SHORT).show();
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

    private void clearArrayList() {
        fishArray.clear();
    }

    public class MyFishAdapter extends BaseAdapter implements Filterable {

        private ArrayList<Fish> originalValues;
        private ArrayList<Fish> displayedValues;
        LayoutInflater inflater;

        public MyFishAdapter(Context context, ArrayList<Fish> fishArrayList) {
            this.originalValues = fishArrayList;
            this.displayedValues = fishArrayList;
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
        public View getView(final int position, View v, ViewGroup viewGroup) {
            v = inflater.inflate(R.layout.custom_fish_master, null);
            TextView tvFishName1 = v.findViewById(R.id.tvFishName1);
            TextView tvMinRate1 = v.findViewById(R.id.tvMinRate1);
            TextView tvMaxRate1 = v.findViewById(R.id.tvMaxRate1);
            TextView tvLabelFishSize1 = v.findViewById(R.id.tvLabelFishSize1);
            TextView tvFishSize1 = v.findViewById(R.id.tvFishSize1);
            TextView tvLabelRate1 = v.findViewById(R.id.tvLabelRate1);
            ImageView ivPopup1 = v.findViewById(R.id.ivPopUp1);

            Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
            Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");

            tvFishName1.setTypeface(boldFont);
            tvMinRate1.setTypeface(lightFont);
            tvMaxRate1.setTypeface(lightFont);
            tvLabelFishSize1.setTypeface(boldFont);
            tvFishSize1.setTypeface(lightFont);
            tvLabelRate1.setTypeface(boldFont);

            tvFishName1.setText(displayedValues.get(position).getFishName());
            tvMinRate1.setText("" + displayedValues.get(position).getFishMinRate());
            tvMaxRate1.setText("" + displayedValues.get(position).getFishMaxRate());
            tvFishSize1.setText(displayedValues.get(position).getFishSize());

            ivPopup1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(getContext(), view);
                    popupMenu.getMenuInflater().inflate(R.menu.popup_edit_delete, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            if (menuItem.getItemId() == R.id.item_edit) {

                                Fragment adf = new EditFishFragment();
                                Bundle args = new Bundle();
                                args.putString("Fish_Name", displayedValues.get(position).getFishName());
                                args.putDouble("Min_Rate", displayedValues.get(position).getFishMinRate());
                                args.putDouble("Max_Rate", displayedValues.get(position).getFishMaxRate());
                                args.putString("Fish_Type", displayedValues.get(position).getFishType());
                                args.putString("Fish_Size", displayedValues.get(position).getFishSize());
                                args.putInt("Fish_Id", displayedValues.get(position).getFishId());
                                adf.setArguments(args);
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf).commit();

                            } else if (menuItem.getItemId() == R.id.item_delete) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                                builder.setTitle("Confirm Action");
                                builder.setMessage("Do you really want to delete fish?");
                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        deleteFishData(displayedValues.get(position).getFishId());
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
                                // Toast.makeText(getContext(), "deleted", Toast.LENGTH_SHORT).show();
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
                    ArrayList<Fish> filteredArrayList = new ArrayList<>();
                    if (originalValues == null) {
                        originalValues = new ArrayList<Fish>(displayedValues);
                    }

                    if (charSequence == null || charSequence.length() == 0) {
                        results.count = originalValues.size();
                        results.values = originalValues;
                    } else {
                        charSequence = charSequence.toString().toLowerCase();
                        for (int i = 0; i < originalValues.size(); i++) {
                            String fishName = originalValues.get(i).getFishName();
                            if (fishName.toLowerCase().startsWith(charSequence.toString())) {
                                filteredArrayList.add(new Fish(originalValues.get(i).getFishId(), originalValues.get(i).getFishName(), originalValues.get(i).getFishType(), originalValues.get(i).getFishIsUsed(), originalValues.get(i).getFishSize(), originalValues.get(i).getFishMinRate(), originalValues.get(i).getFishMaxRate(), originalValues.get(i).getCoId(), originalValues.get(i).getEnterDate(), originalValues.get(i).getEnterBy()));
                            }
                            Log.e("Filter Array : ", " values : " + filteredArrayList);
                        }
                        results.count = filteredArrayList.size();
                        results.values = filteredArrayList;
                    }

                    return results;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {


                    displayedValues = (ArrayList<Fish>) filterResults.values;

                    notifyDataSetChanged();


                }
            };
            Log.e("Filter : ", "" + filter);
            return filter;
        }
    }
}
