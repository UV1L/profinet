package anton.dev.profinet.presentation.common.navigation

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import anton.dev.profinet.presentation.common.ui.ProfiMainActivity
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

internal class NavEventsHandlerImpl : NavEventsHandler {
    private val _events = MutableSharedFlow<NavEvent>(
        replay = 1,
        extraBufferCapacity = 0,
        onBufferOverflow = BufferOverflow.SUSPEND
    )

    override fun handleEvent(activity: AppCompatActivity, navEvent: NavEvent) {
        (activity as ProfiMainActivity).navHostFragment?.let(navEvent::navigate)
    }

    override fun postEvent(navEvent: NavEvent) {
        _events.tryEmit(navEvent)
    }

    override fun onActivityPostCreated(activity: Activity, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).lifecycleScope.launch {
            _events.collect {
                handleEvent(activity, it)
            }
        }
    }
}