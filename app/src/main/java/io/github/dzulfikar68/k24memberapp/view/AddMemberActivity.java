package io.github.dzulfikar68.k24memberapp.view;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.github.dzulfikar68.k24memberapp.R;
import io.github.dzulfikar68.k24memberapp.controller.DatabaseHelper;

public class AddMemberActivity extends AppCompatActivity {

    Spinner spinner;
    private TextView tvBirth;
    private Calendar myCalendar;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        spinner = findViewById(R.id.sp_gender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.genders, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        tvBirth = findViewById(R.id.tv_birth);
        myCalendar = Calendar.getInstance();
        ImageButton btnBirth = findViewById(R.id.btn_birth);
        btnBirth.setOnClickListener(v -> new DatePickerDialog(AddMemberActivity.this, (view1, year, month, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String formatDate = "yyyy-MM-dd";
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(formatDate);
            tvBirth.setText(sdf.format(myCalendar.getTime()));
        },
                myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());

        db = new DatabaseHelper(this);
        findViewById(R.id.btn_register).setOnClickListener((v) -> {
            EditText etUsername = findViewById(R.id.et_username);
            String username = etUsername.getText().toString().trim();
            EditText etPassword = findViewById(R.id.et_password);
            String password = etPassword.getText().toString().trim();
            EditText etAddress = findViewById(R.id.et_address);
            String address = etAddress.getText().toString().trim();
            EditText etFullname = findViewById(R.id.et_fullname);
            String fullname = etFullname.getText().toString().trim();
            String gender = spinner.getSelectedItem().toString();
            String birth = tvBirth.getText().toString().trim();
            if (
                    !username.isEmpty()
                            && !password.isEmpty()
                            && !fullname.isEmpty()
                            && !gender.isEmpty()
                            && !birth.isEmpty()
                            && !address.isEmpty()
            ) {
                boolean isExist = db.existCheck(username);
                if (!isExist) {
                    long idUser = db.insertUser(
                            fullname, username, password, birth, gender, address
                    );
                    if (idUser > 0) {
                        Toast.makeText(AddMemberActivity.this, "berhasil menambahkan data member", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, ListMemberActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(AddMemberActivity.this, "member sudah ada", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(AddMemberActivity.this, "harap diisi semua formurlir dengan benar", Toast.LENGTH_SHORT).show();
            }
        });
    }
}