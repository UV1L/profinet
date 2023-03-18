package anton.dev.profinet.presentation.common.navigation

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

interface NavHostFragmentHolder {

    val navHostFragment: NavHostFragment?

    val navController: NavController?
}