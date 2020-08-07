package com.example.gitaplication.repositories.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gitaplication.repositories.table.FollowJoinEntity
import com.example.gitaplication.repositories.table.UserEntity
import com.example.gitaplication.repositories.table.UserListItemEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIncompleteUsers(users: List<UserEntity>)

    @Query(
        """
            SELECT * FROM users
            WHERE username = :username
            COLLATE NOCASE
        """
    )
    suspend fun getUser(username: String): UserEntity?

    @Query(
        """
            SELECT username, avatar_url FROM users
            INNER JOIN follow_join
            ON username = follower_username
            WHERE followed_username = :username
        """
    )
    suspend fun getFollowers(username: String): List<UserListItemEntity>

    @Query(
        """
            SELECT username, avatar_url FROM users
            INNER JOIN follow_join  
            ON username = followed_username
            WHERE follower_username = :username
        """
    )
    suspend fun getFollowing(username: String): List<UserListItemEntity>

    @Query(
        """
            DELETE FROM repos
            WHERE user_username = :username
        """
    )
    suspend fun deleteRepos(username: String)

    @Query(
        """
            DELETE FROM follow_join
            WHERE followed_username = :username
        """
    )
    suspend fun deleteFollowers(username: String)

    @Query(
        """
            DELETE FROM follow_join
            WHERE follower_username = :username
        """
    )
    suspend fun deleteFollowing(username: String)


    @Query(
        """
            DELETE FROM users
            WHERE username = :username 
        """
    )
    suspend fun delete(username: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFollowPair(pairs: List<FollowJoinEntity>)
}