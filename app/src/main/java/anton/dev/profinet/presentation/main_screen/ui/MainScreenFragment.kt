package anton.dev.profinet.presentation.main_screen.ui

import androidx.fragment.app.viewModels
import anton.dev.profinet.R
import anton.dev.profinet.databinding.FragmentMainScreenBinding
import anton.dev.profinet.presentation.common.ui.BaseHiltFragment
import anton.dev.profinet.presentation.main_screen.vm.MainScreenViewModel

internal class MainScreenFragment : BaseHiltFragment() {

    override val viewModel: MainScreenViewModel by viewModels()
    override val layout: Int = R.layout.fragment_main_screen
    override val binding: FragmentMainScreenBinding by lazy { FragmentMainScreenBinding.inflate(layoutInflater) }
}