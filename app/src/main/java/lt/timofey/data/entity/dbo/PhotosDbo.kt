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
    @Embedded val src: SrcDbo,
    val alt: String
)

@Entity(tableName = "src_photos")
data class SrcDbo(
    val original: String,
    val large2x: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String
)

//data class PhotoSrcDbo(
//    @Embedded val photo: PhotosDbo,
//    @Relation(
//        parentColumn = "id",
//        entityColumn = "srcId"
//    )
//    val src: SrcDbo
//)

fun PhotosDbo.toPhotosDto() = PhotosDto(
    id = id,
    width = width,
    height = height,
    url = url,
    photographer = photographer,
    photographer_id = photographer_id,
    photographer_url = photographer_url,
    src = src.toSrcDto(),
    alt = alt
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
