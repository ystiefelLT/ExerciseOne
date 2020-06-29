package com.example.exerciseone.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.example.exerciseone.models.Contact;

import java.util.ArrayList;


public class ContactRetriever {

    public static ArrayList<Contact> getContacts(Context context) {
        ArrayList<Contact> contactList = new ArrayList<>();
        ContentResolver cr = context.getContentResolver();
        String[] contactColumns = {ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts._ID};


        Cursor contactCursor = cr.query(ContactsContract.Contacts.CONTENT_URI,
                contactColumns, null, null, null);

        if ((contactCursor != null ? contactCursor.getCount() : 0) > 0) {
            int nameColumnIndex = contactCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);

            String[] cursorColumns = {ContactsContract.RawContacts._ID,
                    ContactsContract.Contacts.DISPLAY_NAME,
                    ContactsContract.Contacts.PHOTO_URI,
                    ContactsContract.CommonDataKinds.Email.DATA,
                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                    ContactsContract.CommonDataKinds.Photo.CONTACT_ID};
            String[] emailColumns = {ContactsContract.CommonDataKinds.Email.DATA};


            while (contactCursor.moveToNext()) {
                String contactID = contactCursor.getString(
                        contactCursor.getColumnIndex(ContactsContract.Contacts._ID));

                String phoneNumber = null;
                String email = null;
                String image = null;
                String name = contactCursor.getString(nameColumnIndex);

                Cursor phoneCursor = cr.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        cursorColumns,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        new String[]{contactID}, null);

                if (phoneCursor.moveToNext()) {
                    phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    image = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));
                }
                phoneCursor.close();

                Cursor emailCursor = cr.query(
                        ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                        emailColumns,
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                        new String[]{contactID}, null);
                if (emailCursor.moveToNext()) {
                    email = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                }
                emailCursor.close();

                contactList.add(new Contact(name, phoneNumber, email, image));

            }
        }
        if (contactCursor != null) {
            contactCursor.close();
        }

        return contactList;
    }
}
