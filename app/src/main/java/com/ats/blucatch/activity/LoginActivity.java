package com.ats.blucatch.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ats.blucatch.R;
import com.ats.blucatch.bean.AddDevices;
import com.ats.blucatch.bean.ErrorMessage;
import com.ats.blucatch.bean.Login;
import com.ats.blucatch.utils.CheckNetwork;
import com.ats.blucatch.utils.InterfaceApi;
import com.ats.blucatch.utils.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout textUsername, textPassword;
    private EditText edUsername, edPassword;
    private Button btnLogin;
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textUsername = (TextInputLayout) findViewById(R.id.textUsername);
        textPassword = (TextInputLayout) findViewById(R.id.textPassword);

        edUsername = (EditText) findViewById(R.id.edLoginUsername);
        edPassword = (EditText) findViewById(R.id.edLoginPassword);

        btnLogin = (Button) findViewById(R.id.btnLoginSignIn);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = edUsername.getText().toString().trim();
                String password = edPassword.getText().toString().trim();

                if (email.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "please enter email id", Toast.LENGTH_SHORT).show();
                    edUsername.requestFocus();
                } else if (password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "please enter password", Toast.LENGTH_SHORT).show();
                    edPassword.requestFocus();
                } else {

                    if (CheckNetwork.isInternetAvailable(getApplicationContext())) {

                        Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        InterfaceApi api = retrofit.create(InterfaceApi.class);
                        Call<Login> loginCall = api.doLogin(email, password);

                        progressBar = new ProgressDialog(LoginActivity.this);
                        progressBar.setCancelable(false);
                        progressBar.setMessage("please wait....");
                        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressBar.setProgress(0);
                        progressBar.setMax(100);
                        progressBar.show();

                        loginCall.enqueue(new Callback<Login>() {
                            @Override
                            public void onResponse(Call<Login> call, Response<Login> response) {
                                if (response.body() != null) {
                                    Login data = response.body();

                                    if (data.getErrorMessage().getError()) {
                                        Log.e("USERDATA : ", "ERROR : " + data.getErrorMessage().getMessage());
                                        progressBar.dismiss();
                                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this, R.style.AlertDialogTheme);
                                        builder.setTitle("Alert");
                                        builder.setCancelable(false);
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
                                        Log.e("USERDATA : ", "DATA : " + data.getUser());

                                        if (data.getUser().getUserAccessType().equalsIgnoreCase("Admin")) {
                                            SharedPreferences pref = getApplicationContext().getSharedPreferences(InterfaceApi.MY_PREF, MODE_PRIVATE);
                                            SharedPreferences.Editor editor = pref.edit();
                                            editor.putInt("AppUserId", data.getUser().getUserId());
                                            editor.putLong("AppAccId", data.getUser().getAccId());
                                            editor.putString("AppUserType", "Admin");
                                            editor.putInt("AppCoId", data.getUser().getCoId());
                                            editor.apply();

                                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                            progressBar.dismiss();
                                            finish();

                                        } else {
                                            progressBar.dismiss();

                                            Log.e("USER :  ", "NOT ADMIN");
                                            SharedPreferences pref = getApplicationContext().getSharedPreferences(InterfaceApi.MY_PREF, MODE_PRIVATE);
                                            SharedPreferences.Editor editor = pref.edit();
                                            editor.putInt("AppUserId", data.getUser().getUserId());
                                            editor.putLong("AppAccId", data.getUser().getAccId());
                                            editor.putString("AppUserType", data.getUser().getUserAccessType());
                                            editor.putInt("AppCoId", data.getUser().getCoId());
                                            editor.apply();

                                            if (data.getUser().getBlockStatus() == 0) {
                                                sendTokenToServer(data.getUser().getUserId(), data.getUser().getAccId(), data.getUser().getCoId());
                                                startActivity(new Intent(LoginActivity.this, MainActivity.class));

                                                finish();
                                            } else {
                                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this, R.style.AlertDialogTheme);
                                                builder.setTitle("Alert");
                                                builder.setCancelable(false);
                                                builder.setMessage("You are blocked! \nsorry you cannot able to logged in");
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
                                } else {
                                    progressBar.dismiss();
                                    Log.e("LoginActivity->", " onResponse : No Data");
                                }
                            }

                            @Override
                            public void onFailure(Call<Login> call, Throwable t) {
                                Log.e("ON FAILURE : ", "ERROR : " + t.getMessage());
                                progressBar.dismiss();

                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this, R.style.AlertDialogTheme);
                                builder.setTitle("Error");
                                builder.setCancelable(false);
                                builder.setMessage("Server Error!");
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this, R.style.AlertDialogTheme);
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
        });

    }

    private void sendTokenToServer(int userId, long accId, int coId) {

        String token = SharedPrefManager.getmInstance(this).getDeviceToken();
        AddDevices addDevices = new AddDevices(userId, accId, token, coId);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(InterfaceApi.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        InterfaceApi api = retrofit.create(InterfaceApi.class);
        Call<ErrorMessage> errorMessageCall = api.addDeviceToken(addDevices);
        Log.e("Add Device : Parameters", " : " + addDevices);


        errorMessageCall.enqueue(new Callback<ErrorMessage>() {
            @Override
            public void onResponse(Call<ErrorMessage> call, Response<ErrorMessage> response) {
                if (response.body() != null) {

                    ErrorMessage message = response.body();
                    if (!message.getError()) {
                        Log.e("sendTokenToServer", " : onResume : Success");
                    } else {
                        Log.e("sendTokenToServer", " : onResume : Failed : " + message.getMessage());
                    }
                } else {
                    Log.e("sendTokenToServer", " : onResume : NULL ");
                }
            }

            @Override
            public void onFailure(Call<ErrorMessage> call, Throwable t) {
                Log.e("sendTokenToServer", " : onFailure : " + t.getMessage());
            }
        });


    }
}
