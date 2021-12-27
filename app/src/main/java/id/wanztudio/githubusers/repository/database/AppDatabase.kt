package id.wanztudio.githubusers.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import id.wanztudio.githubusers.repository.database.dao.UserFavoriteDao
import id.wanztudio.githubusers.repository.database.entity.UserFavoriteEntity

/**
 * @author Ridwan Ismail  (iwanz@pm.me)
 * @version Source, v 0.1 26/12/2021 by Ridwan Ismail
 */

/**
 * App Database
 * Define all entities and access DAO's here/ Each entity is a table.
 */
@Database(entities = [
    UserFavoriteEntity::class,
], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Get DAO's
     */

    abstract fun userFavoriteDao(): UserFavoriteDao

    companion object {
        const val DATABASE_NAME = "github_users.db"
    }
}