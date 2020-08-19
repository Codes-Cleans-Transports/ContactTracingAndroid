package com.example.gitaplication.di

import com.example.gitaplication.repositories.CachingRepository
import com.example.gitaplication.repositories.Repository
import com.example.gitaplication.repositories.local.LocalRepository
import com.example.gitaplication.repositories.local.RoomImplementation
import com.example.gitaplication.repositories.rest.RemoteRepository
import com.example.gitaplication.repositories.rest.RestRepository
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val repositoryModule = DI.Module("ModelModule") {

    bind<LocalRepository>() with singleton { RoomImplementation(instance()) }

    bind<RestRepository>() with singleton {
        RestRepository(
            instance()
        )
    }

    bind<RemoteRepository>() with singleton { instance<RestRepository>() }

    bind<CachingRepository>() with singleton {
        CachingRepository(
            instance(),
            instance()
        )
    }

    bind<Repository>() with singleton { instance<CachingRepository>() }
}
