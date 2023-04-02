package anton.dev.profinet.presentation.data.mapper

import anton.dev.profinet.presentation.data.model.ReviewNet
import anton.dev.profinet.presentation.domain.models.Review

val Review.asNet: ReviewNet
    get() = ReviewNet(
    customerId = customerId,
    message = message
)

val ReviewNet.fromNet: Review get() = Review(
    customerId = customerId,
    message = message
)