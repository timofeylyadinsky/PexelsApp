package lt.timofey.data.entity.dto

data class SearchPhotosDto(
    val total_results: Int,
    val photos: List<PhotosDto>
)
