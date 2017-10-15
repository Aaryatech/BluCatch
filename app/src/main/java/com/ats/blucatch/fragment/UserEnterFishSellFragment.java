package com.ats.blucatch.fragment;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.blucatch.R;
import com.ats.blucatch.activity.MainActivity;
import com.ats.blucatch.bean.ErrorMessage;
import com.ats.blucatch.bean.Fish;
import com.ats.blucatch.bean.FishCatch;
import com.ats.blucatch.bean.FishData;
import com.ats.blucatch.utils.CheckNetwork;
import com.ats.blucatch.utils.InterfaceApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserEnterFishSellFragment extends Fragment {

    static long tripId, boatId;
    static String boatName;
    static int seasonId;

    private int coId, userId;

    private ListView lvFishList;
    private TextView tvFishId, tvFishName;
    private EditText edFishName, edRate, edQty, edTotal;
    private TextInputLayout textFishName, textRate, textQty, textTotal;
    private Button btnAdd, btnSave, btnClear;

    private ProgressDialog progressBar, progressBarSell;

    private ArrayAdapter<String> adapter;

    private ArrayList<Fish> fishArray = new ArrayList<>();
    customFishAdapter myAdapter;

    private ArrayList<String> fishNameList = new ArrayList<>();
    private ArrayList<Integer> fishIdList = new ArrayList<>();
    private ArrayList<Double> fishRateList = new ArrayList<>();
    private ArrayList<Integer> fishQtyList = new ArrayList<>();
    private ArrayList<Double> fishTotalList = new ArrayList<>();

    Dialog dialog;

    ArrayList<FishCatch> catchArrayList = new ArrayList<FishCatch>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_enter_fish_sell, container, false);
        Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
        Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");
        MainActivity.tvTitle.setText("Fish Sell");
        MainActivity.tvTitle.setTypeface(boldFont);

        MainActivity.isAtHome = false;
        MainActivity.isAtUserFishSell = true;
        MainActivity.isAtUserTripExp = false;
        MainActivity.isAtUserViewLedger=false;
        MainActivity.isAtUserAccDetails=false;

        try {
            SharedPreferences pref = getContext().getSharedPreferences(InterfaceApi.MY_PREF, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            userId = pref.getInt("AppUserId", 0);
            coId = pref.getInt("AppCoId", 0);
        } catch (Exception e) {
            Log.e("Exception : ", "" + e.getMessage());
        }

        try {
            boatName = getArguments().getString("User_Trip_Boat_Name");
            boatId = getArguments().getLong("User_Trip_Boat_ID");
            tripId = getArguments().getLong("User_Trip_ID");
            seasonId = getArguments().getInt("User_Trip_Season_Id");
        } catch (Exception e) {
            e.printStackTrace();
        }

        tvFishName = view.findViewById(R.id.tvUSerEnterFishExp_FishName);
        lvFishList = view.findViewById(R.id.lvUserEnterFishExp_FishList);
        tvFishId = view.findViewById(R.id.tvUSerEnterFishExp_FishId);
        textFishName = view.findViewById(R.id.textUserEnterFishExp_Fish);
        textRate = view.findViewById(R.id.textUserEnterFishExp_Rate);
        textQty = view.findViewById(R.id.textUserEnterFishExp_Qty);
        textTotal = view.findViewById(R.id.textUserEnterFishExp_Total);

        edFishName = view.findViewById(R.id.edUserEnterFishExp_Fish);
        edRate = view.findViewById(R.id.edUserEnterFishExp_Rate);
        edQty = view.findViewById(R.id.edUserEnterFishExp_Qty);
        edTotal = view.findViewById(R.id.edUserEnterFishExp_Total);

        btnAdd = view.findViewById(R.id.btnUserEnterFishExp_Add);
        btnSave = view.findViewById(R.id.btnUserEnterFishExp_Save);
        btnClear = view.findViewById(R.id.btnUserEnterFishExp_Clear);

        textFishName.setTypeface(lightFont);
        textRate.setTypeface(lightFont);
        textQty.setTypeface(lightFont);
        textTotal.setTypeface(lightFont);
        edFishName.setTypeface(lightFont);
        edRate.setTypeface(lightFont);
        edQty.setTypeface(lightFont);
        edTotal.setTypeface(lightFont);
        btnAdd.setTypeface(lightFont);
        btnSave.setTypeface(lightFont);
        btnClear.setTypeface(lightFont);

        getFishData();

        edFishName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        edRate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    edTotal.setText("" + Double.parseDouble(edRate.getText().toString()) * Integer.parseInt(edQty.getText().toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        edQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    edTotal.setText("" + Double.parseDouble(edRate.getText().toString()) * Integer.parseInt(edQty.getText().toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvFishId.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "please select fish first", Toast.LENGTH_SHORT).show();
                    edFishName.requestFocus();
                } else if (edRate.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "please enter fish rate", Toast.LENGTH_SHORT).show();
                    edRate.requestFocus();
                } else if (edQty.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "please enter fish quantity", Toast.LENGTH_SHORT).show();
                    edQty.requestFocus();
                } else {
                    if (tvFishId.getText().toString().isEmpty()) {

                    } else {
                        fishIdList.add(Integer.parseInt(tvFishId.getText().toString()));
                        fishNameList.add(tvFishName.getText().toString());
                        fishRateList.add(Double.parseDouble(edRate.getText().toString()));
                        fishQtyList.add(Integer.parseInt(edQty.getText().toString()));
                        fishTotalList.add(Double.parseDouble(edTotal.getText().toString()));
                        lvFishList.setAdapter(adapter);
                        resetData();
                    }
                }
            }
        });

        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, fishNameList) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                LayoutInflater inflater = getActivity().getLayoutInflater();
                View v = inflater.inflate(R.layout.custom_fish_display_list, null);
                Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
                Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");

                TextView tvName = (TextView) v.findViewById(R.id.tvCustomFishList_Name);
                TextView tvRate = (TextView) v.findViewById(R.id.tvCustomFishList_Rate);
                TextView tvQty = (TextView) v.findViewById(R.id.tvCustomFishList_Qty);
                TextView tvTotal = (TextView) v.findViewById(R.id.tvCustomFishList_Total);
                TextView tvLabelRate = (TextView) v.findViewById(R.id.tvCustomFishList_LabelRate);
                TextView tvLabelQty = (TextView) v.findViewById(R.id.tvCustomFishList_LabelQty);
                TextView tvLabelTotal = (TextView) v.findViewById(R.id.tvCustomFishList_LabelTotal);

                tvName.setTypeface(lightFont);
                tvRate.setTypeface(lightFont);
                tvQty.setTypeface(lightFont);
                tvTotal.setTypeface(lightFont);
                tvLabelRate.setTypeface(boldFont);
                tvLabelQty.setTypeface(boldFont);
                tvLabelTotal.setTypeface(boldFont);

                tvName.setText("" + fishNameList.get(position));
                tvRate.setText("" + fishRateList.get(position));
                tvQty.setText("" + fishQtyList.get(position));
                tvTotal.setText("" + fishTotalList.get(position));


                return v;
            }
        };
        lvFishList.setAdapter(adapter);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearData();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fishIdList.size() <= 0) {
                    Toast.makeText(getContext(), "please add fish data first", Toast.LENGTH_SHORT).show();
                    edFishName.requestFocus();
                } else {
                    catchArrayList.clear();
                    for (int i = 0; i < fishIdList.size(); i++) {
                        FishCatch fishCatch = new FishCatch(tripId, fishIdList.get(i), fishRateList.get(i), fishQtyList.get(i), fishTotalList.get(i), "", coId, 0, userId);
                        catchArrayList.add(fishCatch);
                    }
                    addFishSell(catchArrayList);
                }
            }
        });

        return view;
    }

    public void resetData() {
        edFishName.setText("");
        tvFishId.setText("");
        tvFishName.setText("");
        edRate.setText("");
        edQty.setText("");
        edTotal.setText("");
        edFishName.requestFocus();
    }

    public void clearData() {
        edFishName.setText("");
        tvFishId.setText("");
        tvFishName.setText("");
        edRate.setText("");
        edQty.setText("");
        edTotal.setText("");
        fishNameList.clear();
        fishRateList.clear();
        fishQtyList.clear();
        fishTotalList.clear();
        lvFishList.setAdapter(adapter);
        catchArrayList.clear();
        edFishName.requestFocus();
    }

    public void getFishData() {
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
                                Log.e("ON RESPONSE : ", " ERROR :  " + data.getErrorMessage().getMessage());
                            } else {
                                fishArray.clear();
                                for (int i = 0; i < data.getFish().size(); i++) {
                                    fishArray.add(i, data.getFish().get(i));
                                }
                                progressBar.dismiss();
                            }

                        } else {
                            progressBar.dismiss();
                            Log.e("ON RESPONSE : ", " NO DATA ");
                        }
                    } catch (Exception e) {
                        progressBar.dismiss();
                    }

                }

                @Override
                public void onFailure(Call<FishData> call, Throwable t) {
                    progressBar.dismiss();
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

    public void showDialog() {
        dialog = new Dialog(getContext(), android.R.style.Theme_Light_NoTitleBar);
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = li.inflate(R.layout.transaction_account_dialog, null, false);
        dialog.setContentView(v);
        dialog.setCancelable(true);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        ListView list1 = (ListView) dialog.findViewById(R.id.lvTransactionDialog_AccountList);
        myAdapter = new customFishAdapter(getActivity().getApplicationContext(), fishArray);
        list1.setAdapter(myAdapter);

        EditText edSearch = (EditText) dialog.findViewById(R.id.edTransactionDialog_Search);
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

        dialog.show();
    }

    public class customFishAdapter extends BaseAdapter implements Filterable {

        private ArrayList<Fish> originalValues;
        private ArrayList<Fish> displayedValues;
        LayoutInflater inflater;

        public customFishAdapter(Context context, ArrayList<Fish> fishArrayList) {
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

        public class ViewHolder {
            TextView tvName;
            TextView tvAmount;
            TextView tvLabelRate;
            LinearLayout llFish;
        }

        @Override
        public View getView(final int position, View v, ViewGroup parent) {
            customFishAdapter.ViewHolder holder = null;

            if (v == null) {
                v = inflater.inflate(R.layout.custom_fish_display, null);
                holder = new customFishAdapter.ViewHolder();
                holder.tvName = v.findViewById(R.id.tvCustomFish_Name);
                holder.tvAmount = v.findViewById(R.id.tvCustomFish_Rate);
                holder.tvLabelRate = v.findViewById(R.id.tvCustomFish_LabelRate);
                holder.llFish = v.findViewById(R.id.llCustomFish);
                v.setTag(holder);
            } else {
                holder = (customFishAdapter.ViewHolder) v.getTag();
            }


            Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
            Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");

            holder.tvName.setTypeface(boldFont);
            holder.tvAmount.setTypeface(lightFont);
            holder.tvLabelRate.setTypeface(lightFont);

            holder.tvName.setText("" + displayedValues.get(position).getFishName());
            holder.tvAmount.setText("" + displayedValues.get(position).getFishMinRate() + "/-  to  " + displayedValues.get(position).getFishMaxRate() + "/-");

            holder.llFish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    edFishName.setText("" + displayedValues.get(position).getFishName() + "\nRate : " + displayedValues.get(position).getFishMinRate() + " - " + displayedValues.get(position).getFishMaxRate());
                    tvFishId.setText("" + displayedValues.get(position).getFishId());
                    tvFishName.setText("" + displayedValues.get(position).getFishName());
                    dialog.dismiss();
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
                            String name = "" + originalValues.get(i).getFishName();
                            if (name.toLowerCase().startsWith(charSequence.toString())) {
                                filteredArrayList.add(new Fish(originalValues.get(i).getFishId(), originalValues.get(i).getFishName(), originalValues.get(i).getFishType(), originalValues.get(i).getFishIsUsed(), originalValues.get(i).getFishSize(), originalValues.get(i).getFishMinRate(), originalValues.get(i).getFishMaxRate(), originalValues.get(i).getCoId(), originalValues.get(i).getEnterDate(), originalValues.get(i).getEnterBy()));
                            }
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

            return filter;
        }
    }
    
    public void addFishSell(ArrayList<FishCatch> catchArrayList) {
        if (CheckNetwork.isInternetAvailable(getContext())) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            InterfaceApi api = retrofit.create(InterfaceApi.class);

            Call<ErrorMessage> errorMessageCall = api.addFishCatch(catchArrayList);

            progressBarSell = new ProgressDialog(getContext());
            progressBarSell.setCancelable(false);
            progressBarSell.setMessage("please wait....");
            progressBarSell.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBarSell.setProgress(0);
            progressBarSell.setMax(100);
            progressBarSell.show();

            errorMessageCall.enqueue(new Callback<ErrorMessage>() {
                @Override
                public void onResponse(Call<ErrorMessage> call, Response<ErrorMessage> response) {
                    try {
                        if (response.body() != null) {
                            ErrorMessage data = response.body();
                            if (data.getError()) {
                                progressBarSell.dismiss();
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                                builder.setTitle("Error");
                                builder.setCancelable(false);
                                builder.setMessage("" + data.getMessage());
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();

                                Log.e("ON RESPONSE : ", "ERROR : " + data.getMessage());

                            } else {
                                progressBarSell.dismiss();
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                                builder.setTitle("Success");
                                builder.setCancelable(false);
                                builder.setMessage("fish catch added successfully.");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        clearData();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }

                        } else {
                            progressBarSell.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                            builder.setTitle("Error");
                            builder.setCancelable(false);
                            builder.setMessage("Unable to save");
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
                public void onFailure(Call<ErrorMessage> call, Throwable t) {
                    progressBarSell.dismiss();
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

}
