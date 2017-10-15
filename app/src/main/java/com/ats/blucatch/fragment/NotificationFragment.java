package com.ats.blucatch.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ats.blucatch.R;
import com.ats.blucatch.activity.MainActivity;
import com.ats.blucatch.bean.Notification;
import com.ats.blucatch.bean.NotificationData;
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

public class NotificationFragment extends Fragment {

    private ListView lvNotification;
    int coId;
    private ProgressDialog progressBar;
    private ArrayList<Notification> notifications = new ArrayList<>();

    MyAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
        Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");

        MainActivity.tvTitle.setText("Notification");
        MainActivity.tvTitle.setTypeface(boldFont);

        MainActivity.isAtHome = false;
        MainActivity.isAtNotification = true;

        try {
            SharedPreferences pref = getContext().getSharedPreferences(InterfaceApi.MY_PREF, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            coId = pref.getInt("AppCoId", 0);
            Log.e("Co_id : ", "" + coId);
        } catch (Exception e) {
            Log.e("Exception : ", "" + e.getMessage());
        }

        lvNotification = view.findViewById(R.id.lvNotification);
        if (coId > 0) {
            getNotificationData(coId);
        }
        return view;
    }

    public void getNotificationData(int coId) {
        if (CheckNetwork.isInternetAvailable(getContext())) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            InterfaceApi api = retrofit.create(InterfaceApi.class);
            Call<NotificationData> notificationDataCall = api.getNotificationData(coId);

            progressBar = new ProgressDialog(getContext());
            progressBar.setCancelable(false);
            progressBar.setMessage("please wait....");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.setProgress(0);
            progressBar.setMax(100);
            progressBar.show();

            notificationDataCall.enqueue(new Callback<NotificationData>() {
                @Override
                public void onResponse(Call<NotificationData> call, Response<NotificationData> response) {
                    try {
                        if (response.body() != null) {

                            NotificationData data = response.body();
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
                                notifications.clear();
                                for (int i = 0; i < data.getNotification().size(); i++) {
                                    notifications.add(data.getNotification().get(i));
                                }

                                adapter = new MyAdapter(getContext(), notifications);
                                lvNotification.setAdapter(adapter);
                                progressBar.dismiss();

                            }

                        } else {
                            progressBar.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                            builder.setCancelable(false);
                            builder.setMessage("No messages available");
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
                        e.printStackTrace();
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                        builder.setCancelable(false);
                        builder.setMessage("Oops something went wrong!");
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
                public void onFailure(Call<NotificationData> call, Throwable t) {
                    progressBar.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                    builder.setCancelable(false);
                    builder.setTitle("Server Error");
                    builder.setMessage("Oops something went wrong!");
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

    public class MyAdapter extends BaseAdapter {

        private ArrayList<Notification> originalValues;
        private ArrayList<Notification> displayedValues;
        LayoutInflater inflater;

        public MyAdapter(Context context, ArrayList<Notification> notificationArrayList) {
            this.originalValues = notificationArrayList;
            this.displayedValues = notificationArrayList;
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
            TextView tvTitle;
            TextView tvDesc;
        }

        @Override
        public View getView(final int position, View v, ViewGroup parent) {
            MyAdapter.ViewHolder holder = null;

            if (v == null) {
                v = inflater.inflate(R.layout.custom_notification_layout, null);
                holder = new MyAdapter.ViewHolder();
                holder.tvDate = v.findViewById(R.id.tvNotify_Date);
                holder.tvTitle = v.findViewById(R.id.tvNotify_Title);
                holder.tvDesc = v.findViewById(R.id.tvNotify_Desc);
                v.setTag(holder);
            } else {
                holder = (MyAdapter.ViewHolder) v.getTag();
            }


            Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
            Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");

            holder.tvDate.setTypeface(boldFont);
            holder.tvTitle.setTypeface(boldFont);
            holder.tvDesc.setTypeface(lightFont);

            Date date1 = new Date(displayedValues.get(position).getOnDate());
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
            String dateText = sdf.format(date1);
            System.out.println(dateText);
            holder.tvDate.setText(" " + dateText + " ");
            holder.tvTitle.setText("" + displayedValues.get(position).getTitle());
            holder.tvDesc.setText("" + displayedValues.get(position).getDescription());

            return v;
        }
    }


}
