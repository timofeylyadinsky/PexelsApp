package lt.timofey.ui.state

import lt.timofey.domain.entity.CuratedPhotos
import lt.timofey.domain.entity.FeaturedCollections

sealed class HomeScreenUIState {
    private var isSearched: Boolean = false

}

sealed class FeaturedCollectionsUIState {
    data object LOADING : FeaturedCollectionsUIState()
    data class SUCCESS(val featuredCollections: FeaturedCollections) : FeaturedCollectionsUIState()
    data class ERROR(val message: String) :FeaturedCollectionsUIState()
}

sealed class CuratedPhotosUIState {
    data object LOADING : CuratedPhotosUIState()
    data class SUCCESS(val curatedPhotos: CuratedPhotos) : CuratedPhotosUIState()
    data class ERROR(val message: String) : CuratedPhotosUIState()
}

