package com.example.exerciseone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ContactAdapter.ItemClickListener {

    ContactAdapter mAdapter;
    private List<Contact> mContactList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContactList = ContactRepository.getContacts();
        if (mContactList != null && mContactList.size() > 0) {
            RecyclerView recyclerView = findViewById(R.id.rv_contacts);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            mAdapter = new ContactAdapter(this, mContactList);
            mAdapter.setClickListener(this);
            recyclerView.setAdapter(mAdapter);
        } else {
            CharSequence text = "Error: no contacts found";
            Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Contact contact = mContactList.get(position);
        Intent intent = new Intent(this, Details.class);
        intent.putExtra("name", contact.mName);
        intent.putExtra("image", contact.mImage);
        intent.putExtra("email", contact.mEmail);
        intent.putExtra("phone", contact.mPhoneNumber);
        startActivity(intent);
    }

}
