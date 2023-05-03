package anton.dev.profinet.data.mapper

import anton.dev.profinet.domain.models.Sex

val Sex.asString: String get() = name

val String.asSex: Sex get() = Sex.valueOf(this)