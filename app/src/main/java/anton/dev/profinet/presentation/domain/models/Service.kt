package anton.dev.profinet.presentation.domain.models

import androidx.annotation.IntRange
import java.util.*

//нужны виды услуги??? может хватит квалификаций??
data class Service(
    val id: String,
    val description: String,
    val requiredQualifications: List<Qualification>,//это заместо вида услуги, я не понял что за виды нужны
    val createdDate: Date,// время, когда заявка была создана
    val requiredEndDate: Date,//когда уже должна быть выполнена
    val place: Place,
    @IntRange(from = 0, to = 10)
    val rating: Int,
    val reviews: List<Review>,

    //исполнитель заказа, если он есть, то заказ в работе
    val performerCustomerId: String? = null
)

sealed interface Place {

    //адрес заказчик
    object Customer : Place

    //исполнитель
    object Performer : Place


    data class Address(val address: String) : Place
}