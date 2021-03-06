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
import android.support.v4.app.FragmentTransaction;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.blucatch.R;
import com.ats.blucatch.activity.MainActivity;
import com.ats.blucatch.bean.ErrorMessage;
import com.ats.blucatch.bean.SenderAccount;
import com.ats.blucatch.bean.TransactionAccountData;
import com.ats.blucatch.bean.TripSettleTransactions;
import com.ats.blucatch.utils.CheckNetwork;
import com.ats.blucatch.utils.InterfaceApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TripMasterTripSettleFragment extends Fragment {

    private RadioGroup rgPayment;
    private TextView tvLabelFishCatch, tvFishCatch, tvLabelUnsettledAmt, tvUnsettledAmt, tvToAccId;
    private EditText edAmount, edToAcc;
    private TextInputLayout textToAcc;
    private Button btnPay, btnSettle, btnClear;
    private ListView lvList;
    private RadioButton rbCash, rbCredit;
    private LinearLayout llToAcc;

    private ProgressDialog progressBar1, progressBar2;

    private ArrayList<SenderAccount> receiverAccountArray = new ArrayList<>();
    CustomCashAccAdapter myAdapter;

    private ArrayList<String> paymentModeArray = new ArrayList<>();
    private ArrayList<Double> amountArray = new ArrayList<>();
    private ArrayList<Long> toAccIdArray = new ArrayList<>();
    private ArrayList<String> toAccNameArray = new ArrayList<>();

    private ArrayAdapter<String> adapter;
    Dialog dialog;

    static long tripId;
    static int seasonId;
    int coId, userId;
    double remainingAmount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_master_trip_settle, container, false);
        Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
        Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");
        MainActivity.tvTitle.setText("Trip Settle");
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
        MainActivity.isAtTripMasterTripExp = true;
        MainActivity.isAtTripMasterFishSell = false;
        MainActivity.isAtTripMasterEnterTripExp = false;
        MainActivity.isAtTripMasterEnterFishSell = false;

        try {
            tripId = getArguments().getLong("Trip_Id");
            seasonId = getArguments().getInt("Season_Id");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            SharedPreferences pref = getContext().getSharedPreferences(InterfaceApi.MY_PREF, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            userId = pref.getInt("AppUserId", 0);
            coId = pref.getInt("AppCoId", 0);
            Log.e("Co_id : ", "" + coId);
        } catch (Exception e) {
            Log.e("Exception : ", "" + e.getMessage());
        }

        rgPayment = view.findViewById(R.id.rgTripMasterTripSettle_radio);
        lvList = view.findViewById(R.id.lvTripMasterTripSettle_list);
        tvToAccId = view.findViewById(R.id.tvTripMasterTripSettle_ToAccId);
        tvLabelFishCatch = view.findViewById(R.id.tvTripMasterTripSettle_LabelFishCatch);
        tvLabelUnsettledAmt = view.findViewById(R.id.tvTripMasterTripSettle_LabelUnsettleAmt);
        tvFishCatch = view.findViewById(R.id.tvTripMasterTripSettle_FishCatch);
        tvUnsettledAmt = view.findViewById(R.id.tvTripMasterTripSettle_UnsettleAmt);
        edAmount = view.findViewById(R.id.edTripMasterTripSettle_Amount);
        edToAcc = view.findViewById(R.id.edTripMasterTripSettle_ToAcc);
        textToAcc = view.findViewById(R.id.textTripMasterTripSettle_ToAcc);
        rbCash = view.findViewById(R.id.rbTripMasterTripSettle_Cash);
        rbCredit = view.findViewById(R.id.rbTripMasterTripSettle_Credit);
        btnPay = view.findViewById(R.id.btnTripMasterTripSettle_Pay);
        btnSettle = view.findViewById(R.id.btnTripMasterTripSettle_Settle);
        btnClear = view.findViewById(R.id.btnTripMasterTripSettle_Clear);
        llToAcc = view.findViewById(R.id.llTripMasterTripSettle_ToAcc);

        tvLabelFishCatch.setTypeface(boldFont);
        tvLabelUnsettledAmt.setTypeface(boldFont);
        tvFishCatch.setTypeface(boldFont);
        tvUnsettledAmt.setTypeface(boldFont);
        edAmount.setTypeface(lightFont);
        edToAcc.setTypeface(lightFont);
        textToAcc.setTypeface(lightFont);
        rbCash.setTypeface(lightFont);
        rbCredit.setTypeface(lightFont);
        btnPay.setTypeface(lightFont);
        btnSettle.setTypeface(lightFont);
        btnClear.setTypeface(lightFont);

        getToAccountData();

        rbCash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    llToAcc.setVisibility(View.VISIBLE);
                    edToAcc.setText("");
                    tvToAccId.setText("");
                    edAmount.setText("");
                }
            }
        });

        rbCredit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    llToAcc.setVisibility(View.GONE);
                    edToAcc.setText("");
                    tvToAccId.setText("");
                    edAmount.setText("");
                    for (int i = 0; i < receiverAccountArray.size(); i++) {
                        if (receiverAccountArray.get(i).getAccType().equalsIgnoreCase("Auctioner")) {
                            edToAcc.setText("" + receiverAccountArray.get(i).getAccName());
                            tvToAccId.setText("" + receiverAccountArray.get(i).getAccId());
                            // Toast.makeText(getContext(), "" + receiverAccountArray.get(i).getAccName() + "\n" + receiverAccountArray.get(i).getAccId(), Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
            }
        });

        edToAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        final ArrayList<TripSettleTransactions> trList = new ArrayList<>();

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!rbCash.isChecked() && !rbCredit.isChecked()) {
                    Toast.makeText(getContext(), "please select payment mode", Toast.LENGTH_SHORT).show();
                } else if (rbCash.isChecked()) {
                    if (tvToAccId.getText().toString().isEmpty()) {
                        Toast.makeText(getContext(), "please select To account", Toast.LENGTH_SHORT).show();
                        edToAcc.requestFocus();
                    } else if (edAmount.getText().toString().isEmpty()) {
                        Toast.makeText(getContext(), "please enter amount", Toast.LENGTH_SHORT).show();
                        edAmount.requestFocus();
                    } else if (remainingAmount < Double.parseDouble(edAmount.getText().toString())) {
                        Toast.makeText(getContext(), "Amount not available", Toast.LENGTH_SHORT).show();
                    } else if (remainingAmount == 0) {
                        Toast.makeText(getContext(), "Amount not available", Toast.LENGTH_SHORT).show();
                    } else {
                        toAccIdArray.add(Long.parseLong(tvToAccId.getText().toString()));
                        toAccNameArray.add(edToAcc.getText().toString());
                        paymentModeArray.add("Cash");
                        amountArray.add(Double.parseDouble(edAmount.getText().toString()));
                        remainingAmount = remainingAmount - (Integer.parseInt(edAmount.getText().toString()));
                        tvUnsettledAmt.setText("" + remainingAmount);
                        lvList.setAdapter(adapter);

                        TripSettleTransactions bean = new TripSettleTransactions(Long.parseLong(tvToAccId.getText().toString()), Double.parseDouble(edAmount.getText().toString()), tripId, coId, userId, seasonId);
                        trList.add(bean);

                        //---------Clear fields----------
                        tvToAccId.setText("");
                        edToAcc.setText("");
                        rgPayment.clearCheck();
                        edAmount.setText("");
                        llToAcc.setVisibility(View.GONE);
                    }
                } else if (rbCredit.isChecked()) {
                    if (tvToAccId.getText().toString().isEmpty()) {
                        Toast.makeText(getContext(), "To Account not found", Toast.LENGTH_SHORT).show();
                        edToAcc.requestFocus();
                    } else if (edAmount.getText().toString().isEmpty()) {
                        Toast.makeText(getContext(), "please enter amount", Toast.LENGTH_SHORT).show();
                        edAmount.requestFocus();
                    } else if (remainingAmount < Double.parseDouble(edAmount.getText().toString())) {
                        Toast.makeText(getContext(), "Amount not available", Toast.LENGTH_SHORT).show();
                    } else if (remainingAmount == 0) {
                        Toast.makeText(getContext(), "Amount not available", Toast.LENGTH_SHORT).show();
                    } else {
                        toAccIdArray.add(Long.parseLong(tvToAccId.getText().toString()));
                        toAccNameArray.add(edToAcc.getText().toString());
                        paymentModeArray.add("Credit");
                        amountArray.add(Double.parseDouble(edAmount.getText().toString()));
                        remainingAmount = remainingAmount - (Integer.parseInt(edAmount.getText().toString()));
                        tvUnsettledAmt.setText("" + remainingAmount);
                        lvList.setAdapter(adapter);

                        TripSettleTransactions bean = new TripSettleTransactions(Long.parseLong(tvToAccId.getText().toString()), Double.parseDouble(edAmount.getText().toString()), tripId, coId, userId, seasonId);
                        trList.add(bean);

                        //---------Clear_Fields-----------
                        tvToAccId.setText("");
                        edToAcc.setText("");
                        rgPayment.clearCheck();
                        edAmount.setText("");
                        llToAcc.setVisibility(View.GONE);
                    }
                }
            }
        });

        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, toAccNameArray) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                LayoutInflater inflater = getActivity().getLayoutInflater();
                View v = inflater.inflate(R.layout.custom_trip_settle_layout, null);
                Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
                Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");

                TextView tvSrno = (TextView) v.findViewById(R.id.tvLayoutTripSettle_SrNo);
                TextView tvName = (TextView) v.findViewById(R.id.tvLayoutTripSettle_ToAcc);
                TextView tvType = (TextView) v.findViewById(R.id.tvLayoutTripSettle_Type);
                TextView tvAmount = (TextView) v.findViewById(R.id.tvLayoutTripSettle_Amount);

                tvSrno.setTypeface(lightFont);
                tvName.setTypeface(lightFont);
                tvType.setTypeface(lightFont);
                tvAmount.setTypeface(lightFont);

                tvSrno.setText("" + (position + 1));
                tvName.setText("" + toAccNameArray.get(position));
                tvType.setText("" + paymentModeArray.get(position));
                tvAmount.setText("" + amountArray.get(position));


                return v;
            }
        };
        lvList.setAdapter(adapter);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetData();
            }
        });

        btnSettle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Double.parseDouble(tvUnsettledAmt.getText().toString()) > 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                    builder.setTitle("Alert");
                    builder.setCancelable(false);
                    builder.setMessage("You cannot settle and close trip unsless unsettled amount is cleared");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                    builder.setTitle("Confirm Action");
                    builder.setMessage("Are you sure you want to settle and close trip?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            settleAndCloseTrip(tripId, trList);
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
                }
            }
        });


        return view;
    }

    public void resetData() {
        tvToAccId.setText("");
        edToAcc.setText("");
        rgPayment.clearCheck();
        edAmount.setText("");
        llToAcc.setVisibility(View.GONE);
        toAccIdArray.clear();
        toAccNameArray.clear();
        paymentModeArray.clear();
        amountArray.clear();
        lvList.setAdapter(adapter);
        rbCash.requestFocus();

    }


    public void showDialog() {
        dialog = new Dialog(getContext(), android.R.style.Theme_Light_NoTitleBar);
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = li.inflate(R.layout.transaction_account_dialog, null, false);
        dialog.setContentView(v);
        dialog.setCancelable(true);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        ListView list1 = (ListView) dialog.findViewById(R.id.lvTransactionDialog_AccountList);
        myAdapter = new CustomCashAccAdapter(getActivity().getApplicationContext(), receiverAccountArray);
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

    public class CustomCashAccAdapter extends BaseAdapter implements Filterable {

        private ArrayList<SenderAccount> originalValues;
        private ArrayList<SenderAccount> displayedValues;
        LayoutInflater inflater;

        public CustomCashAccAdapter(Context context, ArrayList<SenderAccount> showAccountsList) {
            this.originalValues = showAccountsList;
            this.displayedValues = showAccountsList;
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
            LinearLayout llAccount;
        }

        @Override
        public View getView(final int position, View v, ViewGroup parent) {
            CustomCashAccAdapter.ViewHolder holder = null;

            if (v == null) {
                v = inflater.inflate(R.layout.custom_transaction_dialog_layout, null);
                holder = new CustomCashAccAdapter.ViewHolder();
                holder.tvName = v.findViewById(R.id.tvDialog_Name);
                holder.tvAmount = v.findViewById(R.id.tvDialog_Amount);
                holder.llAccount = v.findViewById(R.id.llDialog_Account);
                v.setTag(holder);
            } else {
                holder = (CustomCashAccAdapter.ViewHolder) v.getTag();
            }


            Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
            Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");

            holder.tvName.setTypeface(lightFont);
            holder.tvAmount.setTypeface(boldFont);

            holder.tvName.setText("" + displayedValues.get(position).getAccName());
            if (displayedValues.get(position).getAccType().equalsIgnoreCase("Default")) {
                holder.tvAmount.setText("");
            } else {
                holder.tvAmount.setText("" + displayedValues.get(position).getAccType());
            }

            holder.llAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    edToAcc.setText("" + displayedValues.get(position).getAccName());
                    tvToAccId.setText("" + displayedValues.get(position).getAccId());
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
                    ArrayList<SenderAccount> filteredArrayList = new ArrayList<>();
                    if (originalValues == null) {
                        originalValues = new ArrayList<SenderAccount>(displayedValues);
                    }

                    if (charSequence == null || charSequence.length() == 0) {
                        results.count = originalValues.size();
                        results.values = originalValues;
                    } else {
                        charSequence = charSequence.toString().toLowerCase();
                        for (int i = 0; i < originalValues.size(); i++) {
                            String accId = "" + originalValues.get(i).getAccId();
                            String accName = originalValues.get(i).getAccName();
                            String accType = originalValues.get(i).getAccType();
                            if (accId.toLowerCase().startsWith(charSequence.toString()) || accName.toLowerCase().startsWith(charSequence.toString()) || accType.toLowerCase().startsWith(charSequence.toString())) {
                                filteredArrayList.add(new SenderAccount(originalValues.get(i).getAccId(), originalValues.get(i).getAccName(), originalValues.get(i).getAccType(), originalValues.get(i).getAccAmount()));
                            }
                        }
                        results.count = filteredArrayList.size();
                        results.values = filteredArrayList;
                    }

                    return results;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    displayedValues = (ArrayList<SenderAccount>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
            return filter;
        }

    }

    public void getToAccountData() {

        if (CheckNetwork.isInternetAvailable(getContext())) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            InterfaceApi api = retrofit.create(InterfaceApi.class);
            Call<TransactionAccountData> transactionAccountDataCall = api.allAccountForTripSettle(tripId);

            progressBar1 = new ProgressDialog(getContext());
            progressBar1.setCancelable(false);
            progressBar1.setMessage("please wait....");
            progressBar1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar1.setProgress(0);
            progressBar1.setMax(100);
            progressBar1.show();
            transactionAccountDataCall.enqueue(new Callback<TransactionAccountData>() {
                @Override
                public void onResponse(Call<TransactionAccountData> call, Response<TransactionAccountData> response) {
                    try {
                        if (response.body() != null) {
                            TransactionAccountData data = response.body();
                            if (data.getErrorMessage().getError()) {
                                progressBar1.dismiss();
                                Log.e("ON RESPONSE : ", "ERROR : " + data.getErrorMessage().getMessage());
                            } else {
                                receiverAccountArray.clear();
                                for (int i = 0; i < data.getSenderAccount().size(); i++) {
                                    receiverAccountArray.add(i, data.getSenderAccount().get(i));
                                }
                                Log.e("ON RESPONSE : ", "Receiver Account Array : " + receiverAccountArray);

                                tvFishCatch.setText("" + data.getSellCount() + "/-");
                                remainingAmount = data.getSellCount();
                                tvUnsettledAmt.setText("" + remainingAmount);

                                progressBar1.dismiss();
                            }
                        } else {
                            progressBar1.dismiss();
                            Log.e("ON RESPONSE : ", "NO DATA");
                        }
                    } catch (Exception e) {
                        progressBar1.dismiss();
                        Log.e("TRIP SETTLE : ", " EXCEPTION : " + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<TransactionAccountData> call, Throwable t) {
                    progressBar1.dismiss();
                    Log.e("ON Failure : ", "ERROR : " + t.getMessage());
                }
            });
        } else {
            Log.e("Check Connectivity : ", " : NO Internet");
        }
    }

    public void settleAndCloseTrip(long tId, ArrayList<TripSettleTransactions> listArray) {

        if (CheckNetwork.isInternetAvailable(getContext())) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            InterfaceApi api = retrofit.create(InterfaceApi.class);
            Call<ErrorMessage> errorMessageCall = api.tripSettle(tripId, listArray);

            progressBar2 = new ProgressDialog(getContext());
            progressBar2.setCancelable(false);
            progressBar2.setMessage("please wait....");
            progressBar2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar2.setProgress(0);
            progressBar2.setMax(100);
            progressBar2.show();

            errorMessageCall.enqueue(new Callback<ErrorMessage>() {
                @Override
                public void onResponse(Call<ErrorMessage> call, Response<ErrorMessage> response) {
                    try {
                        if (response.body() != null) {
                            ErrorMessage errorMessage = response.body();
                            if (errorMessage.getError()) {
                                progressBar2.dismiss();
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                                builder.setCancelable(false);
                                builder.setTitle("Error");
                                builder.setMessage("" + errorMessage.getMessage());
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            } else {
                                progressBar2.dismiss();
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                                builder.setCancelable(false);
                                builder.setMessage("Success");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        resetData();
                                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                        ft.replace(R.id.content_frame, new TripMasterFragment());
                                        ft.commit();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }

                        } else {
                            progressBar2.dismiss();
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

                    } catch (Exception e) {
                        progressBar2.dismiss();
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
                public void onFailure(Call<ErrorMessage> call, Throwable t) {
                    progressBar2.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                    builder.setTitle("Server Error");
                    builder.setCancelable(false);
                    builder.setMessage("Something went wrong!");
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
            Log.e("Check Connectivity : ", " : NO Internet");
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

