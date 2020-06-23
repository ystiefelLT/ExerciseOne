package com.example.exerciseone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // move the details to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_details);

        Contact contact = this.getIntent().getParcelableExtra("contact");

        String image = contact.mImage;
        if(image == null){
            ((ImageView) findViewById(R.id.imageView2)).setImageResource(R.drawable.empty_portrait);
        }
        else{
            Glide.with(this)
                    .load(Uri.parse(image))
                    .into((ImageView) findViewById(R.id.imageView2));
        }

        ((TextView) findViewById(R.id.phoneView)).setText(contact.mPhoneNumber);
        String email = contact.mEmail;
        if(email != null){
            ((TextView) findViewById(R.id.emailView)).setText(email);
        }
        else {
            findViewById(R.id.emailView).setVisibility(View.GONE);
        }
        ((TextView) findViewById(R.id.nameView)).setText(contact.mName);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
