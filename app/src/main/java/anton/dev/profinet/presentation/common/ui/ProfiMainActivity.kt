package anton.dev.profinet.presentation.common.ui

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import anton.dev.profinet.R
import anton.dev.profinet.data.Repository
import anton.dev.profinet.databinding.ActivityProfiMainBinding
import anton.dev.profinet.presentation.common.navigation.NavHostFragmentHolder
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference
import javax.inject.Inject

@AndroidEntryPoint
internal class ProfiMainActivity : AppCompatActivity(),
    NavHostFragmentHolder {

    override val navHostFragment: NavHostFragment?
        get() = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment

    override val navController: NavController?
        get() = navHostFragment?.navController

    private val auth = FirebaseAuth.getInstance()

    private lateinit var binding: ActivityProfiMainBinding

    @Inject
    lateinit var errorViewHolder: ErrorViewHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfiMainBinding.inflate(layoutInflater)
        errorViewHolder.errorView = WeakReference(binding.errorView)
        setContentView(binding.root)
        val inflater = navHostFragment!!.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_app)
        when (auth.currentUser) {
            null -> graph.setStartDestination(R.id.loginFragment)
            else -> graph.setStartDestination(R.id.mainScreenFragment)
        }
        navController?.graph = graph
    }
}