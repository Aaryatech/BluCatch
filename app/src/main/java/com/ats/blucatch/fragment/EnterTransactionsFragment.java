package com.ats.blucatch.fragment;


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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.blucatch.R;
import com.ats.blucatch.activity.MainActivity;
import com.ats.blucatch.bean.ExpensesData;
import com.ats.blucatch.utils.CheckNetwork;
import com.ats.blucatch.utils.InterfaceApi;
import com.ats.blucatch.utils.MySpinnerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EnterTransactionsFragment extends Fragment {

    int userId, coId;
    private TextInputLayout textFromAcc, textToAcc, textRQTRate, textRQTQty, textRQTTotal, textRQTMRate, textRQTMQty, textRQTMTotal, textRQTMMisc, textAmount, textAmtOfType;
    private TextView tvLabelExpType, tvLabelComboType, tvExpId;
    private EditText edFromAcc, edToAcc, edRQTRate, edRQTQty, edRQTTotal, edRQTMRate, edRQTMQty, edRQTMTotal, edRQTMMisc, edAmt, edTypeAmt;
    private LinearLayout llRQT, llRQTM, llAmt, llType;
    private ListView lvType;
    private Spinner spExpType;
    private Button btnSave, btnReset;
    private ProgressDialog progressBar, progressBar1;
    private ArrayList<String> expNameArray = new ArrayList<>();
    private ArrayList<String> expEntryArray = new ArrayList<>();
    private ArrayList<String> expComboTypeArray = new ArrayList<>();
    private ArrayList<Long> expIdArray = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ExpenseMasterFragment.MyAdapter adapter1;
    List<String> strList = new ArrayList<String>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enter_transactions, container, false);
        Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
        Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");
        MainActivity.tvTitle.setText("Enter Expenses");
        MainActivity.tvTitle.setTypeface(boldFont);

        SharedPreferences pref = getContext().getSharedPreferences(InterfaceApi.MY_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        userId = pref.getInt("AppUserId", 0);
        coId = pref.getInt("AppCoId", 0);
        Log.e("Co_id : ", "" + coId);

        tvExpId = view.findViewById(R.id.tvEnterExp_ExpId);
        tvLabelExpType = view.findViewById(R.id.tvEnterExp_LabelExpType);
        tvLabelComboType = view.findViewById(R.id.tvEnterExp_LabelComboType);
        lvType = view.findViewById(R.id.lvEnterExp_Type);
        spExpType = view.findViewById(R.id.spEnterExp_ExpType);
        edFromAcc = view.findViewById(R.id.edEnterExp_FromAcc);
        edToAcc = view.findViewById(R.id.edEnterExp_ToAcc);
        edRQTRate = view.findViewById(R.id.edEnterExp_RTQRate);
        edRQTQty = view.findViewById(R.id.edEnterExp_RQTQty);
        edRQTTotal = view.findViewById(R.id.edEnterExp_RQTTotal);
        edRQTMRate = view.findViewById(R.id.edEnterExp_RQTMRate);
        edRQTMQty = view.findViewById(R.id.edEnterExp_RQTMQty);
        edRQTMTotal = view.findViewById(R.id.edEnterExp_RQTMTotal);
        edRQTMMisc = view.findViewById(R.id.edEnterExp_RQTMMisc);
        edAmt = view.findViewById(R.id.edEnterExp_Amount);
        edTypeAmt = view.findViewById(R.id.edEnterExp_AmountOfType);

        textFromAcc = view.findViewById(R.id.textEnterExp_FromAcc);
        textToAcc = view.findViewById(R.id.textEnterExp_ToAcc);
        textRQTRate = view.findViewById(R.id.textEnterExp_RQTRate);
        textRQTQty = view.findViewById(R.id.textEnterExp_RQTQty);
        textRQTTotal = view.findViewById(R.id.textEnterExp_RQTTotal);
        textRQTMRate = view.findViewById(R.id.textEnterExp_RQTMRate);
        textRQTMQty = view.findViewById(R.id.textEnterExp_RQTMQty);
        textRQTMTotal = view.findViewById(R.id.textEnterExp_RQTMTotal);
        textRQTMMisc = view.findViewById(R.id.textEnterExp_RQTMMisc);
        textAmount = view.findViewById(R.id.textEnterExp_Amount);
        textAmtOfType = view.findViewById(R.id.textEnterExp_AmountOfType);

        textFromAcc.setTypeface(lightFont);
        textToAcc.setTypeface(lightFont);
        textRQTRate.setTypeface(lightFont);
        textRQTQty.setTypeface(lightFont);
        textRQTTotal.setTypeface(lightFont);
        textRQTMRate.setTypeface(lightFont);
        textRQTMQty.setTypeface(lightFont);
        textRQTMTotal.setTypeface(lightFont);
        textRQTMMisc.setTypeface(lightFont);
        textAmount.setTypeface(lightFont);
        textAmtOfType.setTypeface(lightFont);

        btnSave = view.findViewById(R.id.btnEnterExp_save);
        btnReset = view.findViewById(R.id.btnEnterExp_reset);

        btnSave.setTypeface(lightFont);
        btnReset.setTypeface(lightFont);
        tvLabelExpType.setTypeface(lightFont);
        tvLabelComboType.setTypeface(lightFont);
        edFromAcc.setTypeface(lightFont);
        edToAcc.setTypeface(lightFont);
        edRQTRate.setTypeface(lightFont);
        edRQTQty.setTypeface(lightFont);
        edRQTTotal.setTypeface(lightFont);
        edRQTMRate.setTypeface(lightFont);
        edRQTMQty.setTypeface(lightFont);
        edRQTMTotal.setTypeface(lightFont);
        edRQTMMisc.setTypeface(lightFont);
        edAmt.setTypeface(lightFont);
        edTypeAmt.setTypeface(lightFont);

        llRQT = view.findViewById(R.id.llEnterExp_RQT);
        llRQTM = view.findViewById(R.id.llEnterExp_RQTM);
        llAmt = view.findViewById(R.id.llEnterExp_Amt);
        llType = view.findViewById(R.id.llEnterExp_TypeAmt);

        getAllExpenseData();

        spExpType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getContext(), "ID : " + i, Toast.LENGTH_SHORT).show();
                if (spExpType.getSelectedItemPosition() == 0) {
                    tvExpId.setText("");
                    llRQT.setVisibility(View.GONE);
                    llRQTM.setVisibility(View.GONE);
                    llAmt.setVisibility(View.GONE);
                    llType.setVisibility(View.GONE);
                } else if (expEntryArray.get(i).toString().equalsIgnoreCase("Rate-Qty-Total")) {
                    tvExpId.setText("" + expIdArray.get(i));
                    llRQT.setVisibility(View.VISIBLE);
                    llRQTM.setVisibility(View.GONE);
                    llAmt.setVisibility(View.GONE);
                    llType.setVisibility(View.GONE);
                } else if (expEntryArray.get(i).toString().equalsIgnoreCase("Rate-Qty-Total-Misc")) {
                    tvExpId.setText("" + expIdArray.get(i));
                    llRQT.setVisibility(View.GONE);
                    llRQTM.setVisibility(View.VISIBLE);
                    llAmt.setVisibility(View.GONE);
                    llType.setVisibility(View.GONE);
                } else if (expEntryArray.get(i).toString().equalsIgnoreCase("Type-Amount")) {
                    tvExpId.setText("" + expIdArray.get(i));
                    llRQT.setVisibility(View.GONE);
                    llRQTM.setVisibility(View.GONE);
                    llAmt.setVisibility(View.GONE);
                    llType.setVisibility(View.VISIBLE);
                    if (expComboTypeArray.get(i) == null) {
                        Toast.makeText(getContext(), "No Data Found for this Expense", Toast.LENGTH_SHORT).show();
                        lvType.setVisibility(View.GONE);
                        setAdapterData("");
                    } else {
                        lvType.setVisibility(View.VISIBLE);
                        setAdapterData(expComboTypeArray.get(i));
                    }
                } else if (expEntryArray.get(i).toString().equalsIgnoreCase("Amount")) {
                    tvExpId.setText("" + expIdArray.get(i));
                    llRQT.setVisibility(View.GONE);
                    llRQTM.setVisibility(View.GONE);
                    llAmt.setVisibility(View.VISIBLE);
                    llType.setVisibility(View.GONE);
                }
            }
            //Rate-Qty-Total, Type-Amount, Rate-Qty-Total, Rate-Qty-Total-Misc, Type-Amount, Type-Amount

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetData();
            }
        });

        return view;
    }

    public void addNewTransaction() {
        if (CheckNetwork.isInternetAvailable(getContext())) {

            String fromAcc = edFromAcc.getText().toString().trim();
            String toAcc = edToAcc.getText().toString().trim();
            String epType = spExpType.getSelectedItem().toString();
            long expId = Long.parseLong(tvExpId.getText().toString().trim());
            double RQT_Rate = Double.parseDouble(edRQTRate.getText().toString().trim());
            int RQT_Qty = Integer.parseInt(edRQTQty.getText().toString().trim());
            double RQT_Total = Double.parseDouble(edRQTTotal.getText().toString().trim());
            double RQTM_Rate = Double.parseDouble(edRQTMRate.getText().toString().trim());
            int RQTM_Qty = Integer.parseInt(edRQTMQty.getText().toString().trim());
            double RQTM_Total = Double.parseDouble(edRQTMTotal.getText().toString().trim());
            String RQTM_Misc = edRQTMMisc.getText().toString().trim();
            double amt = Double.parseDouble(edAmt.getText().toString().trim());
            double typeAmt = Double.parseDouble(edTypeAmt.getText().toString().trim());
            

            Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            InterfaceApi api = retrofit.create(InterfaceApi.class);

            progressBar1 = new ProgressDialog(getContext());
            progressBar1.setCancelable(false);
            progressBar1.setMessage("please wait....");
            progressBar1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar1.setProgress(0);
            progressBar1.setMax(100);
            progressBar1.show();


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


    public void getAllExpenseData() {
        if (CheckNetwork.isInternetAvailable(getContext())) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            InterfaceApi api = retrofit.create(InterfaceApi.class);
            Call<ExpensesData> expensesDataCall = api.allExpenseData();

            progressBar = new ProgressDialog(getContext());
            progressBar.setCancelable(false);
            progressBar.setMessage("please wait....");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.setProgress(0);
            progressBar.setMax(100);
            progressBar.show();

            expensesDataCall.enqueue(new Callback<ExpensesData>() {
                @Override
                public void onResponse(Call<ExpensesData> call, Response<ExpensesData> response) {
                    if (response.body() != null) {

                        ExpensesData data = response.body();
                        if (data.getErrorMessage().getError()) {
                            progressBar.dismiss();
                            Toast.makeText(getContext(), "unable to fetch data!", Toast.LENGTH_SHORT).show();
                            Log.e("ON RESPONSE : ", " ERROR : " + data.getErrorMessage().getMessage());
                        } else {
                            expNameArray.clear();
                            expIdArray.clear();
                            expEntryArray.clear();
                            expComboTypeArray.clear();

                            expNameArray.add(0, "Select Expense Type");
                            expIdArray.add(0, 0l);
                            expEntryArray.add(0, "");
                            expComboTypeArray.add(0, "");
                            for (int i = 0, j = 1; i < data.getExpenses().size(); i++) {
                                expNameArray.add(j, data.getExpenses().get(i).getExpName());
                                expIdArray.add(j, data.getExpenses().get(i).getExpId());
                                expEntryArray.add(j, data.getExpenses().get(i).getExpEntryType());
                                expComboTypeArray.add(j, data.getExpenses().get(i).getComboValues());
                                j++;
                            }
                            Log.e("ON RESPONSE : ", " DATA : " + expEntryArray);
                            //setAdapterData();


                            MySpinnerAdapter spAdapter = new MySpinnerAdapter(
                                    getContext(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    expNameArray);
                            spExpType.setAdapter(spAdapter);

                            progressBar.dismiss();
                        }

                    } else {
                        progressBar.dismiss();
                        Toast.makeText(getContext(), "unable to fetch data!", Toast.LENGTH_SHORT).show();
                        Log.e("ON RESPONSE : ", " NO DATA ");
                    }
                }

                @Override
                public void onFailure(Call<ExpensesData> call, Throwable t) {
                    progressBar.dismiss();
                    Toast.makeText(getContext(), "unable to fetch data! server error", Toast.LENGTH_SHORT).show();
                    Log.e("ON Failure : ", " ERROR :  " + t.getMessage());
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

    private void setAdapterData(String data) {

        if (data != null) {
            if (data.equals("")) {
                Log.e("DATA : ", " EMPTY ");
            } else {
                String[] strArray = data.split(",");
                strList = Arrays.asList(strArray);
                Log.e("STR LIST SIZE : ", " : " + strList.size());
                if (strList.size() <= 0) {
                    lvType.setVisibility(View.GONE);
                    Log.e("--------------------", "-----------------------LIST EMPTY---------------");
                } else {
                    lvType.setVisibility(View.VISIBLE);
                    adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, strList) {
                        @NonNull
                        @Override
                        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                            LayoutInflater inflater = getActivity().getLayoutInflater();
                            View v = inflater.inflate(R.layout.custom_transaction_type, null);
                            CheckBox cbType = v.findViewById(R.id.cbTransactionType);

                            Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
                            cbType.setTypeface(lightFont);

                            if (strList.get(position).equals("")) {
                                cbType.setVisibility(View.GONE);
                            } else {
                                cbType.setVisibility(View.VISIBLE);
                                cbType.setText(strList.get(position));
                            }
                            return v;
                        }
                    };
                    lvType.setAdapter(adapter);
                }
            }
        } else {
            adapter.notifyDataSetChanged();
            Toast.makeText(getContext(), "NO DATA!", Toast.LENGTH_SHORT).show();
        }
    }

    public void resetData() {
        spExpType.setSelection(0);
        edFromAcc.setText("");
        edToAcc.setText("");
        edRQTRate.setText("");
        edRQTQty.setText("");
        edRQTTotal.setText("");
        edRQTMRate.setText("");
        edRQTMQty.setText("");
        edRQTMTotal.setText("");
        edRQTMMisc.setText("");
        edAmt.setText("");
        edTypeAmt.setText("");
        edFromAcc.requestFocus();
    }

}
