package com.example.gitaplication.di

import org.kodein.di.DI
import org.kodein.di.with

val constantModule = DI.Module("constantModule") {

    constant(tag = "URL") with "http://901f54fc5cc8.ngrok.io/"
}