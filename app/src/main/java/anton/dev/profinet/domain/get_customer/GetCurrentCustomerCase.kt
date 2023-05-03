package anton.dev.profinet.presentation.domain.get_customer

import anton.dev.profinet.presentation.data.Repository
import anton.dev.profinet.presentation.domain.models.Customer
import javax.inject.Inject

internal interface GetCurrentCustomerCase {

    suspend fun execute(): Customer
}

internal class GetCurrentCustomerCaseImpl @Inject constructor(
    private val repository: Repository
) : GetCurrentCustomerCase {

    override suspend fun execute(): Customer = repository.currentCustomer()
}