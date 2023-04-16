package anton.dev.profinet.presentation.main_screen.vm.model

internal data class CustomerListItem(
    val id: String,
    val name: String,
    val age: Int,
    val experience: Int,
    val rating: Double,
    val speciality: String
)