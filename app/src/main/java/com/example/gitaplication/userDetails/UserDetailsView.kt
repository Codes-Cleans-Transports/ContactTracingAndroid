package com.example.gitaplication.userDetails

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.core.statefulview.SceneView
import com.example.gitaplication.R
import com.example.gitaplication.userDetails.recyclerView.RepoRecyclerItem
import com.example.gitaplication.userDetails.recyclerView.TopSpacingItemDecoration
import com.example.gitaplication.userDetails.recyclerView.UserRecyclerItem
import com.multiplatform.util.Diff
import com.trading212.diverserecycleradapter.DiverseRecyclerAdapter
import kotlinx.android.synthetic.main.userdetails_view.view.*


class UserDetailsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : SceneView<UserDetailsState>(context, attrs, defStyleAttr) {

    // TODO review2: Use single adapter and replace its items instead of replacing the adapter
    private lateinit var repoAdapter: DiverseRecyclerAdapter

    private lateinit var followersAdapter: DiverseRecyclerAdapter

    private lateinit var followingAdapter: DiverseRecyclerAdapter

    override fun createView(inflater: LayoutInflater, parent: ViewGroup): View =
        inflater.inflate(R.layout.userdetails_view, parent, false)

    override fun onViewCreated(view: View) {
        super.onViewCreated(view)

        followers.setOnClickListener {
            dispatchAction(UserDetailsAction.FetchFollowers)
            recycler_list.adapter = followersAdapter // TODO review2: delete
        }

        following.setOnClickListener {
            dispatchAction(UserDetailsAction.FetchFollowing)
            recycler_list.adapter = followingAdapter // TODO review2: delete
        }

        repos.setOnClickListener {
            recycler_list.adapter = repoAdapter // TODO review2: delete
        }

        logout_button.setOnClickListener {
            dispatchAction(UserDetailsAction.Logout)
        }

        initRecycleView(recycler_list)
    }

    private fun initRecycleView(view: RecyclerView) {
        view.apply {
            layoutManager = LinearLayoutManager(context)
            val topSpacingItemDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingItemDecoration)
            repoAdapter = DiverseRecyclerAdapter()
            followersAdapter = DiverseRecyclerAdapter()
            followingAdapter = DiverseRecyclerAdapter()
            adapter = repoAdapter
        }
    }

    override fun render(state: UserDetailsState, diff: Diff<UserDetailsState>) {

        if (diff.by { it.isItFetching }) {
            detailsProgressIndicator.visibility = if (state.isItFetching) View.VISIBLE else View.GONE
        }

        if (diff.by { it.repos }) {
            if (state.repos != null) repoAdapter.addItems(state.repos.map { RepoRecyclerItem(it) })
            recycler_list.adapter = repoAdapter
        }

        if (diff.by { it.followers }) {
            if (state.followers != null) followersAdapter.addItems(state.followers.map { UserRecyclerItem(it) })
            recycler_list.adapter = followersAdapter
        }

        if (diff.by { it.following }) {
            if (state.following != null) followingAdapter.addItems(state.following.map { UserRecyclerItem(it) })
            recycler_list.adapter = followingAdapter
        }

        if (diff.by { it.user }) {
            user_login.text = state.user.username
            user_bio.text = state.user.bio
            user_location.text = state.user.location

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(context)
                .applyDefaultRequestOptions(requestOptions)
                .load(state.user.avatarUrl)
                .into(avatar)
        }
    }

}
