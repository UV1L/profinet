package anton.dev.uikit.text

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity.CENTER
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.ViewGroup.MarginLayoutParams
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import anton.dev.uikit.R

open class BaseTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    var textStyle: TextViewStyle? = null
        set(value) {
            when (value) {
                TextViewStyle.PRIMARY -> setTextSize(TypedValue.COMPLEX_UNIT_SP, 22f)
                TextViewStyle.PRIMARY2 -> setTextSize(TypedValue.COMPLEX_UNIT_SP, 17f)
                TextViewStyle.SECONDARY -> setTextSize(TypedValue.COMPLEX_UNIT_SP, 13f)
                TextViewStyle.HINT -> setTextSize(TypedValue.COMPLEX_UNIT_SP, 11f)
                else -> Unit
            }
            field = value
        }

    init {
        getAttrs(attrs, defStyleAttr)
        layoutParams = MarginLayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        typeface = ResourcesCompat.getFont(context, R.font.baloo_cyrillic)
        gravity = CENTER
        includeFontPadding = false
    }

    private fun getAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.BaseTextView, defStyleAttr, 0)
            textStyle = TextViewStyle.values()[typedArray.getInt(R.styleable.BaseTextView_textStyle, 4)]
            typedArray.recycle()
        }
    }
}