package anton.dev.uikit.text

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity.CENTER
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.ViewGroup.MarginLayoutParams
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.doOnAttach
import androidx.core.view.updateLayoutParams
import anton.dev.uikit.R

class BaseTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        text = "Добро пожаловать!"
        textAlignment = TEXT_ALIGNMENT_CENTER
        gravity = CENTER
        layoutParams = MarginLayoutParams(WRAP_CONTENT, WRAP_CONTENT)
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
}