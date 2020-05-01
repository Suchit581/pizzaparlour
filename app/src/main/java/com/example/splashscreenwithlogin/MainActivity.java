package com.example.splashscreenwithlogin;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout main;
    private RelativeLayout rellay1, rellay2,rellay3;
    GifImageView gif;
    private Button login,sign_up;
    private EditText editTextUsername , editTextPassword,editTextEmail,editTextNumber,EditTextUsername , EditTextPassword;
    private Button buttonRegister ,buttonLogin;
    private com.example.splashscreenwithlogin.ProgressDialog progressDialog;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initzialize();
        Handler h = new Handler();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                gif.setVisibility(View.GONE);
                rellay3.setVisibility(View.VISIBLE);
                rellay1.setVisibility(View.VISIBLE);
                login.setBackground(getDrawable(R.drawable.btn_bg_active));
                login.setTextColor(getColor(R.color.color_white));
            }
        };
        h.postDelayed(r,4930);
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this,ProfileActivity.class));
            return;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initzialize(){
        //      register initialize

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword =  findViewById(R.id.editTextPassword);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextNumber=findViewById(R.id.editTextNumber);
        buttonRegister = findViewById(R.id.buttonRegister);
        progressDialog  = new ProgressDialog();
        buttonRegister.setOnClickListener(this);
//      login

        EditTextUsername= findViewById(R.id.loginEditTextUsername);
        EditTextPassword= findViewById(R.id.loginEditTextPassword);
        buttonLogin=findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener( this);

//        Upper button
        rellay1 = findViewById(R.id.login);
        rellay2 = findViewById(R.id.signup);
        rellay3 = findViewById(R.id.buttonPanel);
        main = findViewById(R.id.main);
        login = findViewById(R.id.btn_login);
        sign_up = findViewById(R.id.btn_sign_up);
        gif = findViewById(R.id.animBottom);





    }

    private  void registerUser(){
        final String email =  editTextEmail.getText().toString().trim();
        final String username =  editTextUsername.getText().toString().trim();
        final String password =  editTextPassword.getText().toString().trim();
        final String number =  editTextNumber.getText().toString().trim();
        progressDialog.show(getSupportFragmentManager(),"Register user");
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String responce) {

                        try {
                            progressDialog.dismiss();
                            JSONObject jsonObject = new JSONObject(responce);
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String ,String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                params.put("email", email);
                params.put("number", number);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }


    private void userLogin(){
        final String username=EditTextUsername.getText().toString().trim();
        final String password=EditTextPassword.getText().toString().trim();
        progressDialog.show(getSupportFragmentManager(),"Login user");
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.URL_LOGIN, new Response
    .Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    if (!object.getBoolean("error")){
                        progressDialog.dismiss();
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(object.getString("id"), object.getString("username"), object.getString("email"), object.getString("number"));
                        Intent i = new Intent(MainActivity.this,ProfileActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        i.addFlags(Intent.FLAG_FROM_BACKGROUND);
                        startActivity(i);
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage() , Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String ,String>params=new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }


    @TargetApi(Build.VERSION_CODES.M)
    public void onClickButton(View view) {
        if (view.getId() == R.id.btn_login){
            login.setBackground(getDrawable(R.drawable.btn_bg_active));
            login.setTextColor(getColor(R.color.color_white));
            rellay2.setVisibility(View.GONE);
            rellay1.setVisibility(View.VISIBLE);
            sign_up.setBackground(getDrawable(R.drawable.btn_bg));
            sign_up.setTextColor(getColor(R.color.color_red));
        }
        if (view.getId() == R.id.btn_sign_up){
            rellay1.setVisibility(View.GONE);
            rellay2.setVisibility(View.VISIBLE);
            sign_up.setBackground(getDrawable(R.drawable.btn_bg_active));
            sign_up.setTextColor(getColor(R.color.color_white));
            login.setBackground(getDrawable(R.drawable.btn_bg));
            login.setTextColor(getColor(R.color.color_red));

        }

    }

    @Override
    public void onClick(View v) {
        if (v==buttonLogin){
            userLogin();
        }
        if (v == buttonRegister) {
            registerUser();
        }

    }
}

