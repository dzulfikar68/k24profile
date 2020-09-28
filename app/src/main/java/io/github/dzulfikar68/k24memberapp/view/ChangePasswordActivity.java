package io.github.dzulfikar68.k24memberapp.view;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.github.dzulfikar68.k24memberapp.R;
import io.github.dzulfikar68.k24memberapp.controller.DatabaseHelper;
import io.github.dzulfikar68.k24memberapp.controller.SharedPrefManager;
import io.github.dzulfikar68.k24memberapp.model.User;

public class ChangePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        DatabaseHelper db = new DatabaseHelper(this);
        String username = SharedPrefManager.getInstance(this).getUsername();
        String OldPassword = SharedPrefManager.getInstance(this).getPassword();
        User member = db.getUser(username);

        findViewById(R.id.btn_chg_pass).setOnClickListener((v) -> {
            String NewPassword = ((TextView) findViewById(R.id.et_password_new)).getText().toString().trim();
            String OldPasswordET = ((TextView) findViewById(R.id.et_password_now)).getText().toString().trim();
            if (OldPasswordET.equals(OldPassword)) {
                db.updatePassword(member.getId(), NewPassword);
                Toast.makeText(ChangePasswordActivity.this, "berhasil mengganti password baru", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(ChangePasswordActivity.this, "password baru tidak cocok dengan password lama", Toast.LENGTH_SHORT).show();
            }
        });
    }
}