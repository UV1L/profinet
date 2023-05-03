package anton.dev.profinet.data.mapper

import anton.dev.profinet.domain.models.Language

val Language.asString: String get() = name

val String.asLanguage: Language get() = Language.valueOf(this)