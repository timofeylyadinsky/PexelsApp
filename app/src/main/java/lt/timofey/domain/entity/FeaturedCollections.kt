package lt.timofey.domain.entity

import lt.timofey.data.entity.dto.CollectionsDto
import lt.timofey.data.entity.dto.FeaturedCollectionsDto

data class FeaturedCollections(
    val collections: List<Collections>
)

data class Collections(
    val id: Int,
    val title: String
)

fun FeaturedCollectionsDto.toFeaturedCollections() = FeaturedCollections(
    collections = collections.map { it.toCollections() }
)

fun CollectionsDto.toCollections() = Collections(
    id = id,
    title = title
)