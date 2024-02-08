package lt.timofey.ui.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
        fetchFeaturedCollections()
        fetchCuratedCollection()
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
                    uiState.value.loadingFeaturedCollections =
                        FeaturedCollectionsUIState.ERROR(message = data.errorMessage)
                    Log.d("!!!!!!!", data.errorMessage.toString())
                }
            }
        } catch (e: Exception) {
            uiState.value.loadingFeaturedCollections =
                FeaturedCollectionsUIState.ERROR(message = e.localizedMessage)
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
                } else {
                    uiState.update {
                        it.copy(loadingCuratedPhotos = CuratedPhotosUIState.ERROR(data.errorMessage))
                    }
                    Log.d("!!!!!!!", data.errorMessage)
                }
            }
        } catch (e: Exception) {
            uiState.value.loadingCuratedPhotos =
                CuratedPhotosUIState.ERROR(message = e.localizedMessage)
            Log.d("!!!!!!!", e.localizedMessage)
        }
    }

}