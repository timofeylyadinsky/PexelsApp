package lt.timofey.ui.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import lt.timofey.domain.usecases.deletePhotosFromBookmarkUseCase
import lt.timofey.domain.usecases.isPhotoSavedUseCase
import lt.timofey.domain.usecases.savePhotoToBookmarkUseCase
import lt.timofey.ui.state.DetailsScreenUIState
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val isSavedUseCase: isPhotoSavedUseCase,
    private val savePhotoToBookmarkUseCase: savePhotoToBookmarkUseCase,
    private val deletePhotosFromBookmarkUseCase: deletePhotosFromBookmarkUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val uiState = MutableStateFlow<DetailsScreenUIState>(DetailsScreenUIState())

    val id = savedStateHandle.get<String>("id")!!

    init {
        viewModelScope.launch {
            Log.d("!!!!", id)
        //isSaved()
        }
    }

    private suspend fun isSaved() = viewModelScope.launch {
        isSavedUseCase(id.toInt())
    }
}