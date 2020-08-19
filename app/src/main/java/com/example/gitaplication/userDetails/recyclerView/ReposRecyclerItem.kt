package com.example.gitaplication.userDetails.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.gitaplication.R
import com.example.gitaplication.models.Repo
import com.trading212.diverserecycleradapter.DiverseRecyclerAdapter

class RepoRecyclerItem(override val data: Repo) :
    DiverseRecyclerAdapter.RecyclerItem<Repo, RepoRecyclerItem.RepoViewHolder>() {

    override val type: Int = ItemTypes.REPO.ordinal

    override fun createViewHolder(parent: ViewGroup, inflater: LayoutInflater) =
        RepoViewHolder(
            inflater.inflate(R.layout.repo_item_holder, parent, false)
        )

    class RepoViewHolder(itemView: View) :
        DiverseRecyclerAdapter.ViewHolder<Repo>(itemView) {

        private val repoName = findViewById<TextView>(R.id.repo_name)
        private val repoDescription = findViewById<TextView>(R.id.repo_description)
        private val watchersCount = findViewById<TextView>(R.id.watchers_count)
        private val forksCount = findViewById<TextView>(R.id.forks_count)

        override fun bindTo(data: Repo) {
            data.let {
                repoName.text = it.name
                repoDescription.text = it.description
                watchersCount.text = it.watchersCount.toString()
                forksCount.text = it.forksCount.toString()
            }
        }
    }
}