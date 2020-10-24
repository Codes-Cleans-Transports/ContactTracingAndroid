package com.example.gitaplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.example.gitaplication.firstScreen.MainController

class MainActivity : AppCompatActivity() {

    private lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        router = Conductor.attachRouter(this, findViewById(R.id.routerContainer), savedInstanceState)

        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(MainController()))
        }
    }
}