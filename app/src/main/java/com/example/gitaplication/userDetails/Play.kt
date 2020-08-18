package com.example.gitaplication.userDetails

import com.example.gitaplication.account.AccountManager
import com.example.gitaplication.models.Repo
import com.example.gitaplication.models.User
import com.example.gitaplication.models.UserList
import com.multiplatform.play.Action
import com.multiplatform.play.Actor
import com.multiplatform.play.React
import com.multiplatform.play.StateTransformer
import com.multiplatform.util.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class UserDetailsState(
    val user: User,
    val repos: List<Repo>? = null,
    val followers: List<UserList>? = null,
    val following: List<UserList>? = null,
    val isItFetching: Boolean = false
)

sealed class UserDetailsAction : Action {

    object FetchRepos : UserDetailsAction() {

        sealed class Reaction : com.multiplatform.play.Reaction {

            class Success(val repos: List<Repo>) : Reaction()

            class Error(val error: Throwable) : Reaction()

        }
    }

    object FetchFollowers : UserDetailsAction() {

        sealed class Reaction : com.multiplatform.play.Reaction {

            class Success(val followers: List<UserList>) : Reaction()

            class Error(val error: Throwable) : Reaction()
        }
    }

    object FetchFollowing : UserDetailsAction() {

        sealed class Reaction : com.multiplatform.play.Reaction {

            class Success(val following: List<UserList>) : Reaction()

            class Error(val error: Throwable) : Reaction()

        }
    }

    object StopFetching: UserDetailsAction(){
        sealed class Reaction : com.multiplatform.play.Reaction {
            object Success : Reaction()
        }
    }

    object Logout : UserDetailsAction()

}

object UserDetailsStateTransformer : StateTransformer<UserDetailsState> {

    override fun invoke(state: UserDetailsState, action: Action): UserDetailsState = when (action) {
        is UserDetailsAction.FetchRepos -> state.copy(
            isItFetching = true
        )
        is UserDetailsAction.FetchFollowers -> state.copy(
            isItFetching = true
        )
        is UserDetailsAction.FetchFollowing -> state.copy(
            isItFetching = true
        )
        is UserDetailsAction.StopFetching.Reaction.Success->state.copy(
            isItFetching = false
        )
        is UserDetailsAction.FetchRepos.Reaction.Error -> state.copy(
            isItFetching = false
        )
        is UserDetailsAction.FetchFollowers.Reaction.Error -> state.copy(
            isItFetching = false
        )
        is UserDetailsAction.FetchFollowing.Reaction.Error -> state.copy(
            isItFetching = false
        )
        is UserDetailsAction.FetchRepos.Reaction.Success -> state.copy(
            isItFetching = false,
            repos = action.repos
        )
        is UserDetailsAction.FetchFollowers.Reaction.Success -> state.copy(
            isItFetching = false,
            followers = action.followers
        )
        is UserDetailsAction.FetchFollowing.Reaction.Success -> state.copy(
            isItFetching = false,
            following = action.following
        )
        else -> state
    }

}

class UserDetailsActor(
    private val scope: CoroutineScope,

    private val fetchReposUseCase: FetchReposUseCase,

    private val fetchFollowingUseCase: FetchFollowingUseCase,

    private val fetchFollowersUseCase: FetchFollowersUseCase,

    private val accountManager: AccountManager

) : Actor<UserDetailsState> {

    override fun invoke(action: Action, state: UserDetailsState, react: React) {

        when (action) {

            is UserDetailsAction.Logout -> {
                accountManager.deleteSavedAccount()
            }

            is UserDetailsAction.FetchRepos -> {
                scope.launch(Dispatchers.Main) {

                    when (val result = fetchReposUseCase(state.user.username)) {
                        is Result.Success -> react(UserDetailsAction.FetchRepos.Reaction.Success(result.data))
                        is Result.Error -> react(UserDetailsAction.FetchRepos.Reaction.Error(result.error))
                    }
                }
            }

            is UserDetailsAction.FetchFollowers -> {

                if (state.followers == null) {
                    scope.launch(Dispatchers.Main) {

                        when (val result = fetchFollowersUseCase(state.user.username)) {
                            is Result.Success -> react(UserDetailsAction.FetchFollowers.Reaction.Success(result.data))
                            is Result.Error -> react(UserDetailsAction.FetchFollowers.Reaction.Error(result.error))
                        }
                    }
                }
                react(UserDetailsAction.StopFetching.Reaction.Success)
            }

            is UserDetailsAction.FetchFollowing -> {

                if (state.following == null) {
                    scope.launch(Dispatchers.Main) {

                        when (val result = fetchFollowingUseCase(state.user.username)) {
                            is Result.Success -> react(UserDetailsAction.FetchFollowing.Reaction.Success(result.data))
                            is Result.Error -> react(UserDetailsAction.FetchFollowing.Reaction.Error(result.error))
                        }
                    }
                }
                react(UserDetailsAction.StopFetching.Reaction.Success)
            }

        }
    }
}
