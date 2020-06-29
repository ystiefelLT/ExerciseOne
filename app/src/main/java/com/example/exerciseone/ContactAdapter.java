package com.example.exerciseone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// https://stackoverflow.com/questions/40584424/simple-android-recyclerview-example
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder>{


    private ItemClickListener clickListener;
    private LayoutInflater inflater;
    private MutableLiveData<ArrayList<Contact>> contactList;

    ContactAdapter(Context context, MutableLiveData<ArrayList<Contact>> data){
        this.inflater = LayoutInflater.from(context);
        this.contactList = data;
    }
    @NonNull
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.contact_name, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ViewHolder holder, int position) {
        String name = contactList.getValue().get(position).name;
        holder.myTextView.setText(name);
    }

    @Override
    public int getItemCount() {
        return contactList.getValue().size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.contact_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onItemClick(view, getAdapterPosition());
        }
    }

    void setClickListener(ItemClickListener itemClickListener){
        clickListener = itemClickListener;
    }
    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
