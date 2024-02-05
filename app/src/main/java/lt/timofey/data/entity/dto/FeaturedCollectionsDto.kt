package lt.timofey.data.entity.dto

data class FeaturedCollectionsDto(
    val collections: List<CollectionsDto>
)

data class CollectionsDto(
    val id: Int,
    val title: String
)
