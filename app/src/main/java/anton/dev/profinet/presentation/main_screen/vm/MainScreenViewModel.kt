package anton.dev.profinet.presentation.main_screen.vm

import android.location.Geocoder
import android.location.Location
import android.view.View
import android.view.View.OnClickListener
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import anton.dev.profinet.R
import anton.dev.profinet.data.Repository
import anton.dev.profinet.presentation.common.ext.combineWith
import anton.dev.profinet.presentation.common.ext.map
import anton.dev.profinet.presentation.common.ext.requestCode
import anton.dev.profinet.presentation.common.navigation.NavEvent
import anton.dev.profinet.presentation.common.ui.BaseViewModel
import anton.dev.profinet.domain.update_city.UpdateCustomerAddressCase
import anton.dev.profinet.presentation.customer_public_profile.ui.CustomerPublicProfileFragmentArgs
import anton.dev.profinet.presentation.main_screen.services.LastLocationService
import anton.dev.profinet.presentation.main_screen.vm.model.CustomerListItem
import anton.dev.profinet.presentation.search_city.vm.model.CityModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
internal class MainScreenViewModel @Inject constructor(
    private val locationService: LastLocationService,
    private val geocoder: Geocoder,
    private val updateCustomerAddressCase: UpdateCustomerAddressCase,
    private val repository: Repository
) : BaseViewModel(), OnCustomerClick {

    private val currentCustomer = async { repository.currentCustomer() }
    val username = FirebaseAuth.getInstance().currentUser?.displayName
    val avatarText = username?.firstOrNull()?.uppercaseChar().toString()
    val isGpsOn = MutableLiveData<Boolean>()

    val specialists = repository.getCustomers {
        it.qualifications.isNotEmpty()
                && it.id != FirebaseAuth.getInstance().currentUser!!.uid
    }.asLiveData(Dispatchers.Main)

    private val lastLocation = MutableLiveData<Location>()
    private val manuallySelectedCityName = MutableLiveData<String>()
    val currentCityName: LiveData<String?> = lastLocation.combineWith(manuallySelectedCityName).map {
        if (it.second.isNullOrEmpty()) getCityNameByLocation(it.first!!)
        else it.second!!
    }

    override fun initialize() {
        launch {
            if (repository.isCustomerExist().not()) {
                FirebaseAuth.getInstance().currentUser!!.delete().addOnSuccessListener { logout() }
            }
            currentCustomer.await().address?.let { manuallySelectedCityName.value = it }
        }
        subscribeOnResult<CityModel>(SELECT_CITY_CODE) {
            manuallySelectedCityName.value = it.name
        }
        locationService.requestForGps(
            onSuccess = { isGpsOn.value = true },
            onFailure = { isGpsOn.value = false }
        )
        currentCityName.observe(this@MainScreenViewModel) {
            launch { updateCustomerAddressCase.execute(it.orEmpty()) }
        }
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
        postEvent(NavEvent.To(R.id.loginFragment, popUpTo = R.id.mainScreenFragment, inclusive = true))
    }

    fun tryGetLastLocation() {
        locationService.tryGetLastLocation { location ->
            lastLocation.value = location
        }
    }

    private fun getCityNameByLocation(location: Location): String? = runCatching {
        val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)

        return addresses?.getOrNull(0)?.locality
    }.getOrNull()

    companion object {

        val SELECT_CITY_CODE by requestCode()
    }

    override fun onClick(customerListItem: CustomerListItem) {
        postEvent(
            NavEvent.To(
                resId = R.id.action_to_customerPublicProfileFragment,
                args = CustomerPublicProfileFragmentArgs(customerListItem).toBundle()
            )
        )
    }
}

internal fun interface OnCustomerClick {

    fun onClick(customerListItem: CustomerListItem)
}