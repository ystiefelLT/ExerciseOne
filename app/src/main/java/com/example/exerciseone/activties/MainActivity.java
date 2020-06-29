package com.example.exerciseone.activties;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.exerciseone.models.Contact;
import com.example.exerciseone.utils.ContactRetriever;
import com.example.exerciseone.models.ContactsViewModel;
import com.example.exerciseone.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private final int REQUEST_READ_CONTACTS = 79;
    private ContactsViewModel contactsViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final NavController navController = Navigation.findNavController(this, R.id.fragment_container);
        BottomNavigationView navView = findViewById(R.id.bottom_nav);
        NavigationUI.setupWithNavController(navView, navController);

    }

    @Override
    protected void onResume() {
        super.onResume();
        // moved this from onCreate to onResume to handle cases where the user adds new contacts while the app is open
        if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_CONTACTS)) {
            initializeViewModel();
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_contact),
                    REQUEST_READ_CONTACTS,
                    Manifest.permission.READ_CONTACTS);
        }
    }

    private void initializeViewModel(){
        ArrayList<Contact> contactList= ContactRetriever.getContacts(this);
        if (contactList != null && contactList.size() > 0) {
            contactsViewModel = new ViewModelProvider(this).get(ContactsViewModel.class);
            contactsViewModel.contactList.setValue(contactList);
        } else {
            String text = "Error: no contacts found";
            Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
            toast.show();
        }
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
            initializeViewModel();
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
                initializeViewModel();
            } else {
                String text = "Error: permissions denied";
                Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }
}
