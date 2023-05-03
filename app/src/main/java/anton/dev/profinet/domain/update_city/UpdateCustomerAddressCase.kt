package anton.dev.profinet.domain.update_city

import anton.dev.profinet.data.Repository
import anton.dev.profinet.domain.get_customer.GetCurrentCustomerCase
import javax.inject.Inject

internal interface UpdateCustomerAddressCase {

    suspend fun execute(city: String)
}

internal class UpdateCustomerAddressCaseImpl @Inject constructor(
    private val repository: Repository,
    private val getCurrentCustomerCase: GetCurrentCustomerCase,
) : UpdateCustomerAddressCase {

    override suspend fun execute(city: String) {
        println("Before!")
        val currentCustomer = getCurrentCustomerCase.execute()
        repository.createOrUpdateCustomer(
            currentCustomer.copy(
                address = city
            )
        )
        println("After!")
    }
}