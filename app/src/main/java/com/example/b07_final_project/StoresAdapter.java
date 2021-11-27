package com.example.b07_final_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class StoresAdapter extends RecyclerView.Adapter<StoresAdapter.StoresViewHolder> {

    private Context context;
    String[] data;

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
        holder.text.setText(data[position]);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class StoresViewHolder extends RecyclerView.ViewHolder {
        TextView text;

        public StoresViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.storeName);
        }
    }

}
