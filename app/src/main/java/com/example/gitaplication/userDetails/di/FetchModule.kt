package com.example.gitaplication.userDetails.di

import com.example.gitaplication.userDetails.FetchFollowersUseCase
import com.example.gitaplication.userDetails.FetchFollowingUseCase
import com.example.gitaplication.userDetails.FetchReposUseCase
import com.example.gitaplication.userDetails.FetchUserUseCase
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider

val fetchModule = DI.Module("FetchModule") {

    bind<FetchReposUseCase>() with provider { FetchReposUseCase(instance()) }

    bind<FetchFollowingUseCase>() with provider { FetchFollowingUseCase(instance()) }

    bind<FetchFollowersUseCase>() with provider { FetchFollowersUseCase(instance()) }

    bind<FetchUserUseCase>() with provider { FetchUserUseCase(instance()) }
}