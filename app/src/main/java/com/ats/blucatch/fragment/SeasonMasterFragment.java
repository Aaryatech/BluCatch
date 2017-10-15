package com.ats.blucatch.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ats.blucatch.R;
import com.ats.blucatch.activity.MainActivity;
import com.ats.blucatch.bean.ErrorMessage;
import com.ats.blucatch.bean.Season;
import com.ats.blucatch.bean.SeasonData;
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

public class SeasonMasterFragment extends Fragment {

    private int userId, coId;
    private EditText edSearch;
    private FloatingActionButton fab;
    private ListView lvSeason;
    private ProgressDialog progressBar, progressBar1;
    private ArrayList<Season> seasonArray = new ArrayList<>();
    SeasonMasterAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_season_master, container, false);
        Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
        Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");

        MainActivity.tvTitle.setText("Season Master");
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
        MainActivity.isAtAccDetails = false;
        MainActivity.isAtUserMaster = false;
        MainActivity.isAtAddUser = false;
        MainActivity.isAtExpMaster = false;
        MainActivity.isAtAddExp = false;
        MainActivity.isAtChangePass = false;
        MainActivity.isAtHomeTripExp = false;
        MainActivity.isAtHomeFishSell = false;
        MainActivity.isAtSeasonMaster = true;
        MainActivity.isAtAddSeason = false;

        SharedPreferences pref = getContext().getSharedPreferences(InterfaceApi.MY_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        userId = pref.getInt("AppUserId", 0);
        coId = pref.getInt("AppCoId", 0);
        Log.e("Co_id : ", "" + coId);

        edSearch = view.findViewById(R.id.edSeasonMaster_search);
        lvSeason = view.findViewById(R.id.lvSeasonMaster);
        fab = view.findViewById(R.id.fabSeasonMaster);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new AddSeasonFragment();
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
                adapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        getSeasonData(coId);
        return view;

    }

    public void getSeasonData(int coId) {
        if (CheckNetwork.isInternetAvailable(getContext())) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            final InterfaceApi api = retrofit.create(InterfaceApi.class);

            Call<SeasonData> seasonDataCall = api.getAllSeason(coId);

            progressBar = new ProgressDialog(getContext());
            progressBar.setCancelable(false);
            progressBar.setMessage("please wait....");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.setProgress(0);
            progressBar.setMax(100);
            progressBar.show();

            seasonDataCall.enqueue(new Callback<SeasonData>() {
                @Override
                public void onResponse(Call<SeasonData> call, Response<SeasonData> response) {
                    try {
                        if (response.body() != null) {

                            SeasonData data = response.body();
                            if (data.getErrorMessage().getError()) {
                                progressBar.dismiss();
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                                builder.setCancelable(false);
                                builder.setTitle("Error");
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
                                seasonArray.clear();
                                for (int i = 0; i < data.getSeason().size(); i++) {
                                    seasonArray.add(i, data.getSeason().get(i));
                                }
                                adapter = new SeasonMasterAdapter(getContext(), seasonArray);
                                lvSeason.setAdapter(adapter);
                                progressBar.dismiss();
                            }

                        } else {
                            progressBar.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                            builder.setCancelable(false);
                            builder.setMessage("No data found for season");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    } catch (Exception e) {
                        progressBar.dismiss();
                        Log.e("Exception : ", "" + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<SeasonData> call, Throwable t) {
                    progressBar.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                    builder.setCancelable(false);
                    builder.setTitle("Server Error");
                    builder.setMessage("No data found for season");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
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

    public class SeasonMasterAdapter extends BaseAdapter implements Filterable {

        private ArrayList<Season> originalValues;
        private ArrayList<Season> displayedValues;
        LayoutInflater inflater;

        public SeasonMasterAdapter(Context context, ArrayList<Season> seasonArrayList) {
            this.originalValues = seasonArrayList;
            this.displayedValues = seasonArrayList;
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
            v = inflater.inflate(R.layout.custom_season_master, null);
            TextView tvRemark = v.findViewById(R.id.tvSeasonMaster_SeasonRemark);
            TextView tvStartDate = v.findViewById(R.id.tvSeasonMaster_StartDate);
            TextView tvEndDate = v.findViewById(R.id.tvSeasonMaster_EndDate);
            TextView tvLabelEndDate = v.findViewById(R.id.tvSeasonMaster_LabelEndDate);
            TextView tvLabelStartDate = v.findViewById(R.id.tvSeasonMaster_LabelStartDate);
            LinearLayout llSeasonLayout = v.findViewById(R.id.llSeasonMaster);
            LinearLayout llToDate = v.findViewById(R.id.llSeasonMaster_ToDate);
            Button btnEndSeason = v.findViewById(R.id.btnSeasonMaster_EndSeason);

            Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
            Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");

            tvRemark.setTypeface(boldFont);
            tvStartDate.setTypeface(lightFont);
            tvEndDate.setTypeface(lightFont);
            tvLabelEndDate.setTypeface(lightFont);
            tvLabelStartDate.setTypeface(lightFont);

            Date dt = new Date();
            dt.setTime(displayedValues.get(position).getStartDate());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
            String dateText = simpleDateFormat.format(dt);

            Date dt2 = new Date();
            dt2.setTime(displayedValues.get(position).getEndDate());
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd MMM yyyy");
            String dateText2 = simpleDateFormat2.format(dt2);

            tvRemark.setText(displayedValues.get(position).getRemark());
            tvStartDate.setText("" + dateText);
            tvEndDate.setText("" + dateText2);

            if ((displayedValues.get(position).getEndDate() <= 0)) {
                llToDate.setVisibility(View.GONE);
                btnEndSeason.setVisibility(View.VISIBLE);
            } else {
                llToDate.setVisibility(View.VISIBLE);
                btnEndSeason.setVisibility(View.GONE);
            }

            if (displayedValues.get(position).getIsActive() == 1) {
                llSeasonLayout.setBackgroundColor(Color.argb(255, 255, 214, 214));
            } else if (displayedValues.get(position).getIsActive() == 0) {
                llSeasonLayout.setBackgroundColor(Color.argb(255, 214, 255, 214));
            }

            btnEndSeason.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                    builder.setCancelable(false);
                    builder.setTitle("Confirm");
                    builder.setMessage("Do you want to end season?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            endSeason(displayedValues.get(position).getSeasonId());
                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
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
                    ArrayList<Season> filteredArrayList = new ArrayList<>();
                    if (originalValues == null) {
                        originalValues = new ArrayList<Season>(displayedValues);
                    }

                    if (charSequence == null || charSequence.length() == 0) {
                        results.count = originalValues.size();
                        results.values = originalValues;
                    } else {
                        charSequence = charSequence.toString().toLowerCase();
                        for (int i = 0; i < originalValues.size(); i++) {
                            String remark = originalValues.get(i).getRemark();
                            if (remark.toLowerCase().startsWith(charSequence.toString())) {
                                filteredArrayList.add(new Season(originalValues.get(i).getSeasonId(), originalValues.get(i).getStartDate(), originalValues.get(i).getEndDate(), originalValues.get(i).getRemark(), originalValues.get(i).getIsActive(), originalValues.get(i).getCoId(), originalValues.get(i).getEnterDate(), originalValues.get(i).getEnterBy()));
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
                    displayedValues = (ArrayList<Season>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
            Log.e("Filter : ", "" + filter);
            return filter;
        }
    }

    public void endSeason(int seasonId) {
        if (CheckNetwork.isInternetAvailable(getContext())) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            final InterfaceApi api = retrofit.create(InterfaceApi.class);

            Call<ErrorMessage> errorMessageCall = api.seasonEnd(seasonId);

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
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                                builder.setCancelable(false);
                                builder.setTitle("Error");
                                builder.setMessage("" + data.getMessage());
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();

                            } else {
                                progressBar1.dismiss();
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                                builder.setCancelable(false);
                                builder.setTitle("success");
                                builder.setMessage("Season ended successfully");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        getSeasonData(coId);
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }

                        } else {
                            progressBar1.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                            builder.setCancelable(false);
                            builder.setMessage("sorry unable to end season!");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    } catch (Exception e) {
                        progressBar1.dismiss();
                        Log.e("Exception : ", "" + e.getMessage());
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                        builder.setCancelable(false);
                        builder.setMessage("sorry unable to end season!");
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

                @Override
                public void onFailure(Call<ErrorMessage> call, Throwable t) {
                    progressBar1.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                    builder.setCancelable(false);
                    builder.setTitle("Server Error");
                    builder.setMessage("No data found for season");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
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
