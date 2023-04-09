package anton.dev.profinet.presentation.common.navigation

import androidx.fragment.app.Fragment
import anton.dev.profinet.presentation.common.ui.BaseFragment

fun interface ViewEvent {

    class ShowError(private val message: String?) : ViewEvent {

        override fun execute(fragment: Fragment) {
            (fragment as? BaseFragment<*>)?.showError(message)
        }
    }

    fun execute(fragment: Fragment)
}