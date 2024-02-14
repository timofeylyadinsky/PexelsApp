package lt.timofey.data.repository.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import lt.timofey.data.db.BookmarkDB
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun providesBookmarkDao(bookmarkDB: BookmarkDB) = bookmarkDB.bookmarkDao()

    @Provides
    @Singleton
    fun providesBookmarkDB(@ApplicationContext context: Context) = Room
        .databaseBuilder(context, BookmarkDB::class.java, "bookmark_photos")
        .build()
}