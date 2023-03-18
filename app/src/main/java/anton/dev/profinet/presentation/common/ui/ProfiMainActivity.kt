package anton.dev.profinet.presentation.common.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import anton.dev.profinet.R
import anton.dev.profinet.databinding.ActivityProfiMainBinding
import anton.dev.profinet.presentation.common.navigation.NavEvent
import anton.dev.profinet.presentation.common.navigation.NavEventsHandler
import anton.dev.profinet.presentation.common.navigation.NavHostFragmentHolder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class ProfiMainActivity : AppCompatActivity(),
    NavHostFragmentHolder {

    override val navHostFragment: NavHostFragment?
        get() = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment

    override val navController: NavController?
        get() = navHostFragment?.navController

    private lateinit var binding: ActivityProfiMainBinding

    @Inject
    lateinit var errorViewHolder: ErrorViewHolder

    @Inject
    lateinit var navEventsHandler: NavEventsHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        application.registerActivityLifecycleCallbacks(navEventsHandler)
        binding = ActivityProfiMainBinding.inflate(layoutInflater)
        errorViewHolder.errorView = binding.errorView
        setContentView(binding.root)
    }
}