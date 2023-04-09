package anton.dev.uikit.container

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import anton.dev.uikit.R

class ContainerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

   init {
       setBackgroundColor(context.getColor(R.color.colorAccent))
   }
}