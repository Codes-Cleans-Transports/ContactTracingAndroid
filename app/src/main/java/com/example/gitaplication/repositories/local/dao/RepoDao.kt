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
            WHERE user_username = :login
        """
    )
    suspend fun getRepos(login: String): List<RepoEntity>

    @Query(
        """
            DELETE FROM repos
            WHERE user_username = :login
        """
    )
    suspend fun deleteRepos(login: String)
}