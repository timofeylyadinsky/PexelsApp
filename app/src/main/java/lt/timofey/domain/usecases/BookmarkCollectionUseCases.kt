package lt.timofey.domain.usecases

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import lt.timofey.data.repository.impl.BookmarkRepositoryImpl
import lt.timofey.data.repository.module.IoDispatcher
import lt.timofey.domain.entity.Photos
import lt.timofey.domain.entity.toPhotos
import lt.timofey.domain.entity.toPhotosDbo
import javax.inject.Inject

class savePhotoToBookmarkUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val bookmarkRepositoryImpl: BookmarkRepositoryImpl
) {
    suspend operator fun invoke(photos: Photos) = withContext(ioDispatcher) {
        bookmarkRepositoryImpl.savePhoto(photos.toPhotosDbo())
    }
}

class isPhotoSavedUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val bookmarkRepositoryImpl: BookmarkRepositoryImpl
) {
    suspend operator fun invoke(id: Int) = withContext(ioDispatcher) {
        bookmarkRepositoryImpl.getSavedPhotoById(id) != null
    }
}

class deletePhotosFromBookmarkUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val bookmarkRepositoryImpl: BookmarkRepositoryImpl
) {
    suspend operator fun invoke(photos: Photos) = withContext(ioDispatcher) {
        bookmarkRepositoryImpl.deletePhoto(photos.toPhotosDbo())
    }
}

class getPhotoFromDBUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val bookmarkRepositoryImpl: BookmarkRepositoryImpl
) {
    suspend operator fun invoke(id: Int) = withContext(ioDispatcher) {
        bookmarkRepositoryImpl.getSavedPhotoById(id)
    }
}

class getBookmarkCollectionsUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val bookmarkRepositoryImpl: BookmarkRepositoryImpl
) {
    suspend operator fun invoke() = withContext(ioDispatcher) {
        bookmarkRepositoryImpl.getSavedPhotos().map { it.toPhotos() }
    }
}