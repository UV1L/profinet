package anton.dev.profinet.presentation.search_city.vm

import androidx.lifecycle.viewModelScope
import anton.dev.profinet.presentation.common.ext.requestCode
import anton.dev.profinet.presentation.common.navigation.NavEvent
import anton.dev.profinet.presentation.common.navigation.NavResult
import anton.dev.profinet.presentation.common.ui.BaseViewModel
import anton.dev.profinet.presentation.main_screen.vm.MainScreenViewModel
import anton.dev.profinet.presentation.search_city.ui.SearchCityAdapter
import anton.dev.profinet.presentation.search_city.ui.SearchCityQueryTextListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SearchCityViewModel @Inject constructor(
    val searchCityQueryTextListener: SearchCityQueryTextListener
) : BaseViewModel() {

    val adapter = SearchCityAdapter()
    val suggestions = searchCityQueryTextListener.suggestions

    val isLoading = searchCityQueryTextListener.isLoading

    override fun initialize() {
        viewModelScope.launch {
            adapter.selectedCity.collectLatest {
                val result = NavResult(
                    MainScreenViewModel.SELECT_CITY_CODE,
                    it
                )
                postEvent(NavEvent.BackWithResult(result))
            }
        }
    }
}