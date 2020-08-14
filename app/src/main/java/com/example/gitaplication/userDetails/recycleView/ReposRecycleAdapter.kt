package com.example.gitaplication.userDetails.recycleView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gitaplication.R
import com.example.gitaplication.models.Repo
import kotlinx.android.synthetic.main.repo_item_holder.view.*
import java.util.*

class ReposRecycleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Repo>? = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RepoViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.repo_item_holder,parent,false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is RepoViewHolder->{
                holder.bind(items!![position])
            }
        }

    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    fun submitList(repoList: List<Repo>?){
        items=repoList
    }

    class RepoViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView){
        private val repoName: TextView = itemView.repo_name
        private val repoDescription: TextView = itemView.repo_description
        private val watchersCount: TextView = itemView.watchers_count
        private val forksCount: TextView = itemView.forks_count

        fun bind(repo :Repo){
            repo.let{
                repoName.text=it.name
                repoDescription.text=it.description
                watchersCount.text=it.watchersCount.toString()
                forksCount.text=it.forksCount.toString()
            }
        }
    }
}
