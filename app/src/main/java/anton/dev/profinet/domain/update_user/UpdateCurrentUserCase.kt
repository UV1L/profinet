package anton.dev.profinet.domain.update_user

import com.google.firebase.auth.FirebaseUser

internal interface UpdateCurrentUserCase {

    fun execute(user: FirebaseUser)
}