package anton.dev.profinet.presentation.main_screen.ui

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import anton.dev.profinet.R
import anton.dev.profinet.databinding.FragmentMainScreenBinding
import anton.dev.profinet.databinding.NavHeaderMainBinding
import anton.dev.profinet.presentation.common.navigation.NavEvent
import anton.dev.profinet.presentation.common.ui.BaseHiltFragment
import anton.dev.profinet.presentation.main_screen.vm.MainScreenViewModel
import anton.dev.profinet.presentation.main_screen.vm.model.CustomerListItem
import kotlin.random.Random

internal class MainScreenFragment : BaseHiltFragment() {

    override val viewModel: MainScreenViewModel by viewModels()
    override val layout: Int = R.layout.fragment_main_screen
    override val binding: FragmentMainScreenBinding by lazy { FragmentMainScreenBinding.inflate(layoutInflater) }
    private val headerBinding: NavHeaderMainBinding by lazy {
        NavHeaderMainBinding.inflate(layoutInflater, binding.navView, false)
    }

    private val adapter = MainScreenCustomerAdapter()

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                viewModel.tryGetLastLocation()
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                viewModel.tryGetLastLocation()
            }
            else -> {
                headerBinding.navHeaderCity.value = getString(R.string.city_not_found)
                headerBinding.navHeaderSetCityManuallyHint.isVisible = true
            }
        }
    }

    init {
        disableOnBackPressedCallback()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requestPermissions()
        return super.onCreateView(inflater, container, savedInstanceState)
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
        observeLocation()
        setHeader()
        setCustomersRv()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setHeader() {
        runCatching { binding.navView.addHeaderView(headerBinding.root) }
        // не дровер открывать по свайпу
        binding.root.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        headerBinding.navHeaderUsername.text = viewModel.username
        headerBinding.navHeaderAvatarName.text = viewModel.avatarText
        headerBinding.navHeaderSetCityManuallyHint.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> v.alpha = 0.5f
                else -> v.alpha = 1f
            }
            return@setOnTouchListener false
        }
        headerBinding.navHeaderSetCityManuallyHint.setOnClickListener {
            postEvent(NavEvent.To(R.id.action_to_searchCityFragment))
        }
    }

    private fun setCustomersRv() {
        binding.mainScreenCustomersRv.adapter = adapter
        adapter.submitList(
            listOf(
                CustomerListItem(
                    id = "1", name = "Василий Иванович", age = 44, experience = 19,
                    rating = Random.nextDouble(0.0, 5.0), speciality = "Сантехник"
                ),
                CustomerListItem(
                    id = "2", name = "Иван Васильевич", age = 25, experience = 4, rating = Random.nextDouble(0.0, 5.0),
                    speciality = "Электрик"
                ),
                CustomerListItem(
                    id = "3", name = "Влада Давыдова", age = 22, experience = 1, rating = 5.0,
                    speciality = "Сантехник"
                ),
                CustomerListItem(
                    id = "4", name = "Тест Тестерский", age = 37, experience = 12, rating = Random.nextDouble(0.0, 5.0),
                    speciality = "Сантехник"
                ),
            )
        )
    }

    private fun requestPermissions() {
        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))
    }

    private fun observeLocation() {
        viewModel.currentCityName.observe(viewLifecycleOwner) {
            binding.mainScreenLoadingView.isGone = true
            binding.mainScreenContentContainer.isVisible = true

            headerBinding.navHeaderCity.value = it ?: getString(R.string.city_not_found)
        }
        viewModel.isGpsOn.observe(viewLifecycleOwner) { isGpsOn ->
            if (!isGpsOn) headerBinding.navHeaderCity.value = getString(R.string.city_not_found)
        }
    }
}