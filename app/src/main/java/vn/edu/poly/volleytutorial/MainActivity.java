package vn.edu.poly.volleytutorial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vn.edu.poly.volleytutorial.adapter.MyCustomAdapter;
import vn.edu.poly.volleytutorial.network.VolleySingleton;

import static vn.edu.poly.volleytutorial.extras.UrlEndpoints.URL_API_KEY;
import static vn.edu.poly.volleytutorial.extras.UrlEndpoints.URL_IMG;
import static vn.edu.poly.volleytutorial.extras.UrlEndpoints.URL_PARAM;
import static vn.edu.poly.volleytutorial.extras.UrlEndpoints.URL_TMDB;

import static vn.edu.poly.volleytutorial.extras.Keys.EndpointKeys.*;

public class MainActivity extends AppCompatActivity {

    List<Movie> movies;
    AVLoadingIndicatorView avi;

    private RecyclerView listMovies;
    private DateFormat dateFormat;
    private MyCustomAdapter myCustomAdapter;

    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;

    public static String getRequestUrl() {
        return URL_TMDB + URL_PARAM + URL_API_KEY;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movies = new ArrayList<>();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        listMovies = (RecyclerView) findViewById(R.id.listMovies);

        avi.show();

        volleySingleton = VolleySingleton.getsInstance();
        requestQueue = VolleySingleton.getRequestQueue();
        sendJsonRequest();

        listMovies.setLayoutManager(new LinearLayoutManager(this));
        myCustomAdapter = new MyCustomAdapter(this);
        listMovies.setAdapter(myCustomAdapter);
    }

    private void sendJsonRequest() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, getRequestUrl(), null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                movies = parseJsonResponse(response);
                myCustomAdapter.setMoviesList(movies);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    private List<Movie> parseJsonResponse(JSONObject response) {

        StringBuilder data = new StringBuilder();
        List<Movie> list = new ArrayList<>();

        if (response == null || response.length() == 0) {
            return null;
        }

        try {
            JSONArray jsonArray = response.getJSONArray(KEY_RESULTS);

            for (int i = 0; i<jsonArray.length(); i++) {
                JSONObject currentMovie = jsonArray.getJSONObject(i);

                String title=null;
                if (currentMovie.has(KEY_TITLE)) {
                    title = currentMovie.getString(KEY_TITLE);
                }

                String releaseDate = null;
                if (currentMovie.has(KEY_RELEASE_DATE)) {
                    releaseDate = currentMovie.getString(KEY_RELEASE_DATE);
                }

                data.append(title + "\n" + releaseDate + "\n");

                String posterUrl = null;
                if (currentMovie.has(KEY_POSTER)) {
                    posterUrl = URL_IMG + currentMovie.getString(KEY_POSTER);
                }

                Double rating = null;
                if (currentMovie.has(KEY_VOTE)) {
                    rating = currentMovie.getDouble(KEY_VOTE);
                }

                Date release = dateFormat.parse(releaseDate);
                Movie movie = new Movie(posterUrl, title, release, rating);
                list.add(movie);
            }
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }

        avi.hide();
        return list;
    }
}
