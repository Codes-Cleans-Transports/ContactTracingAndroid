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
import com.example.gitaplication.login.LoginAction
import com.example.gitaplication.userDetails.recycleView.FollowRecyclerAdapter
import com.example.gitaplication.userDetails.recycleView.ReposRecycleAdapter
import com.example.gitaplication.userDetails.recycleView.TopSpacingItemDecoration
import com.multiplatform.util.Diff
import kotlinx.android.synthetic.main.userdetails_view.view.*
import kotlinx.android.synthetic.main.view_login.view.*


private lateinit var repoAdapter: ReposRecycleAdapter

private lateinit var followersAdapter: FollowRecyclerAdapter

private lateinit var followingAdapter: FollowRecyclerAdapter

class UserDetailsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : SceneView<UserDetailsState>(context, attrs, defStyleAttr) {

    override fun createView(inflater: LayoutInflater, parent: ViewGroup): View =
        inflater.inflate(R.layout.userdetails_view, parent, false)

    override fun onViewCreated(view: View) {
        super.onViewCreated(view)

        followers.setOnClickListener {
            recycler_list.adapter= followersAdapter
        }

        following.setOnClickListener {
            recycler_list.adapter= followingAdapter
        }

        repos.setOnClickListener {
            recycler_list.adapter= repoAdapter
        }

        initRecycleView(recycler_list)
    }

    private fun initRecycleView(view: RecyclerView) {
        view.apply {
            layoutManager=LinearLayoutManager(context)
            val topSpacingItemDecoration= TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingItemDecoration)
            repoAdapter= ReposRecycleAdapter()
            followersAdapter= FollowRecyclerAdapter()
            followingAdapter= FollowRecyclerAdapter()
            adapter = repoAdapter
        }
    }

    override fun render(state: UserDetailsState, diff: Diff<UserDetailsState>) {

        if(diff.by{it.isItFetching}){
                detailsProgressIndicator.visibility = if (state.isItFetching) View.VISIBLE else View.GONE
        }

        if (diff.by { it.repos }) {
            repoAdapter.submitList(state.repos)
            recycler_list.adapter= repoAdapter
        }

        if (diff.by { it.followers }) {
            followersAdapter.submitList(state.followers)
        }

        if (diff.by { it.following }) {
            followingAdapter.submitList(state.following)
        }

        if(diff.by{it.user}){
            user_login.text=state.user.username
            user_bio.text=state.user.bio
            user_location.text=state.user.location

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
