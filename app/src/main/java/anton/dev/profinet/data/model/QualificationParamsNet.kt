package anton.dev.profinet.presentation.data.model

import com.google.firebase.database.PropertyName

//обязательно нужно делать значения по умолчанию или можно сделать все поля var вне конструктора,
// иначе десериализатор бибилотеки не найдет конструктор без аргументов
data class QualificationParamsNet(

    @get:PropertyName("qualification")
    val qualification: String = "",

    @get:PropertyName("level")
    val level: Int? = null,

    @get:PropertyName("carierStart")
    val carierStart: String = ""
)