package anton.dev.profinet.presentation.common.ui

import anton.dev.uikit.error.BaseErrorView
import java.lang.ref.WeakReference

class ErrorViewHolder {

    lateinit var errorView: WeakReference<BaseErrorView>

    fun showError() = errorView.get()?.expand()
}