package com.example.gitaplication.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val persistentStorageModule = DI.Module("PersistentStorageModule") {

    bind<SharedPreferences>() with singleton {
        instance<Application>().getSharedPreferences(
            "github_connections_prefs",
            Context.MODE_PRIVATE
        )
    }
}