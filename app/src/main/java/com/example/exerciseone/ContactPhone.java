package com.example.exerciseone;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;


public class ContactPhone {

    public static List<Contact> getContacts(Context context){
        List<Contact> contactList = new ArrayList<>();
        ContentResolver cr = context.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if ((cur != null ? cur.getCount() : 0) > 0) {
            int nameColumnIndex = cur.getColumnIndex(
                    ContactsContract.Contacts.DISPLAY_NAME);


            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                if (cur.getInt(cur.getColumnIndex( ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    // having a phone number isn't optional
                    String name;
                    String phoneNumber = "";
                    String email = null;
                    String image = null;
                    name = cur.getString(nameColumnIndex);
                    String[] columns = new String[] { ContactsContract.RawContacts._ID,
                            ContactsContract.Contacts.DISPLAY_NAME,
                            ContactsContract.Contacts.PHOTO_URI,
                            ContactsContract.CommonDataKinds.Email.DATA,
                            ContactsContract.CommonDataKinds.Phone.NUMBER,
                            ContactsContract.CommonDataKinds.Photo.CONTACT_ID};

                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            columns,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        phoneNumber = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        image = pCur.getString(pCur.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));
                    }
                    contactList.add(new Contact(name, phoneNumber, email, image));
                    pCur.close();
                }
            }
        }
        if (cur != null) {
            cur.close();
        }

       return contactList;
    }


//    public ArrayList<String> getNameEmailDetails() {
//        ArrayList<String> emlRecs = new ArrayList<String>();
//        HashSet<String> emlRecsHS = new HashSet<String>();
//        Context context = getActivity();
//        ContentResolver cr = context.getContentResolver();
//        String[] PROJECTION = new String[] { ContactsContract.RawContacts._ID,
//                ContactsContract.Contacts.DISPLAY_NAME,
//                ContactsContract.Contacts.PHOTO_ID,
//                ContactsContract.CommonDataKinds.Email.DATA,
//                ContactsContract.CommonDataKinds.Photo.CONTACT_ID };
//        String order = "CASE WHEN "
//                + ContactsContract.Contacts.DISPLAY_NAME
//                + " NOT LIKE '%@%' THEN 1 ELSE 2 END, "
//                + ContactsContract.Contacts.DISPLAY_NAME
//                + ", "
//                + ContactsContract.CommonDataKinds.Email.DATA
//                + " COLLATE NOCASE";
//        String filter = ContactsContract.CommonDataKinds.Email.DATA + " NOT LIKE ''";
//        Cursor cur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, PROJECTION, filter, null, order);
//        if (cur.moveToFirst()) {
//            do {
//                // names comes in hand sometimes
//                String name = cur.getString(1);
//                String emlAddr = cur.getString(3);
//
//                // keep unique only
//                if (emlRecsHS.add(emlAddr.toLowerCase())) {
//                    emlRecs.add(emlAddr);
//                }
//            } while (cur.moveToNext());
//        }
//
//        cur.close();
//        return emlRecs;
//    }
}
