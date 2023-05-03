package anton.dev.profinet.presentation.data.mapper

import anton.dev.profinet.presentation.domain.models.Sex

val Sex.asString: String get() = name

val String.asSex: Sex get() = Sex.valueOf(this)