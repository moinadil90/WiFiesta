package moin.movies.models.api;

import java.util.HashMap;

import io.reactivex.Observable;
import moin.movies.models.MovieListModel;
import moin.movies.models.MovieModel;
import moin.movies.utils.Constants;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;

public interface MoviesApiService {
    @Headers({
            "Content-Type: application/json;charset=utf-8",
            "Accept: application/json"
    })
    @GET(Constants.BASE_URL)
    Observable<MovieListModel> getMovieList(@QueryMap HashMap<String, String> params);

    @GET(Constants.BASE_URL)
    Observable<MovieModel> getMovie(@QueryMap HashMap<String, String> params);
}
