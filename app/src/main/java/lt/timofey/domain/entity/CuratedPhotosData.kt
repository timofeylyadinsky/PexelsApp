package lt.timofey.domain.entity

data class CuratedPhotosData(
    val curatedPhotos: List<CuratedPhotos> = listOf(),
    val errorMessage: String? = null
)
