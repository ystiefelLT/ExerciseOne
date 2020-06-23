package com.example.exerciseone;

import android.os.Parcel;
import android.os.Parcelable;

public class Contact implements Parcelable {
    String mName;
    String mPhoneNumber;
    String mEmail;
    int mImage;

    Contact(String name, String phoneNumber, String email, int image) {
        mName = name;
        mPhoneNumber = phoneNumber;
        mEmail = email;
        mImage = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mName);
        dest.writeString(this.mPhoneNumber);
        dest.writeString(this.mEmail);
        dest.writeInt(this.mImage);
    }

    protected Contact(Parcel in) {
        this.mName = in.readString();
        this.mPhoneNumber = in.readString();
        this.mEmail = in.readString();
        this.mImage = in.readInt();
    }

    public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel source) {
            return new Contact(source);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
}
