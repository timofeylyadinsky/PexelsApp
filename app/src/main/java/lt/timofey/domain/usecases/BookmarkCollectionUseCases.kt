package lt.timofey.domain.usecases

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import lt.timofey.data.repository.impl.BookmarkRepositoryImpl
import lt.timofey.domain.entity.Photos
import lt.timofey.domain.entity.toPhotosDbo
import javax.inject.Inject

class savePhotoToBookmarkUseCase @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val bookmarkRepositoryImpl: BookmarkRepositoryImpl
) {
    suspend operator fun invoke(photos: Photos) = withContext(ioDispatcher) {
        bookmarkRepositoryImpl.savePhoto(photos.toPhotosDbo())
    }
}

class isPhotoSavedUseCase @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val bookmarkRepositoryImpl: BookmarkRepositoryImpl
) {
    suspend operator fun invoke(photos: Photos) = withContext(ioDispatcher){
        bookmarkRepositoryImpl.getSavedPhotoById(photos.id) != null
    }
}

class deletePhotosFromBookmarkUseCase @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val bookmarkRepositoryImpl: BookmarkRepositoryImpl
) {
    suspend operator fun invoke(photos: Photos) = withContext(ioDispatcher){
        bookmarkRepositoryImpl.deletePhoto(photos.toPhotosDbo())
    }
}