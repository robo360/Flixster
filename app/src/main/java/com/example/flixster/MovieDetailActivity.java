package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;
import org.w3c.dom.Text;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;


public class MovieDetailActivity extends AppCompatActivity {
    Movie movie;
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RatingBar rbVoteAverage;
        TextView tvOverView;
        TextView tvTitle;
        ImageView ivPoster;
        setContentView(R.layout.activity_movie_detail2);
        rbVoteAverage = findViewById(R.id.rbVoteAverage);
        tvOverView = findViewById(R.id.tvOverview);
        tvTitle = findViewById(R.id.tvTitle);
        ivPoster = findViewById(R.id.ivPoster);
        //unwrap the parceled movie passed using intent
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailActivity", "voteAverage"+movie.getVoteAverage());
        float VoteAverage = (float) movie.getVoteAverage();
        rbVoteAverage.setRating(VoteAverage);
        tvOverView.setText(movie.getOverview());
        tvTitle.setText(movie.getTitle());
        Glide.with(context).load(movie.getBackDropPath()).into(ivPoster);
    }
}


