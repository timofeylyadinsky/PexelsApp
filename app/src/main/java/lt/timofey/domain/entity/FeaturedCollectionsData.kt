package lt.timofey.domain.entity

data class FeaturedCollectionsData(
    val featuredCollections: List<FeaturedCollections> = listOf(),
    val errorMessage: String? = null
)
