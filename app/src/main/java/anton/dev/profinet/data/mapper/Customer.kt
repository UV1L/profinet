package anton.dev.profinet.data.mapper

import anton.dev.profinet.data.model.CustomerNet
import anton.dev.profinet.domain.models.Customer
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

val Customer.asNet: CustomerNet
    get() = CustomerNet(
        id = id,
        name = name,
        sex = sex?.asString,
        dateOfBirt = birthday?.asString,
        language = language?.asString,
        aboutMe = aboutMe,
        reviews = reviews.map { it.asNet },
        rating = rating,
        qualifications = qualifications.map { it.asNet },
        address = address
    )

val CustomerNet.fromNet: Customer
    get() = Customer(
        id = id,
        name = name,
        sex = sex?.asSex,
        birthday = dateOfBirt?.asDate,
        language = language?.asLanguage,
        aboutMe = aboutMe,
        reviews = reviews.map { it.fromNet },
        rating = rating,
        qualifications = qualifications.map { it.fromNet },
        address = address
    )