package lt.timofey.domain.entity

data class SearchPhotosData(
    val curatedPhotos: List<SearchPhotos> = listOf(),
    val errorMessage: String? = null
)