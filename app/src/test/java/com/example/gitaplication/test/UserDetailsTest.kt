package com.example.gitaplication.test

import com.example.gitaplication.mocks.testModule
import com.example.gitaplication.mocks.testingReposSorted
import com.example.gitaplication.mocks.testingUser
import com.example.gitaplication.mocks.testingUserListItems
import com.example.gitaplication.userDetails.UserDetailsAction
import com.example.gitaplication.userDetails.UserDetailsActor
import com.example.gitaplication.userDetails.UserDetailsState
import com.example.gitaplication.userDetails.UserDetailsStateTransformer
import com.example.gitaplication.userDetails.di.userDetailsModule
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
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

class UserDetailsTest() : DIAware {
    override val di: DI = DI.lazy {
        import(testModule)

        import(userDetailsModule)
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
        delay(1000)
        scene.dispatch(UserDetailsAction.FetchFollowing)
        delay(1000)
        scene.dispatch(UserDetailsAction.FetchFollowers)

        assertTrue(states[0].user == testingUser)
        assertTrue(states[1].isItFetching)
        assertEquals(states[2].repos, testingReposSorted)
        assertTrue(states[3].isItFetching)
        assertEquals(states[4].following, testingUserListItems)
        assertTrue(states[5].isItFetching)
        assertEquals(states[6].followers, testingUserListItems)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}