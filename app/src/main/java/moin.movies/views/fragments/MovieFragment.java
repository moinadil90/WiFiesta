package moin.movies.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import moin.movies.utils.Constants;
import moin.movies.R;
import moin.movies.models.MovieModel;
import moin.movies.viewModels.MovieViewModel;
import moin.movies.tasks.Movie;
import moin.movies.views.adapters.GenreAdapter;
import moin.movies.views.adapters.RatingAdapter;
import moin.movies.views.utils.Snack;

public class MovieFragment extends Fragment implements Movie.View {

    @BindView(R.id.tv_released) TextView tv_released;
    @BindView(R.id.tv_director) TextView tv_director;
    @BindView(R.id.tv_writer) TextView tv_writer;
    @BindView(R.id.tv_rated) TextView tv_rated;
    @BindView(R.id.tv_plot) TextView tv_plot;
    @BindView(R.id.rv_genre) RecyclerView rv_genre;
    @BindView(R.id.rv_ratings) RecyclerView rv_ratings;
    @BindView(R.id.sv_movie) ScrollView sv_movie;
    @BindView(R.id.pg_movie) ProgressBar pg_movie;
    private ArrayList<String> genres;
    private ArrayList<MovieModel.Rating> ratings;

    public static MovieFragment newInstance(String imdbID) {
        Bundle args = new Bundle();
        args.putString(Constants.imdbId, imdbID);
        MovieFragment fragment = new MovieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragment_movie = inflater.inflate(R.layout.fragment_movie, container, false);
        ButterKnife.bind(this, fragment_movie);
        genres = new ArrayList<>();
        ratings = new ArrayList<>();
        Movie.Presenter presenter = new MovieViewModel(this);
        presenter.getMovie(Objects.requireNonNull(getArguments()).getString(Constants.imdbId));
        return fragment_movie;
    }

    @Override
    public void showMovie(MovieModel movie) {
        tv_released.setText(String.format("%s: %s", getText(R.string.year), movie.getReleased()));
        tv_director.setText(String.format("%s: %s", getText(R.string.director), movie.getDirector()));
        tv_writer.setText(String.format("%s: %s", getText(R.string.writer), movie.getWriter()));
        tv_rated.setText(movie.getRated());
        tv_plot.setText(movie.getPlot());
        genres.addAll(movie.getGenre());
        ratings.addAll(movie.getRatings());
        initGenresRecyclerView();
        initRatingRecyclerView();
        updateUi(false);
    }

    public void initGenresRecyclerView() {
        rv_genre.setHasFixedSize(true);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getContext());
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        rv_genre.setLayoutManager(layoutManager);
        GenreAdapter adapter = new GenreAdapter(genres);
        rv_genre.setAdapter(adapter);
    }

    public void initRatingRecyclerView() {
        rv_ratings.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_ratings.setLayoutManager(linearLayoutManager);
        RatingAdapter adapter = new RatingAdapter(ratings);
        rv_ratings.setAdapter(adapter);
    }

    @Override
    public void onFailed(String message) {
        if (getActivity() != null) {
            Snack.onError(getActivity(), message);
        }
    }

    private void updateUi(boolean isLoading) {
        pg_movie.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        sv_movie.setVisibility(isLoading ? View.GONE : View.VISIBLE);
    }
}
