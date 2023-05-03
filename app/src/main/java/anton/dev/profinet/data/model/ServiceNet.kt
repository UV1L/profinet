package anton.dev.profinet.data.model

import com.google.firebase.database.PropertyName

//обязательно нужно делать значения по умолчанию или можно сделать все поля var вне конструктора,
// иначе десериализатор бибилотеки не найдет конструктор без аргументов
class ServiceNet(

    @get:PropertyName("id")
    val id: String = "",

    @get:PropertyName("description")
    val description: String = "",

    @get:PropertyName("requiredQualifications")
    val requiredQualifications: List<String> = emptyList(),

    @get:PropertyName("createdDate")
    val createdDate: String = "",

    @get:PropertyName("requiredEndDate")
    val requiredEndDate: String = "",

    @get:PropertyName("place")
    val place: String = "",

    @get:PropertyName("rating")
    val rating: Int? = null,

    @get:PropertyName("reviews")
    val reviews: List<ReviewNet> = emptyList(),

    @get:PropertyName(PerformerCustomerId)
    val performerCustomerId: String? = null
) {

    companion object PropertyNames {
        const val PerformerCustomerId = "performerCustomerId"
    }
}