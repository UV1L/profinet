package anton.dev.profinet.presentation.data.mapper

import anton.dev.profinet.presentation.domain.models.Language

val Language.asString: String get() = name

val String.asLanguage: Language get() = Language.valueOf(this)