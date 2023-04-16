package anton.dev.profinet.presentation.search_city.ui

import android.content.Context
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.MutableLiveData
import anton.dev.profinet.R
import com.mapbox.search.ResponseInfo
import com.mapbox.search.SearchEngine
import com.mapbox.search.SearchEngineSettings
import com.mapbox.search.SearchOptions
import com.mapbox.search.SearchSuggestionsCallback
import com.mapbox.search.result.SearchSuggestion
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.logging.Logger
import javax.inject.Inject

internal class SearchCityQueryTextListener @Inject constructor(
    @ApplicationContext context: Context,
    private val logger: Logger
) : OnQueryTextListener {

    private val searchEngine = SearchEngine.createSearchEngine(
        SearchEngineSettings(context.getString(R.string.mapbox_public_token))
    )
    private val searchCallback = SearchCallback()

    val suggestions = MutableLiveData<List<SearchSuggestion>>()
    val isLoading = MutableLiveData(false)

    override fun onQueryTextSubmit(query: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onQueryTextChange(newText: String): Boolean {
        isLoading.value = true
        val task = searchEngine.search(newText, SearchOptions(limit = 10), searchCallback)
        return false
    }

    private inner class SearchCallback : SearchSuggestionsCallback {

        override fun onError(e: Exception) {
            logger.severe(e.message)
        }

        override fun onSuggestions(suggestions: List<SearchSuggestion>, responseInfo: ResponseInfo) {
            this@SearchCityQueryTextListener.suggestions.value = suggestions
        }
    }
}