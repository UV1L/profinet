package anton.dev.profinet.presentation.main_screen.services

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest

private const val LOCATION_REQUEST_INTERVAL = 60000L
private const val FASTEST_LOCATION_INTERVAL = 5000L

internal class GmsLastLocationService(

    private val context: Context

) : LastLocationService {

    private var onLocationFound: ((Location) -> Unit)? = null

    private val locationRequest = LocationRequest.create().apply {
        interval = LOCATION_REQUEST_INTERVAL
        fastestInterval = FASTEST_LOCATION_INTERVAL
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private val locationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationClient.removeLocationUpdates(this)
            if (locationResult.locations.isNotEmpty()) {
                val l = locationResult.locations[0]
                onLocationFound?.invoke(l)
            }
            onLocationFound = null
        }
    }

    /**
     * поставил супресс MissionPermission чтобы не ругался линтер
     * на самом деле все проверяется
     */
    @SuppressLint("MissingPermission")
    override fun tryGetLastLocation(onLocationFound: (Location) -> Unit) {
        this.onLocationFound = onLocationFound
        locationClient.lastLocation.addOnSuccessListener { lastLocation ->
            tryGetLastLocation(lastLocation)
        }
    }

    @SuppressLint("MissingPermission")
    private fun tryGetLastLocation(lastLocation: Location?) {
        if (lastLocation == null) {
            locationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                null
            )
        } else {
            onLocationFound?.invoke(lastLocation)
        }
    }

    override fun requestForGps(onSuccess: () -> Unit, onFailure: (ResolvableApiException) -> Unit) {
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val client = LocationServices.getSettingsClient(context)
        val task = client.checkLocationSettings(builder?.build())

        task.addOnSuccessListener { onSuccess.invoke() }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                onFailure.invoke(exception)
            }
        }
    }
}