package moin.movies.network

import moin.movies.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object NetworkClient {
    @JvmStatic
    var retrofit: Retrofit? = null
        get() {
            if (field == null) {
                val builder = OkHttpClient.Builder()
                val okHttpClient = builder.build()
                field = Retrofit.Builder()
                        .baseUrl(Constants.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .client(okHttpClient)
                        .build()
            }
            return field
        }
        private set
}