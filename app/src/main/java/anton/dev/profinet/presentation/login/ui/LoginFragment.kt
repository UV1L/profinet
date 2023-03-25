package anton.dev.profinet.presentation.login.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import anton.dev.profinet.R
import anton.dev.profinet.databinding.FragmentLoginBinding
import anton.dev.profinet.presentation.common.navigation.NavEvent
import anton.dev.profinet.presentation.common.ui.BaseHiltFragment
import anton.dev.profinet.presentation.common.ui.ProfiMainActivity
import anton.dev.profinet.presentation.login.vm.LoginViewModel

internal class LoginFragment : BaseHiltFragment() {

    override val viewModel: LoginViewModel by viewModels()
    override val layout: Int = R.layout.fragment_login
    override val binding: FragmentLoginBinding by lazy { FragmentLoginBinding.inflate(layoutInflater) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.enterButton.setOnClickListener {
            Toast.makeText(context, "Hello world!", Toast.LENGTH_SHORT).show()
        }
    }
}