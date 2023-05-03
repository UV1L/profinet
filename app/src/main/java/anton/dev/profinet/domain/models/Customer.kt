package anton.dev.profinet.domain.models

import androidx.annotation.IntRange
import java.util.*

data class Customer(
    val id: String,
    val name: String,
    val sex: Sex? = null,
    val birthday: Date? = null,
    val language: Language? = null,
    val aboutMe: String? = null,
    val reviews: List<Review> = listOf(),

    @IntRange(from = 0, to = 10)
    val rating: Int? = null,

    val qualifications: List<QualificationParams> = listOf(),
    val address: String? = null
) {

    //возраст в годах
    val age by lazy {
        val calendarCurrent = Calendar.getInstance().apply { time = Date() }
        val calendarDateOfBrith = Calendar.getInstance().apply {
            if (birthday != null) {
                time = birthday
            }
        }
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