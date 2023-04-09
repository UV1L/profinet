package anton.dev.profinet.presentation.common.navigation

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

fun interface NavEvent {

    class To(
        @IdRes private val resId: Int,
        private val args: Bundle?
    ) : NavEvent {

        override fun navigate(fragment: Fragment) {
            fragment.findNavController().navigate(resId, args)
        }
    }

    fun navigate(fragment: Fragment)
}