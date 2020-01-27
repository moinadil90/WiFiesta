package moin.movies.views.utils

import android.app.Activity
import android.graphics.Color
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import moin.movies.R

object Snack {
    @JvmStatic
    fun onSuccess(activity: Activity) {
        val snackbar = Snackbar.make(activity.findViewById(R.id.cl_movies), activity.getText(R.string.search_success), Snackbar.LENGTH_LONG)
        val sbView = snackbar.view
        sbView.setBackgroundColor(Color.WHITE)
        val textView = sbView.findViewById<TextView>(R.id.snackbar_text)
        textView.setTextColor(Color.BLACK)
        snackbar.show()
    }

    @JvmStatic
    fun onError(activity: Activity, message: String?) {
        val snackbar = Snackbar.make(activity.findViewById(R.id.cl_movies), message!!, Snackbar.LENGTH_LONG)
        val sbView = snackbar.view
        sbView.setBackgroundColor(Color.WHITE)
        val textView = sbView.findViewById<TextView>(R.id.snackbar_text)
        textView.setTextColor(Color.RED)
        snackbar.show()
    }
}