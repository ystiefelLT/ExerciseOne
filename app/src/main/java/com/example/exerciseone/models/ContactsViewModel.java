package com.example.exerciseone.models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ContactsViewModel extends ViewModel {
    public MutableLiveData<ArrayList<Contact>> contactList = new MutableLiveData<>();
    public MutableLiveData<ArrayList<Contact>> hiddenList = new MutableLiveData<>(new ArrayList<Contact>());
    public Contact selectedContact = null;


    public void hideShowContact(){
        final boolean isHidden = selectedContact.isHidden;
        if(isHidden){
            //move contact to show list
            contactList.getValue().add(selectedContact);
            hiddenList.getValue().remove(selectedContact);
        }
        else {
            //move contact to hidden list
            hiddenList.getValue().add(selectedContact);
            contactList.getValue().remove(selectedContact);
        }
        selectedContact.isHidden = !isHidden; // flips the hidden boolean
    }
}
