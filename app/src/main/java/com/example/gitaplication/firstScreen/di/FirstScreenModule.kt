package com.example.gitaplication.firstScreen.di

import com.example.gitaplication.firstScreen.useCases.LoadStatusUseCase
import com.example.gitaplication.firstScreen.useCases.SelfReportUseCase
import com.example.gitaplication.firstScreen.useCases.SendDataUseCase
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider

val firstScreenModule = DI.Module("firstScreenModule") {

    bind<SendDataUseCase>() with provider {
        SendDataUseCase(
            instance()
        )
    }

    bind<SelfReportUseCase>() with provider {
        SelfReportUseCase(
            instance()
        )
    }

    bind<LoadStatusUseCase>() with provider {
        LoadStatusUseCase(
            instance()
        )
    }
}