package anton.dev.profinet.presentation.common.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import anton.dev.profinet.R
import anton.dev.profinet.presentation.common.navigation.NavEventsHandler
import anton.dev.profinet.presentation.common.navigation.NavHostFragmentHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import javax.inject.Inject

abstract class BaseFragment<T: BaseViewModel> : Fragment(),
    CoroutineScope by MainScope(),
    NavHostFragmentHolder {

    protected abstract val viewModel: T
    protected abstract val layout: Int
    protected open val binding: ViewBinding? = null

    override val navHostFragment: NavHostFragment?
        get() = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment

    override val navController: NavController?
        get() = navHostFragment?.navController

    @Inject
    lateinit var errorViewHolder: ErrorViewHolder

    @Inject
    lateinit var navEventsHandler: NavEventsHandler

    private val onBackPressedCallback by lazy {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() = viewModel.onBackPressed()
        }
    }

    fun disableOnBackPressedCallback() {
        onBackPressedCallback.isEnabled = false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return binding?.root ?: inflater.inflate(layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)
        navEventsHandler.collectFlow(this)
    }

    fun showError(message: String? = null) = errorViewHolder.showError(message)
}