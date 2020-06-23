package com.example.exerciseone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class Details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // move the details to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_details);

        Contact contact = this.getIntent().getParcelableExtra("contact");

        // set image
        int imageIdx = contact.mImage != ContactRepository.EMPTY_IMAGE_RES ? contact.mImage: R.drawable.empty_portrait;
        ((ImageView) findViewById(R.id.imageView2)).setImageResource(imageIdx);

        // set email
        ((TextView) findViewById(R.id.phoneView)).setText(contact.mPhoneNumber);
        String email = contact.mEmail;
        if (email != null) {
            ((TextView) findViewById(R.id.emailView)).setText(email);
        } else {
            findViewById(R.id.emailView).setVisibility(View.GONE);
        }

        // set name
        ((TextView) findViewById(R.id.nameView)).setText(contact.mName);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
