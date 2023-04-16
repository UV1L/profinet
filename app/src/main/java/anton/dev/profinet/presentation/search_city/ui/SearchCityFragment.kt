package anton.dev.profinet.presentation.search_city.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import anton.dev.profinet.R
import anton.dev.profinet.databinding.FragmentSearchCityBinding
import anton.dev.profinet.presentation.common.ext.map
import anton.dev.profinet.presentation.common.ui.BaseHiltFragment
import anton.dev.profinet.presentation.search_city.vm.SearchCityViewModel
import anton.dev.profinet.presentation.search_city.vm.model.CityModel

internal class SearchCityFragment : BaseHiltFragment() {

    override val viewModel: SearchCityViewModel by viewModels()
    override val layout: Int = R.layout.fragment_search_city
    override val binding: FragmentSearchCityBinding by lazy { FragmentSearchCityBinding.inflate(layoutInflater) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchCityBackButton.parentFragment = this
        binding.searchCitySearchView.setOnClickListener {
            binding.searchCitySearchView.isIconified = false
        }
        binding.searchCitySearchView.setOnQueryTextListener(viewModel.searchCityQueryTextListener)
        binding.searchCityRv.adapter = viewModel.adapter
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.searchCityProgressBar.isGone = !it
        }
        observeSuggestions()
    }

    private fun observeSuggestions() {
        viewModel.suggestions.observe(viewLifecycleOwner) { suggestions ->
            viewModel.isLoading.value = false
            val cities = suggestions.map {
                CityModel(it.name)
            }
            viewModel.adapter.submitList(cities)
        }
    }
}