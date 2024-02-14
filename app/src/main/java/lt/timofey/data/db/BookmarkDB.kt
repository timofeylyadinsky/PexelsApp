package lt.timofey.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import lt.timofey.data.dao.BookmarkDao
import lt.timofey.data.entity.dbo.PhotosDbo

@Database(entities = [PhotosDbo::class], version = 1)
abstract class BookmarkDB : RoomDatabase() {
    abstract fun bookmarkDao() : BookmarkDao
}