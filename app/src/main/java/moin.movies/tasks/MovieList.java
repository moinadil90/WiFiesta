package moin.movies.tasks;

import java.util.ArrayList;

import moin.movies.models.MovieListModel;

public interface MovieList {
    interface View {
        void setMovieList(ArrayList<MovieListModel.Movie> movieList);

        void updateUi(Boolean isLoading);

        void runLayoutAnimation();

        void onSuccess();

        void onFailed(String message);
    }

    interface MovieInterface {
        void getMoviesList(String title);

        void getMoreMovies();
    }
}
