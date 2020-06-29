package com.example.exerciseone;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ContactsViewModel extends ViewModel {
    MutableLiveData<ArrayList<Contact>> contactList = new MutableLiveData<>();
    MutableLiveData<ArrayList<Contact>> hiddenList = new MutableLiveData<>(new ArrayList<Contact>());
    Contact selectedContact = null;
}
