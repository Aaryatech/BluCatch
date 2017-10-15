package com.ats.blucatch.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.IdRes;
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
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.blucatch.R;
import com.ats.blucatch.activity.MainActivity;
import com.ats.blucatch.bean.Account;
import com.ats.blucatch.bean.AccountData;
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

public class AccountMasterFragment extends Fragment {

    private RadioGroup rgFilter;
    private RadioButton rbUser, rbTransaction, rbAll;
    private FloatingActionButton fab;
    private EditText edSearch;
    private TextView tvAccType, tvUserName, tvMobile, tvUserType;
    private ImageView ivpopup, ivFilter;
    private LinearLayout llFilter;
    private boolean flag = false;
    private ProgressDialog progressBar, progressBar1;
    private ListView lvAccount;
    private ArrayList<Account> accountArray = new ArrayList<>();
    private MyAdapter1 adapter1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_master, container, false);
        Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
        Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");
        MainActivity.tvTitle.setText("Account Master");
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
        MainActivity.isAtAccMaster = true;
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

        lvAccount = view.findViewById(R.id.lvAccMaster);
        edSearch = view.findViewById(R.id.edAccMaster_Search);

        rgFilter = view.findViewById(R.id.rgAccMaster_filter);
        rbUser = view.findViewById(R.id.rbAccMaster_User);
        rbTransaction = view.findViewById(R.id.rbAccMaster_Transaction);
        rbAll = view.findViewById(R.id.rbAccMaster_All);

        fab = view.findViewById(R.id.fabAccountMaster);
        ivFilter = view.findViewById(R.id.ivAccMaster_Filter);
        llFilter = view.findViewById(R.id.llAccMasterFilter);

        llFilter.setVisibility(View.GONE);
        flag = false;

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new NewAccountInfoFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }
        });

        ivFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == false) {
                    flag = true;
                    llFilter.setVisibility(View.VISIBLE);
                } else {
                    flag = false;
                    llFilter.setVisibility(View.GONE);
                }
            }
        });

        rgFilter.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                RadioButton rb = (RadioButton) radioGroup.findViewById(i);
                boolean isChecked = rb.isChecked();
                if (isChecked) {
                    accountArray.clear();
                    getAccountData("" + rb.getText());
                }
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

        getAccountData("All");

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

    public void getAccountData(final String type) {

        if (CheckNetwork.isInternetAvailable(getContext())) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            InterfaceApi api = retrofit.create(InterfaceApi.class);
            Call<AccountData> accountDataCall = api.allAccountData();

            progressBar = new ProgressDialog(getContext());
            progressBar.setCancelable(false);
            progressBar.setMessage("please wait....");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.setProgress(0);
            progressBar.setMax(100);
            progressBar.show();

            accountDataCall.enqueue(new Callback<AccountData>() {
                @Override
                public void onResponse(Call<AccountData> call, Response<AccountData> response) {
                    try {
                        if (response.body() != null) {
                            AccountData data = response.body();
                            if (data.getErrorMessage().getError()) {
                                progressBar.dismiss();
                                Toast.makeText(getContext(), "unable to fetch account data.", Toast.LENGTH_SHORT).show();
                                Log.e("RESPONSE : ", " ERROR : " + data.getErrorMessage().getMessage());
                            } else {
                                accountArray.clear();
                                if (type.equalsIgnoreCase("All")) {
                                    for (int i = 0; i < data.getAccount().size(); i++) {
                                        accountArray.add(i, data.getAccount().get(i));
                                    }
                                } else if (type.equalsIgnoreCase("User")) {
                                    for (int i = 0, j = 0; i < data.getAccount().size(); i++) {
                                        if (data.getAccount().get(i).getAccType().equalsIgnoreCase("User"))
                                            accountArray.add(j, data.getAccount().get(i));
                                    }
                                } else if (type.equalsIgnoreCase("Transaction")) {
                                    for (int i = 0, j = 0; i < data.getAccount().size(); i++) {
                                        if (data.getAccount().get(i).getAccType().equalsIgnoreCase("Transaction"))
                                            accountArray.add(j, data.getAccount().get(i));
                                    }
                                } else {
                                    for (int i = 0; i < data.getAccount().size(); i++) {
                                        accountArray.add(i, data.getAccount().get(i));
                                    }
                                }
                                Log.e("RESPONSE : ", " DATA : " + data.getAccount());
                                //setAdapterData();
                                adapter1 = new MyAdapter1(getContext(), accountArray);
                                lvAccount.setAdapter(adapter1);
                                lvAccount.setTextFilterEnabled(true);
                                progressBar.dismiss();
                            }

                        } else {
                            progressBar.dismiss();
                            Toast.makeText(getContext(), "unable to fetch account data.", Toast.LENGTH_SHORT).show();
                            Log.e("RESPONSE : ", " NO DATA");
                        }
                    } catch (Exception e) {
                        progressBar.dismiss();
                        Log.e("Exception : ", "" + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<AccountData> call, Throwable t) {
                    progressBar.dismiss();
                    Toast.makeText(getContext(), "unable to fetch account data! server error", Toast.LENGTH_SHORT).show();
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

    public void deleteAccountData(long id) {

        if (CheckNetwork.isInternetAvailable(getContext())) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            final InterfaceApi api = retrofit.create(InterfaceApi.class);

            Call<ErrorMessage> errorMessageCall = api.deleteAccount(id);

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
                                builder.setMessage("Account deleted successfully.");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        clearArrayList();
                                        getAccountData("All");
                                        // setAdapterData();
                                        adapter1.notifyDataSetChanged();
                                        edSearch.setText("");
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }

                        } else {
                            progressBar1.dismiss();
                            Toast.makeText(getContext(), "Unable to delete account!", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getContext(), "Unable to delete account!", Toast.LENGTH_SHORT).show();
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

    public void clearArrayList() {
        accountArray.clear();
    }


    public class MyAdapter1 extends BaseAdapter implements Filterable {

        private ArrayList<Account> originalValues;
        private ArrayList<Account> displayedValues;
        LayoutInflater inflater;

        public MyAdapter1(Context context, ArrayList<Account> accountArrayList) {
            this.originalValues = accountArrayList;
            this.displayedValues = accountArrayList;
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
            // LayoutInflater inflater = getActivity().getLayoutInflater();
            v = inflater.inflate(R.layout.custom_account_master, null);

            TextView tvAccType = v.findViewById(R.id.tvAccMaster_AccType);
            TextView tvUserName = v.findViewById(R.id.tvAccMaster_UserName);
            TextView tvMobile = v.findViewById(R.id.tvAccMaster_Mobile);
            TextView tvUserType = v.findViewById(R.id.tvAccMaster_UserType);
            ImageView ivpopup = v.findViewById(R.id.ivAccMaster_popup);

            Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
            Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");

            tvAccType.setTypeface(boldFont);
            tvUserName.setTypeface(boldFont);
            tvMobile.setTypeface(boldFont);
            tvUserType.setTypeface(lightFont);

            if (displayedValues.get(position).getAccType().equalsIgnoreCase("User")) {
                tvAccType.setText(" " + displayedValues.get(position).getAccType() + " Account ");
                tvUserName.setText("" + displayedValues.get(position).getAccName());
                tvMobile.setText("" + displayedValues.get(position).getEmpMobile());
                tvUserType.setText("( " + displayedValues.get(position).getEmpType() + " )");
            } else if (displayedValues.get(position).getAccType().equalsIgnoreCase("Transaction")) {
                tvAccType.setText(" " + displayedValues.get(position).getAccType() + " Account ");
                tvUserName.setText("" + displayedValues.get(position).getAccName());
                tvMobile.setText("" + displayedValues.get(position).getEmpMobile());
                tvUserType.setText(" ");
            } else {
                tvAccType.setText(" " + displayedValues.get(position).getAccType() + " Account ");
                tvUserName.setText("" + displayedValues.get(position).getAccName());
                tvMobile.setText("" + displayedValues.get(position).getEmpMobile());
                tvUserType.setText("( " + displayedValues.get(position).getEmpType() + " )");
            }
            ivpopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(getContext(), view);
                    popupMenu.getMenuInflater().inflate(R.menu.popup_account_master, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            if (menuItem.getItemId() == R.id.item_acc_edit) {

                                Fragment adf = new EditAccountFragment();
                                Bundle args = new Bundle();
                                args.putLong("Account_Id", displayedValues.get(position).getAccId());
                                args.putString("Account_Name", displayedValues.get(position).getAccName());
                                args.putString("User_Type", displayedValues.get(position).getEmpType());
                                args.putLong("User_Sub_Type", displayedValues.get(position).getTandelAccId());
                                args.putString("Account_Mobile", displayedValues.get(position).getEmpMobile());
                                args.putLong("Account_DOB", displayedValues.get(position).getEmpDOB());
                                args.putString("Account_Address", displayedValues.get(position).getEmpAddress());
                                args.putString("Account_Aadhar", displayedValues.get(position).getEmpAadhar());
                                args.putString("Account_Bank", displayedValues.get(position).getEmpBankDetails());
                                args.putString("Account_Remark", displayedValues.get(position).getRemark());
                                args.putString("Account_Emrg_Name1", displayedValues.get(position).getEmergencyName1());
                                args.putString("Account_Emrg_Name2", displayedValues.get(position).getEmergencyName2());
                                args.putString("Account_Emrg_Mobile1", displayedValues.get(position).getEmergencyMobile1());
                                args.putString("Account_Emrg_Mobile2", displayedValues.get(position).getEmergencyMobile2());
                                args.putString("Account_BloodGrp", displayedValues.get(position).getEmpBloodGroup());
                                args.putString("Account_Transaction", displayedValues.get(position).getTransactionType());
                                args.putString("Account_Type", displayedValues.get(position).getAccType());
                                args.putDouble("Account_OpenBal", displayedValues.get(position).getOpeningBalance());
                                args.putInt("Account_Percent", displayedValues.get(position).getEmpPerShare());

                                adf.setArguments(args);
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf).commit();

                            } else if (menuItem.getItemId() == R.id.item_acc_ledger) {
                                Fragment adf = new ViewLedgerFragment();
                                Bundle args = new Bundle();
                                args.putLong("Account_Id", displayedValues.get(position).getAccId());
                                args.putString("Account_Name", displayedValues.get(position).getAccName());
                                adf.setArguments(args);
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf).commit();

                            } else if (menuItem.getItemId() == R.id.item_acc_delete) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                                builder.setTitle("Confirm Action");
                                builder.setMessage("Do you really want to delete account?");
                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        deleteAccountData(displayedValues.get(position).getAccId());
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
                    ArrayList<Account> filteredArrayList = new ArrayList<Account>();
                    if (originalValues == null) {
                        originalValues = new ArrayList<Account>(displayedValues);
                    }

                    if (charSequence == null || charSequence.length() == 0) {
                        results.count = originalValues.size();
                        results.values = originalValues;
                    } else {
                        charSequence = charSequence.toString().toLowerCase();
                        for (int i = 0; i < originalValues.size(); i++) {
                            String accName = originalValues.get(i).getAccName();
                            String empType = originalValues.get(i).getEmpType();
                            String accType = originalValues.get(i).getAccType();
                            String mobile = originalValues.get(i).getEmpMobile();
                            String transcType = originalValues.get(i).getTransactionType();
                            if (accName.toLowerCase().startsWith(charSequence.toString()) || accType.toLowerCase().startsWith(charSequence.toString()) || empType.toLowerCase().startsWith(charSequence.toString()) || mobile.toLowerCase().startsWith(charSequence.toString()) || mobile.toLowerCase().startsWith(charSequence.toString()) || transcType.toLowerCase().startsWith(charSequence.toString())) {
                                filteredArrayList.add(new Account(originalValues.get(i).getAccId(), originalValues.get(i).getAccType(), originalValues.get(i).getAccName(), originalValues.get(i).getEmpType(), originalValues.get(i).getTandelAccId(), originalValues.get(i).getEmpMobile(), originalValues.get(i).getEmpDOB(), originalValues.get(i).getEmpAddress(), originalValues.get(i).getEmpAadhar(), originalValues.get(i).getEmpBankDetails(), originalValues.get(i).getEmpPerShare(), originalValues.get(i).getRemark(), originalValues.get(i).getIsUsed(), originalValues.get(i).getEmpBloodGroup(), originalValues.get(i).getEmergencyName1(), originalValues.get(i).getEmergencyMobile1(), originalValues.get(i).getEmergencyName2(), originalValues.get(i).getEmergencyMobile2(), originalValues.get(i).getOpeningBalance(), originalValues.get(i).getTransactionType(), originalValues.get(i).getCoId(), originalValues.get(i).getEnterDate(), originalValues.get(i).getEnterBy()));
                            }
                        }
                        results.count = filteredArrayList.size();
                        results.values = filteredArrayList;
                    }

                    return results;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    displayedValues = (ArrayList<Account>) filterResults.values;
                    notifyDataSetChanged();
                }
            };

            return filter;
        }
    }
}
