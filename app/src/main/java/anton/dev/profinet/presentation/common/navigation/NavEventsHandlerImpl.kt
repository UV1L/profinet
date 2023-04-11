package anton.dev.profinet.presentation.common.navigation

import androidx.lifecycle.lifecycleScope
import anton.dev.profinet.presentation.common.ui.BaseFragment
import anton.dev.profinet.presentation.common.ui.currentFragment
import kotlinx.coroutines.flow.MutableSharedFlow

internal class NavEventsHandlerImpl : NavEventsHandler {

    private val _navEvents = MutableSharedFlow<NavEvent>(extraBufferCapacity = 1)
    private val _viewEvents = MutableSharedFlow<ViewEvent>(extraBufferCapacity = 1)

    override fun handleEvent(fragment: BaseFragment<*>, navEvent: NavEvent) {
        fragment.navHostFragment?.let(navEvent::navigate)
    }

    override fun handleEvent(fragment: BaseFragment<*>, viewEvent: ViewEvent) {
        fragment.navHostFragment?.currentFragment()?.let(viewEvent::execute)
    }

    override fun postNavEvent(navEvent: NavEvent) {
        _navEvents.tryEmit(navEvent)
    }

    override fun postViewEvent(viewEvent: ViewEvent) {
        _viewEvents.tryEmit(viewEvent)
    }

    override fun collectFlow(fragment: BaseFragment<*>) = with(fragment.viewLifecycleOwner.lifecycleScope) {
        launchWhenStarted {
            _navEvents.collect {
                handleEvent(fragment, it)
            }
        }
        launchWhenStarted {
            _viewEvents.collect {
                handleEvent(fragment, it)
            }
        }

        Unit
    }
}