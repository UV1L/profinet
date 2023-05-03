package anton.dev.profinet.data.mapper

import java.text.SimpleDateFormat
import java.util.*

private const val DatePattern = "yyyy-MM-dd'T'HH:mm:ss'Z'"

val Date.asString: String get() = SimpleDateFormat(DatePattern, Locale.ENGLISH).format(this)

val String.asDate: Date? get() = SimpleDateFormat(DatePattern, Locale.ENGLISH).parse(this)