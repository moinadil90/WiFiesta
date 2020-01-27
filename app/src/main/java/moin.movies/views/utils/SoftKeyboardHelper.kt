package moin.movies.views.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

internal object SoftKeyboardHelper {
    @JvmStatic
    fun closeKeyboard(context: Context) {
        val input = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        input?.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
    }

    @JvmStatic
    fun openKeyboard(context: Context) {
        val input = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = (context as Activity).currentFocus
        if (view != null) {
            input.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
            input.toggleSoftInputFromWindow(view.windowToken, InputMethodManager.SHOW_IMPLICIT, 0)
        }
    }
}