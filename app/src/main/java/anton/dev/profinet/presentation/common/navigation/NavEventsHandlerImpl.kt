package anton.dev.profinet.presentation.common.navigation

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import anton.dev.profinet.presentation.common.ui.ProfiMainActivity
import anton.dev.profinet.presentation.common.ui.currentFragment
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

internal class NavEventsHandlerImpl : NavEventsHandler {

    private val _navEvents = MutableSharedFlow<NavEvent>(
        replay = 1,
        extraBufferCapacity = 0,
        onBufferOverflow = BufferOverflow.SUSPEND
    )

    private val _viewEvents = MutableSharedFlow<ViewEvent>(
        replay = 1,
        extraBufferCapacity = 0,
        onBufferOverflow = BufferOverflow.SUSPEND
    )

    override fun handleEvent(activity: AppCompatActivity, navEvent: NavEvent) {
        (activity as ProfiMainActivity).navHostFragment?.let(navEvent::navigate)
    }

    override fun handleEvent(activity: AppCompatActivity, viewEvent: ViewEvent) {
        (activity as ProfiMainActivity).navHostFragment?.currentFragment()?.let(viewEvent::execute)
    }

    override fun postNavEvent(navEvent: NavEvent) {
        _navEvents.tryEmit(navEvent)
    }

    override fun postViewEvent(viewEvent: ViewEvent) {
        _viewEvents.tryEmit(viewEvent)
    }

    override fun onActivityResumed(activity: Activity) = with(activity as AppCompatActivity) {
        lifecycleScope.launch {
            _navEvents.collect {
                handleEvent(activity, it)
            }
        }
        lifecycleScope.launch {
            _viewEvents.collect {
                handleEvent(activity, it)
            }
        }

        Unit
    }
}