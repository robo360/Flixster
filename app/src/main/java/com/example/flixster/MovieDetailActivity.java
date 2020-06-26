package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;
import org.w3c.dom.Text;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Headers;


public class MovieDetailActivity extends YouTubeBaseActivity {
    //declare key variables
    Movie movie;
    Context context = this;
    String apiKey;
    String TAG = "MovieDetailActivity";
    String videoId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //declare views
        RatingBar rbVoteAverage;
        TextView tvOverView;
        TextView tvTitle;

        //locate views
        setContentView(R.layout.activity_movie_detail2);
        rbVoteAverage = findViewById(R.id.rbVoteAverage);
        tvOverView = findViewById(R.id.tvOverview);
        tvTitle = findViewById(R.id.tvTitle);

        //unwrap the parceled movie passed using intent
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailActivity", "voteAverage"+movie.getVoteAverage());

        //fill the views with data
        float VoteAverage = (float) movie.getVoteAverage();
        rbVoteAverage.setRating(VoteAverage);
        tvOverView.setText(movie.getOverview());
        tvTitle.setText(movie.getTitle());

        // request and post video from YouTube API.
        apiKey = getString(R.string.movies_api_key);
        String url = String.format("https://api.themoviedb.org/3/movie/%d/videos?api_key=%s&language=en-US", movie.getId(), apiKey);
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


