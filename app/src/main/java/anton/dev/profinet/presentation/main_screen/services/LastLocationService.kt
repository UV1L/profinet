package anton.dev.profinet.presentation.main_screen.services

import android.location.Location
import com.google.android.gms.common.api.ResolvableApiException

/**
 * Сервис для работы с геолокацией и GPS
 */
interface LastLocationService {

    /**
     * Запросить последнюю геолокацию
     */
    fun tryGetLastLocation(onLocationFound: (Location) -> Unit)

    /**
     * Узнать, включен ли GPS в настройках
     *
     * onSuccess, если включен
     * onFailure, если нет
     */
    fun requestForGps(onSuccess: () -> Unit, onFailure: (ResolvableApiException) -> Unit)
}