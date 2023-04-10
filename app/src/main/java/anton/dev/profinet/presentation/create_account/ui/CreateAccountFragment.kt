package anton.dev.profinet.presentation.create_account.ui

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import anton.dev.profinet.R
import anton.dev.profinet.databinding.FragmentCreateAccountBinding
import anton.dev.profinet.presentation.common.ui.BaseHiltFragment
import anton.dev.profinet.presentation.create_account.vm.CreateAccountViewModel

internal class CreateAccountFragment : BaseHiltFragment() {

    override val viewModel: CreateAccountViewModel by viewModels()
    override val layout: Int = R.layout.fragment_login
    override val binding: FragmentCreateAccountBinding by lazy { FragmentCreateAccountBinding.inflate(layoutInflater) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.fioInput.doOnTextChanged { text, _, _, _ ->
            viewModel.fio.value = text.toString()
        }
        binding.emailInput.doOnTextChanged { text, _, _, _ ->
            viewModel.email.value = text.toString()
        }
        binding.passwordInput.doOnTextChanged { text, _, _, _ ->
            viewModel.password.value = text.toString()
        }

        binding.fioInput.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && !viewModel.isFioCorrect()) {
                binding.fioInput.error = context?.getString(R.string.incorrect_fio)
            }
        }

        binding.createButton.setOnClickListener { viewModel.createAccount() }
        viewModel.buttonEnabled.observe(viewLifecycleOwner) {
            binding.createButton.isEnabled = it
        }
    }
}