package lt.timofey.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
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

    init {
        fetchFeaturedCollections()
        fetchCuratedCollection()
    }

    private fun fetchFeaturedCollections() = viewModelScope.launch {
        try {
            val fetchCollections = getFeaturedCollectionsUseCase()
            fetchCollections.collect { data ->
                if (data.errorMessage.isNullOrEmpty()) {
                    uiState.value.loadingFeaturedCollections =
                        FeaturedCollectionsUIState.SUCCESS(data.featuredCollections[0])
                        Log.d("!!!!!!!", data.featuredCollections.toString())
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
            fetchCollections.collect {
                data ->
                if (data.errorMessage.isNullOrEmpty()) {
                    uiState.value.loadingCuratedPhotos = CuratedPhotosUIState.SUCCESS(data.curatedPhotos[0])
                    Log.d("!!!!!!!", data.curatedPhotos.toString())
                } else {
                    uiState.value.loadingCuratedPhotos = CuratedPhotosUIState.ERROR(data.errorMessage)
                    Log.d("!!!!!!!", data.errorMessage)
                }
            }
        } catch (e: Exception) {
            uiState.value.loadingCuratedPhotos = CuratedPhotosUIState.ERROR(message = e.localizedMessage)
            Log.d("!!!!!!!", e.localizedMessage)
        }
    }

}