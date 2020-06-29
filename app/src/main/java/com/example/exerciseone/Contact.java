package com.example.exerciseone;

import android.os.Parcel;
import android.os.Parcelable;

public class Contact implements Parcelable {
    String name;
    String phoneNumber;
    String email;
    String image;
    boolean isHidden = false; // initialized as false

    Contact(String name, String phoneNumber, String email, String image) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.phoneNumber);
        dest.writeString(this.email);
        dest.writeString(this.image);
        dest.writeBoolean(this.isHidden);
    }

    protected Contact(Parcel in) {
        this.name = in.readString();
        this.phoneNumber = in.readString();
        this.email = in.readString();
        this.image = in.readString();
        this.isHidden = in.readBoolean();
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
