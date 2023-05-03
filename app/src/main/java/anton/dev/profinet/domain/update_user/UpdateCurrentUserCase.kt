package anton.dev.profinet.presentation.domain.update_user

import com.google.firebase.auth.FirebaseUser

internal interface UpdateCurrentUserCase {

    fun execute(user: FirebaseUser)
}