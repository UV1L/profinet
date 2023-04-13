package anton.dev.uikit.text

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity.CENTER
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.ViewGroup.MarginLayoutParams
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.doOnAttach
import androidx.core.view.updateLayoutParams
import anton.dev.uikit.R

@RequiresApi(Build.VERSION_CODES.O)
class BaseTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var textStyle: TextViewStyle? = null
        set(value) {
            when (value) {
                TextViewStyle.PRIMARY -> setTextSize(TypedValue.COMPLEX_UNIT_SP, 22f)
                TextViewStyle.PRIMARY2 -> setTextSize(TypedValue.COMPLEX_UNIT_SP, 17f)
                TextViewStyle.SECONDARY -> setTextSize(TypedValue.COMPLEX_UNIT_SP, 13f)
                TextViewStyle.HINT -> setTextSize(TypedValue.COMPLEX_UNIT_SP, 13f)
                else -> Unit
            }
            field = value
        }

    init {
        getAttrs(attrs, defStyleAttr)
        layoutParams = MarginLayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        typeface = resources.getFont(R.font.baloo_cyrillic)
        gravity = CENTER
        includeFontPadding = false
        doOnAttach {
            updateLayoutParams<MarginLayoutParams> {
                setMargins(
                    resources.getDimensionPixelSize(R.dimen.space_6),
                    resources.getDimensionPixelSize(R.dimen.space_3),
                    resources.getDimensionPixelSize(R.dimen.space_6),
                    resources.getDimensionPixelSize(R.dimen.space_3)
                )
            }
        }
    }

    private fun getAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.BaseTextView, defStyleAttr, 0)
            textStyle = TextViewStyle.values()[typedArray.getInt(R.styleable.BaseTextView_textStyle, 4)]
            typedArray.recycle()
        }
    }
}