package anton.dev.profinet.presentation.login.vm

import androidx.lifecycle.viewModelScope
import anton.dev.profinet.presentation.common.ui.BaseViewModel
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

) : BaseViewModel() {

    fun onAuthSuccess(user: FirebaseUser) {

        viewModelScope.launch {
            val c = repo.currentCustomer()
            val sadas = ""
        }
    }
}