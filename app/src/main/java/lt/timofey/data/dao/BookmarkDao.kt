package lt.timofey.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import lt.timofey.data.entity.dbo.PhotosDbo

@Dao
interface BookmarkDao {
    @Query("select * from bookmark_photos")
    suspend fun selectBookmarkPhotos(): List<PhotosDbo>

    @Insert
    suspend fun savePhoto(photosDbo: PhotosDbo)

    @Query("delete from bookmark_photos where id = :pId")
    suspend fun deletePhoto(pId: Int)

    @Query("select * from bookmark_photos where id = :pId")
    suspend fun selectPhotoById(pId: Int) : Flow<PhotosDbo>
}