package com.example.androidmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    public void moveToHome(View view) {
        Intent moveHome = new Intent(this, MainActivity.class);
        view.getContext().startActivity(moveHome );
    }
}