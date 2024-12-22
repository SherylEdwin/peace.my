package com.example.madgassignment

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

// Entity: User
@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val username: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val address: String,
    val address2: String,
    val postcode: String,
    val city: String,
    val state: String,
    val dob: String,
    val phone: String
)

// DAO: UserDao
@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    suspend fun authenticateUser(username: String, password: String): User?

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): User?

    @Query("UPDATE users SET password = :newPassword WHERE username = :username")
    suspend fun updateUserPassword(username: String, newPassword: String)

    // Search Users by username, first name, or last name (partial matching)
    @Query("SELECT * FROM users WHERE username LIKE :query OR firstName LIKE :query OR lastName LIKE :query")
    suspend fun searchUsers(query: String): List<User>
}

// Room Database: AppDatabase
@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
