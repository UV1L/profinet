package anton.dev.profinet.presentation.customer_settings.ui

import android.os.Bundle
import android.view.View
import android.widget.CheckedTextView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import anton.dev.profinet.R
import anton.dev.profinet.databinding.FragmentCustomerSettingsBinding
import anton.dev.profinet.domain.models.Qualification
import anton.dev.profinet.domain.models.QualificationParams
import anton.dev.profinet.presentation.common.ui.BaseHiltFragment
import anton.dev.profinet.presentation.customer_settings.vm.CustomerSettingsViewModel
import anton.dev.uikit.R as UiKitR

internal class CustomerSettingsFragment : BaseHiltFragment() {

    override val viewModel: CustomerSettingsViewModel by viewModels()
    override val layout: Int = R.layout.fragment_customer_settings
    override val binding by lazy { FragmentCustomerSettingsBinding.inflate(layoutInflater) }

    private lateinit var adapter: CustomerQualificationAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.customerSettingsPb.isGone = !it
            binding.customerSettingsContainer.isGone = it
        }
        viewModel.currentCustomer.observe(viewLifecycleOwner) {
            adapter = CustomerQualificationAdapter(it)
            binding.customerSettingsQualificationsRv.adapter = adapter
            adapter.submitList(QualificationParams.empty())
            if (it.qualifications.isNotEmpty()) binding.customerSettingsIsPerformerCheckbox.performClick()
        }

        binding.customerSettingsQualificationsRv.isGone = binding.customerSettingsIsPerformerCheckbox.isChecked.not()
        binding.customerSettingsIsPerformerCheckbox.setOnClickListener {
            val isChecked = (it as CheckedTextView).isChecked.not()
            it.isChecked = isChecked
            it.setCheckMarkDrawable(
                when (isChecked) {
                    true -> UiKitR.drawable.baseline_check_box_filled_24
                    false -> UiKitR.drawable.baseline_check_box_blank_24
                }
            )
            binding.customerSettingsQualificationsRv.isGone = isChecked.not()
        }
        binding.customerSettingsSaveBtn.setOnClickListener {
            val qualificationParams = when (binding.customerSettingsIsPerformerCheckbox.isChecked) {
                true -> adapter.changedQualificationParams.toList()
                false -> listOf()
            }
            viewModel.save(qualificationParams)
        }
    }
}