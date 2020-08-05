package com.example.gitaplication.userDetails.recycleView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.gitaplication.R
import com.example.gitaplication.models.UserList
import kotlinx.android.synthetic.main.user_item_holder.view.*
import java.util.ArrayList

class FollowRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<UserList>? = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FollowViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.user_item_holder,parent,false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is FollowViewHolder->{
                holder.bind(items!![position])
            }
        }

    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    fun submitList(userList: List<UserList>?){
        items=userList
    }

    class FollowViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView){
        private val username: TextView =itemView.user_item_login
        private val avatar: ImageView =itemView.user_item_avatar

        fun bind(data : UserList){
            data.let{
              username.text=data.username

                val requestOptions = RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)

                Glide.with(itemView.context)
                    .applyDefaultRequestOptions(requestOptions)
                    .load(data.avatarUrl)
                    .into(avatar)
            }
        }
    }
}
