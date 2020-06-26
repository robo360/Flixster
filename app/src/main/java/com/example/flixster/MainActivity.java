package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;

import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.adapters.MovieAdapter;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=";
    public static final String TAG = "MainActivity";
    List<Movie> movies;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvMovies = findViewById(R.id.rvMovies);
        movies = new ArrayList<>();
        //create the adapter
        final MovieAdapter movieAdapter = new MovieAdapter(this, movies);
        //Set the adapter on the recyclerView
        rvMovies.setAdapter(movieAdapter);
        //Set a layout Manager
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        rvMovies.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        //requesting movies using the AsyncHttpClient to make requests to the movies API
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(NOW_PLAYING_URL + getString(R.string.movies_api_key), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    movies.addAll(Movie.fromJsonArray(results));
                    movieAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Movies" + movies.size());
                } catch (JSONException e) {
                    Log.e(TAG, "hit json exception", e);

                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String errorResponse, Throwable t) {
                    // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.d(TAG, "onFailure");

            }
        }
        );
    }
}