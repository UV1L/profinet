package anton.dev.uikit.back_button

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import anton.dev.uikit.R
import anton.dev.uikit.setOnTouchAnim

class BackButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageButton(context, attrs, defStyleAttr) {

    var parentFragment: Fragment? = null

    init {
        setImageResource(R.drawable.baseline_arrow_back_24)
        background = AppCompatResources.getDrawable(context, R.drawable.circle_shape)
        scaleType = ScaleType.CENTER_INSIDE
        isSelected = true
        setOnClickListener {
            parentFragment?.requireActivity()
                ?.onBackPressedDispatcher
                ?.onBackPressed()
        }
        setOnTouchAnim()
    }
}