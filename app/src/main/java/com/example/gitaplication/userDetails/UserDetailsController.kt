package com.example.gitaplication.userDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.example.gitaplication.R
import com.example.gitaplication.login.LoginAction
import com.example.gitaplication.models.User
import com.example.gitaplication.userDetails.di.fetchModule
import com.multiplatform.play.Action
import com.multiplatform.play.Scene
import kotlinx.coroutines.GlobalScope
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

class UserDetailsController(bundle: Bundle) : Controller(), DIAware {

    override val di: DI = DI.lazy {
        extend((applicationContext as DIAware).di)

        import(fetchModule)
    }

    private val user: User = bundle.get("user") as User?
        ?: throw IllegalStateException("Missing user in ${javaClass.simpleName}'s bundle")

    private lateinit var userDetailsView: UserDetailsView

    private lateinit var scene: Scene<UserDetailsState>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {

        val view = inflater.inflate(R.layout.controller_userdetails, container, false)


        val fetchUserUseCase: FetchUserUseCase by instance()

        val fetchFollowersUseCase: FetchFollowersUseCase by instance()

        val fetchFollowingUseCase: FetchFollowingUseCase by instance()

        val fetchReposUseCase: FetchReposUseCase by instance()

        userDetailsView=view.findViewById(R.id.userDetails_view)

        scene = Scene(
            initialState = UserDetailsState(user = user),
            stateTransformer = UserDetailsStateTransformer,
            actor = UserDetailsActor(
                scope = GlobalScope,
                fetchFollowersUseCase = fetchFollowersUseCase,
                fetchReposUseCase = fetchReposUseCase,
                fetchFollowingUseCase = fetchFollowingUseCase,
                fetchUserUseCase = fetchUserUseCase
            ),
            spectators = listOf(
                ::navigationSpectator,
                ::errorHandlingSpectator
            )
        )


        scene.dispatch(UserDetailsAction.FetchRepos(user.username))
        scene.dispatch(UserDetailsAction.FetchFollowing(user.username))
        scene.dispatch(UserDetailsAction.FetchFollowers(user.username))

        userDetailsView.init(scene)

        return view
    }

    private fun navigationSpectator(action: Action, state: UserDetailsState): Boolean {

        return false
    }

    private fun errorHandlingSpectator(action: Action, state: UserDetailsState): Boolean {

        if (action is UserDetailsAction.FetchRepos.Reaction.Error) {
            when (action.error) {
                else -> Log.e("Fetch Repos", "Message: ${action.error.localizedMessage}")
            }
        }

        if (action is UserDetailsAction.FetchFollowing.Reaction.Error) {
            when (action.error) {
                else -> Log.e("Fetch Following", "Message: ${action.error.localizedMessage}")
            }
        }

        if (action is UserDetailsAction.FetchFollowing.Reaction.Error) {
            when (action.error) {
                else -> Log.e("Fetch Followers", "Message: ${action.error.localizedMessage}")
            }
        }

        return false
    }
}