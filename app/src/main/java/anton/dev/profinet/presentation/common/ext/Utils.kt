package anton.dev.profinet.presentation.common.ext

import kotlin.random.Random

fun requestCode(): Lazy<Int> = lazyOf(Random.nextBits(16))