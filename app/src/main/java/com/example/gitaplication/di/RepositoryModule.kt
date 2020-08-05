package com.example.gitaplication.di

import com.example.gitaplication.repositories.RestRepository
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val repositoryModule = DI.Module("ModelModule") {
    bind<RestRepository>() with singleton { RestRepository(instance()) }
}
