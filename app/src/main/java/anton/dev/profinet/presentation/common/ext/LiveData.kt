package anton.dev.profinet.presentation.common.ext

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations

inline fun <T, R> LiveData<T>.map(crossinline mapper: (T) -> R): LiveData<R> {

    return Transformations.map(this) { mapper(it) }
}

fun <T, K> LiveData<T>.combineWith(liveData: LiveData<K>): LiveData<Pair<T?, K?>> {

    val result = MediatorLiveData<Pair<T?, K?>>()

    result.addSource(this) { result.value = it to liveData.value }
    result.addSource(liveData) { result.value = this.value to it }

    return result
}

operator fun <T, K> LiveData<T>.plus(liveData: LiveData<K>): LiveData<Pair<T?, K?>> {
    return this.combineWith(liveData)
}