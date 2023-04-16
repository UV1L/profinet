package anton.dev.profinet.presentation.common.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import anton.dev.profinet.presentation.common.ui.ActivityLifecycleCallbacksOwner
import anton.dev.profinet.presentation.common.ui.BaseFragment

interface NavEventsHandler : ActivityLifecycleCallbacksOwner {

    fun handleEvent(fragment: BaseFragment<*>, navEvent: NavEvent)

    fun handleEvent(fragment: BaseFragment<*>, viewEvent: ViewEvent)

    fun postNavEvent(navEvent: NavEvent)

    fun postViewEvent(viewEvent: ViewEvent)

    fun postNavResult(result: NavResult)

    fun collectFlow(fragment: BaseFragment<*>)

    fun <T : Any> subscribeOnResult(
        lifecycleOwner: LifecycleOwner,
        expectedRequestCode: Int,
        onResultAction: (T) -> Unit
    )
}