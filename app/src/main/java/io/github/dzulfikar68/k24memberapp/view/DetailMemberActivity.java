package io.github.dzulfikar68.k24memberapp.view;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import io.github.dzulfikar68.k24memberapp.R;
import io.github.dzulfikar68.k24memberapp.model.User;

public class DetailMemberActivity extends AppCompatActivity {

    public static String EXTRA_INTENT = "EXTRA_INTENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_member);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            User member = bundle.getParcelable(EXTRA_INTENT);
            ((TextView) findViewById(R.id.myFullname)).setText(member.getFullname());
            ((TextView) findViewById(R.id.myAddress)).setText(member.getAddress());
            ((TextView) findViewById(R.id.myBirth)).setText(member.getBirth());
            ((TextView) findViewById(R.id.myGender)).setText(member.getGender());
            ((TextView) findViewById(R.id.myUsername)).setText(member.getEmail());
        }
    }
}