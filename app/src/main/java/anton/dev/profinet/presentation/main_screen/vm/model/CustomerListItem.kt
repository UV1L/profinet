package anton.dev.profinet.presentation.main_screen.vm.model

import android.os.Parcelable
import anton.dev.profinet.domain.models.Customer
import anton.dev.profinet.domain.models.QualificationParams
import kotlinx.parcelize.Parcelize

@Parcelize
data class CustomerListItem(
    val id: String,
    val name: String,
    val age: Int,
    val rating: Double,
    val qualificationParams: List<QualificationParams>
) : Parcelable

internal fun Customer.asListItem() = CustomerListItem(
    id = id,
    name = name,
    age = age,
    rating = rating?.toDouble() ?: 0.0,
    qualificationParams = qualifications
)