package com.example.myapplication.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Join.JoinActivity;
import com.example.myapplication.R;
import com.example.myapplication.network.RetrofitClient;
import com.example.myapplication.network.ServiceApi;
import com.example.myapplication.ui.HomeActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText emailText;
    private EditText passwordText;
    private ServiceApi service;
    private ProgressBar mProgressView;
    private final String NICKNAME_EXTRA = "NICKNAME_EXTRA";
    private CheckBox auto_login;
    SharedPreferences setting;
    SharedPreferences.Editor editor;
    private long backBtnTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailText = (EditText) findViewById(R.id.username);
        passwordText = (EditText) findViewById(R.id.password);

        Button loginButton = (Button) findViewById(R.id.signin);
        Button registerButton = (Button) findViewById(R.id.register);
        mProgressView = (ProgressBar) findViewById(R.id.loading);

        auto_login = (CheckBox) findViewById(R.id.autologin);

        setting = getSharedPreferences("setting",0);
        editor = setting.edit();

        service = RetrofitClient.getClient().create(ServiceApi.class);

        auto_login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(isChecked){
                    String email = emailText.getText().toString();
                    String password = passwordText.getText().toString();

                    editor.putString("email", email);
                    editor.putString("password",password);
                    editor.putBoolean("auto_login_enabled", true);
                    editor.commit();

                } else{
                    editor.clear();
                    editor.commit();
                }
            }
        });

        if(setting.getBoolean("auto_login_enabled",false)){
            emailText.setText(setting.getString("email",""));
            passwordText.setText(setting.getString("password",""));
            auto_login.setChecked(true);
            attemptLogin();
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent registerIntent = new Intent(LoginActivity.this, JoinActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;

        if(0 <= gapTime && 2000 >= gapTime) {
            super.onBackPressed();
        }
        else {
            backBtnTime = curTime;
            Toast.makeText(this, "한번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT).show();
        }
    }

    private void attemptLogin() {
        emailText.setError(null);
        passwordText.setError(null);

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // 패스워드의 유효성 검사
        if (password.isEmpty()) {
            passwordText.setError("비밀번호를 입력해주세요.");
            focusView = passwordText;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            passwordText.setError("6자 이상의 비밀번호를 입력해주세요.");
            focusView = passwordText;
            cancel = true;
        }

        // 이메일의 유효성 검사
        if (email.isEmpty()) {
            emailText.setError("이메일을 입력해주세요.");
            focusView = emailText;
            cancel = true;
        } else if (!isEmailValid(email)) {
            emailText.setError("@를 포함한 유효한 이메일을 입력해주세요.");
            focusView = emailText;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            startLogin(new LoginData(email, password));
            showProgress(true);
        }
    }

    private void startLogin(LoginData data) {
        service.userLogin(data).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse result = response.body();
                Toast.makeText(LoginActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                showProgress(false);

                if(result.getCode()==200){
                    Intent loginIntent = new Intent(LoginActivity.this, HomeActivity.class);
                    loginIntent.putExtra(NICKNAME_EXTRA, result.getNickname());
                    LoginActivity.this.startActivity(loginIntent);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "로그인 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("로그인 에러 발생", t.getMessage());
                showProgress(false);
            }
        });
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }

    private void showProgress(boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
