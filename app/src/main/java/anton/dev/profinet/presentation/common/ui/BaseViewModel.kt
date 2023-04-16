package anton.dev.profinet.presentation.common.ui

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.annotation.CallSuper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import anton.dev.profinet.presentation.common.navigation.NavEvent
import anton.dev.profinet.presentation.common.navigation.NavEventsHandler
import anton.dev.profinet.presentation.common.navigation.ViewEvent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(),
    LifecycleObserver,
    LifecycleOwner,
    CoroutineScope {

    @Inject
    internal lateinit var eventsHandler: NavEventsHandler

    @Inject
    internal lateinit var viewModelLifecycleOwner: ViewModelLifecycleOwner

    @SuppressLint("StaticFieldLeak")
    @Inject
    @ApplicationContext
    lateinit var context: Context

    private val lifecycleRegistry by lazy { LifecycleRegistry(this) }

    final override val coroutineContext: CoroutineContext =
        Dispatchers.Main + SupervisorJob() + CoroutineExceptionHandler { _, exception ->
            handleCoroutineException(exception)
        }

    protected open fun handleCoroutineException(exception: Throwable) = Unit

    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    protected open fun onStart() = viewModelLifecycleOwner.onStart()

    final override fun getLifecycle(): Lifecycle = lifecycleRegistry

    init {
        lifecycleRegistry.currentState = Lifecycle.State.STARTED
    }

    @Inject
    fun initInject() {
        viewModelLifecycleOwner.lifecycleScope.launchWhenStarted { initialize() }
    }

    open fun initialize() = Unit

    fun onBackPressed() = postEvent(NavEvent.Back)

    fun postEvent(navEvent: NavEvent) = eventsHandler.postNavEvent(navEvent)

    fun postEvent(viewEvent: ViewEvent) = eventsHandler.postViewEvent(viewEvent)

    fun <T : Any> subscribeOnResult(requestCode: Int, callback: (T) -> Unit) {
        eventsHandler.subscribeOnResult(this, requestCode, callback)
    }
}