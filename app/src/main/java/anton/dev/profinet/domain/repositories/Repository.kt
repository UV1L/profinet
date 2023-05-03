package anton.dev.profinet.domain.repositories

import anton.dev.profinet.domain.models.Customer
import anton.dev.profinet.domain.models.Service
import kotlinx.coroutines.flow.Flow

interface ServicesRepository {

    fun getServices(filter: (Service) -> Boolean = { true }): Flow<List<Service>>

    /**
     * Если такой [service] есть, то перезапишет его
     * Если такого [service] нет, то создаст его
     */
    suspend fun createOrUpdateService(service: Service)

    suspend fun updatePerformer(serviceId: String, performerCustomerId: String)
}

interface CustomerRepository {

    /**
     * ВАЖНО!!!! ВЫЗЫВАТЬ ТОЛЬКО ПОСЛЕ ЛОГИНА
     *
     * Вернет текушего кастомера
     */
    suspend fun currentCustomer(): Customer?

    fun getCustomers(filter: (Customer) -> Boolean = { true }): Flow<List<Customer>>

    /**
     * Если такой [customer] есть, то перезапишет его
     * Если такого [customer] нет, то создаст его
     */
    suspend fun createOrUpdateCustomer(customer: Customer)

    suspend fun isCustomerExist(): Boolean
}