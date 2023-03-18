package anton.dev.profinet.presentation.login.ui

import androidx.fragment.app.viewModels
import anton.dev.profinet.R
import anton.dev.profinet.presentation.common.ui.BaseHiltFragment
import anton.dev.profinet.presentation.login.vm.LoginViewModel

internal class LoginFragment : BaseHiltFragment() {

    override val viewModel: LoginViewModel by viewModels()
    override val layout: Int = R.layout.fragment_login
}