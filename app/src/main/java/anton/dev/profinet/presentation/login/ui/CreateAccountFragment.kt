package anton.dev.profinet.presentation.login.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import anton.dev.profinet.R
import anton.dev.profinet.databinding.FragmentCreateAccountBinding
import anton.dev.profinet.presentation.common.ui.BaseHiltFragment
import anton.dev.profinet.presentation.login.vm.LoginViewModel

internal class CreateAccountFragment : BaseHiltFragment() {

    override val viewModel: LoginViewModel by activityViewModels()
    override val layout: Int = R.layout.fragment_login
    override val binding: FragmentCreateAccountBinding by lazy { FragmentCreateAccountBinding.inflate(layoutInflater) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}