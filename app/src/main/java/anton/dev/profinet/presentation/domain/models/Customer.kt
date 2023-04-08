package anton.dev.profinet.presentation.domain.models

import androidx.annotation.IntRange
import java.util.*

data class Customer(
    val id: String,//наверное может быть uid из firebase auth
    val name: String,
    val sex: Sex? = null,
    val birthday: Date? = null,
    val language: Language? = null,//родной язык
    val aboutMe: String,
    val reviews: List<Review>,

    @IntRange(from = 0, to = 10)
    val rating: Int,

    val qualifications: List<QualificationParams>,
    val address: String
) {

    //возраст в годах
    val age by lazy {
        val calendarCurrent = Calendar.getInstance().apply { time = Date() }
        val calendarDateOfBrith = Calendar.getInstance().apply { time = Date(0) }
        calendarCurrent.get(Calendar.YEAR) - calendarDateOfBrith.get(Calendar.YEAR)
    }
}

enum class Sex {
    Male,
    Female
}

enum class Language {
    RUSSIAN,
    ENGLISH
}