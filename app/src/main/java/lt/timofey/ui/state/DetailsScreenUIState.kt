package lt.timofey.ui.state

import lt.timofey.domain.entity.FeaturedCollections
import lt.timofey.domain.entity.Photos

data class DetailsScreenUIState(
    var isSaved: Boolean = false,
    var id: Int = 0,
    var loadingPhoto:LoadingPhotoUIState = LoadingPhotoUIState.LOADING,
    var photo: Photos? = null
)

sealed class LoadingPhotoUIState {
    data object LOADING : LoadingPhotoUIState()
    data class SUCCESS(val photos: Photos) : LoadingPhotoUIState()
    data class ERROR(val message: String) : LoadingPhotoUIState()
}