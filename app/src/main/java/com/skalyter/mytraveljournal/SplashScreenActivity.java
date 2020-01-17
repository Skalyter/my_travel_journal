package com.skalyter.mytraveljournal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.skalyter.mytraveljournal.ui.login.LoginActivity;
import com.skalyter.mytraveljournal.util.SharedPreferencesUtil;

import static com.skalyter.mytraveljournal.util.Constant.ACCOUNT_CONNECTED;
import static com.skalyter.mytraveljournal.util.Constant.ACCOUNT_STATUS;

public class SplashScreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent intent;
        if(SharedPreferencesUtil
                .getStringValueFromSharedPreferences(
                        this, ACCOUNT_STATUS).equals(ACCOUNT_CONNECTED)) {
            intent = new Intent(SplashScreenActivity.this, MainActivity.class);

        } else {
            intent = new Intent(SplashScreenActivity.this, LoginActivity.class);

        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        }, 1000);
    }
}
