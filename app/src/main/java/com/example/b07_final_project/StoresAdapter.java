package com.example.b07_final_project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class StoresAdapter extends RecyclerView.Adapter<StoresAdapter.StoresViewHolder>
        implements View.OnClickListener{

    private Context context;
    private String[] data;

    public StoresAdapter(Context ct, String[] arr) {
        data = arr;
        context = ct;
    }

    @NonNull
    @Override
    public StoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.store_row,parent,false);
        return new StoresViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoresViewHolder holder, int position) {
        TextView storeTextView = (TextView) holder.storeTextView;
        storeTextView.setText(data[position]);
        storeTextView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.storeName:
                TextView storeName = (TextView)v;
                Intent intent = new Intent(context, StoreActivity.class);
                intent.putExtra("ownerName", storeName.getText().toString());
                StoreListActivity activity = (StoreListActivity) context;
                intent.putExtra("userEmail", activity.user.getEmail());
                intent.putExtra("userName", activity.user.getName());
                intent.putExtra("newOrder", true);
                context.startActivity(intent);
                break;
        }
    }

    public class StoresViewHolder extends RecyclerView.ViewHolder {
        TextView storeTextView;

        public StoresViewHolder(@NonNull View itemView) {
            super(itemView);
            storeTextView = itemView.findViewById(R.id.storeName);
        }
    }

}
