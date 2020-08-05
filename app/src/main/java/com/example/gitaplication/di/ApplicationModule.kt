package com.example.gitaplication.di

import org.kodein.di.DI

val applicationModule = DI.Module("applicationModule") {

    import(httpModule)

    import(repositoryModule)
}