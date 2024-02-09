package lt.timofey.domain.entity

import lt.timofey.data.entity.dto.SearchPhotosDto

data class SearchPhotos(
    val total_results: Int,
    val photos: List<Photos>
)

fun SearchPhotosDto.toSearchPhotos() = SearchPhotos(
    total_results = total_results,
    photos = photos.map { it.toPhotos() }
)

fun SearchPhotos.toCuratedPhotos() = CuratedPhotos(
    photos = photos
)