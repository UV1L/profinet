package anton.dev.profinet.presentation.common.ui

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(),
    LifecycleObserver,
    LifecycleOwner,
    CoroutineScope {

    private val lifecycleRegistry by lazy { LifecycleRegistry(this) }

    final override val coroutineContext: CoroutineContext =
        Dispatchers.Main + SupervisorJob() + CoroutineExceptionHandler { _, exception ->
            handleCoroutineException(exception)
        }

    protected open fun handleCoroutineException(exception: Throwable) = Unit

    final override fun getLifecycle(): Lifecycle = lifecycleRegistry
}