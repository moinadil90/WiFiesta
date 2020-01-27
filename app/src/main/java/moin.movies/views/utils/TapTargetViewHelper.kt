package moin.movies.views.utils

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.util.DisplayMetrics
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import butterknife.BindView
import butterknife.ButterKnife
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence
import moin.movies.R

class TapTargetViewHelper(private val activity: Activity) {
    @JvmField
    @BindView(R.id.toolbar)
    var toolbar: Toolbar? = null
    private val context: Context
    fun showTutorial() {
        val displaymetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displaymetrics)
        val screenWidth = displaymetrics.widthPixels
        val screenHeight = displaymetrics.heightPixels
        val movie = ContextCompat.getDrawable(context, R.drawable.ic_view_list)
        val movieTarget = Rect(0, 0,
                (movie?.intrinsicWidth ?: 0) * 2,
                (movie?.intrinsicHeight ?: 0) * 2)
        movieTarget.offset(screenWidth / 2, screenHeight / 2)
        TapTargetSequence(activity)
                .continueOnCancel(true)
                .targets(
                        TapTarget.forToolbarMenuItem(toolbar, R.id.action_search,
                                context.getText(R.string.tutorial_search_title), context.getText(R.string.tutorial_search_description))
                                .targetCircleColor(android.R.color.white)
                                .titleTextColor(android.R.color.white)
                                .descriptionTextColor(android.R.color.white)
                                .drawShadow(true)
                                .cancelable(true)
                                .tintTarget(true),
                        TapTarget.forToolbarMenuItem(toolbar, R.id.action_about,
                                context.getText(R.string.tutorial_about_title), context.getText(R.string.tutorial_about_description))
                                .targetCircleColor(android.R.color.white)
                                .titleTextColor(android.R.color.white)
                                .descriptionTextColor(android.R.color.white)
                                .drawShadow(true)
                                .cancelable(true)
                                .tintTarget(true),
                        TapTarget.forBounds(movieTarget,
                                context.getText(R.string.tutorial_list_title), context.getText(R.string.tutorial_list_description))
                                .targetCircleColor(android.R.color.white)
                                .titleTextColor(android.R.color.white)
                                .descriptionTextColor(android.R.color.white)
                                .icon(movie)
                                .cancelable(true)
                                .tintTarget(true)
                ).start()
    }

    init {
        context = activity.baseContext
        ButterKnife.bind(this, activity)
    }
}