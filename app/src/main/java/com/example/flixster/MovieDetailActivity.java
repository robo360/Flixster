package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.flixster.models.Movie;

import org.parceler.Parcels;
import org.w3c.dom.Text;

public class MovieDetailActivity extends AppCompatActivity {
    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RatingBar rbVoteAverage;
        TextView tvOverView;
        TextView tvTitle;
        setContentView(R.layout.activity_movie_detail2);
        rbVoteAverage = findViewById(R.id.rbVoteAverage);
        tvOverView = findViewById(R.id.tvOverview);
        tvTitle = findViewById(R.id.tvTitle);
        //unwrap the parceled movie passed using intent
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailActivity", "voteAverage"+movie.getVoteAverage());
        float VoteAverage = (float) movie.getVoteAverage();
        rbVoteAverage.setRating(VoteAverage);
        tvOverView.setText(movie.getOverview());
        tvTitle.setText(movie.getTitle());

    }
}


