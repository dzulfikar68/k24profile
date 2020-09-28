package io.github.dzulfikar68.k24memberapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import io.github.dzulfikar68.k24memberapp.controller.SharedPrefManager;
import io.github.dzulfikar68.k24memberapp.view.ListMemberActivity;
import io.github.dzulfikar68.k24memberapp.view.LoginActivity;
import io.github.dzulfikar68.k24memberapp.view.MyMemberActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                gameOver();
            }
        };
        handler.postDelayed(r, 1000);
    }

    private void gameOver() {
        if (SharedPrefManager.getInstance(getApplicationContext()).isAdmin()) {
            Intent intent = new Intent(this, ListMemberActivity.class);
            startActivity(intent);
            finish();
        } else if (SharedPrefManager.getInstance(getApplicationContext()).isUser() > 0) {
            Intent intent = new Intent(this, MyMemberActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}