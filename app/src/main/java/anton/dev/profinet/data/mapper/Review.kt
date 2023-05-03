package anton.dev.profinet.data.mapper

import anton.dev.profinet.data.model.ReviewNet
import anton.dev.profinet.domain.models.Review

val Review.asNet: ReviewNet
    get() = ReviewNet(
    customerId = customerId,
    message = message
)

val ReviewNet.fromNet: Review
    get() = Review(
    customerId = customerId,
    message = message
)