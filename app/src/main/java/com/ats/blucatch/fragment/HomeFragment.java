package com.ats.blucatch.fragment;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.blucatch.R;
import com.ats.blucatch.activity.MainActivity;
import com.ats.blucatch.bean.ErrorMessage;
import com.ats.blucatch.bean.ExpenseApprove;
import com.ats.blucatch.bean.ExpenseApproveData;
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

public class HomeFragment extends Fragment {

    private TextView tvLabelSell, tvLabelExpenses, tvLabelIncome, tvSellCount, tvExpensesCount, tvIncomeCount, tvTripNo, tvStatus, tvBoatName, tvCrew, tvDate, tvDays, tvLabelTripExp, tvLabelFishSell, tvTripExpCount, tvFishSellCount, tvOnBoatCash, tvonBoatCashCount;
    private ViewPager mViewPager;
    public static int no;
    private ImageView ivLeft, ivRight;
    private ListView lvHomeTripData;
    private RelativeLayout rlViewPager;
    private static ProgressDialog progressBar;
    int userId, coId;
    public static Context context;

    private ArrayList<TripDisplay> tripDisplayArray = new ArrayList<>();

    private MyAdapter myAdapter;

    static MyPagerAdapter adapder;

    private ArrayList<ExpenseApprove> expenseApproveArray = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
        Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");
        MainActivity.tvTitle.setText("Home");
        MainActivity.tvTitle.setTypeface(boldFont);

        HomeFragment.context = getContext();

        MainActivity.isAtHome = true;
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

        SharedPreferences pref = getContext().getSharedPreferences(InterfaceApi.MY_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        userId = pref.getInt("AppUserId", 0);
        coId = pref.getInt("AppCoId", 0);
        Log.e("Co_id : ", "" + coId);

        lvHomeTripData = view.findViewById(R.id.lvHomeDispTrip);

        rlViewPager = view.findViewById(R.id.rlViewPager);
        rlViewPager.setVisibility(View.GONE);

        ivLeft = view.findViewById(R.id.ivLeft);
        ivRight = view.findViewById(R.id.ivRight);

        mViewPager = view.findViewById(R.id.viewPager_container);

        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.arrowScroll(View.FOCUS_LEFT);
            }
        });
        ivRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.arrowScroll(View.FOCUS_RIGHT);
            }
        });

        tvLabelSell = view.findViewById(R.id.tvMain_LabelSell);
        tvLabelExpenses = view.findViewById(R.id.tvMain_LabelExpenses);
        tvLabelIncome = view.findViewById(R.id.tvMain_LabelIncome);
        tvSellCount = view.findViewById(R.id.tvMain_SellCount);
        tvExpensesCount = view.findViewById(R.id.tvMain_ExpensesCount);
        tvIncomeCount = view.findViewById(R.id.tvMain_IncomeCount);


        tvLabelSell.setTypeface(boldFont);
        tvLabelExpenses.setTypeface(boldFont);
        tvLabelIncome.setTypeface(boldFont);
        tvSellCount.setTypeface(boldFont);
        tvExpensesCount.setTypeface(boldFont);
        tvIncomeCount.setTypeface(boldFont);

        getAllTripData();
        getExpensesApproveData();
        return view;
    }


    public void getExpensesApproveData() {
        if (CheckNetwork.isInternetAvailable(getContext())) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            InterfaceApi api = retrofit.create(InterfaceApi.class);
            Call<ExpenseApproveData> approveDataCall = api.allExpensesForApprove();

            approveDataCall.enqueue(new Callback<ExpenseApproveData>() {
                @Override
                public void onResponse(Call<ExpenseApproveData> call, Response<ExpenseApproveData> response) {

                    if (response.body() != null) {
                        ExpenseApproveData data = response.body();
                        if (data.getErrorMessage().getError()) {
                            Log.e("Exp Approve : ", "ERROR : " + data.getErrorMessage().getMessage());
                        } else {
                            expenseApproveArray.clear();
                            for (int i = 0; i < data.getExpenseApprove().size(); i++) {
                                expenseApproveArray.add(i, data.getExpenseApprove().get(i));
                            }

                            if (expenseApproveArray.size() <= 0) {
                                rlViewPager.setVisibility(View.GONE);
                            } else {
                                adapder = new MyPagerAdapter(getContext(), expenseApproveArray);
                                mViewPager.setAdapter(adapder);
                                adapder.notifyDataSetChanged();
                                rlViewPager.setVisibility(View.VISIBLE);
                            }
                        }

                    } else {
                        Log.e("Exp Approve : ", "NO DATA");
                    }
                }

                @Override
                public void onFailure(Call<ExpenseApproveData> call, Throwable t) {
                    Log.e("Exp Approve : ", "ON FAILURE : " + t.getMessage());
                }
            });


        } else {
            Log.e("Exp Approve : ", "NO Internet Connection");
        }
    }


    public void getAllTripData() {
        if (CheckNetwork.isInternetAvailable(getContext())) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            InterfaceApi api = retrofit.create(InterfaceApi.class);
            Call<TripData> tripDataCall = api.allTripData();

//            progressBar = new ProgressDialog(getContext());
//            progressBar.setCancelable(false);
//            progressBar.setMessage("please wait....");
//            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressBar.setProgress(0);
//            progressBar.setMax(100);
//            progressBar.show();

            tripDataCall.enqueue(new Callback<TripData>() {
                @Override
                public void onResponse(Call<TripData> call, Response<TripData> response) {
                    if (response.body() != null) {

                        TripData data = response.body();
                        if (data.getErrorMessage().getError()) {
                            // progressBar.dismiss();
                            Toast.makeText(getContext(), "Unable to fetch data!", Toast.LENGTH_SHORT).show();
                            Log.e("ON RESPONSE : ", "ERROR : " + data.getErrorMessage().getMessage());
                        } else {
                            tripDisplayArray.clear();

                            for (int i = 0; i < data.getTripDisplay().size(); i++) {
                                tripDisplayArray.add(i, data.getTripDisplay().get(i));
                            }
                        }
                        Log.e("ON RESPONSE : ", " DATA : " + tripDisplayArray);
                        myAdapter = new MyAdapter(getContext(), tripDisplayArray);
                        lvHomeTripData.setAdapter(myAdapter);
                        //progressBar.dismiss();
                    } else

                    {
                        // progressBar.dismiss();
                        //Toast.makeText(getContext(), "Unable to fetch data!", Toast.LENGTH_SHORT).show();
                        Log.e("ON RESPONSE : ", "NO DATA");
                    }
                }

                @Override
                public void onFailure(Call<TripData> call, Throwable t) {
                    // progressBar.dismiss();
                    // Toast.makeText(getContext(), "Unable to fetch data! server error", Toast.LENGTH_SHORT).show();
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

    public class MyAdapter extends BaseAdapter {

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
            MyAdapter.ViewHolder holder = null;

            if (v == null) {
                v = inflater.inflate(R.layout.custom_trip_master, null);
                holder = new MyAdapter.ViewHolder();
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
                holder.ivpopup.setVisibility(View.GONE);
                v.setTag(holder);
            } else {
                holder = (MyAdapter.ViewHolder) v.getTag();
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

            return v;
        }

    }


    static class MyPagerAdapter extends PagerAdapter {

        private ArrayList<ExpenseApprove> expenseApproveArrayList;
        private Context context;
        // LayoutInflater inflater;

        public MyPagerAdapter(Context context, ArrayList<ExpenseApprove> expenseApproveArrayList) {
            this.context = context;
            this.expenseApproveArrayList = expenseApproveArrayList;
            //  inflater = LayoutInflater.from(context);
        }

        public int getCount() {
            return expenseApproveArrayList.size();
        }

        @Override
        public Object instantiateItem(final ViewGroup collection, final int position) {
            LayoutInflater inflater = LayoutInflater.from(context);
            ViewGroup view = (ViewGroup) inflater.inflate(R.layout.slider_layout, collection, false);
            //   assert view != null;

            TextView tvBoatName = view.findViewById(R.id.tvSliderBoatName);
            TextView tvFrom = view.findViewById(R.id.tvSliderFrom);
            TextView tvTo = view.findViewById(R.id.tvSliderTo);
            TextView tvAmount = view.findViewById(R.id.tvSliderCount);
            ImageView ivYes = view.findViewById(R.id.ivSlider_yes);
            ImageView ivNo = view.findViewById(R.id.ivSlider_no);

            Typeface lightFont = Typeface.createFromAsset(context.getAssets(), "sofiapro-light.otf");
            Typeface boldFont = Typeface.createFromAsset(context.getAssets(), "SofiaProBold.otf");

            tvBoatName.setTypeface(boldFont);
            tvFrom.setTypeface(boldFont);
            tvTo.setTypeface(boldFont);
            tvAmount.setTypeface(boldFont);

            tvBoatName.setText("" + expenseApproveArrayList.get(position).getBoatName());
            tvFrom.setText("From : " + expenseApproveArrayList.get(position).getSenderAccName());
            tvTo.setText("To : " + expenseApproveArrayList.get(position).getReceiverAccName());
            tvAmount.setText("" + expenseApproveArrayList.get(position).getAmount());

            ivYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.custom_expenses_approve);
                    dialog.setTitle("Approve Expense");

                    final EditText edRemark = (EditText) dialog.findViewById(R.id.edExpApprove_Remark);
                    Button btnSubmit = (Button) dialog.findViewById(R.id.btnExpApprove_Submit);

                    btnSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (edRemark.length() == 0) {
                                edRemark.setError("required");
                            } else {

                                SharedPreferences pref = context.getSharedPreferences(InterfaceApi.MY_PREF, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                int userId = pref.getInt("AppUserId", 0);
                                approveExpenses(expenseApproveArrayList.get(position).getTrId(), edRemark.getText().toString(), userId, 0);
                                dialog.dismiss();

                            }
                        }
                    });
                    dialog.show();
                }
            });

            ivNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.custom_expenses_approve);
                    dialog.setTitle("Approve Expense");

                    final EditText edRemark = (EditText) dialog.findViewById(R.id.edExpApprove_Remark);
                    Button btnSubmit = (Button) dialog.findViewById(R.id.btnExpApprove_Submit);

                    btnSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (edRemark.length() == 0) {
                                edRemark.setError("required");
                            } else {
                                SharedPreferences pref = context.getSharedPreferences(InterfaceApi.MY_PREF, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                int userId = pref.getInt("AppUserId", 0);
                                approveExpenses(expenseApproveArrayList.get(position).getTrId(), edRemark.getText().toString(), userId, 1);
                                dialog.dismiss();
                            }
                        }
                    });

                    dialog.show();
                }
            });

            collection.addView(view);
            return view;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((View) object);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

    public static void approveExpenses(int trId, String remark, int userId, int status) {

        if (CheckNetwork.isInternetAvailable(context)) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            InterfaceApi api = retrofit.create(InterfaceApi.class);
            Call<ErrorMessage> errorMessageCall = null;
            if (status == 0) {
                errorMessageCall = api.approveExpense(trId, remark, userId);
            } else if (status == 1) {
                errorMessageCall = api.rejectExpense(trId, remark, userId);
            }
            progressBar = new ProgressDialog(context);
            progressBar.setCancelable(false);
            progressBar.setMessage("please wait....");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.setProgress(0);
            progressBar.setMax(100);
            progressBar.show();

            if (errorMessageCall == null) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
                builder.setTitle("Error");
                builder.setCancelable(false);
                builder.setMessage("Server Error.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                android.app.AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                errorMessageCall.enqueue(new Callback<ErrorMessage>() {
                    @Override
                    public void onResponse(Call<ErrorMessage> call, Response<ErrorMessage> response) {
                        if (response.body() != null) {
                            ErrorMessage data = response.body();
                            if (data.getError()) {
                                progressBar.dismiss();
                                Log.e("ON RESPONSE : ", "ERROR : " + data.getMessage());
                                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
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
                                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
                                builder.setTitle("Success");
                                builder.setCancelable(false);
                                builder.setMessage("" + data.getMessage());
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        adapder.notifyDataSetChanged();

                                    }
                                });
                                android.app.AlertDialog dialog = builder.create();
                                dialog.show();
                            }

                        } else {
                            progressBar.dismiss();
                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
                            builder.setTitle("Error");
                            builder.setCancelable(false);
                            builder.setMessage("unable to approve !");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            android.app.AlertDialog dialog = builder.create();
                            dialog.show();
                            //Toast.makeText(getContext(), "Unable to block / unblock boat!", Toast.LENGTH_SHORT).show();
                            Log.e("ON RESPONSE : ", "NO DATA");
                        }
                    }

                    @Override
                    public void onFailure(Call<ErrorMessage> call, Throwable t) {
                        progressBar.dismiss();
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
                        builder.setTitle("Error");
                        builder.setCancelable(false);
                        builder.setMessage("Server Error");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        android.app.AlertDialog dialog = builder.create();
                        dialog.show();
                        // Toast.makeText(getContext(), "Unable to block / unblock boat!", Toast.LENGTH_SHORT).show();
                        Log.e("ON FAILURE : ", "ERROR : " + t.getMessage());
                    }
                });
            }
        } else

        {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
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


}
