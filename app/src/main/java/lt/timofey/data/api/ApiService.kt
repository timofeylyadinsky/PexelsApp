package lt.timofey.data.api

import lt.timofey.data.entity.dto.CuratedPhotosDto
import lt.timofey.data.entity.dto.FeaturedCollectionsDto
import lt.timofey.data.entity.dto.PhotosDto
import lt.timofey.data.entity.dto.SearchPhotosDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


val BASE_URL = "https://api.pexels.com/v1/"

interface ApiService {
    @GET("collections/featured")
    suspend fun getFeaturedCollections(): Response<FeaturedCollectionsDto>

    @GET("curated")
    suspend fun getCuratedPhotos(): Response<CuratedPhotosDto>

    @GET("search")
    suspend fun searchForPhotos(@Query("query") query: String = ""): Response<SearchPhotosDto>

    @GET("photos/{id}")
    suspend fun fetchPhotoById(@Path("id") id: Int) : Response<PhotosDto>
}