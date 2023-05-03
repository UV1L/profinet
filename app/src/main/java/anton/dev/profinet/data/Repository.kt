package anton.dev.profinet.data

import anton.dev.profinet.data.mapper.asNet
import anton.dev.profinet.data.mapper.fromNet
import anton.dev.profinet.data.model.CustomerNet
import anton.dev.profinet.data.model.ServiceNet
import anton.dev.profinet.domain.models.Customer
import anton.dev.profinet.domain.models.Service
import anton.dev.profinet.domain.repositories.CustomerRepository
import anton.dev.profinet.domain.repositories.ServicesRepository
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.database.ktx.snapshots
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.CountDownLatch
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class Repository @Inject constructor() : ServicesRepository, CustomerRepository {

    private companion object {

        const val ServicesDBKey = "Services"
        const val CustomersDBKey = "Customers"
    }

    private val database = Firebase.database

    private fun getServicesDb(): DatabaseReference = database.reference.child(ServicesDBKey)
    private fun getCustomersDb(): DatabaseReference = database.reference.child(CustomersDBKey)

    override fun getServices(filter: (Service) -> Boolean): Flow<List<Service>> {
        return getServicesDb().snapshots.map {
            //не знаю норм ли так или лучше в месте, где collect будут вызывать менять контекст
            withContext(Dispatchers.IO) {
                it.getValue<HashMap<String, ServiceNet>>()
                    .orEmpty()
                    .values
                    .map { serviceNet -> serviceNet.fromNet }
                    .filter(filter)
            }
        }
    }

    override suspend fun createOrUpdateService(service: Service) {
        withContext(Dispatchers.IO) {
            getServicesDb()
                .child(service.id)
                .setValue(service.asNet)
        }
    }

    override suspend fun updatePerformer(serviceId: String, performerCustomerId: String) {
        withContext(Dispatchers.IO) {
            getServicesDb()
                .child(serviceId)
                .child(ServiceNet.PropertyNames.PerformerCustomerId)
                .setValue(performerCustomerId)
        }
    }

    override suspend fun isCustomerExist(): Boolean {
        if (FirebaseAuth.getInstance().currentUser == null) return false
        return withContext(Dispatchers.IO) {
            suspendCoroutine<DataSnapshot> { continuation ->
                getCustomersDb()
                    .child(FirebaseAuth.getInstance().currentUser!!.uid)
                    .get()
                    .addOnSuccessListener {
                        continuation.resumeWith(
                            Result.success(it)
                        )
                    }
            }.getValue<CustomerNet>()?.fromNet != null
        }
    }

    override suspend fun currentCustomer(): Customer {
        return withContext(Dispatchers.IO) {

            /**
             * Сначала обновляем id у кастомера с таким id
             * Если он уже был, то ничего страшного, просто значение останется таким же
             * А если его не было, то он создастся новый и в него запишется поле id
             */
            suspendCoroutine<Flow<DataSnapshot>> { continuation ->
                getCustomersDb()
                    .child(FirebaseAuth.getInstance().currentUser!!.uid)
                    .child(CustomerNet.PropertyNames.Id)
                    .setValue(FirebaseAuth.getInstance().currentUser!!.uid)
                    .addOnSuccessListener {
                        continuation.resumeWith(
                            Result.success(
                                getCustomersDb()
                                    .child(FirebaseAuth.getInstance().currentUser!!.uid)
                                    .snapshots
                            )
                        )
                    }
            }.first().getValue<CustomerNet>()!!.fromNet
        }
    }

    override fun getCustomers(filter: (Customer) -> Boolean): Flow<List<Customer>> {
        return getCustomersDb().snapshots.map {
            //не знаю норм ли так или лучше в месте, где collect будут вызывать менять контекст
            withContext(Dispatchers.IO) {
                it.getValue<HashMap<String, CustomerNet>>()
                    .orEmpty()
                    .values
                    .map { customerNet -> customerNet.fromNet }
                    .filter(filter)
            }
        }
    }

    override suspend fun createOrUpdateCustomer(customer: Customer) {
        withContext(Dispatchers.IO) {
            getCustomersDb()
                .child(customer.id)
                .setValue(customer.asNet)
        }
    }
}