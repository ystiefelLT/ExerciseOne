package com.example.exerciseone;

import android.net.Uri;

public class Contact {
    String mName;
    String mPhoneNumber;
    String mEmail;
    String mImage;

    Contact(String name, String phoneNumber, String email, String image){
        mName = name;
        mPhoneNumber = phoneNumber;
        mEmail = email;
        mImage = image;
    }
}
