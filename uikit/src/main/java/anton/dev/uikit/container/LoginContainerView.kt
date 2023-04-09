package anton.dev.uikit.container

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import anton.dev.uikit.R

class LoginContainerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        color = context.getColor(R.color.colorAccentVariant)
        style = Paint.Style.FILL
    }
    private val path = Path()

    override fun onDraw(canvas: Canvas) {
        path.lineTo(width - 100f, 0f)
        path.lineTo(width - 200f, height.toFloat())
        path.lineTo(0f, height.toFloat())

        canvas.drawPath(path, paint)
    }
}