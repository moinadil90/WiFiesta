package moin.movies.viewModels;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import moin.movies.models.MovieListModel;
import moin.movies.models.api.MoviesApiService;
import moin.movies.network.NetworkClient;
import moin.movies.tasks.MovieList;
import moin.movies.utils.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieListViewModel implements MovieList.MovieInterface {

    private MovieList.View view;
    private MoviesApiService moviesApiService;
    private int page = 1;
    private int numPages = 2;
    private String query = "";
    private String movie = "movie";
    private HashMap<String, String> params;
    private ArrayList<MovieListModel.Movie> movieList;
    private static final String TAG = "MovieListViewModel";
    private CompositeDisposable mDisposable = new CompositeDisposable();


    public MovieListViewModel(MovieList.View view) {
        this.view = view;
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        moviesApiService = retrofit.create(MoviesApiService.class);
    }

    @Override
    public void getMoviesList(String title) {
        initPagination(title);
        initParams();
        getObservable().subscribeWith(getObserver(title));
    }

    private Observable<MovieListModel> getObservable() {
        return NetworkClient.getRetrofit().create(MoviesApiService.class)
                .getMovieList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private DisposableObserver<MovieListModel> getObserver(String title) {
        return new DisposableObserver<MovieListModel>() {

            @Override
            public void onNext(@io.reactivex.annotations.NonNull MovieListModel response) {
                movieList = response.getMovies();
                if (movieList != null) {
                    setPagination(response);
                    if (page < numPages) {
                        movieList.add(null);
                    }
                    view.setMovieList(movieList);
                    view.runLayoutAnimation();
                    page++;
                    if (!"".equals(title)) {
                        view.onSuccess();
                    }
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

    @Override
    public void getMoreMovies() {
        if (page <= numPages) {
            initParams();
            getObservableForMoreMovies().subscribeWith(getObserverForMoreMovies());
        }
    }

    private Observable<MovieListModel> getObservableForMoreMovies() {
        return NetworkClient.getRetrofit().create(MoviesApiService.class)
                .getMovieList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private DisposableObserver<MovieListModel> getObserverForMoreMovies() {
        return new DisposableObserver<MovieListModel>() {

            @Override
            public void onNext(@io.reactivex.annotations.NonNull MovieListModel response) {
                movieList = response.getMovies();
                if (movieList != null) {
                    page++;
                    if (page < numPages) {
                        movieList.add(null);
                    }
                    view.setMovieList(movieList);
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

    private void initParams() {
        params = new HashMap<>();
        params.put(Constants.paramSearch, query);
        params.put(Constants.paramType, movie);
        params.put(Constants.paramPage, String.valueOf(page));
        params.put(Constants.paramApiKey, Constants.apiKey);
    }

    private void initPagination(String title) {
        page = 1;
        numPages = 2;
        query = title;
    }

    private void setPagination(MovieListModel response) {
        numPages = response.getTotalResults() / 10;
        if (response.getTotalResults() % 10 != 0) {
            numPages++;
        }
    }

}
