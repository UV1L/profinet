package anton.dev.profinet.presentation.main_screen.vm

import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import anton.dev.profinet.R
import anton.dev.profinet.presentation.common.ext.combineWith
import anton.dev.profinet.presentation.common.ext.map
import anton.dev.profinet.presentation.common.ext.requestCode
import anton.dev.profinet.presentation.common.navigation.NavEvent
import anton.dev.profinet.presentation.common.ui.BaseViewModel
import anton.dev.profinet.presentation.main_screen.services.LastLocationService
import anton.dev.profinet.presentation.search_city.vm.model.CityModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
internal class MainScreenViewModel @Inject constructor(
    private val locationService: LastLocationService
) : BaseViewModel() {

    private val russianLocale = Locale("ru", "RU")

    val username = FirebaseAuth.getInstance().currentUser?.displayName
    val avatarText = username?.firstOrNull()?.uppercaseChar().toString()
    val isGpsOn = MutableLiveData<Boolean>()

    private val lastLocation = MutableLiveData<Location>()
    private val manuallySelectedCityName = MutableLiveData<String>()
    val currentCityName: LiveData<String?> = lastLocation.combineWith(manuallySelectedCityName).map {
        if (it.second.isNullOrEmpty()) getCityNameByLocation(it.first!!)
        else it.second!!
    }

    override fun initialize() {
        subscribeOnResult<CityModel>(SELECT_CITY_CODE) {
            manuallySelectedCityName.value = it.name
        }
        locationService.requestForGps(
            onSuccess = { isGpsOn.value = true },
            onFailure = { isGpsOn.value = false }
        )
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
        val geocoder = Geocoder(context, russianLocale)
        val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)

        return addresses?.getOrNull(0)?.locality
    }.getOrNull()

    companion object {
        val SELECT_CITY_CODE by requestCode()
    }
}