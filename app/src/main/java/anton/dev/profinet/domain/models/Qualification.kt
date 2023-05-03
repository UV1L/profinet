package anton.dev.profinet.presentation.domain.models

import androidx.annotation.IntRange
import java.util.*

enum class Qualification {

    Programmer,
    Electric,
    Metallurgist
}

data class QualificationParams(
    val qualification: Qualification,
    @IntRange(from = 0, to = 10)
    val level: Int,

    //можно использовать как стаж, чтобы вычислить сколько лет уже есть такая квалификация
    val carierStart: Date
)