package anton.dev.uikit.text

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.LinearLayout
import anton.dev.uikit.R

class TextWithValue @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var value: String = ""
        set(value) {
            valueTextView.text = value
            field = value
        }

    var textStyle: TextViewStyle = TextViewStyle.UNDEFINED
        set(value) {
            valueTextView.textStyle = value
            descriptionTextView.textStyle = value
            field = value
        }

    private val descriptionTextView = BaseTextView(context).apply {
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 10f)
        alpha = 0.5f
    }
    private val valueTextView = BaseTextView(context).apply {
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 10f)
        ellipsize = TextUtils.TruncateAt.END
    }

    init {
        getAttrs(attrs, defStyleAttr)
        orientation = HORIZONTAL
        addView(descriptionTextView)
        addView(valueTextView)
    }

    private fun getAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.TextWithValue, defStyleAttr, 0)
            valueTextView.text = typedArray.getString(R.styleable.TextWithValue_value)
            descriptionTextView.text = typedArray.getString(R.styleable.TextWithValue_text)
            textStyle = TextViewStyle.values()[typedArray.getInt(R.styleable.TextWithValue_overallTextStyle, 4)]
            typedArray.recycle()
        }
    }
}