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
import com.ats.blucatch.bean.Expense;
import com.ats.blucatch.bean.ExpensesData;
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

public class ExpenseMasterFragment extends Fragment {

    private TextView tvAccType, tvExpName, tvExpType;
    private EditText edSearch;
    private FloatingActionButton fab;
    private ImageView ivPopup;
    private ListView lvExpenseMaster;
    private ProgressDialog progressBar;
    private ArrayList<Expense> expenseArray = new ArrayList<>();
    private ArrayAdapter<Expense> adapter;
    private MyAdapter adapter1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense_master, container, false);
        Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
        Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");
        MainActivity.tvTitle.setText("Expenses Master");
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
        MainActivity.isAtExpMaster = true;
        MainActivity.isAtAddExp = false;
        MainActivity.isAtChangePass = false;
        MainActivity.isAtHomeTripExp = false;
        MainActivity.isAtHomeFishSell = false;

        lvExpenseMaster = view.findViewById(R.id.lvExpenseMaster);
        edSearch = view.findViewById(R.id.edExpenseMaster_Search);
        fab = view.findViewById(R.id.fabExpenseMaster);

        edSearch.setTypeface(lightFont);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new AddNewExpenseFragment());
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

        getAllExpenseData();

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
                            expenseArray.clear();
                            for (int i = 0; i < data.getExpenses().size(); i++) {
                                expenseArray.add(i, data.getExpenses().get(i));
                            }
                            Log.e("ON RESPONSE : ", " DATA : " + expenseArray);
                            //  setAdapterData();
                            adapter1 = new MyAdapter(getContext(), expenseArray);
                            lvExpenseMaster.setAdapter(adapter1);

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


  /*  public void setAdapterData() {
        adapter = new ArrayAdapter<Expense>(getContext(), android.R.layout.simple_list_item_1, expenseArray) {
            @NonNull
            @Override
            public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View v = inflater.inflate(R.layout.custom_expense_master, null);


                ImageView ivPopup1 = v.findViewById(R.id.ivExpenseMaster_popup);

                TextView tvAccType = v.findViewById(R.id.tvExpenseMaster_AccType);
                TextView tvExpName = v.findViewById(R.id.tvExpenseMaster_ExpName);
                TextView tvExpType = v.findViewById(R.id.tvExpenseMaster_ExpType);

                Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
                Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");

                tvAccType.setTypeface(boldFont);
                tvExpName.setTypeface(lightFont);
                tvExpType.setTypeface(boldFont);

                tvAccType.setText(" " + expenseArray.get(position).getExpAccType() + " ");
                tvExpName.setText("" + expenseArray.get(position).getExpName());
                tvExpType.setText("" + expenseArray.get(position).getExpType());


                ivPopup1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PopupMenu popupMenu = new PopupMenu(getContext(), view);
                        popupMenu.getMenuInflater().inflate(R.menu.popup_expense_master, popupMenu.getMenu());
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                if (menuItem.getItemId() == R.id.item_exp_edit) {

                                    Fragment adf = new EditExpenseFragment();
                                    Bundle args = new Bundle();
                                    args.putString("Expense_Name", expenseArray.get(position).getExpName());
                                    args.putString("Expense_Type", expenseArray.get(position).getExpType());
                                    args.putString("Expense_Acc_Type", expenseArray.get(position).getExpAccType());
                                    args.putString("Expense_Entry_Type", expenseArray.get(position).getExpEntryType());
                                    args.putString("Expense_Entry_Access_To", expenseArray.get(position).getExpAccessTo());
                                    args.putInt("Expense_Photo_Req", expenseArray.get(position).getExpIsPhotoReq());
                                    args.putInt("Expense_Id", expenseArray.get(position).getExpId());
                                    adf.setArguments(args);
                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf).commit();

                                } else if (menuItem.getItemId() == R.id.item_exp_ledger) {
                                    Fragment fragment = new ViewExpenseLedgerFragment();
                                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                    ft.replace(R.id.content_frame, fragment);
                                    ft.commit();
                                } else if (menuItem.getItemId() == R.id.item_exp_delete) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("Confirm Action");
                                    builder.setMessage("Do you really want to delete expense?");
                                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            deleteExpenseData(expenseArray.get(position).getExpId());
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
        };
        lvExpenseMaster.setAdapter(adapter);
    }*/

    public void deleteExpenseData(long expId) {

        if (CheckNetwork.isInternetAvailable(getContext())) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            final InterfaceApi api = retrofit.create(InterfaceApi.class);

            Call<ErrorMessage> errorMessageCall = api.deleteExpenses(expId);

            progressBar = new ProgressDialog(getContext());
            progressBar.setCancelable(false);
            progressBar.setMessage("please wait....");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.setProgress(0);
            progressBar.setMax(100);
            progressBar.show();

            errorMessageCall.enqueue(new Callback<ErrorMessage>() {
                @Override
                public void onResponse(Call<ErrorMessage> call, Response<ErrorMessage> response) {
                    if (response.body() != null) {
                        ErrorMessage data = response.body();
                        if (data.getError()) {
                            progressBar.dismiss();
                            Toast.makeText(getContext(), "unable to delete expense!", Toast.LENGTH_SHORT).show();
                            Log.e("ON RESPONSE : ", "ERROR : " + data.getMessage());

                        } else {
                            progressBar.dismiss();
                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
                            builder.setTitle("Success");
                            builder.setCancelable(false);
                            builder.setMessage("Expense deleted successfully.");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    clearArrayList();
                                    getAllExpenseData();
                                    //setAdapterData();
                                    adapter1.notifyDataSetChanged();
                                    edSearch.setText("");
                                }
                            });
                            android.app.AlertDialog dialog = builder.create();
                            dialog.show();
                        }

                    } else {
                        progressBar.dismiss();
                        Toast.makeText(getContext(), "Unable to delete expense!", Toast.LENGTH_SHORT).show();
                        Log.e("ON RESPONSE : ", "NO DATA");
                    }
                }

                @Override
                public void onFailure(Call<ErrorMessage> call, Throwable t) {
                    progressBar.dismiss();
                    Toast.makeText(getContext(), "Unable to delete expense!", Toast.LENGTH_SHORT).show();
                    Log.e("ON FAILURE : ", "ERROR : " + t.getMessage());
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

    private void clearArrayList() {
        expenseArray.clear();
    }


    public class MyAdapter extends BaseAdapter implements Filterable {

        private ArrayList<Expense> originalValues;
        private ArrayList<Expense> displayedValues;
        LayoutInflater inflater;

        public MyAdapter(Context context, ArrayList<Expense> expenseArrayList) {
            this.originalValues = expenseArrayList;
            this.displayedValues = expenseArrayList;
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
            v = inflater.inflate(R.layout.custom_expense_master, null);

            ImageView ivPopup1 = v.findViewById(R.id.ivExpenseMaster_popup);
            TextView tvAccType = v.findViewById(R.id.tvExpenseMaster_AccType);
            TextView tvExpName = v.findViewById(R.id.tvExpenseMaster_ExpName);
            TextView tvExpType = v.findViewById(R.id.tvExpenseMaster_ExpType);

            Typeface lightFont = Typeface.createFromAsset(getContext().getAssets(), "sofiapro-light.otf");
            Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "SofiaProBold.otf");

            tvAccType.setTypeface(boldFont);
            tvExpName.setTypeface(lightFont);
            tvExpType.setTypeface(boldFont);

            tvAccType.setText(" " + displayedValues.get(position).getExpAccType() + " ");
            tvExpName.setText("" + displayedValues.get(position).getExpName());
            tvExpType.setText("" + displayedValues.get(position).getExpType());

            ivPopup1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(getContext(), view);
                    popupMenu.getMenuInflater().inflate(R.menu.popup_expense_master, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            if (menuItem.getItemId() == R.id.item_exp_edit) {

                                Fragment adf = new EditExpenseFragment();
                                Bundle args = new Bundle();
                                args.putString("Expense_Name", displayedValues.get(position).getExpName());
                                args.putString("Expense_Type", displayedValues.get(position).getExpType());
                                args.putString("Expense_Acc_Type", displayedValues.get(position).getExpAccType());
                                args.putString("Expense_Entry_Type", displayedValues.get(position).getExpEntryType());
                                args.putString("Expense_Entry_Access_To", displayedValues.get(position).getExpAccessTo());
                                args.putInt("Expense_Photo_Req", displayedValues.get(position).getExpIsPhotoReq());
                                args.putLong("Expense_Id", displayedValues.get(position).getExpId());
                                args.putString("Expense_Combo", displayedValues.get(position).getComboValues());
                                adf.setArguments(args);
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf).commit();

                            } else if (menuItem.getItemId() == R.id.item_exp_ledger) {
                                Fragment fragment = new ViewExpenseLedgerFragment();
                                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.content_frame, fragment);
                                ft.commit();
                            } else if (menuItem.getItemId() == R.id.item_exp_delete) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle("Confirm Action");
                                builder.setMessage("Do you really want to delete expense?");
                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        deleteExpenseData(displayedValues.get(position).getExpId());
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
                    ArrayList<Expense> filteredArrayList = new ArrayList<>();
                    if (originalValues == null) {
                        originalValues = new ArrayList<Expense>(displayedValues);
                    }

                    if (charSequence == null || charSequence.length() == 0) {
                        results.count = originalValues.size();
                        results.values = originalValues;
                    } else {
                        charSequence = charSequence.toString().toLowerCase();
                        for (int i = 0; i < originalValues.size(); i++) {
                            String expName = originalValues.get(i).getExpName();
                            String accType = originalValues.get(i).getExpAccType();
                            String expType = originalValues.get(i).getExpType();
                            if (expName.toLowerCase().startsWith(charSequence.toString()) || accType.toLowerCase().startsWith(charSequence.toString()) || expType.toLowerCase().startsWith(charSequence.toString())) {
                                // filteredArrayList.add(new Expense(originalValues.get(i).getExpId(), originalValues.get(i).getExpName(), originalValues.get(i).getExpType(), originalValues.get(i).getExpAccType(), originalValues.get(i).getExpEntryType(), originalValues.get(i).getExpIsPhotoReq(), originalValues.get(i).getExpAccessTo(), originalValues.get(i).getExpIsUsed()));
                            }
                        }
                        results.count = filteredArrayList.size();
                        results.values = filteredArrayList;
                    }

                    return results;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    displayedValues = (ArrayList<Expense>) filterResults.values;
                    notifyDataSetChanged();
                }
            };

            return filter;
        }
    }
}
