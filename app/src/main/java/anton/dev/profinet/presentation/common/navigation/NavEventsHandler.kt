package anton.dev.profinet.presentation.common.navigation

import androidx.appcompat.app.AppCompatActivity
import anton.dev.profinet.presentation.common.ui.ActivityLifecycleCallbacksOwner

interface NavEventsHandler : ActivityLifecycleCallbacksOwner {

    fun handleEvent(activity: AppCompatActivity, navEvent: NavEvent)

    fun postEvent(navEvent: NavEvent)
}