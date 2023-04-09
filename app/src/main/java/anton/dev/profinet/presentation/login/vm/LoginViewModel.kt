package anton.dev.profinet.presentation.login.vm

import androidx.lifecycle.MutableLiveData
import anton.dev.profinet.presentation.common.navigation.NavEvent
import anton.dev.profinet.presentation.common.navigation.ViewEvent
import anton.dev.profinet.presentation.common.ui.BaseViewModel
import anton.dev.profinet.presentation.login.ui.LoginFragmentDirections
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class LoginViewModel @Inject constructor() : BaseViewModel(),
    OnCompleteListener<AuthResult> {

    private val auth = FirebaseAuth.getInstance()

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    fun login() = runCatching {
        auth.signInWithEmailAndPassword(email.value!!, password.value!!).addOnCompleteListener(this)
    }.onFailure {
        showInvalidLoginOrPasswordError()
    }

    fun toCreateAccount() = postEvent(
        NavEvent.To(LoginFragmentDirections.actionToCreateAccountFragment().actionId, null)
    )

    private fun showInvalidLoginOrPasswordError() = postEvent(
        ViewEvent.ShowError("Неверный логин или пароль")
    )

    override fun onComplete(authTask: Task<AuthResult>) {
        if (authTask.isSuccessful) {
            val user = auth.currentUser
        } else {
            showInvalidLoginOrPasswordError()
        }
    }
}