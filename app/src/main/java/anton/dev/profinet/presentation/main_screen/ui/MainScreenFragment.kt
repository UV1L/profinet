package anton.dev.profinet.presentation.main_screen.ui

import android.os.Bundle
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import anton.dev.profinet.R
import anton.dev.profinet.databinding.FragmentMainScreenBinding
import anton.dev.profinet.databinding.NavHeaderMainBinding
import anton.dev.profinet.presentation.common.ui.BaseHiltFragment
import anton.dev.profinet.presentation.main_screen.vm.MainScreenViewModel

internal class MainScreenFragment : BaseHiltFragment() {

    override val viewModel: MainScreenViewModel by viewModels()
    override val layout: Int = R.layout.fragment_main_screen
    override val binding: FragmentMainScreenBinding by lazy { FragmentMainScreenBinding.inflate(layoutInflater) }

    init {
        disableOnBackPressedCallback()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mainScreenSearchView.setOnClickListener {
            binding.mainScreenSearchView.isIconified = false
        }
        binding.mainScreenMenu.setOnClickListener {
            binding.root.open()
        }
        binding.navExit.setOnClickListener {
            viewModel.logout()
        }
        val headerBinding: NavHeaderMainBinding =
            NavHeaderMainBinding.inflate(layoutInflater, binding.navView, false)
        binding.navView.addHeaderView(headerBinding.root)
        // не дровер открывать по свайпу
        binding.root.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        headerBinding.navHeaderUsername.text = viewModel.username
        headerBinding.navHeaderAvatarName.text = viewModel.avatarText
    }
}