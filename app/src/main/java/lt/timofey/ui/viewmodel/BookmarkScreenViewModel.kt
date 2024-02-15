package lt.timofey.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lt.timofey.domain.usecases.getBookmarkCollectionsUseCase
import lt.timofey.ui.state.BookmarkScreenUIState
import lt.timofey.ui.state.BookmarkUIState
import javax.inject.Inject

@HiltViewModel
class BookmarkScreenViewModel @Inject constructor(
    private val getBookmarkCollectionsUseCase: getBookmarkCollectionsUseCase
) : ViewModel() {
    val uiState = MutableStateFlow<BookmarkScreenUIState>(BookmarkScreenUIState())

    init {
        viewModelScope.launch {
            getBookmarkCollection()
        }
    }

    private suspend fun getBookmarkCollection() {
        viewModelScope.launch {
            try {
                val bookmark = getBookmarkCollectionsUseCase()
                if (bookmark.isNotEmpty()) {
                    uiState.update {
                        uiState.value.copy(
                            loadingPhoto = BookmarkUIState.SUCCESS(
                                bookmark
                            )
                        )
                    }
                } else
                {
                    uiState.update {
                        uiState.value.copy(
                            loadingPhoto = BookmarkUIState.EMPTY(
                                bookmark
                            )
                        )
                    }
                }
            }
            catch (e: Exception) {
                uiState.update { uiState.value.copy(loadingPhoto = BookmarkUIState.ERROR(e.localizedMessage)) }
            }
        }
    }
}