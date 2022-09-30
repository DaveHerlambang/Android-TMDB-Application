package com.example.androidmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class DetailActivity extends AppCompatActivity {

    ImageView imageView;
    TextView tvTitle, tvRating, tvTanggal, tvOverview;
    String img, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        String id = getIntent().getStringExtra("ID");
        String img = getIntent().getStringExtra("IMG");
        String title = getIntent().getStringExtra("TITLE");
        String tanggal = getIntent().getStringExtra("DATE");
        String overview = getIntent().getStringExtra("OVERVIEW");
        String rating = getIntent().getStringExtra("RATING");

        tvTitle = findViewById(R.id.titleDetail);
        tvTanggal = findViewById(R.id.dateDetail);
        tvRating = findViewById(R.id.ratingDetail);
        tvOverview = findViewById(R.id.overviewDetail);
        imageView = findViewById(R.id.imageDetail);

        tvTitle.setText(title);
        tvTanggal.setText(tanggal);
        tvRating.setText("Rating : " + rating);
        tvOverview.setText(overview);

        String url = "https://image.tmdb.org/t/p/w500" + img;
        Glide.with(this)
                .load(url)
                .into(imageView);

    }

    public void detailToHome(View view) {
        Intent backToHome = new Intent(this, MainActivity.class);
        view.getContext().startActivity(backToHome);
    }

    public void loadImage (View view){

    }
}