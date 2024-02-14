package lt.timofey.ui.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lt.timofey.domain.usecases.deletePhotosFromBookmarkUseCase
import lt.timofey.domain.usecases.fetchPhotoByIdUseCase
import lt.timofey.domain.usecases.isPhotoSavedUseCase
import lt.timofey.domain.usecases.savePhotoToBookmarkUseCase
import lt.timofey.ui.state.DetailsScreenUIState
import lt.timofey.ui.state.LoadingPhotoUIState
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val isSavedUseCase: isPhotoSavedUseCase,
    private val savePhotoToBookmarkUseCase: savePhotoToBookmarkUseCase,
    private val deletePhotosFromBookmarkUseCase: deletePhotosFromBookmarkUseCase,
    private val fetchPhotoById: fetchPhotoByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val uiState = MutableStateFlow<DetailsScreenUIState>(DetailsScreenUIState())

    val id = savedStateHandle.get<String>("id")!!

    init {
        viewModelScope.launch {
            Log.d("!!!!", id)
            isSaved()
            fetchPhotosFromNetwork()
        }
    }

    private fun isSaved() = viewModelScope.launch {
        uiState.update { uiState.value.copy(isSaved = isSavedUseCase(id.toInt())) }
    }

    private suspend fun fetchPhotosFromNetwork() = viewModelScope.launch {
        try {
            val result = fetchPhotoById(id.toInt())
            result.collect() { data ->
                if (data.errorMessage.isNullOrEmpty()) {
                    if (data.photos != null)
                        uiState.update {
                            it.copy(
                                loadingPhoto = LoadingPhotoUIState.SUCCESS(
                                    data.photos
                                ),
                                photo = data.photos
                            )
                        }
                    Log.d("!!!!!!!", data.toString())
                } else {
                    uiState.update {
                        it.copy(loadingPhoto = LoadingPhotoUIState.ERROR(data.errorMessage))
                    }
                    Log.d("!!!!!!!", data.errorMessage)
                }
            }
        } catch (e: Exception) {
            Log.d("!!!!!Fetch", e.localizedMessage)
        }
    }

    fun clickBookmarkButton() {
        if (uiState.value.isSaved) {
            Log.d("!!!!!", "delete")
            deletePhoto()
            uiState.update { uiState.value.copy(isSaved = false) }
        } else {
            Log.d("!!!!!", "save")
            savePhoto()
            uiState.update { uiState.value.copy(isSaved = true) }
        }
//        viewModelScope.launch {
//            Log.d("!!!!!", uiState.value.isSaved.toString())
//            uiState.update { uiState.value.copy(isSaved = isSavedUseCase(id.toInt())) }
//            Log.d("!!!!!", uiState.value.isSaved.toString())
//        }
    }

    private fun savePhoto() {
        viewModelScope.launch {
            if (uiState.value.photo != null) {
                savePhotoToBookmarkUseCase(uiState.value.photo!!)
            }
        }
    }

    private fun deletePhoto() {
        viewModelScope.launch {
            if (uiState.value.photo != null) {
                deletePhotosFromBookmarkUseCase(uiState.value.photo!!)
            }
        }
    }
}