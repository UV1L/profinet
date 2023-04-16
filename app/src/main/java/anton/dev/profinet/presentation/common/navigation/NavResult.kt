package anton.dev.profinet.presentation.common.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class NavResult(
    val requestCode: Int,
    val result: @RawValue Any? = null
) : Parcelable