package com.example.gitaplication.di

import androidx.room.Room
import com.example.gitaplication.repositories.local.RoomDB
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val roomModule = DI.Module("RoomModule") {

    bind<RoomDB>() with singleton {
        Room.databaseBuilder(
            instance(),
            RoomDB::class.java,
            " RoomDB" // TODO review2: remove the empty space at the beginning of the DB name
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}