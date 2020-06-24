package com.example.exerciseone;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

        showImage(contact.image);
        showText(contact.name, (TextView) findViewById(R.id.name_text));
        showText(contact.email, (TextView) findViewById(R.id.email_text));
        showText(contact.phoneNumber, (TextView) findViewById(R.id.phone_num_text));

    }


    private void showImage(String image){
        if(image == null){
            Glide.with(this)
                    .load(this.getDrawable(R.drawable.empty_portrait))
                    .into((ImageView) findViewById(R.id.contact_image));
        }
        else{
            Glide.with(this)
                    .load(Uri.parse(image))
                    .into((ImageView) findViewById(R.id.contact_image));
        }
    }

    private void showText(String text, TextView textView){
        // if the field either does not exist or is an empty string
        if(text == null || text.equals("")){
            textView.setVisibility(View.GONE);
        }
        else{
            textView.setText(text);
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
