package anton.dev.profinet.presentation.data.model

import com.google.firebase.database.PropertyName

//обязательно нужно делать значения по умолчанию или можно сделать все поля var вне конструктора,
// иначе десериализатор бибилотеки не найдет конструктор без аргументов
data class ReviewNet(

    @get:PropertyName("customerId")
    val customerId: String = "", //автор

    @get:PropertyName("message")
    val message: String = ""
)