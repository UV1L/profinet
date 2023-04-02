package anton.dev.profinet.presentation.data.mapper

import anton.dev.profinet.presentation.domain.models.Place

val Place.asString: String get() = when(this) {
    is Place.Address -> address
    Place.Customer -> "Customer"
    Place.Performer -> "Performer"
}

val String.asPlace: Place get() = when(this) {
    "Customer" -> Place.Customer
    "Performer" -> Place.Customer
    else -> Place.Address(address = this)
}
