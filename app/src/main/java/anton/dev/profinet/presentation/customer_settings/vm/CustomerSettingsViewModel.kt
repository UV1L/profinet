package anton.dev.profinet.presentation.customer_settings.vm

import androidx.lifecycle.MutableLiveData
import anton.dev.profinet.data.Repository
import anton.dev.profinet.domain.get_customer.GetCurrentCustomerCase
import anton.dev.profinet.domain.models.Customer
import anton.dev.profinet.domain.models.QualificationParams
import anton.dev.profinet.presentation.common.navigation.NavEvent
import anton.dev.profinet.presentation.common.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CustomerSettingsViewModel @Inject constructor(
    private val getCurrentCustomerCase: GetCurrentCustomerCase,
    private val repository: Repository
) : BaseViewModel() {

    val isLoading = MutableLiveData(true)
    val currentCustomer = MutableLiveData<Customer>()

    override fun initialize(): Any = launch {
        currentCustomer.value = getCurrentCustomerCase.execute()
        isLoading.value = false
    }

    fun save(qualificationParams: List<QualificationParams>) = launch {
        getCurrentCustomerCase.execute()?.let {
            repository.createOrUpdateCustomer(
                it.copy(
                    qualifications = qualificationParams
                )
            )
        }

        postEvent(NavEvent.Back)
    }
}