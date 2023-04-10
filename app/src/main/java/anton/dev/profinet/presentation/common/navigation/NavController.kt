package anton.dev.profinet.presentation.common.navigation

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavOptions

fun NavController.safeNavigate(
    @IdRes id: Int,
    args: Bundle? = null,
    navOptions: NavOptions? = null
) {
    if (id != currentDestination?.id) {
        navigate(id, args, navOptions)
    }
}