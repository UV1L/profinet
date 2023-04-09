package anton.dev.profinet.presentation.login.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import anton.dev.profinet.R
import anton.dev.profinet.databinding.FragmentLoginBinding
import anton.dev.profinet.presentation.common.ui.BaseHiltFragment
import anton.dev.profinet.presentation.login.vm.LoginViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

internal class LoginFragment : BaseHiltFragment() {

    override val viewModel: LoginViewModel by activityViewModels()
    override val layout: Int = R.layout.fragment_login
    override val binding: FragmentLoginBinding by lazy { FragmentLoginBinding.inflate(layoutInflater) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.enterButton.setOnClickListener { viewModel.login() }
        binding.noAccountYet.setOnClickListener { viewModel.toCreateAccount() }

        binding.emailInput.doOnTextChanged { text, _, _, _ ->
            viewModel.email.value = text.toString()
        }
        binding.passwordInput.doOnTextChanged { text, _, _, _ ->
            viewModel.password.value = text.toString()
        }
    }
}