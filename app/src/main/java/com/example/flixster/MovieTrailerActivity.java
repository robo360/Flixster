package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import okhttp3.Headers;

public class MovieTrailerActivity extends YouTubeBaseActivity {
    Movie movie;
    String apiKey;
    String TAG = "MovieTrailerActivity";
    String videoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailer);
        // set up an a url
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        apiKey = getString(R.string.movies_api_key);
        String url = String.format("https://api.themoviedb.org/3/movie/%d/videos?api_key=%s&language=en-US", movie.getId(), apiKey);

        // make a request to get the movie video id
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.d(TAG, "onSuccess");
                        JSONObject jsonObject = json.jsonObject;
                        try {
                            JSONArray result = jsonObject.getJSONArray("results");
                            videoId = result.getJSONObject(0).getString("key");
                            Log.d(TAG, "id:"+videoId);
                            YouTubePlayerView playerView = (YouTubePlayerView) findViewById(R.id.player);

                            // initialize with API key stored in secrets.xml
                            playerView.initialize(getString(R.string.youtube_api_key), new YouTubePlayer.OnInitializedListener() {
                                @Override
                                public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                                    YouTubePlayer youTubePlayer, boolean b) {
                                    // do any work here to cue video, play video, etc.
                                    youTubePlayer.cueVideo(videoId);
                                }

                                @Override
                                public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                                    YouTubeInitializationResult youTubeInitializationResult) {
                                    // log the error
                                    Log.e("MovieTrailerActivity", "Error initializing YouTube player");
                                }
                            });

                        } catch (JSONException e) {
                            Log.e(TAG, "hit json exception", e);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String errorResponse, Throwable t) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                        Log.d(TAG, "onFailure");

                    }
                });

    }
}