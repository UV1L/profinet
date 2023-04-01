package anton.dev.profinet.presentation.login.ui

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import anton.dev.profinet.R
import anton.dev.profinet.databinding.FragmentLoginBinding
import anton.dev.profinet.presentation.common.navigation.NavEvent
import anton.dev.profinet.presentation.common.ui.BaseHiltFragment
import anton.dev.profinet.presentation.common.ui.ProfiMainActivity
import anton.dev.profinet.presentation.login.vm.LoginViewModel
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth

internal class LoginFragment : BaseHiltFragment() {

    override val viewModel: LoginViewModel by viewModels()
    override val layout: Int = R.layout.fragment_login
    override val binding: FragmentLoginBinding by lazy { FragmentLoginBinding.inflate(layoutInflater) }

    private val signInLauncher = registerForActivityResult(FirebaseAuthUIActivityResultContract(), ::onSignInResult)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.enterButton.setOnClickListener {
            Toast.makeText(context, "Hello world!", Toast.LENGTH_SHORT).show()
            login()
        }

    }

    fun login() {

        // Choose authentication providers
        val providers = arrayListOf(AuthUI.IdpConfig.EmailBuilder().build())

        // Create and launch sign-in intent
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setIsSmartLockEnabled(false)
            .setAvailableProviders(providers).build()
        signInLauncher.launch(signInIntent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            val user = FirebaseAuth.getInstance().currentUser
            // ...

        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
            showError()
        }
    }
}