package anton.dev.profinet.presentation.login.ui

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import anton.dev.profinet.R
import anton.dev.profinet.databinding.FragmentLoginBinding
import anton.dev.profinet.presentation.common.ui.BaseHiltFragment
import anton.dev.profinet.presentation.login.vm.LoginViewModel

internal class LoginFragment : BaseHiltFragment() {

    override val viewModel: LoginViewModel by viewModels()
    override val layout: Int = R.layout.fragment_login
    override val binding: FragmentLoginBinding by lazy { FragmentLoginBinding.inflate(layoutInflater) }

    init {
        disableOnBackPressedCallback()
    }

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