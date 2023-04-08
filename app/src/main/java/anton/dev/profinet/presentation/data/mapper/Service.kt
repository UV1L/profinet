package anton.dev.profinet.presentation.data.mapper

import anton.dev.profinet.presentation.data.model.ServiceNet
import anton.dev.profinet.presentation.domain.models.Service

val Service.asNet: ServiceNet
    get() = ServiceNet(
        id = id,
        description = description,
        requiredQualifications = requiredQualifications.map { it.asString },
        createdDate = createdDate.asString,
        requiredEndDate = requiredEndDate.asString,
        place = place.asString,
        rating = rating,
        reviews = reviews.map { it.asNet }
    )

val ServiceNet.fromNet: Service
    get() = Service(
        id = id,
        description = description,
        requiredQualifications = requiredQualifications.map { it.asQualification },
        createdDate = createdDate.asDate!!,
        requiredEndDate = requiredEndDate.asDate!!,
        place = place.asPlace,
        rating = rating!!,
        reviews = reviews.map { it.fromNet }
    )