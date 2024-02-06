package lt.timofey.domain.usecases

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import lt.timofey.data.api.NetworkResult
import lt.timofey.data.repository.impl.PhotosRepositoryImpl
import lt.timofey.data.repository.module.IoDispatcher
import lt.timofey.domain.entity.FeaturedCollectionsData
import lt.timofey.domain.entity.toFeaturedCollections
import javax.inject.Inject

class getFeaturedCollectionsUseCase @Inject constructor(
    private val photosRepositoryImpl: PhotosRepositoryImpl,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke() : Flow<FeaturedCollectionsData> = withContext(ioDispatcher) {
        flow{
            when (val response = photosRepositoryImpl.getFeaturedCollections()) {
                is NetworkResult.Success -> emit(FeaturedCollectionsData(listOf(response.data.toFeaturedCollections())))
                is NetworkResult.Error -> emit(FeaturedCollectionsData(errorMessage = "${response.code} : ${response.message}"))
            }
        }
    }
}