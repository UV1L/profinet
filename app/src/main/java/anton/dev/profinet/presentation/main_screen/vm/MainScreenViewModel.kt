package anton.dev.profinet.presentation.main_screen.vm

import anton.dev.profinet.R
import anton.dev.profinet.presentation.common.navigation.NavEvent
import anton.dev.profinet.presentation.common.ui.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class MainScreenViewModel @Inject constructor() : BaseViewModel() {

    val username = FirebaseAuth.getInstance().currentUser?.displayName
    val avatarText = username?.firstOrNull()?.uppercaseChar().toString()

    fun logout() {
        FirebaseAuth.getInstance().signOut()
        postEvent(NavEvent.To(R.id.loginFragment, popUpTo = R.id.mainScreenFragment, inclusive = true))
    }
}