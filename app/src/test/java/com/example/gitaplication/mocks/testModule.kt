package com.example.gitaplication.mocks

import com.example.gitaplication.account.AccountManager
import com.example.gitaplication.repositories.Repository
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

val testModule = DI.Module("Test Module"){
    bind<Repository>() with singleton { RepositoryMock() }
    bind<AccountManager>() with singleton { AuthenticatorMock() }
}