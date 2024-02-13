package lt.timofey.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import lt.timofey.domain.entity.Photos

//@HiltViewModel
class NavigationViewModel : ViewModel() {
    val uiState = MutableStateFlow<Photos?>(null)
    fun setPhoto(currentPhoto: Photos) {
        uiState.update { currentPhoto }
    }
}