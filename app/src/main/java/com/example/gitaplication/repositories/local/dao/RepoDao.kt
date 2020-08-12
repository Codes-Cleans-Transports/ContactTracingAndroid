package com.example.gitaplication.repositories.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gitaplication.repositories.table.RepoEntity

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepos(repos: List<RepoEntity>)

    @Query(
        """
            SELECT * FROM repos
            WHERE user_username = :username
        """
    )
    suspend fun getRepos(username: String): List<RepoEntity>

    @Query(
        """
            DELETE FROM repos
            WHERE user_username = :username
        """
    )
    suspend fun deleteRepos(username: String)
}