package anton.dev.profinet.presentation.create_account.vm

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import anton.dev.profinet.R
import anton.dev.profinet.presentation.common.ext.combineWith
import anton.dev.profinet.presentation.common.ext.map
import anton.dev.profinet.presentation.common.ext.plus
import anton.dev.profinet.presentation.common.navigation.NavEvent
import anton.dev.profinet.presentation.common.navigation.ViewEvent
import anton.dev.profinet.presentation.common.ui.BaseViewModel
import anton.dev.profinet.data.Repository
import anton.dev.profinet.domain.models.Customer
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
internal class CreateAccountViewModel @Inject constructor(
    private val repo: Repository
) : BaseViewModel(),
    OnCompleteListener<AuthResult> {

    private val auth = FirebaseAuth.getInstance()

    val fio = MutableLiveData("")
    val email = MutableLiveData("")
    val password = MutableLiveData("")
    val birthday = MutableLiveData<Date>()
    val buttonEnabled = (fio + email + password).map {
        isFioCorrect()
                && it.first?.second?.isNotEmpty() == true
                && it.second?.isNotEmpty() == true
    } as MutableLiveData

    fun createAccount() {
        if (buttonEnabled.value == true) {
            auth.createUserWithEmailAndPassword(email.value!!, password.value!!)
                .addOnCompleteListener(this)
            buttonEnabled.value = false
        }
    }

    fun isFioCorrect(): Boolean = fio.value
        ?.matches(
            Regex(context.getString(R.string.fio_regex))
        ) == true

    private fun toMainScreen() = postEvent(
        NavEvent.To(R.id.action_global_to_mainScreenFragment, inclusive = true)
    )

    private fun updateCustomer() = with(auth.currentUser!!) {
        viewModelScope.launch {
            repo.createOrUpdateCustomer(
                Customer(
                    id = uid,
                    name = displayName!!,
                    birthday = birthday.value
                )
            )
        }
    }

    override fun onComplete(authTask: Task<AuthResult>) {
        if (authTask.isSuccessful) {
            val user = auth.currentUser
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(fio.value)
                .build()
            user?.updateProfile(profileUpdates)?.addOnCompleteListener {
                if (it.isSuccessful) {
                    updateCustomer()
                    toMainScreen()
                }
                else postEvent(ViewEvent.ShowError())
            }
        } else {
            postEvent(ViewEvent.ShowError())
        }
        buttonEnabled.value = true
    }
}