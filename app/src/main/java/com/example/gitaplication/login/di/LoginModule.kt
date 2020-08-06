package com.example.gitaplication.login.di

import com.example.gitaplication.login.useCases.AutoLoginUseCase
import com.example.gitaplication.login.useCases.LoginUseCase
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider

val loginModule = DI.Module("LoginModule") {

    bind<LoginUseCase>() with provider {
        LoginUseCase(
            instance(),
            instance()
        )
    }

    bind<AutoLoginUseCase>() with provider {
        AutoLoginUseCase(
            instance(),
            instance()
        )
    }
}