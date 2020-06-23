package com.example.exerciseone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// https://stackoverflow.com/questions/40584424/simple-android-recyclerview-example
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {


    private ItemClickListener mClickListener;
    private LayoutInflater mInflater;
    private List<Contact> mData;

    ContactAdapter(Context context, List<Contact> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @NonNull
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.contact_name, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ViewHolder holder, int position) {
        String name = mData.get(position).mName;
        holder.myTextView.setText(name);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    void setClickListener(ItemClickListener itemClickListener) {
        mClickListener = itemClickListener;
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
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
