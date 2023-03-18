package anton.dev.profinet.presentation.common.ui

import anton.dev.uikit.error.BaseErrorView

class ErrorViewHolder {

    lateinit var errorView: BaseErrorView

    fun showError() = errorView.expand()
}