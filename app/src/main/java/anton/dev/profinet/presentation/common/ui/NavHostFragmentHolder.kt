package anton.dev.profinet.presentation.common.ui

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

interface NavHostFragmentHolder {

    val navHostFragment: NavHostFragment?

    val navController: NavController?
}