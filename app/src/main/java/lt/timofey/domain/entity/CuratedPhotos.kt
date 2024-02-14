package lt.timofey.domain.entity

import lt.timofey.data.entity.dbo.PhotosDbo
import lt.timofey.data.entity.dbo.SrcDbo
import lt.timofey.data.entity.dto.CuratedPhotosDto
import lt.timofey.data.entity.dto.PhotosDto
import lt.timofey.data.entity.dto.SrcDto

data class CuratedPhotos(
    val photos: List<Photos>
)

data class Photos(
    val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String,
    val photographer_url: String,
    val photographer_id: Int,
    val src: Src,
    val alt: String
)

data class Src(
    val original: String,
    val large2x: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String
)

fun CuratedPhotosDto.toCuratedPhotos() = CuratedPhotos(
    photos = photos.map { it.toPhotos() }
)

fun PhotosDto.toPhotos() = Photos(
    id = id,
    width = width,
    height = height,
    url = url,
    photographer = photographer,
    photographer_url = photographer_url,
    photographer_id = photographer_id,
    src = src.toSrc(),
    alt = alt
)

fun SrcDto.toSrc() = Src(
    original = original,
    large2x = large2x,
    large = large,
    medium = medium,
    small = small,
    portrait = portrait,
    landscape = landscape,
    tiny = tiny
)

fun Src.toSrcDbo() = SrcDbo(
    original = original,
    large2x = large2x,
    large = large,
    medium = medium,
    small = small,
    portrait = portrait,
    landscape = landscape,
    tiny = tiny
)

fun Photos.toPhotosDbo() = PhotosDbo (
    id = id,
    width = width,
    height = height,
    url = url,
    photographer = photographer,
    photographer_url = photographer_url,
    photographer_id = photographer_id,
    src = src.toSrcDbo(),
    alt = alt
)