package com.example.exerciseone;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements ContactAdapter.ItemClickListener,
        EasyPermissions.PermissionCallbacks {

    private ContactAdapter adapter;
    private final int REQUEST_READ_CONTACTS = 79;
    private List<Contact> contactList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    protected void onResume() {
        super.onResume();
        // moved this from onCreate to onResume to handle cases where the user adds new contacts while the app is open
        if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_CONTACTS)) {
            displayContacts();
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_contact),
                    REQUEST_READ_CONTACTS,
                    Manifest.permission.READ_CONTACTS);
        }
    }

    private void displayContacts() {
        contactList = ContactRetriever.getContacts(this);
        if (contactList != null && contactList.size() > 0) {
            RecyclerView recyclerView = findViewById(R.id.rv_contacts);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new ContactAdapter(this, contactList);
            adapter.setClickListener(this);
            recyclerView.setAdapter(adapter);
        } else {
            String text = "Error: no contacts found";
            Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("contact", contactList.get(position));
        startActivity(intent);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            displayContacts();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        new AppSettingsDialog.Builder(this).build().show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_CONTACTS)) {
                displayContacts();
            } else {
                String text = "Error: permissions denied";
                Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }
}
