package com.example.gitaplication.test

import com.example.gitaplication.account.AccountManager
import com.example.gitaplication.mocks.*
import com.example.gitaplication.userDetails.*
import com.example.gitaplication.userDetails.di.fetchModule
import com.example.gitaplication.userDetails.useCases.FetchFollowersUseCase
import com.example.gitaplication.userDetails.useCases.FetchFollowingUseCase
import com.example.gitaplication.userDetails.useCases.FetchReposUseCase
import com.example.gitaplication.userDetails.useCases.LogoutUseCase
import com.multiplatform.play.Scene
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Test
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

class UserDetailsTest() : DIAware {
    override val di: DI = DI.lazy {
        import(testModule)

        import(fetchModule)
    }

    private val fetchFollowersUseCase: FetchFollowersUseCase by instance()

    private val fetchFollowingUseCase: FetchFollowingUseCase by instance()

    private val fetchReposUseCase: FetchReposUseCase by instance()

    private val logoutUseCase: LogoutUseCase by instance()

    @ExperimentalCoroutinesApi
    @Test
    fun onFetchUserDetails() = runBlocking {

        Dispatchers.setMain(TestCoroutineDispatcher())

        val scene = Scene(
            initialState = UserDetailsState(user = testingUser),

            stateTransformer = UserDetailsStateTransformer,

            actor = UserDetailsActor(
                scope = GlobalScope,
                fetchFollowersUseCase = fetchFollowersUseCase,
                fetchReposUseCase = fetchReposUseCase,
                fetchFollowingUseCase = fetchFollowingUseCase,
                logoutUseCase = logoutUseCase
            )
        )

        val states = mutableListOf<UserDetailsState>()
        scene.subscribe {
            states.add(it)
        }

        scene.dispatch(UserDetailsAction.FetchRepos)
        scene.dispatch(UserDetailsAction.FetchFollowing)
        scene.dispatch(UserDetailsAction.FetchFollowers)

        assertTrue(states[0].user == testingUser)
        assertTrue(states[1].isItFetching)
        delay(1000)
        assertEquals( states[2].repos, testingReposSorted)
        assertEquals( states[3].following, testingUserListItems)
        assertEquals( states[4].followers, testingUserListItems)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}