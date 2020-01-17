package com.skalyter.mytraveljournal.activities;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.skalyter.mytraveljournal.R;
import com.skalyter.mytraveljournal.ui.login.LoginActivity;
import com.skalyter.mytraveljournal.util.Constant;
import com.skalyter.mytraveljournal.util.SharedPreferencesUtil;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;

import static com.skalyter.mytraveljournal.util.Constant.USER_FIRST_NAME;
import static com.skalyter.mytraveljournal.util.Constant.USER_LAST_NAME;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_about, R.id.nav_contact,
                R.id.nav_share)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        String userEmail = SharedPreferencesUtil.getStringValueFromSharedPreferences(this, Constant.USER_EMAIL);
        String userFirstName = SharedPreferencesUtil.getStringValueFromSharedPreferences(this, USER_FIRST_NAME);
        String userLastName = SharedPreferencesUtil.getStringValueFromSharedPreferences(this, USER_LAST_NAME);
        if (userFirstName.length() > 0 && userLastName.length() > 0) {
            TextView username = navigationView.getHeaderView(0).findViewById(R.id.username);
            username.setText(userFirstName + " " + userLastName);
        }
        if (userEmail.length() > 0) {
            TextView email = navigationView.getHeaderView(0).findViewById(R.id.email);
            email.setText(userEmail);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                SharedPreferencesUtil.setStringValueInSharedPreferences(
                        this, Constant.ACCOUNT_STATUS, Constant.ACCOUNT_DISCONNECTED);
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
