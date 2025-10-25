package com.example.photorating.data

import androidx.room.*

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val username: String,
    val isFollowing: Boolean,
    val lastUpdated: Long = System.currentTimeMillis()
)

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun getUser(username: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("UPDATE users SET isFollowing = :isFollowing WHERE username = :username")
    suspend fun updateFollowStatus(username: String, isFollowing: Boolean)
}

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}