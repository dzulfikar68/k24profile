package io.github.dzulfikar68.k24memberapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.github.dzulfikar68.k24memberapp.MainActivity;
import io.github.dzulfikar68.k24memberapp.R;
import io.github.dzulfikar68.k24memberapp.controller.DatabaseHelper;
import io.github.dzulfikar68.k24memberapp.controller.IsAdminUtil;
import io.github.dzulfikar68.k24memberapp.controller.SharedPrefManager;
import io.github.dzulfikar68.k24memberapp.model.User;

public class LoginActivity extends AppCompatActivity {

    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new DatabaseHelper(this);
        findViewById(R.id.btn_login).setOnClickListener((v) -> {
            EditText etUsername = findViewById(R.id.et_username);
            String username = etUsername.getText().toString().trim();
            EditText etPassword = findViewById(R.id.et_password);
            String password = etPassword.getText().toString().trim();
            if (!username.isEmpty() && !password.isEmpty()) {
                IsAdminUtil admin = new IsAdminUtil();
                boolean isAdmin = admin.login(username, password);

                //TODO isUser by DB (cek username dan password)
                User member = db.loginCheck(username, password);

                int id;
                if (member == null) {
                    id = 0;
                } else {
                    id = member.getId();
                }

                boolean loginOk = SharedPrefManager.getInstance(getApplicationContext()).setLogin(
                        isAdmin,
                        id,
                        username,
                        password
                );

                if (loginOk) {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Email dan Password Salah", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(LoginActivity.this, "Harap diisi formurlirnya dengan benar", Toast.LENGTH_SHORT).show();
            }
        });
    }
}