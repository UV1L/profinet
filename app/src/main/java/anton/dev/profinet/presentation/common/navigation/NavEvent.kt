package anton.dev.profinet.presentation.common.navigation

import androidx.fragment.app.Fragment

fun interface NavEvent {

    fun navigate(fragment: Fragment)
}