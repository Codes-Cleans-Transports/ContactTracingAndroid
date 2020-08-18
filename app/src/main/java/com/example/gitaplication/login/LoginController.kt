package com.example.gitaplication.login

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler
import com.example.gitaplication.R
import com.example.gitaplication.login.di.loginModule
import com.example.gitaplication.login.useCases.AutoLoginUseCase
import com.example.gitaplication.login.useCases.LoginUseCase
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

        val loginUseCase: LoginUseCase by instance()

        val autoLoginUseCase: AutoLoginUseCase by instance()

        loginView = view.findViewById(R.id.loginView)

        scene = Scene(
            initialState = LoginState(),

            stateTransformer = LoginStateTransformer,

            actor = LoginActor(
                scope = GlobalScope,
                autoLoginUseCase = autoLoginUseCase,
                loginUseCase = loginUseCase
            ),

            spectators = listOf(
                ::navigationSpectator,
                ::errorHandlingSpectator
            )
        )

        scene.dispatch(LoginAction.AutoLogin)

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
                ).popChangeHandler(HorizontalChangeHandler())
                    .pushChangeHandler(HorizontalChangeHandler())
            )
        }

        return false
    }

    private fun errorHandlingSpectator(action: Action, state: LoginState): Boolean {

        if (action is LoginAction.Login.Reaction.Failed) {
            Toast.makeText(loginView.context, action.error.message.toString(), Toast.LENGTH_SHORT).show()
            when (action.error) {
                else -> Log.e("Login", "Message: ${action.error.localizedMessage}")
            }
        }

        return false
    }
}
