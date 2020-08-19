package com.example.gitaplication.userDetails.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.gitaplication.R
import com.example.gitaplication.models.UserList
import com.trading212.diverserecycleradapter.DiverseRecyclerAdapter
import kotlinx.android.synthetic.main.user_item_holder.view.*

class UserRecyclerItem(override val data: UserList) :
    DiverseRecyclerAdapter.RecyclerItem<UserList, UserRecyclerItem.UserViewHolder>() {

    override val type: Int = ItemTypes.USER.ordinal

    override fun createViewHolder(parent: ViewGroup, inflater: LayoutInflater): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.user_item_holder,parent,false)
        )
    }

    class UserViewHolder constructor(itemView: View):
        DiverseRecyclerAdapter.ViewHolder<UserList>(itemView){

        private val username: TextView =itemView.user_item_login
        private val avatar: ImageView =itemView.user_item_avatar

        override fun bindTo(data : UserList){

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
