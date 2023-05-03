package anton.dev.profinet.domain.get_customer

import anton.dev.profinet.data.Repository
import anton.dev.profinet.domain.models.Customer
import javax.inject.Inject

internal interface GetCurrentCustomerCase {

    suspend fun execute(): Customer
}

internal class GetCurrentCustomerCaseImpl @Inject constructor(
    private val repository: Repository
) : GetCurrentCustomerCase {

    override suspend fun execute(): Customer = repository.currentCustomer()
}