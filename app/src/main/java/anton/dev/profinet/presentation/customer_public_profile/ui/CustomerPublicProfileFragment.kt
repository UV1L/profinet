package anton.dev.profinet.presentation.customer_public_profile.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import anton.dev.profinet.R
import anton.dev.profinet.databinding.FragmentCustomerPublicProfileBinding
import anton.dev.profinet.presentation.common.ui.BaseHiltFragment
import anton.dev.profinet.presentation.customer_public_profile.vm.CustomerPublicProfileViewModel

internal class CustomerPublicProfileFragment : BaseHiltFragment() {

    override val viewModel: CustomerPublicProfileViewModel by viewModels()
    override val layout: Int = R.layout.fragment_customer_public_profile
    override val binding: FragmentCustomerPublicProfileBinding by lazy {
        FragmentCustomerPublicProfileBinding.inflate(
            layoutInflater
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.customerPublicProfileName.text = viewModel.customer.name
        binding.customerPublicProfileQualifications.value =
            viewModel.customer.qualificationParams
                .joinToString(", ") { it.qualification.displayName }
        binding.customerPublicProfileRatingCounter.text = viewModel.customer.rating.toString()
    }
}