package com.ats.blucatch.fragment;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.blucatch.R;
import com.ats.blucatch.activity.MainActivity;
import com.ats.blucatch.bean.ErrorMessage;
import com.ats.blucatch.bean.ReceiverAccount;
import com.ats.blucatch.bean.SenderAccount;
import com.ats.blucatch.bean.Transaction;
import com.ats.blucatch.bean.TransactionAccountData;
import com.ats.blucatch.db.MySqliteHelper;
import com.ats.blucatch.utils.CheckNetwork;
import com.ats.blucatch.utils.InterfaceApi;
import com.ats.blucatch.utils.StoreCameraOrGalleryData;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TripMasterEnterTripExpFragment extends Fragment {

    private static final int GALLERY_PICTURE_1 = 101;
    private static final int CAMERA_PICTURE_1 = 102;
    private static final int GALLERY_PICTURE_2 = 201;
    private static final int CAMERA_PICTURE_2 = 202;
    private static final int GALLERY_PICTURE_3 = 301;
    private static final int CAMERA_PICTURE_3 = 302;
    AlertDialog.Builder builder;
    String imageEncoded_1, imageEncoded_2, imageEncoded_3;
    String imageName1;
    File folder = new File(Environment.getExternalStorageDirectory() + File.separator, "BLUCATCH");
    File f;
    Bitmap bitmap1, bitmap2, bitmap3;
    String selectedImagePath;

    MySqliteHelper db;
    int userId, coId, seasonId;
    private TextInputLayout textFromAcc, textToAcc, textRQTRate, textRQTQty, textRQTTotal, textRQTMRate, textRQTMQty, textRQTMTotal, textRQTMMisc, textAmount, textAmtOfType, textRemark;
    private TextView tvLabelExpType, tvLabelComboType, tvExpId, tvPhoto1, tvPhoto2, tvPhoto3, tvFromAccId, tvToAccId, tvToAccEntry, tvToAccAmt, tvToAccPhoto, tvFromAccAmt, tvToAccCombo;
    private EditText edFromAcc, edToAcc, edRQTRate, edRQTQty, edRQTTotal, edRQTMRate, edRQTMQty, edRQTMTotal, edRQTMMisc, edAmt, edTypeAmt, edRemark;
    private LinearLayout llRQT, llRQTM, llAmt, llType, llPhoto;
    private ListView lvType;
    private Spinner spExpType;
    private ImageView ivPhoto1, ivPhoto2, ivPhoto3;
    private Button btnSave, btnReset, btnPhoto1, btnPhoto2, btnPhoto3;
    private ProgressDialog progressBar, progressBar1, pbTransaction;
    private ArrayList<String> expNameArray = new ArrayList<>();
    private ArrayList<String> expEntryArray = new ArrayList<>();
    private ArrayList<String> expComboTypeArray = new ArrayList<>();
    private ArrayList<Integer> expPhotoArray = new ArrayList<>();
    private ArrayList<Double> expAmtArray = new ArrayList<>();
    private ArrayList<Long> expIdArray = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ExpenseMasterFragment.MyAdapter adapter1;
    List<String> strList = new ArrayList<String>();

    private ArrayList<Long> longIdArray = new ArrayList<>();
    private ArrayList<Integer> intPhotoArray = new ArrayList<>();
    private List<String> nameArray = new ArrayList<>();
    private List<String> entryArray = new ArrayList<>();
    private List<String> comboArray = new ArrayList<>();
    private List<String> photoArray = new ArrayList<>();

    private ArrayList<SenderAccount> showAccountArray = new ArrayList<>();
    private ArrayList<ReceiverAccount> receiverAccountArray = new ArrayList<>();
    private MyAdapter myAdapter;
    private MyAdapterRecAcc myAdapterRecAcc;
    Dialog dialog;
    String type;
    long tripId, boatId;
    String userType;

    final List<String> selectedStringArray = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trip_master_enter_trip_exp, container, false);
        Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
        Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");
        MainActivity.tvTitle.setText("Enter Expenses");
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
        MainActivity.isAtTripMasterTripExp = false;
        MainActivity.isAtTripMasterFishSell = false;
        MainActivity.isAtTripMasterEnterTripExp = true;
        MainActivity.isAtTripMasterEnterFishSell = false;

        try {
            SharedPreferences pref = getContext().getSharedPreferences(InterfaceApi.MY_PREF, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            userId = pref.getInt("AppUserId", 0);
            coId = pref.getInt("AppCoId", 0);
            userType = pref.getString("AppUserType", "");
            Log.e("Co_id : ", "" + coId);
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            tripId = getArguments().getLong("User_Trip_ID");
            seasonId = getArguments().getInt("User_Trip_Season_Id");
            boatId = getArguments().getLong("User_Trip_Boat_ID");
            type = getArguments().getString("Expense_Type");
        } catch (Exception e) {
            e.printStackTrace();
        }
        createFolder();
        getFromToAccountData(userType);
        //getAccountList();

        tvExpId = view.findViewById(R.id.tvTripMasterEnterExp_ExpId);
        tvLabelExpType = view.findViewById(R.id.tvTripMasterEnterExp_LabelExpType);
        tvLabelComboType = view.findViewById(R.id.tvTripMasterEnterExp_LabelComboType);
        lvType = view.findViewById(R.id.lvTripMasterEnterExp_Type);
        spExpType = view.findViewById(R.id.spTripMasterEnterExp_ExpType);
        edFromAcc = view.findViewById(R.id.edTripMasterEnterExp_FromAcc);
        edToAcc = view.findViewById(R.id.edTripMasterEnterExp_ToAcc);
        edRQTRate = view.findViewById(R.id.edTripMasterEnterExp_RTQRate);
        edRQTQty = view.findViewById(R.id.edTripMasterEnterExp_RQTQty);
        edRQTTotal = view.findViewById(R.id.edTripMasterEnterExp_RQTTotal);
        edRQTMRate = view.findViewById(R.id.edTripMasterEnterExp_RQTMRate);
        edRQTMQty = view.findViewById(R.id.edTripMasterEnterExp_RQTMQty);
        edRQTMTotal = view.findViewById(R.id.edTripMasterEnterExp_RQTMTotal);
        edRQTMMisc = view.findViewById(R.id.edTripMasterEnterExp_RQTMMisc);
        edAmt = view.findViewById(R.id.edTripMasterEnterExp_Amount);
        edTypeAmt = view.findViewById(R.id.edTripMasterEnterExp_AmountOfType);
        edRemark = view.findViewById(R.id.edTripMasterEnterExp_Remark);

        textRemark = view.findViewById(R.id.textTripMasterEnterExp_Remark);
        textFromAcc = view.findViewById(R.id.textTripMasterEnterExp_FromAcc);
        textToAcc = view.findViewById(R.id.textTripMasterEnterExp_ToAcc);
        textRQTRate = view.findViewById(R.id.textTripMasterEnterExp_RQTRate);
        textRQTQty = view.findViewById(R.id.textTripMasterEnterExp_RQTQty);
        textRQTTotal = view.findViewById(R.id.textTripMasterEnterExp_RQTTotal);
        textRQTMRate = view.findViewById(R.id.textTripMasterEnterExp_RQTMRate);
        textRQTMQty = view.findViewById(R.id.textTripMasterEnterExp_RQTMQty);
        textRQTMTotal = view.findViewById(R.id.textTripMasterEnterExp_RQTMTotal);
        textRQTMMisc = view.findViewById(R.id.textTripMasterEnterExp_RQTMMisc);
        textAmount = view.findViewById(R.id.textTripMasterEnterExp_Amount);
        textAmtOfType = view.findViewById(R.id.textTripMasterEnterExp_AmountOfType);

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
        textRemark.setTypeface(lightFont);

        tvFromAccId = view.findViewById(R.id.tvTripMasterEnterExp_FromAccId);
        tvToAccId = view.findViewById(R.id.tvTripMasterEnterExp_ToAccId);
        tvToAccEntry = view.findViewById(R.id.tvTripMasterEnterExp_ToAccEntry);
        tvToAccAmt = view.findViewById(R.id.tvTripMasterEnterExp_ToAccAmount);
        tvToAccPhoto = view.findViewById(R.id.tvTripMasterEnterExp_ToAccPhoto);
        tvFromAccAmt = view.findViewById(R.id.tvTripMasterEnterExp_FromAccAmount);
        tvToAccCombo = view.findViewById(R.id.tvTripMasterEnterExp_ToAccCombo);

        tvPhoto1 = view.findViewById(R.id.tvTripMasterEnterExp_Photo1);
        tvPhoto2 = view.findViewById(R.id.tvTripMasterEnterExp_Photo2);
        tvPhoto3 = view.findViewById(R.id.tvTripMasterEnterExp_Photo3);

        ivPhoto1 = view.findViewById(R.id.ivTripMasterEnterExp_Photo1);
        ivPhoto2 = view.findViewById(R.id.ivTripMasterEnterExp_Photo2);
        ivPhoto3 = view.findViewById(R.id.ivTripMasterEnterExp_Photo3);

        btnPhoto1 = view.findViewById(R.id.btnTripMasterEnterExp_Photo1);
        btnPhoto2 = view.findViewById(R.id.btnTripMasterEnterExp_Photo2);
        btnPhoto3 = view.findViewById(R.id.btnTripMasterEnterExp_Photo3);
        btnSave = view.findViewById(R.id.btnTripMasterEnterExp_save);
        btnReset = view.findViewById(R.id.btnTripMasterEnterExp_reset);

        btnPhoto1.setTypeface(lightFont);
        btnPhoto2.setTypeface(lightFont);
        btnPhoto3.setTypeface(lightFont);
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
        edRemark.setTypeface(lightFont);

        llRQT = view.findViewById(R.id.llTripMasterEnterExp_RQT);
        llRQTM = view.findViewById(R.id.llTripMasterEnterExp_RQTM);
        llAmt = view.findViewById(R.id.llTripMasterEnterExp_Amt);
        llType = view.findViewById(R.id.llTripMasterEnterExp_TypeAmt);
        llPhoto = view.findViewById(R.id.llTripMasterEnterExp_Photo);

        // getAllExpenseData();

        edFromAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("From");
            }
        });
        edToAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("To");
            }
        });

        tvToAccEntry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (tvToAccEntry.getText().toString().equalsIgnoreCase("Rate-Qty-Total")) {
                    llRQT.setVisibility(View.VISIBLE);
                    llRQTM.setVisibility(View.GONE);
                    llAmt.setVisibility(View.GONE);
                    llType.setVisibility(View.GONE);
                } else if (tvToAccEntry.getText().toString().equalsIgnoreCase("Rate-Qty-Total-Misc")) {
                    llRQT.setVisibility(View.GONE);
                    llRQTM.setVisibility(View.VISIBLE);
                    llAmt.setVisibility(View.GONE);
                    llType.setVisibility(View.GONE);
                } else if (tvToAccEntry.getText().toString().equalsIgnoreCase("Type-Amount")) {
                    llRQT.setVisibility(View.GONE);
                    llRQTM.setVisibility(View.GONE);
                    llAmt.setVisibility(View.GONE);
                    llType.setVisibility(View.VISIBLE);
                } else if (tvToAccEntry.getText().toString().equalsIgnoreCase("Amount")) {
                    llRQT.setVisibility(View.GONE);
                    llRQTM.setVisibility(View.GONE);
                    llAmt.setVisibility(View.VISIBLE);
                    llType.setVisibility(View.GONE);
                } else if (tvToAccEntry.getText().toString().equalsIgnoreCase("na")) {
                    llRQT.setVisibility(View.GONE);
                    llRQTM.setVisibility(View.GONE);
                    llAmt.setVisibility(View.VISIBLE);
                    llType.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        tvToAccPhoto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (Integer.parseInt(tvToAccPhoto.getText().toString()) == 0) {
                    llPhoto.setVisibility(View.VISIBLE);
                } else {
                    llPhoto.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        tvToAccCombo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (tvToAccCombo.getText().toString().isEmpty()) {
                    //Toast.makeText(getContext(), "No Data Found for this Expense", Toast.LENGTH_SHORT).show();
                    lvType.setVisibility(View.GONE);

                    setAdapterData("");
                } else {
                    lvType.setVisibility(View.VISIBLE);
                    setAdapterData(tvToAccCombo.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewTransaction();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetData();
            }
        });


        lvType.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v.getId() == R.id.lvTripMasterEnterExp_Type) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }

                return false;
            }
        });

        btnPhoto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDialog(CAMERA_PICTURE_1, GALLERY_PICTURE_1);
            }
        });

        btnPhoto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDialog(CAMERA_PICTURE_2, GALLERY_PICTURE_2);
            }
        });

        btnPhoto3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDialog(CAMERA_PICTURE_3, GALLERY_PICTURE_3);
            }
        });


        return view;
    }

    public void addNewTransaction() {
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
                    /*else if (llPhoto.getVisibility() == View.VISIBLE) {
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
                    }*/
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
                            final CheckBox cbType = v.findViewById(R.id.cbTransactionType);

                            Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
                            cbType.setTypeface(lightFont);

                            if (strList.get(position).equals("")) {
                                cbType.setVisibility(View.GONE);
                            } else {
                                cbType.setVisibility(View.VISIBLE);
                                cbType.setText(strList.get(position));
                            }


                            cbType.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (cbType.isChecked()) {
                                        selectedStringArray.add(cbType.getText().toString());
                                        Log.e("ON CLICK : ", "COMBO VALUES ARRAY : " + selectedStringArray);
                                    } else {
                                        selectedStringArray.remove(cbType.getText().toString());
                                        Log.e("ON CLICK : NOT CHECKED", "COMBO VALUES ARRAY : " + selectedStringArray);
                                    }
                                }
                            });

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
        tvPhoto1.setText("");
        tvPhoto2.setText("");
        tvPhoto3.setText("");
        ivPhoto1.setImageResource(0);
        ivPhoto2.setImageResource(0);
        ivPhoto3.setImageResource(0);
        tvFromAccId.setText("");
        tvToAccId.setText("");
        edRemark.setText("");
        imageEncoded_1 = "";
        imageEncoded_2 = "";
        imageEncoded_3 = "";

        llPhoto.setVisibility(View.GONE);
        llType.setVisibility(View.GONE);
        llRQTM.setVisibility(View.GONE);
        llRQT.setVisibility(View.GONE);
        llAmt.setVisibility(View.GONE);
    }


    public void startDialog(final int camera, final int gallery) {
        builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
        builder.setTitle("Choose");
        builder.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent pictureActionIntent = null;
                pictureActionIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pictureActionIntent, gallery);
            }
        });
        builder.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                f = new File(folder + File.separator, "Camera.jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                startActivityForResult(intent, camera);
            }
        });
        builder.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bitmap1 = null;
        if (resultCode == getActivity().RESULT_OK && requestCode == CAMERA_PICTURE_1) {
            try {
                bitmap1 = BitmapFactory.decodeFile(f.getAbsolutePath());
                int rotate = 0;
                Bitmap imageBitmap = StoreCameraOrGalleryData.ShrinkBitmap(f.getAbsolutePath(), 1280, 720);
                ivPhoto1.setImageBitmap(imageBitmap);

                File outputFile = new File(folder + File.separator, "Camera_Expenses_" + currentDateFormat() + ".jpeg");
                Log.e("PATH : ", outputFile.getAbsolutePath());

                ByteArrayOutputStream stream = new ByteArrayOutputStream(1000);
                bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                imageEncoded_1 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                //  imageEncoded = StoreCameraOrGalleryData.storeCameraPhotoInSDCard(imageBitmap, currentDateFormat(), "Expenses");

                Log.e("Array  : ", "" + imageEncoded_1);

                tvPhoto1.setText(outputFile.getName());

                FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
                bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (resultCode == getActivity().RESULT_OK && requestCode == GALLERY_PICTURE_1) {
            try {
                if (data != null) {
                    //Toast.makeText(getActivity().getApplicationContext(), "Result OK  " + RESULT_OK + "Request Code  " + requestCode, Toast.LENGTH_LONG).show();
                    Uri selectedImage = data.getData();
                    String[] filePath = {MediaStore.Images.Media.DATA};
                    Cursor c = getActivity().getContentResolver().query(selectedImage, filePath, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePath[0]);
                    selectedImagePath = c.getString(columnIndex);
                    c.close();

                   /* if (selectedImagePath != null) {
                        tvPhoto1.setText(selectedImagePath);
                    }*/
                    bitmap1 = BitmapFactory.decodeFile(selectedImagePath); // load
                    ivPhoto1.setImageBitmap(bitmap1);

                    File outputFile = new File(folder + File.separator, "Gallery_Expenses_" + currentDateFormat() + ".jpeg");
                    Log.e("PATH : ", outputFile.getAbsolutePath());

                    tvPhoto1.setText(outputFile.getName());

                    ByteArrayOutputStream stream = new ByteArrayOutputStream(1000);
                    bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    imageEncoded_1 = Base64.encodeToString(byteArray, Base64.DEFAULT);

                    FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
                    bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    //imageEncoded = StoreCameraOrGalleryData.storeGalleryPhotoInSDCard(bitmap, currentDateFormat(), "Expenses");
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Cancelled",
                            Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (resultCode == getActivity().RESULT_OK && requestCode == CAMERA_PICTURE_2) {
            try {
                bitmap2 = BitmapFactory.decodeFile(f.getAbsolutePath());
                int rotate = 0;
                Bitmap imageBitmap = StoreCameraOrGalleryData.ShrinkBitmap(f.getAbsolutePath(), 1280, 720);
                ivPhoto2.setImageBitmap(imageBitmap);

                File outputFile = new File(folder + File.separator, "Camera_Expenses_" + currentDateFormat() + ".jpeg");
                Log.e("PATH : ", outputFile.getAbsolutePath());

                ByteArrayOutputStream stream = new ByteArrayOutputStream(1000);
                bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                imageEncoded_2 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                //  imageEncoded = StoreCameraOrGalleryData.storeCameraPhotoInSDCard(imageBitmap, currentDateFormat(), "Expenses");

                tvPhoto2.setText(outputFile.getName());

                FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
                bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (resultCode == getActivity().RESULT_OK && requestCode == GALLERY_PICTURE_2) {
            try {
                if (data != null) {
                    //Toast.makeText(getActivity().getApplicationContext(), "Result OK  " + RESULT_OK + "Request Code  " + requestCode, Toast.LENGTH_LONG).show();
                    Uri selectedImage = data.getData();
                    String[] filePath = {MediaStore.Images.Media.DATA};
                    Cursor c = getActivity().getContentResolver().query(selectedImage, filePath, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePath[0]);
                    selectedImagePath = c.getString(columnIndex);
                    c.close();

                    bitmap2 = BitmapFactory.decodeFile(selectedImagePath); // load
                    ivPhoto2.setImageBitmap(bitmap2);

                    File outputFile = new File(folder + File.separator, "Gallery_Expenses_" + currentDateFormat() + ".jpeg");
                    Log.e("PATH : ", outputFile.getAbsolutePath());

                    tvPhoto2.setText(outputFile.getName());

                    ByteArrayOutputStream stream = new ByteArrayOutputStream(1000);
                    bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    imageEncoded_2 = Base64.encodeToString(byteArray, Base64.DEFAULT);

                    FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
                    bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    //imageEncoded = StoreCameraOrGalleryData.storeGalleryPhotoInSDCard(bitmap, currentDateFormat(), "Expenses");
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Cancelled",
                            Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (resultCode == getActivity().RESULT_OK && requestCode == CAMERA_PICTURE_3) {
            try {
                bitmap3 = BitmapFactory.decodeFile(f.getAbsolutePath());
                int rotate = 0;
                Bitmap imageBitmap = StoreCameraOrGalleryData.ShrinkBitmap(f.getAbsolutePath(), 1280, 720);
                ivPhoto3.setImageBitmap(imageBitmap);

                File outputFile = new File(folder + File.separator, "Camera_Expenses_" + currentDateFormat() + ".jpeg");
                Log.e("PATH : ", outputFile.getAbsolutePath());

                ByteArrayOutputStream stream = new ByteArrayOutputStream(1000);
                bitmap3.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                imageEncoded_3 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                //  imageEncoded = StoreCameraOrGalleryData.storeCameraPhotoInSDCard(imageBitmap, currentDateFormat(), "Expenses");

                tvPhoto3.setText(outputFile.getName());

                FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
                bitmap3.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (resultCode == getActivity().RESULT_OK && requestCode == GALLERY_PICTURE_3) {
            try {
                if (data != null) {
                    //Toast.makeText(getActivity().getApplicationContext(), "Result OK  " + RESULT_OK + "Request Code  " + requestCode, Toast.LENGTH_LONG).show();
                    Uri selectedImage = data.getData();
                    String[] filePath = {MediaStore.Images.Media.DATA};
                    Cursor c = getActivity().getContentResolver().query(selectedImage, filePath, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePath[0]);
                    selectedImagePath = c.getString(columnIndex);
                    c.close();

                    bitmap3 = BitmapFactory.decodeFile(selectedImagePath); // load
                    ivPhoto3.setImageBitmap(bitmap3);

                    File outputFile = new File(folder + File.separator, "Gallery_Expenses_" + currentDateFormat() + ".jpeg");
                    Log.e("PATH : ", outputFile.getAbsolutePath());

                    tvPhoto3.setText(outputFile.getName());

                    ByteArrayOutputStream stream = new ByteArrayOutputStream(1000);
                    bitmap3.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    imageEncoded_3 = Base64.encodeToString(byteArray, Base64.DEFAULT);

                    FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
                    bitmap3.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    //imageEncoded = StoreCameraOrGalleryData.storeGalleryPhotoInSDCard(bitmap, currentDateFormat(), "Expenses");
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Cancelled",
                            Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private String currentDateFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }

    public void createFolder() {
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    public class MyAdapter extends BaseAdapter implements Filterable {

        private ArrayList<SenderAccount> originalValues;
        private ArrayList<SenderAccount> displayedValues;
        LayoutInflater inflater;

        public MyAdapter(Context context, ArrayList<SenderAccount> showAccountsList) {
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
            MyAdapter.ViewHolder holder = null;

            if (v == null) {
                v = inflater.inflate(R.layout.custom_transaction_dialog_layout, null);
                holder = new MyAdapter.ViewHolder();
                holder.tvName = v.findViewById(R.id.tvDialog_Name);
                holder.tvAmount = v.findViewById(R.id.tvDialog_Amount);
                holder.llAccount = v.findViewById(R.id.llDialog_Account);
                v.setTag(holder);
            } else {
                holder = (MyAdapter.ViewHolder) v.getTag();
            }


            Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
            Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");

            holder.tvName.setTypeface(lightFont);
            holder.tvAmount.setTypeface(boldFont);

            holder.tvName.setText("" + displayedValues.get(position).getAccName());
            holder.tvAmount.setText(" [ " + displayedValues.get(position).getAccAmount() + " ] ");

            holder.llAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    edFromAcc.setText("" + displayedValues.get(position).getAccName());
                    tvFromAccId.setText("" + displayedValues.get(position).getAccId());
                    tvFromAccAmt.setText("" + displayedValues.get(position).getAccAmount());
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

    public void showDialog(String whichAcc) {
//        dialog = new Dialog(getContext(), android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        dialog = new Dialog(getContext(), android.R.style.Theme_Light_NoTitleBar);
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = li.inflate(R.layout.transaction_account_dialog, null, false);
        dialog.setContentView(v);
        dialog.setCancelable(true);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        if (whichAcc.equalsIgnoreCase("From")) {
            ArrayList<SenderAccount> tempSenderArray = new ArrayList<>();
            if (tvToAccId.getText().toString().isEmpty()) {
                ListView list1 = (ListView) dialog.findViewById(R.id.lvTransactionDialog_AccountList);
                myAdapter = new MyAdapter(getActivity().getApplicationContext(), showAccountArray);
                list1.setAdapter(myAdapter);
            } else {
                for (int i = 0, j = 0; i < showAccountArray.size(); i++) {
                    if (Double.parseDouble(tvToAccId.getText().toString()) != showAccountArray.get(i).getAccId()) {
                        tempSenderArray.add(j, showAccountArray.get(i));
                    }
                }
                ListView list1 = (ListView) dialog.findViewById(R.id.lvTransactionDialog_AccountList);
                myAdapter = new MyAdapter(getActivity().getApplicationContext(), tempSenderArray);
                list1.setAdapter(myAdapter);
            }


        } else {
            ArrayList<ReceiverAccount> tempRecArray = new ArrayList<>();
            if (tvFromAccId.getText().toString().isEmpty()) {
                ListView list2 = (ListView) dialog.findViewById(R.id.lvTransactionDialog_AccountList);
                myAdapterRecAcc = new MyAdapterRecAcc(getActivity().getApplicationContext(), receiverAccountArray);
                list2.setAdapter(myAdapterRecAcc);
            } else {
                for (int i = 0, j = 0; i < receiverAccountArray.size(); i++) {
                    if (Double.parseDouble(tvFromAccId.getText().toString()) != receiverAccountArray.get(i).getExpId()) {
                        tempRecArray.add(j, receiverAccountArray.get(i));
                    }
                }
                ListView list2 = (ListView) dialog.findViewById(R.id.lvTransactionDialog_AccountList);
                myAdapterRecAcc = new MyAdapterRecAcc(getActivity().getApplicationContext(), tempRecArray);
                list2.setAdapter(myAdapterRecAcc);
            }


        }

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

    public void getFromToAccountData(final String accessTo) {

        if (CheckNetwork.isInternetAvailable(getContext())) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            InterfaceApi api = retrofit.create(InterfaceApi.class);
            Call<TransactionAccountData> transactionAccountDataCall = api.allAccountTripWise(tripId, type);

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
                    if (response.body() != null) {
                        TransactionAccountData data = response.body();
                        if (data.getErrorMessage().getError()) {
                            progressBar1.dismiss();
                            Log.e("ON RESPONSE : ", "ERROR : " + data.getErrorMessage().getMessage());
                        } else {
                            showAccountArray.clear();

                            expNameArray.clear();
                            expIdArray.clear();
                            expEntryArray.clear();
                            expComboTypeArray.clear();
                            expPhotoArray.clear();

                            receiverAccountArray.clear();

                            for (int i = 0; i < data.getSenderAccount().size(); i++) {
                                showAccountArray.add(i, data.getSenderAccount().get(i));

                            }

                            if (accessTo.equalsIgnoreCase("Manager")) {
                                for (int j = 0; j < data.getReceiverAccount().size(); j++) {
                                    if (data.getReceiverAccount().get(j).getExpAccess() == null || !data.getReceiverAccount().get(j).getExpAccess().equalsIgnoreCase("Admin") || data.getReceiverAccount().get(j).getExpAccess().isEmpty()) {
                                        receiverAccountArray.add(data.getReceiverAccount().get(j));
                                        Log.e("MANAGER : ", "" + data.getReceiverAccount().get(j).getExpName());
                                    }
                                }
                            } else if (accessTo.equalsIgnoreCase("Auctioner")) {
                                for (int j = 0; j < data.getReceiverAccount().size(); j++) {
                                    if (data.getReceiverAccount().get(j).getExpAccess() == null || data.getReceiverAccount().get(j).getExpAccess().equalsIgnoreCase("Auctioner") || data.getReceiverAccount().get(j).getExpAccess().isEmpty()) {
                                        receiverAccountArray.add(data.getReceiverAccount().get(j));
                                        Log.e("AUCTIONER : ", "" + data.getReceiverAccount().get(j).getExpName());
                                    }
                                }
                            } else if (accessTo.equalsIgnoreCase("Tandel")) {
                                for (int j = 0; j < data.getReceiverAccount().size(); j++) {
                                    if (data.getReceiverAccount().get(j).getExpAccess() == null || data.getReceiverAccount().get(j).getExpAccess().equalsIgnoreCase("Tandel") || data.getReceiverAccount().get(j).getExpAccess().isEmpty()) {
                                        receiverAccountArray.add(data.getReceiverAccount().get(j));
                                        Log.e("TANDEL : ", "" + data.getReceiverAccount().get(j).getExpName());
                                    }
                                }
                            } else {
                                for (int j = 0; j < data.getReceiverAccount().size(); j++) {
                                    expNameArray.add(j, data.getReceiverAccount().get(j).getExpName());
                                    expIdArray.add(j, data.getReceiverAccount().get(j).getExpId());
                                    expEntryArray.add(j, data.getReceiverAccount().get(j).getExpEntry());
                                    expComboTypeArray.add(j, data.getReceiverAccount().get(j).getExpCombo());
                                    expPhotoArray.add(j, data.getReceiverAccount().get(j).getExpPhoto());
                                    expAmtArray.add(j, data.getReceiverAccount().get(j).getExpAmount());

                                    receiverAccountArray.add(data.getReceiverAccount().get(j));
                                }
                            }

/*
                            for (int j = 0; j < data.getReceiverAccount().size(); j++) {
                                expNameArray.add(j, data.getReceiverAccount().get(j).getExpName());
                                expIdArray.add(j, data.getReceiverAccount().get(j).getExpId());
                                expEntryArray.add(j, data.getReceiverAccount().get(j).getExpEntry());
                                expComboTypeArray.add(j, data.getReceiverAccount().get(j).getExpCombo());
                                expPhotoArray.add(j, data.getReceiverAccount().get(j).getExpPhoto());
                                expAmtArray.add(j, data.getReceiverAccount().get(j).getExpAmount());

                                receiverAccountArray.add(j, data.getReceiverAccount().get(j));
                            }
*/
                            progressBar1.dismiss();
                        }
                    } else {
                        progressBar1.dismiss();
                        Log.e("ON RESPONSE : ", "NO DATA");
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

    public class MyAdapterRecAcc extends BaseAdapter implements Filterable {

        private ArrayList<ReceiverAccount> originalValues;
        private ArrayList<ReceiverAccount> displayedValues;
        LayoutInflater inflater;

        public MyAdapterRecAcc(Context context, ArrayList<ReceiverAccount> showAccountsList) {
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
            MyAdapterRecAcc.ViewHolder holder = null;

            if (v == null) {
                v = inflater.inflate(R.layout.custom_transaction_dialog_layout, null);
                holder = new MyAdapterRecAcc.ViewHolder();
                holder.tvName = v.findViewById(R.id.tvDialog_Name);
                holder.tvAmount = v.findViewById(R.id.tvDialog_Amount);
                holder.llAccount = v.findViewById(R.id.llDialog_Account);
                v.setTag(holder);
            } else {
                holder = (MyAdapterRecAcc.ViewHolder) v.getTag();
            }


            Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
            Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");

            holder.tvName.setTypeface(lightFont);
            holder.tvAmount.setTypeface(boldFont);

            holder.tvName.setText("" + displayedValues.get(position).getExpName());
            holder.tvAmount.setText(" [ " + displayedValues.get(position).getExpAmount() + " ] ");

            holder.llAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    edToAcc.setText("" + displayedValues.get(position).getExpName());
                    tvToAccId.setText("" + displayedValues.get(position).getExpId());
                    tvToAccEntry.setText("" + displayedValues.get(position).getExpEntry());
                    tvToAccAmt.setText("" + displayedValues.get(position).getExpAmount());
                    tvToAccPhoto.setText("" + displayedValues.get(position).getExpPhoto());
                    tvToAccCombo.setText("" + displayedValues.get(position).getExpCombo());
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
                    ArrayList<ReceiverAccount> filteredArrayList = new ArrayList<>();
                    if (originalValues == null) {
                        originalValues = new ArrayList<ReceiverAccount>(displayedValues);
                    }

                    if (charSequence == null || charSequence.length() == 0) {
                        results.count = originalValues.size();
                        results.values = originalValues;
                    } else {
                        charSequence = charSequence.toString().toLowerCase();
                        for (int i = 0; i < originalValues.size(); i++) {
                            String accId = "" + originalValues.get(i).getExpId();
                            String accName = originalValues.get(i).getExpName();
                            String accType = originalValues.get(i).getExpType();
                            if (accId.toLowerCase().startsWith(charSequence.toString()) || accName.toLowerCase().startsWith(charSequence.toString()) || accType.toLowerCase().startsWith(charSequence.toString())) {
                                filteredArrayList.add(new ReceiverAccount(originalValues.get(i).getExpId(), originalValues.get(i).getExpName(), originalValues.get(i).getExpType(), originalValues.get(i).getExpEntry(), originalValues.get(i).getExpCombo(), originalValues.get(i).getExpPhoto(), originalValues.get(i).getExpAmount(), originalValues.get(i).getExpAccess()));
                            }
                        }
                        results.count = filteredArrayList.size();
                        results.values = filteredArrayList;
                    }

                    return results;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    displayedValues = (ArrayList<ReceiverAccount>) filterResults.values;
                    notifyDataSetChanged();
                }
            };

            return filter;
        }
    }
}

