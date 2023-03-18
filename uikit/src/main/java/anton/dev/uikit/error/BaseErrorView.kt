package anton.dev.uikit.error

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.doOnAttach
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.view.postDelayed
import androidx.core.view.updateLayoutParams
import anton.dev.uikit.R

class BaseErrorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val animationEndListener = object : ViewPropertyAnimationListener() {
        override fun onAnimationEnd(animation: Animator) {
            postDelayed(2000L) {
                shrink()
            }
        }
    }

    private val defaultHeight by lazy { resources.getDimensionPixelSize(R.dimen.space_10) }

    private val textView = TextView(context).apply {
        layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        setTextColor(Color.WHITE)
        textAlignment = TEXT_ALIGNMENT_CENTER
        gravity = Gravity.CENTER_VERTICAL
        textSize = 15F
    }

    var errorText: String? = null
        set(value) {
            if (field == value) return
            textView.text = value
            field = value
        }

    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER
        pivotY = 0f
        setBackgroundColor(
            resources.getColor(
                R.color.light_alert_red,
                context.theme
            )
        )
        setText()
        addView(textView)
        scaleY = 0f
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measuredWidth, defaultHeight)
    }

    private fun setText() {
        errorText = resources.getString(R.string.something_went_wrong)
    }

    fun expand() = post {
        animate()
            .scaleY(1f)
            .setDuration(ANIMATION_LENGTH)
            .setInterpolator(INTERPOLATOR)
            .setListener(animationEndListener)
            .start()
    }

    fun shrink() = post {
        animate()
            .scaleY(0f)
            .setDuration(ANIMATION_LENGTH)
            .setInterpolator(INTERPOLATOR)
            .setListener(null)
            .start()
    }

    companion object {

        const val ANIMATION_LENGTH = 250L

        val INTERPOLATOR by lazy { AccelerateDecelerateInterpolator() }
    }
}