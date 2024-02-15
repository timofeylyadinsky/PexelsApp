package lt.timofey.ui.state

import lt.timofey.domain.entity.Photos

data class BookmarkScreenUIState(
    var loadingPhoto: BookmarkUIState = BookmarkUIState.LOADING,
)

sealed class BookmarkUIState {
    data object LOADING : BookmarkUIState()
    data class SUCCESS(val photos: List<Photos>) : BookmarkUIState()
    data class ERROR(val message: String) : BookmarkUIState()
    data class EMPTY(val photos: List<Photos>) : BookmarkUIState()
}
