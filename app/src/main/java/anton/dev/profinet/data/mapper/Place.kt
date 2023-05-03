package anton.dev.profinet.data.mapper

import anton.dev.profinet.domain.models.Place

val Place.asString: String get() = when(this) {
    is Place.Address -> address
    Place.Customer -> "Customer"
    Place.Performer -> "Performer"
}

val String.asPlace: Place
    get() = when(this) {
    "Customer" -> Place.Customer
    "Performer" -> Place.Customer
    else -> Place.Address(address = this)
}
