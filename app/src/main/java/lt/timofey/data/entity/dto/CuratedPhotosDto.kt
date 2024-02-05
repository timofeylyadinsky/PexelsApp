package lt.timofey.data.entity.dto

data class CuratedPhotosDto(
    val photos: List<PhotosDto>
)

data class PhotosDto(
    val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String,
    val photographer_url: String,
    val photographer_id: Int,
    val src: SrcDto,
    val alt: String
)

data class SrcDto(
    val original: String,
    val large2x: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String
)
