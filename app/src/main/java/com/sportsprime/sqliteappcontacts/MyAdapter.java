package com.sportsprime.sqliteappcontacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private final LayoutInflater inflater;
    private final List<Item> items;

    MyAdapter(Context context, List<Item> items) {
        this.items = items;
        this.inflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.textview_id.setText(String.valueOf(item.getId()));
        holder.textview_name.setText(item.getName());
        holder.textview_email.setText(item.getEmail());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView textview_name;
        final TextView textview_email;
        final TextView textview_id;
        ViewHolder(View view){
            super(view);
            textview_name = view.findViewById(R.id.name);
            textview_email = view.findViewById((R.id.email));
            textview_id = view.findViewById((R.id.id));
        }
    }
}
