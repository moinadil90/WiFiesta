package moin.movies.views.utils;

import android.app.Activity;
import android.graphics.Color;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;
import android.widget.TextView;

import moin.movies.R;

public class Snack {


    public static void Success(Activity activity) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(R.id.cl_movies), activity.getText(R.string.search_success), Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.WHITE);
        TextView textView = sbView.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.BLACK);
        snackbar.show();
    }

    public static void Error(Activity activity, String message) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(R.id.cl_movies), message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.WHITE);
        TextView textView = sbView.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.RED);
        snackbar.show();
    }

}
