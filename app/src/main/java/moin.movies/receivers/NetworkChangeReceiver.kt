package moin.movies.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import moin.movies.views.utils.NetworkUtil.getConnectivityStatus

class NetworkChangeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        networkChange!!.onNetworkChanged(getConnectivityStatus(context))
    }

    fun setNetworkChange(networkChange: NetworkChange?) {
        Companion.networkChange = networkChange
    }

    interface NetworkChange {
        fun onNetworkChanged(status: Int)
    }

    companion object {
        private var networkChange: NetworkChange? = null
    }
}