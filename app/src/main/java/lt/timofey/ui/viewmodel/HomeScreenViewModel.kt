package lt.timofey.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lt.timofey.domain.entity.toCuratedPhotos
import lt.timofey.domain.usecases.getCuratedPhotosUseCase
import lt.timofey.domain.usecases.getFeaturedCollectionsUseCase
import lt.timofey.domain.usecases.getSearchPhotosUseCase
import lt.timofey.ui.state.CuratedPhotosUIState
import lt.timofey.ui.state.FeaturedCollectionsUIState
import lt.timofey.ui.state.HomeScreenUIState
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getFeaturedCollectionsUseCase: getFeaturedCollectionsUseCase,
    private val getCuratedPhotosUseCase: getCuratedPhotosUseCase,
    private val searchPhotosUseCase: getSearchPhotosUseCase
) : ViewModel() {

    val uiState = MutableStateFlow<HomeScreenUIState>(HomeScreenUIState())
    val stat: StateFlow<HomeScreenUIState> = uiState.asStateFlow()

    init {
        viewModelScope.launch {
            fetchFeaturedCollections()
            fetchCuratedCollection()
        }
    }

    fun onSearchTextChange(text: String) {
        uiState.update { it.copy(searchQuery = text, isSearched = text.isNotBlank()) }
        if (text.isNotBlank()) {
            searchPhotosCollections(text)
        } else {
            fetchCuratedCollection()
        }
    }

    @OptIn(FlowPreview::class)
    private fun searchPhotosCollections(query: String) = viewModelScope.launch {
        try {
            val searchResult = searchPhotosUseCase(query)
            searchResult.debounce(500L).collect() { data ->
                if (data.errorMessage.isNullOrEmpty()) {
                    uiState.update {
                        it.copy(
                            loadingCuratedPhotos = CuratedPhotosUIState.SUCCESS(
                                data.curatedPhotos[0].toCuratedPhotos()
                            )
                        )
                    }
                    Log.d("!!!!!!!", data.curatedPhotos.toString())
                } else {
                    uiState.update {
                        it.copy(loadingCuratedPhotos = CuratedPhotosUIState.ERROR(data.errorMessage))
                    }
                    Log.d("!!!!!!!", data.errorMessage)
                }
            }
        } catch (e: Exception) {
            Log.d("!!!!!Search", e.localizedMessage)
        }
    }

    private fun fetchFeaturedCollections() = viewModelScope.launch {
        try {
            val fetchCollections = getFeaturedCollectionsUseCase()
            fetchCollections.collect { data ->
                if (data.errorMessage.isNullOrEmpty()) {
                    uiState.update { currentState ->
                        currentState.copy(
                            loadingFeaturedCollections =
                            FeaturedCollectionsUIState.SUCCESS(data.featuredCollections[0])
                        )
                    }
                } else {
                    uiState.update { currentState ->
                        currentState.copy(
                            loadingFeaturedCollections =
                            FeaturedCollectionsUIState.ERROR(message = data.errorMessage)
                        )
                    }
                    Log.d("!!!!!!!", data.errorMessage.toString())
                }
            }
        } catch (e: Exception) {
            uiState.update { currentState ->
                currentState.copy(
                    loadingFeaturedCollections =
                    FeaturedCollectionsUIState.ERROR(message = e.localizedMessage)
                )
            }
            Log.d("!!!!!!!", e.localizedMessage)
        }
    }

    private fun fetchCuratedCollection() = viewModelScope.launch {
        try {
            val fetchCollections = getCuratedPhotosUseCase()
            fetchCollections.collect { data ->
                if (data.errorMessage.isNullOrEmpty()) {
                    uiState.update { currentState ->
                        currentState.copy(
                            loadingCuratedPhotos =
                            CuratedPhotosUIState.SUCCESS(data.curatedPhotos[0])
                        )
                    }
                    Log.d("!!!!!!!", data.curatedPhotos.toString())
                    Log.d("!!!!!!!", data.curatedPhotos[0].photos.size.toString())
                } else {
                    uiState.update {
                        it.copy(loadingCuratedPhotos = CuratedPhotosUIState.ERROR(data.errorMessage))
                    }
                    Log.d("!!!!!!!", data.errorMessage)
                }
            }
        } catch (e: Exception) {
            uiState.update {
                it.copy(loadingCuratedPhotos = CuratedPhotosUIState.ERROR(e.localizedMessage))
            }
            Log.d("!!!!!!!", e.localizedMessage)
        }
    }

}