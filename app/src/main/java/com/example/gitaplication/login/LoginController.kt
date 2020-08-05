package com.example.gitaplication.login

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RouterTransaction
import com.example.gitaplication.R
import com.example.gitaplication.login.di.loginModule
import com.example.gitaplication.userDetails.UserDetailsController
import com.multiplatform.play.Action
import com.multiplatform.play.Scene
import kotlinx.coroutines.GlobalScope
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance


class LoginController : Controller(), DIAware {

    override val di: DI = DI.lazy {
        extend((applicationContext as DIAware).di)

        import(loginModule)
    }

    private lateinit var loginView: LoginView

    private lateinit var scene: Scene<LoginState>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {

        val view = inflater.inflate(R.layout.controller_login, container, false)

        val loginUseCase : LoginUseCase by instance()

        loginView = view.findViewById(R.id.loginView)

        scene = Scene(
            initialState = LoginState(),
            stateTransformer = LoginStateTransformer,
            actor = LoginActor(
                scope = GlobalScope,

                loginUseCase = loginUseCase
            ),
            spectators = listOf(
                ::navigationSpectator,
                ::errorHandlingSpectator
            )
        )

        loginView.init(scene)

        return view
    }

    private fun navigationSpectator(action: Action, state: LoginState): Boolean {

        if (action is LoginAction.Login.Reaction.LoggedIn) {
            router.setRoot(
                RouterTransaction.with(
                    UserDetailsController(
                        bundleOf("user" to action.user)
                    )
                )
            )
        }

        return false
    }

    private fun errorHandlingSpectator(action: Action, state: LoginState): Boolean {

        if (action is LoginAction.Login.Reaction.Failed) {
            when (action.error) {
                else -> Log.e("Login", "Message: ${action.error.localizedMessage}")
            }
        }

        return false
    }
}
