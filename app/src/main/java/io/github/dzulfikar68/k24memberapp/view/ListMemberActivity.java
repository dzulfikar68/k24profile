package io.github.dzulfikar68.k24memberapp.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.github.dzulfikar68.k24memberapp.MainActivity;
import io.github.dzulfikar68.k24memberapp.R;
import io.github.dzulfikar68.k24memberapp.controller.DatabaseHelper;
import io.github.dzulfikar68.k24memberapp.controller.ItemClickSupport;
import io.github.dzulfikar68.k24memberapp.controller.SharedPrefManager;
import io.github.dzulfikar68.k24memberapp.model.User;

public class ListMemberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_member);

        findViewById(R.id.btn_add).setOnClickListener((v) -> {
            Intent intent = new Intent(this, AddMemberActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btn_logut).setOnClickListener((v) -> {
            SharedPrefManager.getInstance(getApplicationContext()).logout();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        DatabaseHelper db = new DatabaseHelper(this);
        ArrayList<User> list = db.getAllData();

        MemberAdapter adapter = new MemberAdapter(getApplicationContext());
        adapter.setList(list);

        RecyclerView recyclerView = findViewById(R.id.rv_list_member);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener((recyclerView1, position, view) -> {
            Intent moveWithObjectIntent = new Intent(getApplicationContext(), DetailMemberActivity.class);
            moveWithObjectIntent.putExtra(DetailMemberActivity.EXTRA_INTENT, list.get(position));
            startActivity(moveWithObjectIntent);
        });
    }
}