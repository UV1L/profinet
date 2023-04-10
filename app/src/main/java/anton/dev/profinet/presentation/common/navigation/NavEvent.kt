package anton.dev.profinet.presentation.common.navigation

import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import anton.dev.profinet.R

fun interface NavEvent {

    class To(
        @IdRes private val resId: Int,
        private val args: Bundle?,
        private val inclusive: Boolean = false,
    ) : NavEvent {

        private val navOptions = navOptions {
            anim {
                enter = R.anim.enter_anim
                exit = R.anim.exit_anim
            }
            popUpTo(popUpToId) {
                inclusive = this@To.inclusive
            }
        }

        override fun navigate(fragment: Fragment) {
            fragment.findNavController().safeNavigate(resId, args, navOptions)
        }
    }

    object Back : NavEvent {

        override fun navigate(fragment: Fragment) {
            fragment.findNavController().popBackStack()
        }
    }

    fun navigate(fragment: Fragment)
}