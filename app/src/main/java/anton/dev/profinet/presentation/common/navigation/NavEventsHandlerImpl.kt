package anton.dev.profinet.presentation.common.navigation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import anton.dev.profinet.presentation.common.ui.BaseFragment
import anton.dev.profinet.presentation.common.ui.currentFragment
import kotlinx.coroutines.flow.MutableSharedFlow

internal class NavEventsHandlerImpl : NavEventsHandler {

    private val _navEvents = MutableSharedFlow<NavEvent>(extraBufferCapacity = 1)
    private val _viewEvents = MutableSharedFlow<ViewEvent>(extraBufferCapacity = 1)
    private val _navResult = MutableLiveData<NavResult?>()

    override fun handleEvent(fragment: BaseFragment<*>, navEvent: NavEvent) {
        fragment.let(navEvent::navigate)
    }

    override fun handleEvent(fragment: BaseFragment<*>, viewEvent: ViewEvent) {
        fragment.let(viewEvent::execute)
    }

    override fun postNavEvent(navEvent: NavEvent) {
        _navEvents.tryEmit(navEvent)
    }

    override fun postViewEvent(viewEvent: ViewEvent) {
        _viewEvents.tryEmit(viewEvent)
    }

    override fun postNavResult(result: NavResult) {
        _navResult.postValue(result)
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

    override fun <T : Any> subscribeOnResult(
        lifecycleOwner: LifecycleOwner,
        expectedRequestCode: Int,
        onResultAction: (T) -> Unit
    ) {
        val observer = object : Observer<NavResult?> {

            override fun onChanged(navResultModel: NavResult?) {

                if (expectedRequestCode != navResultModel?.requestCode) return

                (navResultModel.result as? T)?.let { result ->

                    onResultAction(result)
                    _navResult.value = null
                }
            }
        }

        _navResult.observe(lifecycleOwner, observer)
    }
}