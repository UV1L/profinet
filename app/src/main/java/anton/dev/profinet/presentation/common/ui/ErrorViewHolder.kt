package anton.dev.profinet.presentation.common.ui

import anton.dev.uikit.error.BaseErrorView
import java.lang.ref.WeakReference

class ErrorViewHolder {

    lateinit var errorView: WeakReference<BaseErrorView>

    fun showError(message: String?) {
        message?.let { errorView.get()?.errorText = it }
        errorView.get()?.expand()
    }
}