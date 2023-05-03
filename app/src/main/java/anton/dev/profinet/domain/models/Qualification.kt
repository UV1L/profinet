package anton.dev.profinet.domain.models

import android.os.Parcelable
import androidx.annotation.IntRange
import kotlinx.parcelize.Parcelize
import java.util.*

enum class Qualification(val displayName: String) {

    Programmer("Программист"),
    Electric("Электрик"),
    Loader("Грузчик"),
    Plumber("Сантехник")
}

@Parcelize
data class QualificationParams(
    val qualification: Qualification,
    @IntRange(from = 0, to = 10)
    var level: Int,
    var experience: Int,
) : Parcelable {

    companion object {
        fun empty() = Qualification.values().map {
            QualificationParams(
                qualification = it,
                level = 0,
                experience = 0
            )
        }
    }
}