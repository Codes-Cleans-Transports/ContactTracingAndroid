package com.example.gitaplication.test

import com.example.gitaplication.login.LoginAction
import com.example.gitaplication.login.LoginActor
import com.example.gitaplication.login.LoginState
import com.example.gitaplication.login.LoginStateTransformer
import com.example.gitaplication.login.di.loginModule
import com.example.gitaplication.login.useCases.AutoLoginUseCase
import com.example.gitaplication.login.useCases.LoginUseCase
import com.example.gitaplication.mocks.testModule
import com.example.gitaplication.mocks.testingUser
import com.multiplatform.play.Scene
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

class LoginTest() : DIAware {
    override val di: DI = DI.lazy {
        import(testModule)

        import(loginModule)
    }

    private val autoLoginUseCase: AutoLoginUseCase by instance()

    private val loginUseCase: LoginUseCase by instance()


    @ExperimentalCoroutinesApi
    @Test
    fun onAutoLoginSuccess() = runBlocking {

        Dispatchers.setMain(TestCoroutineDispatcher())

        val scene = Scene(
            initialState = LoginState(),

            stateTransformer = LoginStateTransformer,

            actor = LoginActor(
                scope = GlobalScope,
                autoLoginUseCase = autoLoginUseCase,
                loginUseCase = loginUseCase
            )
        )

        val states = mutableListOf<LoginState>()
        scene.subscribe {
            states.add(it)
        }

        scene.dispatch(LoginAction.AutoLogin)

        delay(100)

        assertTrue(states[1].loggedInUser == testingUser)

    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}