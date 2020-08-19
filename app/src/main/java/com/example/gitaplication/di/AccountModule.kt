package com.example.gitaplication.di

import com.example.gitaplication.account.AccountManager
import com.example.gitaplication.account.AccountManagerImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val accountModule = DI.Module("AccountModule") {
    bind<AccountManager>() with singleton { AccountManagerImpl(instance()) }
}
