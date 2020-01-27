package moin.movies.views.utils

import android.content.Context
import android.net.ConnectivityManager
import moin.movies.utils.Constants
import java.util.*

object NetworkUtil {
    @JvmStatic
    fun getConnectivityStatus(context: Context): Int {
        val cm = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = Objects.requireNonNull(cm).activeNetworkInfo
        return if (activeNetwork == null || !activeNetwork.isConnected) Constants.NETWORK_DESCONNECTED else Constants.NETWORK_CONNECTED
    }
}