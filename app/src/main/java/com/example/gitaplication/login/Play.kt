package com.example.gitaplication.login

import com.example.gitaplication.login.useCases.AutoLoginUseCase
import com.example.gitaplication.login.useCases.LoginUseCase
import com.example.gitaplication.models.User
import com.multiplatform.play.Action
import com.multiplatform.play.Actor
import com.multiplatform.play.React
import com.multiplatform.play.StateTransformer
import com.multiplatform.util.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/* State */
data class LoginState(
    val isLoggingIn: Boolean = false,
    val loggedInUser: User? = null
)
/* Actions */

sealed class LoginAction : Action {

    class Login(val username: String) : LoginAction() {

        sealed class Reaction : com.multiplatform.play.Reaction {

            class LoggedIn(val user: User) : Reaction()

            class Failed(val error: Throwable) : Reaction()

        }
    }

    object AutoLogin : LoginAction() {

        sealed class Reaction : com.multiplatform.play.Reaction {

            class LoggedIn(val user: User) : Reaction()

            class Failed(val error: Throwable) : Reaction()

        }
    }
}

/* State transformer */

object LoginStateTransformer : StateTransformer<LoginState> {

    override fun invoke(state: LoginState, action: Action): LoginState = when (action) {

        is LoginAction.Login -> state.copy(
            isLoggingIn = true
        )

        is LoginAction.Login.Reaction.LoggedIn -> state.copy(
            isLoggingIn = false,
            loggedInUser = action.user
        )

        is LoginAction.Login.Reaction.Failed -> state.copy(
            isLoggingIn = false
        )

        else -> state
    }
}

/* Actor */

class LoginActor(
    private val scope: CoroutineScope,

    private val loginUseCase: LoginUseCase,

    private val autoLoginUseCase: AutoLoginUseCase
) : Actor<LoginState> {

    override fun invoke(action: Action, state: LoginState, react: React) {

        when (action) {

            is LoginAction.AutoLogin->{
                scope.launch(Dispatchers.Main) {
                    when (val result = autoLoginUseCase()) {
                        is Result.Success -> react(LoginAction.Login.Reaction.LoggedIn(result.data))
                        is Result.Error -> react(LoginAction.Login.Reaction.Failed(result.error))
                    }
                }
            }

            is LoginAction.Login -> {
                scope.launch(Dispatchers.Main) {
                    when (val result = loginUseCase(action.username)) {
                        is Result.Success -> react(LoginAction.Login.Reaction.LoggedIn(result.data))
                        is Result.Error -> react(LoginAction.Login.Reaction.Failed(result.error))
                    }
                }
            }
        }
    }
}