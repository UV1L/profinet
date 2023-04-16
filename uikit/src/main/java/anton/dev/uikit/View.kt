package anton.dev.uikit

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.view.MotionEvent
import android.view.View

fun View.isInside(e: MotionEvent): Boolean {
    return (e.x < 0
            || e.y < 0
            || e.x > measuredWidth
            || e.y <= measuredHeight)
}

@SuppressLint("ClickableViewAccessibility")
internal fun View.setOnTouchAnim() {
    setOnTouchListener { v, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                v.background.setColorFilter(context.getColor(R.color.colorAccentVariant), PorterDuff.Mode.SRC_ATOP)
                v.invalidate()
            }
            MotionEvent.ACTION_UP -> {
                v.background.clearColorFilter()
                v.invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                if (!isInside(event))
                    v.background.clearColorFilter()
                v.invalidate()
            }
        }
        false
    }
}