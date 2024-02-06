package lt.timofey.ui.state

import lt.timofey.domain.entity.CuratedPhotos
import lt.timofey.domain.entity.FeaturedCollections
import lt.timofey.domain.entity.SearchPhotos

data class HomeScreenUIState (
    var loadingFeaturedCollections: FeaturedCollectionsUIState = FeaturedCollectionsUIState.LOADING,
    var loadingCuratedPhotos: CuratedPhotosUIState = CuratedPhotosUIState.LOADING,
    var loadingSearchedPhotos: SearchPhotosUIState = SearchPhotosUIState.WAIT,
    var isSearched: Boolean = false,
    var searchQuery: String = ""
)

sealed class FeaturedCollectionsUIState {
    data object LOADING : FeaturedCollectionsUIState()
    data class SUCCESS(val featuredCollections: FeaturedCollections) : FeaturedCollectionsUIState()
    data class ERROR(val message: String) : FeaturedCollectionsUIState()
}

sealed class CuratedPhotosUIState {
    data object LOADING : CuratedPhotosUIState()
    data class SUCCESS(val curatedPhotos: CuratedPhotos) : CuratedPhotosUIState()
    data class ERROR(val message: String) : CuratedPhotosUIState()
}

sealed class SearchPhotosUIState {
    data object WAIT : SearchPhotosUIState()
    data object LOADING : SearchPhotosUIState()
    data class SUCCESS(val searchPhotos: SearchPhotos) : SearchPhotosUIState()
    data class ERROR(val message: String) : SearchPhotosUIState()
}