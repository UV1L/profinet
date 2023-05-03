package anton.dev.profinet.presentation.customer_public_profile.vm

import anton.dev.profinet.presentation.common.ui.BaseViewModel
import anton.dev.profinet.presentation.customer_public_profile.ui.CustomerPublicProfileFragmentArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class CustomerPublicProfileViewModel @Inject constructor() : BaseViewModel() {

    val args by navArgs<CustomerPublicProfileFragmentArgs>()
    val customer by lazy { args.value.customerListItem }
}