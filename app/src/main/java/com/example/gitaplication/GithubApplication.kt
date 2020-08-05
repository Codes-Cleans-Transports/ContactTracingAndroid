package com.example.gitaplication

import android.app.Application
import com.example.gitaplication.di.applicationModule
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.bind
import org.kodein.di.singleton

class GithubApplication : Application(), DIAware {

    override val di: DI = DI.lazy {
        bind<Application>() with singleton { this@GithubApplication }
        import(applicationModule)
    }

    override fun onCreate() {
        super.onCreate()


    }
}