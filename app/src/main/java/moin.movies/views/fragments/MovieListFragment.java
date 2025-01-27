package moin.movies.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import moin.movies.R;
import moin.movies.models.MovieListModel;
import moin.movies.viewModels.MovieListViewModel;
import moin.movies.tasks.MovieList;
import moin.movies.tasks.OnLoadMoreListener;
import moin.movies.views.adapters.MovieAdapter;
import moin.movies.views.utils.Snack;
import moin.movies.utils.Constants;

public class MovieListFragment extends Fragment implements MovieList.View, OnLoadMoreListener, MovieAdapter.Callback {

    @BindView(R.id.iv_empty) ImageView iv_empty;
    @BindView(R.id.tv_empty) TextView tv_empty;
    @BindView(R.id.rv_movies) RecyclerView rv_movies;
    @BindView(R.id.pg_movies) ProgressBar pg_movies;
    private MovieList.MovieInterface movieInterface;
    private MovieAdapter adapter;
    private ArrayList<MovieListModel.Movie> movieList;
    private MovieListCallback callback;

    public static MovieListFragment newInstance(String query) {
        Bundle args = new Bundle();
        args.putString(Constants.query, query);
        MovieListFragment fragment = new MovieListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragment_movie_list = inflater.inflate(R.layout.fragment_movie_list, container, false);
        ButterKnife.bind(this, fragment_movie_list);
        movieList = new ArrayList<>();
        movieInterface = new MovieListViewModel(this);
        initRecyclerView();
        if (getArguments() != null && !"".equals(getArguments().getString(Constants.query))) {
            queryMovies(getArguments().getString(Constants.query));
        } else {
            /*tv_empty.setText(getText(R.string.welcome_message));
            updateUi(false);*/
            queryMovies("queen");
        }
        return fragment_movie_list;
    }

    private void queryMovies(String title) {
        updateUi(true);
        clearMovieList();
        movieInterface.getMoviesList(title);
    }

    private void initRecyclerView() {
        rv_movies.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_movies.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv_movies.getContext(),
                linearLayoutManager.getOrientation());
        rv_movies.addItemDecoration(dividerItemDecoration);
        adapter = new MovieAdapter(movieList, rv_movies, this);
        adapter.setOnLoadMoreListener(this);
        rv_movies.setAdapter(adapter);
    }

    @Override
    public void setMovieList(ArrayList<MovieListModel.Movie> movieList) {
        adapter.setLoaded();
        if (movieList != null) {
            this.movieList.remove(null);
            this.movieList.addAll(movieList);
            adapter.notifyDataSetChanged();
        }
        updateUi(false);
    }

    @Override
    public void onLoadMore() {
        movieInterface.getMoreMovies();
    }

    @Override
    public void onItemClick(MovieListModel.Movie movie) {
        callback.OpenMovieDetails(movie);
    }

    @Override
    public void runLayoutAnimation() {
        final Context context = rv_movies.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_from_bottom);
        rv_movies.setLayoutAnimation(controller);
        Objects.requireNonNull(rv_movies.getAdapter()).notifyDataSetChanged();
        rv_movies.scheduleLayoutAnimation();
    }

    private void clearMovieList() {
        Objects.requireNonNull(rv_movies.getLayoutManager()).scrollToPosition(0);
        int size = movieList.size();
        movieList.clear();
        adapter.notifyItemRangeRemoved(0, size);
    }

    @Override
    public void updateUi(Boolean isLoading) {
        tv_empty.setVisibility(movieList.size() == 0 && !isLoading ? View.VISIBLE : View.GONE);
        iv_empty.setVisibility(movieList.size() == 0 && !isLoading ? View.VISIBLE : View.GONE);
        rv_movies.setVisibility(movieList.size() == 0 || isLoading ? View.GONE : View.VISIBLE);
        pg_movies.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onSuccess() {
        if (getActivity() != null) {
            Snack.onSuccess(getActivity());
        }
    }

    @Override
    public void onFailed(String message) {
        updateUi(false);
        if (getActivity() != null) {
            Snack.onError(getActivity(), message);
        }
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        try {
            callback = (MovieListCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement MovieListCallback");
        }

    }

    public interface MovieListCallback {
        void OpenMovieDetails(MovieListModel.Movie movie);
    }
}
