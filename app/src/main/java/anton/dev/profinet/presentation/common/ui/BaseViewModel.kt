package anton.dev.profinet.presentation.common.ui

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModel
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
    lateinit var eventsHandler: NavEventsHandler

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

    final override fun getLifecycle(): Lifecycle = lifecycleRegistry

    fun onBackPressed() = postEvent(NavEvent.Back)

    fun postEvent(navEvent: NavEvent) = eventsHandler.postNavEvent(navEvent)

    fun postEvent(viewEvent: ViewEvent) = eventsHandler.postViewEvent(viewEvent)
}