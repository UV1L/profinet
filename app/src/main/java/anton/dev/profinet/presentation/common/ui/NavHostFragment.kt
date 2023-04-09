package anton.dev.profinet.presentation.common.ui

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment

fun NavHostFragment.currentFragment(): Fragment? = childFragmentManager.fragments.getOrNull(0)