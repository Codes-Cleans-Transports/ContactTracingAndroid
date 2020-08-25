package com.example.gitaplication.userDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RouterTransaction
import com.example.gitaplication.R
import com.example.gitaplication.login.LoginController
import com.example.gitaplication.models.User
import com.example.gitaplication.userDetails.di.userDetailsModule
import com.example.gitaplication.userDetails.useCases.FetchFollowersUseCase
import com.example.gitaplication.userDetails.useCases.FetchFollowingUseCase
import com.example.gitaplication.userDetails.useCases.FetchReposUseCase
import com.example.gitaplication.userDetails.useCases.LogoutUseCase
import com.multiplatform.play.Action
import com.multiplatform.play.Scene
import kotlinx.coroutines.GlobalScope
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

class UserDetailsController(bundle: Bundle) : Controller(), DIAware {

    override val di: DI = DI.lazy {
        extend((applicationContext as DIAware).di)

        import(userDetailsModule)
    }

    private val user: User = bundle.get("user") as User?
        ?: throw IllegalStateException("Missing user in ${javaClass.simpleName}'s bundle")

    private lateinit var userDetailsView: UserDetailsView

    private lateinit var scene: Scene<UserDetailsState>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {

        val view = inflater.inflate(R.layout.controller_userdetails, container, false)

        val fetchFollowersUseCase: FetchFollowersUseCase by instance()

        val fetchFollowingUseCase: FetchFollowingUseCase by instance()

        val fetchReposUseCase: FetchReposUseCase by instance()

        val logoutUseCase: LogoutUseCase by instance()

        userDetailsView = view.findViewById(R.id.userDetails_view)

        scene = Scene(
            initialState = UserDetailsState(user = user),
            stateTransformer = UserDetailsStateTransformer,
            actor = UserDetailsActor(
                scope = GlobalScope,
                fetchFollowersUseCase = fetchFollowersUseCase,
                fetchReposUseCase = fetchReposUseCase,
                fetchFollowingUseCase = fetchFollowingUseCase,
                logoutUseCase = logoutUseCase
            ),
            spectators = listOf(
                ::navigationSpectator,
                ::errorHandlingSpectator
            )
        )

        scene.dispatch(UserDetailsAction.FetchRepos)

        userDetailsView.init(scene)

        return view
    }

    private fun navigationSpectator(action: Action, state: UserDetailsState): Boolean {

        if (action is UserDetailsAction.Logout) {
            router.setRoot(
                RouterTransaction.with(
                    LoginController()
                )
            )
        }

        return false
    }

    private fun errorHandlingSpectator(action: Action, state: UserDetailsState): Boolean {

        if (action is UserDetailsAction.FetchRepos.Reaction.Error) {
            Toast.makeText(userDetailsView.context, action.error.message.toString(), Toast.LENGTH_SHORT).show()
            when (action.error) { // TODO review2: what is the purpose of this construct? The same applies to the other ones in the method.
                else -> Log.e("Fetch Repos", "Message: ${action.error.localizedMessage}")
            }
        }

        if (action is UserDetailsAction.FetchFollowing.Reaction.Error) {
            Toast.makeText(userDetailsView.context, action.error.message.toString(), Toast.LENGTH_SHORT).show()
            when (action.error) {
                else -> Log.e("Fetch Following", "Message: ${action.error.localizedMessage}")
            }
        }

        if (action is UserDetailsAction.FetchFollowers.Reaction.Error) {
            Toast.makeText(userDetailsView.context, action.error.message.toString(), Toast.LENGTH_SHORT).show()
            when (action.error) {
                else -> Log.e("Fetch Followers", "Message: ${action.error.localizedMessage}")
            }
        }

        return false
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)

        savedInstanceState.putParcelable("user", user)
    }
}