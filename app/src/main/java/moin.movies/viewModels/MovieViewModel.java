package moin.movies.viewModels;

import android.util.Log;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import moin.movies.models.MovieModel;
import moin.movies.models.api.MoviesApiService;
import moin.movies.network.NetworkClient;
import moin.movies.tasks.Movie;
import moin.movies.utils.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieViewModel implements Movie.Presenter {

    private Movie.View view;
    private HashMap<String, String> params;
    private MoviesApiService moviesApiService;
    private static final String TAG = "MovieViewModel";

    public MovieViewModel(Movie.View view) {
        this.view = view;
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        moviesApiService = retrofit.create(MoviesApiService.class);
    }

    @Override
    public void getMovie(String imdbID) {
        initParams(imdbID);
        getObservable().subscribeWith(getObserver());
    }

    private Observable<MovieModel> getObservable() {
        return NetworkClient.getRetrofit().create(MoviesApiService.class)
                .getMovie(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private DisposableObserver<MovieModel> getObserver() {
        return new DisposableObserver<MovieModel>() {

            @Override
            public void onNext(@io.reactivex.annotations.NonNull MovieModel response) {
                final MovieModel movie = response;
                if (movie != null) {
                    view.showMovie(movie);
                } else {
                    view.onFailed(response.toString());
                }
            }

            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                Log.d(TAG, "Error" + e);
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "Completed");
            }
        };
    }

    private void initParams(String imdbID) {
        params = new HashMap<>();
        params.put(Constants.paramId, imdbID);
        params.put(Constants.paramPlot, "full");
        params.put(Constants.paramApiKey, Constants.apiKey);
    }
}
