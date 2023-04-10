package anton.dev.profinet.presentation.login.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import anton.dev.profinet.R
import anton.dev.profinet.presentation.common.navigation.NavEvent
import anton.dev.profinet.presentation.common.navigation.ViewEvent
import androidx.lifecycle.viewModelScope
import anton.dev.profinet.presentation.common.ui.BaseViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import anton.dev.profinet.presentation.data.Repository
import anton.dev.profinet.presentation.domain.repositories.CustomerRepository
import anton.dev.profinet.presentation.domain.repositories.ServicesRepository
import com.google.firebase.auth.FirebaseUser
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class LoginViewModel @Inject constructor(
    private val repo: CustomerRepository
) : BaseViewModel(),
    OnCompleteListener<AuthResult> {

    private val auth = FirebaseAuth.getInstance()
    private val canClick = MutableLiveData(true)

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    fun login() = runCatching {
        if (canClick.value == true) {
            auth.signInWithEmailAndPassword(email.value!!, password.value!!)
                .addOnCompleteListener(this)
            canClick.value = false
        }
    }.onFailure {
        showInvalidLoginOrPasswordError()
    }

    fun toCreateAccount() = postEvent(
        NavEvent.To(R.id.action_to_createAccountFragment)
    )

    private fun showInvalidLoginOrPasswordError() = postEvent(
        ViewEvent.ShowError(
            context.getString(R.string.incorrect_login_or_password)
        )
    )

    private fun toMainScreen() = postEvent(
        NavEvent.To(R.id.action_global_to_mainScreenFragment, inclusive = true)
    )

    override fun onComplete(authTask: Task<AuthResult>) {
        canClick.value = true
        if (authTask.isSuccessful) {
            viewModelScope.launch {
                val c = repo.currentCustomer()
                val sadas = ""
            }
            toMainScreen()
        } else {
            showInvalidLoginOrPasswordError()
        }
    }
}