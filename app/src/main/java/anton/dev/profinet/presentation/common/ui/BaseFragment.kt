package anton.dev.profinet.presentation.common.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import javax.inject.Inject

abstract class BaseFragment<T: BaseViewModel> : Fragment(),
    CoroutineScope by MainScope() {

    protected abstract val viewModel: T
    protected abstract val layout: Int
    protected open val binding: ViewBinding? = null

    @Inject
    lateinit var errorViewHolder: ErrorViewHolder

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return binding?.root ?: inflater.inflate(layout, container, false)
    }

    fun showError(message: String? = null) = errorViewHolder.showError(message)
}