package anton.dev.uikit.text

import android.content.Context
import android.os.Build
import android.util.AttributeSet
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
                TextViewStyle.PRIMARY -> Unit
                TextViewStyle.SECONDARY -> Unit
                TextViewStyle.HINT -> Unit
                else -> Unit
            }
            field = value
        }

    init {
        getAttrs(attrs, defStyleAttr)
        textAlignment = TEXT_ALIGNMENT_CENTER
        gravity = CENTER
        layoutParams = MarginLayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        typeface = resources.getFont(R.font.baloo_cyrillic)
        doOnAttach {
            updateLayoutParams<MarginLayoutParams> {
                setMargins(
                    resources.getDimensionPixelSize(R.dimen.space_6),
                    resources.getDimensionPixelSize(R.dimen.space_4),
                    resources.getDimensionPixelSize(R.dimen.space_6),
                    resources.getDimensionPixelSize(R.dimen.space_4)
                )
            }
        }
    }

    private fun getAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.BaseTextView, defStyleAttr, 0)
            textStyle = TextViewStyle.values()[typedArray.getInt(R.styleable.BaseTextView_textStyle, 0)]
            typedArray.recycle()
        }
    }
}