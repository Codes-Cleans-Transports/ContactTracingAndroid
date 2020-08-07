package com.example.gitaplication.repositories.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gitaplication.repositories.local.dao.RepoDao
import com.example.gitaplication.repositories.local.dao.UserDao
import com.example.gitaplication.repositories.table.FollowJoinEntity
import com.example.gitaplication.repositories.table.RepoEntity
import com.example.gitaplication.repositories.table.UserEntity

@Database(
    entities = [UserEntity::class
        , FollowJoinEntity::class
        , RepoEntity::class]
    , version = 1
    , exportSchema = false
)
abstract class RoomDB : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun repoDao(): RepoDao
}