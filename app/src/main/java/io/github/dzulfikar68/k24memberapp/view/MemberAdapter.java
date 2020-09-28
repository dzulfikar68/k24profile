package io.github.dzulfikar68.k24memberapp.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.github.dzulfikar68.k24memberapp.R;
import io.github.dzulfikar68.k24memberapp.model.User;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<User> list;

    public MemberAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<User> getList() {
        return list;
    }

    public void setList(ArrayList<User> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MemberAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_item, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull MemberAdapter.ItemViewHolder movieViewHolder, int i) {
        movieViewHolder.title.setText("Nama: " + getList().get(i).getFullname());
        movieViewHolder.address.setText("Alamat: " + getList().get(i).getAddress());
    }

    @Override
    public int getItemCount() {
        return getList().size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView title, address;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.nameTextView);
            address = itemView.findViewById(R.id.addressTextView);
        }
    }
}
