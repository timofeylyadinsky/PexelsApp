package lt.timofey.ui.state

import lt.timofey.domain.entity.CuratedPhotos
import lt.timofey.domain.entity.FeaturedCollections
import lt.timofey.domain.entity.SearchPhotos

sealed class HomeScreenUIState {
    private var loadingFeaturedCollections: FeaturedCollectionsUIState = FeaturedCollectionsUIState.LOADING
    private var loadingCuratedPhotos: CuratedPhotosUIState = CuratedPhotosUIState.LOADING
    private var loadingSearchedPhotos: SearchPhotosUIState = SearchPhotosUIState.WAIT
    private var isSearched: Boolean = false
    private var searchQuery: String = ""
}

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