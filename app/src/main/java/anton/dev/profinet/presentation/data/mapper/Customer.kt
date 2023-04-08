package anton.dev.profinet.presentation.data.mapper

import anton.dev.profinet.presentation.data.model.CustomerNet
import anton.dev.profinet.presentation.domain.models.Customer

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