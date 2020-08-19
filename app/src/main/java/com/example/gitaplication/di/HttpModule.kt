package com.example.gitaplication.di

import com.example.gitaplication.repositories.rest.GitHubService
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val httpModule = DI.Module("HttpModule") {

    val interceptor = HttpLoggingInterceptor()
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

    bind<OkHttpClient>() with singleton {
        OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    bind<Retrofit>() with singleton {
        Retrofit.Builder()
            .baseUrl(instance<String>(tag = "URL"))
            .client(instance())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    bind<GitHubService>() with singleton {
        instance<Retrofit>().create(
            GitHubService::class.java
        )
    }

    bind<Gson>() with singleton { Gson() }

}
