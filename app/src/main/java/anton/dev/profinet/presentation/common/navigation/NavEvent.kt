package anton.dev.profinet.presentation.common.navigation

import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import anton.dev.profinet.R
import anton.dev.profinet.presentation.common.ui.BaseFragment
import javax.inject.Inject

fun interface NavEvent {

    class To(
        @IdRes private val resId: Int,
        private val args: Bundle? = null,
        private val popUpTo: Int = -1,
        private val inclusive: Boolean = false,
    ) : NavEvent {

        private val navOptions = navOptions {
            anim {
                enter = R.anim.enter_anim
                exit = R.anim.exit_anim
            }
            popUpTo(this@To.popUpTo) {
                inclusive = this@To.inclusive
            }
        }

        override fun navigate(fragment: BaseFragment<*>) {
            fragment.findNavController().safeNavigate(resId, args, navOptions)
        }
    }

    object Back : NavEvent {

        override fun navigate(fragment: BaseFragment<*>) {
            fragment.findNavController().popBackStack()

        }
    }

    class BackWithResult(private val result: NavResult) : NavEvent {

        override fun navigate(fragment: BaseFragment<*>) {
            fragment.findNavController().popBackStack()
            fragment.navEventsHandler.postNavResult(result)
        }
    }

    fun navigate(fragment: BaseFragment<*>)
}