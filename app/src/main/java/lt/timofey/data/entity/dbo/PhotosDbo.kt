package lt.timofey.data.entity.dbo

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import lt.timofey.data.entity.dto.PhotosDto
import lt.timofey.data.entity.dto.SrcDto


@Entity(tableName = "bookmark_photos")
data class PhotosDbo(
    @PrimaryKey val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String,
    val photographer_url: String,
    val photographer_id: Int,
    val srcId: Int,
    val alt: String
)

@Entity(tableName = "src_photos")
data class SrcDbo(
    @PrimaryKey val photoId: Int,
    val original: String,
    val large2x: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String
)

data class PhotoSrcDbo(
    @Embedded val photo: PhotosDbo,
    @Relation(
        parentColumn = "id",
        entityColumn = "srcId"
    )
    val src: SrcDbo
)

fun PhotoSrcDbo.toPhotosDto() = PhotosDto(
    id = photo.id,
    width = photo.width,
    height = photo.height,
    url = photo.url,
    photographer = photo.photographer,
    photographer_id = photo.photographer_id,
    photographer_url = photo.photographer_url,
    src = src.toSrcDto(),
    alt = photo.alt
)

fun SrcDbo.toSrcDto() = SrcDto(
    original = original,
    large2x = large2x,
    large = large,
    medium = medium,
    small = small,
    portrait = portrait,
    landscape = landscape,
    tiny = tiny
)
