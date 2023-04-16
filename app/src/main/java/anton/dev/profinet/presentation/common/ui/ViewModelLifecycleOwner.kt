package anton.dev.profinet.presentation.common.ui

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import dagger.Reusable
import javax.inject.Inject

@Reusable
class ViewModelLifecycleOwner @Inject constructor() : LifecycleOwner {

    private val lifecycleRegistry = LifecycleRegistry(this).apply {
        currentState = Lifecycle.State.CREATED
    }

    override fun getLifecycle() = lifecycleRegistry

    internal fun onStart() = lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)

    internal fun onDestroy() = lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
}