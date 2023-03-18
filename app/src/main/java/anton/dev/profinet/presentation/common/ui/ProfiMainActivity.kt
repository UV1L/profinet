package anton.dev.profinet.presentation.common.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import anton.dev.profinet.R
import anton.dev.profinet.databinding.ActivityProfiMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class ProfiMainActivity : AppCompatActivity(),
    NavHostFragmentHolder {

    override val navHostFragment: NavHostFragment?
        get() = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment

    override val navController: NavController?
        get() = navHostFragment?.navController

    private lateinit var binding: ActivityProfiMainBinding

    private val errorView by lazy { binding.errorView }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfiMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        errorView.expand()
    }
}