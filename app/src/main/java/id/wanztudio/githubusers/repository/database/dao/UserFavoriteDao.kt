package id.wanztudio.githubusers.repository.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import id.wanztudio.githubusers.repository.database.entity.UserFavoriteEntity
import kotlinx.coroutines.flow.Flow

/**
 * @author Ridwan Ismail  (iwanz@pm.me)
 * @version Source, v 0.1 26/12/2021 by Ridwan Ismail
 */
@Dao
interface UserFavoriteDao {

    @Query("SELECT * FROM favorite_user_table")
    fun fetchAllUsers() : LiveData<List<UserFavoriteEntity>>

    @Query("SELECT * FROM favorite_user_table WHERE username = :userName")
    fun getFavByUsername(userName: String) : LiveData<List<UserFavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUserToFavoriteDB(userEntity: UserFavoriteEntity)

    @Delete
    fun deleteUserFromFavoriteDB(userEntity: UserFavoriteEntity)
}