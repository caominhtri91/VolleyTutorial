package vn.edu.poly.volleytutorial.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import vn.edu.poly.volleytutorial.Movie;
import vn.edu.poly.volleytutorial.R;
import vn.edu.poly.volleytutorial.network.VolleySingleton;

/**
 * Created by nix on 11/4/16.
 */

public class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.MyViewHolder>{

    private LayoutInflater inflater;
    private List<Movie> movies = Collections.emptyList();
    private Context context;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public MyCustomAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setMoviesList(List<Movie> movies) {
        this.movies = movies;
        notifyItemRangeChanged(0, movies.size());
        volleySingleton = VolleySingleton.getsInstance();
        imageLoader = VolleySingleton.getImageLoader();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_movie_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.title.setText(movie.getTitle());
        holder.release_date.setText(dateFormat.format(movie.getReleaseDate()));
        holder.ratingBar.setRating((float) (movie.getRate()/2.0F));

        String urlPoster = movie.getUrlPoster();
        if (urlPoster != null) {
            imageLoader.get(urlPoster, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.imageView.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView title, release_date;
        RatingBar ratingBar;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            title = (TextView) itemView.findViewById(R.id.title);
            release_date = (TextView) itemView.findViewById(R.id.release_date);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
        }
    }
}
