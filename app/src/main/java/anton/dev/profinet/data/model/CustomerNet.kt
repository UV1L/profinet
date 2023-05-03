package anton.dev.profinet.data.model

import com.google.firebase.database.PropertyName

//обязательно нужно делать значения по умолчанию или можно сделать все поля var вне конструктора,
// иначе десериализатор бибилотеки не найдет конструктор без аргументов
data class CustomerNet(

    @get:PropertyName("id")
    val id: String = "",

    @get:PropertyName("name")
    val name: String = "",

    @get:PropertyName("sex")
    val sex: String? = null,

    @get:PropertyName("dateOfBirt")
    val dateOfBirt: String? = null,

    @get:PropertyName("language")
    val language: String? = null,

    @get:PropertyName("aboutMe")
    val aboutMe: String? = null,

    @get:PropertyName("reviews")
    val reviews: List<ReviewNet> = listOf(),

    @get:PropertyName("rating")
    val rating: Int? = null,

    @get:PropertyName("qualifications")
    val qualifications: List<QualificationParamsNet> = listOf(),

    @get:PropertyName("address")
    val address: String? = null
) {

    companion object PropertyNames {
        const val Id = "id"
    }
}