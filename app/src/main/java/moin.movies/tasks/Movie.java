package moin.movies.tasks;

import moin.movies.models.MovieModel;

public interface Movie {
    interface View {
        void showMovie(MovieModel movie);

        void onFailed(String message);
    }

    interface Presenter {
        void getMovie(String imdbID);
    }
}
