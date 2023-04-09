package anton.dev.profinet.presentation.login.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import anton.dev.profinet.R
import anton.dev.profinet.databinding.FragmentLoginBinding
import anton.dev.profinet.presentation.common.ui.BaseHiltFragment
import anton.dev.profinet.presentation.login.vm.LoginViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

internal class LoginFragment : BaseHiltFragment(), OnCompleteListener<AuthResult> {

    override val viewModel: LoginViewModel by viewModels()
    override val layout: Int = R.layout.fragment_login
    override val binding: FragmentLoginBinding by lazy { FragmentLoginBinding.inflate(layoutInflater) }

    private val auth = FirebaseAuth.getInstance()
    private val email = MutableLiveData<String>()
    private val password = MutableLiveData<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.enterButton.setOnClickListener {
            Toast.makeText(context, "Hello world!", Toast.LENGTH_SHORT).show()
            login()
        }

        binding.emailInput.doOnTextChanged { text, _, _, _ ->
            email.value = text.toString()
        }
        binding.passwordInput.doOnTextChanged { text, _, _, _ ->
            password.value = text.toString()
        }
    }

    fun login() = runCatching {
        auth.signInWithEmailAndPassword(email.value!!, password.value!!).addOnCompleteListener(this)
    }.onFailure {
        showError()
    }

    override fun onComplete(authTask: Task<AuthResult>) {
        if (authTask.isSuccessful) {
            val user = auth.currentUser
        } else {
            showError()
        }
    }
}