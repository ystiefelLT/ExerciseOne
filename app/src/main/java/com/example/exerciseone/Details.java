package com.example.exerciseone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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

//        int imageIdx = contact.mImage != ContactRepository.EMPTY_IMAGE_RES ? contact.mImage : R.drawable.empty_portrait;
//        ((ImageView) findViewById(R.id.imageView2)).setImageResource(imageIdx);
        String image = this.getIntent().getStringExtra("image");
        if(image == null){
            ((ImageView) findViewById(R.id.imageView2)).setImageResource(R.drawable.empty_portrait);
        }
        else{
            ((ImageView) findViewById(R.id.imageView2)).setImageURI(Uri.parse(image));
        }
        
        ((TextView) findViewById(R.id.phoneView)).setText(this.getIntent().getStringExtra("phone"));
        String email = this.getIntent().getStringExtra("email");
        if(email != null){
            ((TextView) findViewById(R.id.emailView)).setText(email);
        }
        else {
            findViewById(R.id.emailView).setVisibility(View.GONE);
        }
        ((TextView) findViewById(R.id.nameView)).setText(this.getIntent().getStringExtra("name"));
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
