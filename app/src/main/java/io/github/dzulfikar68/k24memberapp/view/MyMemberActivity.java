package io.github.dzulfikar68.k24memberapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import io.github.dzulfikar68.k24memberapp.MainActivity;
import io.github.dzulfikar68.k24memberapp.R;
import io.github.dzulfikar68.k24memberapp.controller.DatabaseHelper;
import io.github.dzulfikar68.k24memberapp.controller.SharedPrefManager;
import io.github.dzulfikar68.k24memberapp.model.User;

public class MyMemberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_member);

        DatabaseHelper db = new DatabaseHelper(this);
        String username = SharedPrefManager.getInstance(getApplicationContext()).getUsername();
        User member = db.getUser(username);

        ((TextView) findViewById(R.id.myFullname)).setText(member.getFullname());
        ((TextView) findViewById(R.id.myAddress)).setText(member.getAddress());
        ((TextView) findViewById(R.id.myBirth)).setText(member.getBirth());
        ((TextView) findViewById(R.id.myGender)).setText(member.getGender());
        ((TextView) findViewById(R.id.myUsername)).setText(member.getEmail());

        findViewById(R.id.btn_logut).setOnClickListener((v) -> {
            SharedPrefManager.getInstance(getApplicationContext()).logout();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.btn_change_pass).setOnClickListener((v) -> {
            Intent intent = new Intent(this, ChangePasswordActivity.class);
            startActivity(intent);
        });

    }
}