package com.example.gitaplication.userDetails

import com.example.gitaplication.models.Data
import com.example.gitaplication.models.User
import com.example.gitaplication.models.UserList
import com.multiplatform.play.*
import com.multiplatform.util.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class UserDetailsState(
    val user: User,
    val repos: List<Data>? = null,
    val followers: List<UserList>? = null,
    val following: List<UserList>? = null
)

sealed class UserDetailsAction : Action {

    class FetchUser(val username: String) : UserDetailsAction() {

        sealed class Reaction : com.multiplatform.play.Reaction {

            class Success(val user: User) : Reaction()

            class Error(val error: Throwable) : Reaction()

        }
    }
    class FetchRepos(val username: String) : UserDetailsAction() {

        sealed class Reaction : com.multiplatform.play.Reaction {

            class Success(val repos: List<Data>) : Reaction()

            class Error(val error: Throwable) : Reaction()

        }
    }
    class FetchFollowers(val username: String) : UserDetailsAction() {

        sealed class Reaction : com.multiplatform.play.Reaction {

            class Success(val followers: List<UserList>) : Reaction()

            class Error(val error: Throwable) : Reaction()
        }
    }

    class FetchFollowing(val username: String) : UserDetailsAction() {

        sealed class Reaction : com.multiplatform.play.Reaction {

            class Success(val following: List<UserList>) : Reaction()

            class Error(val error: Throwable) : Reaction()

        }
    }
}

object UserDetailsStateTransformer: StateTransformer<UserDetailsState>{
    override fun invoke(state: UserDetailsState, action: Action): UserDetailsState = when(action){
        is UserDetailsAction.FetchRepos.Reaction.Success ->state.copy(
            repos = action.repos
        )
        is UserDetailsAction.FetchFollowers.Reaction.Success ->state.copy(
            followers = action.followers
        )
        is UserDetailsAction.FetchFollowing.Reaction.Success ->state.copy(
            following = action.following
        )
        is UserDetailsAction.FetchUser.Reaction.Success ->state.copy(
            user = action.user
        )
       else -> state
    }

}

class UserDetailsActor(
    private val scope: CoroutineScope,

    private val fetchReposUseCase: FetchReposUseCase,

    private val fetchFollowingUseCase: FetchFollowingUseCase,

    private val fetchFollowersUseCase: FetchFollowersUseCase,

    private val fetchUserUseCase: FetchUserUseCase
) : Actor<UserDetailsState> {

    override fun invoke(action: Action, state: UserDetailsState, react: React) {

        when (action) {

            is UserDetailsAction.FetchRepos-> {

                scope.launch(Dispatchers.Main) {

                    when (val result = fetchReposUseCase(action.username)) {
                        is Result.Success -> react(UserDetailsAction.FetchRepos.Reaction.Success(result.data))
                        is Result.Error -> react(UserDetailsAction.FetchRepos.Reaction.Error(result.error))
                    }
                }
            }

            is UserDetailsAction.FetchFollowers-> {

                scope.launch(Dispatchers.Main) {

                    when (val result = fetchFollowersUseCase(action.username)) {
                        is Result.Success -> react(UserDetailsAction.FetchFollowers.Reaction.Success(result.data))
                        is Result.Error -> react(UserDetailsAction.FetchFollowers.Reaction.Error(result.error))
                    }
                }
            }

            is UserDetailsAction.FetchFollowing-> {

                scope.launch(Dispatchers.Main) {

                    when (val result = fetchFollowingUseCase(action.username)) {
                        is Result.Success -> react(UserDetailsAction.FetchFollowing.Reaction.Success(result.data))
                        is Result.Error -> react(UserDetailsAction.FetchFollowing.Reaction.Error(result.error))
                    }
                }
            }

            is UserDetailsAction.FetchUser-> {

                scope.launch(Dispatchers.Main) {

                    when (val result = fetchUserUseCase(action.username)) {
                        is Result.Success -> react(UserDetailsAction.FetchUser.Reaction.Success(result.data))
                        is Result.Error -> react(UserDetailsAction.FetchUser.Reaction.Error(result.error))
                    }
                }
            }
        }
    }
}
