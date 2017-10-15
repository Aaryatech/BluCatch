package com.ats.blucatch.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ats.blucatch.R;
import com.ats.blucatch.activity.MainActivity;
import com.ats.blucatch.utils.InterfaceApi;

public class AddTransactionFragment extends Fragment {

    int userId, coId;
    private TextInputLayout textFromAcc, textToAcc, textAmount, textRemark;
    private EditText edFromAcc, edToAcc, edAmount, edRemark;
    private Button btnSave, btnReset;
    private ProgressDialog progressBar, progressBar1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_transaction, container, false);
        Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
        Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");
        MainActivity.tvTitle.setText("Add Transaction");
        MainActivity.tvTitle.setTypeface(boldFont);

        SharedPreferences pref = getContext().getSharedPreferences(InterfaceApi.MY_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        userId = pref.getInt("AppUserId", 0);
        coId = pref.getInt("AppCoId", 0);
        Log.e("Co_id : ", "" + coId);

        textFromAcc = view.findViewById(R.id.textAddTransaction_FromAcc);
        textToAcc = view.findViewById(R.id.textAddTransaction_ToAcc);
        textAmount = view.findViewById(R.id.textAddTransaction_Amount);
        textRemark = view.findViewById(R.id.textAddTransaction_Remark);

        edFromAcc = view.findViewById(R.id.edAddTransaction_FromAcc);
        edToAcc = view.findViewById(R.id.edAddTransaction_ToAcc);
        edAmount = view.findViewById(R.id.edAddTransaction_Amount);
        edRemark = view.findViewById(R.id.edAddTransaction_Remark);

        btnSave = view.findViewById(R.id.btnAddTransaction_save);
        btnReset = view.findViewById(R.id.btnAddTransaction_reset);

        textFromAcc.setTypeface(lightFont);
        textToAcc.setTypeface(lightFont);
        textAmount.setTypeface(lightFont);
        textRemark.setTypeface(lightFont);
        edFromAcc.setTypeface(lightFont);
        edToAcc.setTypeface(lightFont);
        edAmount.setTypeface(lightFont);
        edRemark.setTypeface(lightFont);
        btnSave.setTypeface(lightFont);
        btnReset.setTypeface(lightFont);

        return view;
    }

   /* public void addNewTransaction() {
        if (CheckNetwork.isInternetAvailable(getContext())) {

            if (tvFromAccId.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "please select From account", Toast.LENGTH_SHORT).show();
                edFromAcc.requestFocus();
            } else if (tvToAccId.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "please select To account", Toast.LENGTH_SHORT).show();
                edFromAcc.requestFocus();
            } else {
                Transaction transaction = null;
                long fromAcc = Long.parseLong(tvFromAccId.getText().toString().trim());
                long toAcc = Long.parseLong(tvToAccId.getText().toString().trim());
                String remark = edRemark.getText().toString();
                String expType = edToAcc.getText().toString();
                //long expId = Long.parseLong(tvExpId.getText().toString().trim());
                boolean status = false;
                long expId;
                if (!tvToAccEntry.getText().toString().equalsIgnoreCase("na")) {
                    expId = Long.parseLong(tvToAccId.getText().toString().trim());
                } else {
                    expId = -1;
                }

                if (llAmt.getVisibility() == View.VISIBLE) {
                    if (edAmt.getText().toString().isEmpty()) {
                        Toast.makeText(getContext(), "please enter amount", Toast.LENGTH_SHORT).show();
                        edAmt.requestFocus();
                    } else {
                        double amt = Double.parseDouble(edAmt.getText().toString().trim());
                        if (amt > Double.parseDouble(tvFromAccAmt.getText().toString())) {
                            //Toast.makeText(getContext(), "insufficient amount", Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                            builder.setCancelable(false);
                            builder.setMessage("insufficient amount");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        } else {
                            transaction = new Transaction(seasonId, 0, fromAcc, toAcc, amt, "", tripId, expType, 0, 0, 0, 0, "na", remark, imageEncoded_1, imageEncoded_2, imageEncoded_3, userId, 0, 2, "na", 0, 0, "na", 0, 0, expId, coId, 0, userId, 1, boatId);
                        }
                    }
                } else if (llRQT.getVisibility() == View.VISIBLE) {
                    if (edRQTRate.getText().toString().isEmpty()) {
                        Toast.makeText(getContext(), "please enter Rate", Toast.LENGTH_SHORT).show();
                        edRQTRate.requestFocus();
                    } else if (edRQTQty.getText().toString().isEmpty()) {
                        Toast.makeText(getContext(), "please enter Quantity", Toast.LENGTH_SHORT).show();
                        edRQTQty.requestFocus();
                    } else if (edRQTTotal.getText().toString().isEmpty()) {
                        Toast.makeText(getContext(), "please enter Total", Toast.LENGTH_SHORT).show();
                        edRQTTotal.requestFocus();
                    } else {
                        double rate = Double.parseDouble(edRQTRate.getText().toString().trim());
                        int qty = Integer.parseInt(edRQTQty.getText().toString().trim());
                        double total = rate * qty;

                        if (total > Double.parseDouble(tvFromAccAmt.getText().toString())) {
                            //                            Toast.makeText(getContext(), "insufficient amount", Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                            builder.setCancelable(false);
                            builder.setMessage("insufficient amount");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();

                        } else {
                            transaction = new Transaction(seasonId, 0, fromAcc, toAcc, total, "", tripId, expType, rate, qty, total, 0, "na", remark, imageEncoded_1, imageEncoded_2, imageEncoded_3, userId, 0, 2, "na", 0, 0, "na", 0, 0, expId, coId, 0, userId, 1, boatId);
                        }
                    }
                } else if (llRQTM.getVisibility() == View.VISIBLE) {
                    if (edRQTMRate.getText().toString().isEmpty()) {
                        Toast.makeText(getContext(), "please enter Rate", Toast.LENGTH_SHORT).show();
                        edRQTMRate.requestFocus();
                    } else if (edRQTMQty.getText().toString().isEmpty()) {
                        Toast.makeText(getContext(), "please enter Quantity", Toast.LENGTH_SHORT).show();
                        edRQTMQty.requestFocus();
                    } else if (edRQTMTotal.getText().toString().isEmpty()) {
                        Toast.makeText(getContext(), "please enter Total", Toast.LENGTH_SHORT).show();
                        edRQTMTotal.requestFocus();
                    } else if (edRQTMMisc.getText().toString().isEmpty()) {
                        Toast.makeText(getContext(), "please enter Miscellaneous", Toast.LENGTH_SHORT).show();
                        edRQTMMisc.requestFocus();
                    } else {

                        double rate = Double.parseDouble(edRQTMRate.getText().toString().trim());
                        int qty = Integer.parseInt(edRQTMQty.getText().toString().trim());
                        double total = rate * qty;
                        double misc = Double.parseDouble(edRQTMMisc.getText().toString().trim());
                        if (total > Double.parseDouble(tvFromAccAmt.getText().toString())) {
//                            Toast.makeText(getContext(), "insufficient amount", Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                            builder.setCancelable(false);
                            builder.setMessage("insufficient amount");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();

                        } else {
                            transaction = new Transaction(seasonId, 0, fromAcc, toAcc, total, "", tripId, expType, rate, qty, total, misc, "", remark, imageEncoded_1, imageEncoded_2, imageEncoded_3, userId, 0, 2, "", 0, 0, "", 0, 0, expId, coId, 0, userId, 1, boatId);
                        }
                    }
                } else if (llType.getVisibility() == View.VISIBLE) {
                    if (selectedStringArray.size() <= 0) {
                        Toast.makeText(getContext(), "please select type", Toast.LENGTH_SHORT).show();
                        lvType.requestFocus();
                    } else if (edTypeAmt.getText().toString().isEmpty()) {
                        Toast.makeText(getContext(), "please enter Amount", Toast.LENGTH_SHORT).show();
                        edTypeAmt.requestFocus();
                    }
                    *//*else if (llPhoto.getVisibility() == View.VISIBLE) {
                        if (tvPhoto1.getText().toString().isEmpty()) {
                            Toast.makeText(getContext(), "please select photo 1", Toast.LENGTH_SHORT).show();
                            btnPhoto1.requestFocus();
                        } else if (tvPhoto2.getText().toString().isEmpty()) {
                            Toast.makeText(getContext(), "please select photo 2", Toast.LENGTH_SHORT).show();
                            btnPhoto2.requestFocus();
                        } else if (tvPhoto3.getText().toString().isEmpty()) {
                            Toast.makeText(getContext(), "please select photo 3", Toast.LENGTH_SHORT).show();
                            btnPhoto3.requestFocus();
                        }
                    }*//*
                    else {

                        double amt = Double.parseDouble(edTypeAmt.getText().toString().trim());
                        if (amt > Double.parseDouble(tvFromAccAmt.getText().toString())) {
//                            Toast.makeText(getContext(), "insufficient amount", Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                            builder.setCancelable(false);
                            builder.setMessage("insufficient amount");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();

                        } else {
                            transaction = new Transaction(seasonId, 0, fromAcc, toAcc, amt, "", tripId, expType, 0, 0, 0, 0, selectedStringArray.toString(), remark, imageEncoded_1, imageEncoded_2, imageEncoded_3, userId, 0, 2, "", 0, 0, "", 0, 0, expId, coId, 0, userId, 1, boatId);
                        }
                    }
                } else {
                    transaction = new Transaction(seasonId, 0, fromAcc, toAcc, 5456, "tr_type", 4000, "exp_type", 55, 23, 56633, 54545, "dsfdsfds", "sdfdsfds", "", "", "", userId, 0, 2, "na", 0, 0, "na", 0, 0, -1, coId, 0, userId, 1, boatId);
                }


                Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                InterfaceApi api = retrofit.create(InterfaceApi.class);
                if (transaction == null) {
                    Log.e("BEAN NULL : ", " : something went wrong please try again.");
                    //Toast.makeText(getContext(), "something went wrong please try again.", Toast.LENGTH_SHORT).show();

                } else {
                    Log.e("TRANSACTION BEAN : ", " : " + transaction.toString());
                    Call<ErrorMessage> errorMessageCall = api.addNewTransaction(transaction);

                    pbTransaction = new ProgressDialog(getContext());
                    pbTransaction.setCancelable(false);
                    pbTransaction.setMessage("please wait....");
                    pbTransaction.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    pbTransaction.setProgress(0);
                    pbTransaction.setMax(100);
                    pbTransaction.show();

                    errorMessageCall.enqueue(new Callback<ErrorMessage>() {
                        @Override
                        public void onResponse(Call<ErrorMessage> call, Response<ErrorMessage> response) {
                            try {
                                if (response.body() != null) {
                                    ErrorMessage data = response.body();
                                    if (data.getError()) {
                                        pbTransaction.dismiss();
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
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
                                    } else {
                                        pbTransaction.dismiss();
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                                        builder.setTitle("Success");
                                        builder.setCancelable(false);
                                        builder.setMessage("Transaction success.");
                                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                getFromToAccountData(userType);
                                                resetData();
                                            }
                                        });
                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                    }

                                } else {
                                    pbTransaction.dismiss();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                                    builder.setCancelable(false);
                                    builder.setTitle("Error");
                                    builder.setMessage("Transaction failed!");
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
                                pbTransaction.dismiss();
                                Log.e("Exception : ", "" + e.getMessage());
                            }
                        }

                        @Override
                        public void onFailure(Call<ErrorMessage> call, Throwable t) {
                            pbTransaction.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
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
                        }
                    });
                }

            }
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
            builder.setCancelable(false);
            builder.setTitle("Check Connectivity");
            builder.setMessage("please check internet connection.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            Log.e("Check Connectivity : ", " : NO Internet");
        }
    }*/

}
