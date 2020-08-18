package com.example.gitaplication.userDetails.di

import com.example.gitaplication.account.AccountManager
import com.example.gitaplication.userDetails.useCases.*
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider

val fetchModule = DI.Module("FetchModule") {

    bind<FetchReposUseCase>() with provider { FetchReposUseCase(instance()) }

    bind<FetchFollowingUseCase>() with provider { FetchFollowingUseCase(instance()) }

    bind<FetchFollowersUseCase>() with provider {
        FetchFollowersUseCase(
            instance()
        )
    }

    bind<LogoutUseCase>() with provider {
        LogoutUseCase(instance())
    }

    bind<FetchUserUseCase>() with provider { FetchUserUseCase(instance()) }
}