package com.example.pcmovies.ui.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pcmovies.R
import com.example.pcmovies.data.model.User
import com.example.pcmovies.databinding.ItemUserBinding

class UserAdapter(
    private val onClick: (User) -> Unit
) : PagingDataAdapter<User, UserAdapter.UserViewHolder>(UserComparator) {

    object UserComparator : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: User, newItem: User) = oldItem == newItem
    }

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.tvName.text = "${user.first_name} ${user.last_name}"
            Glide.with(binding.ivAvatar.context)
                .load(user.avatar)
                .placeholder(R.drawable.ic_avatar_placeholder)
                .error(R.drawable.ic_avatar_placeholder)
                .circleCrop()
                .into(binding.ivAvatar)

            if (user.isSynced)
                binding.textViewStatus.text = "Synced (ID: ${user.id})"
             else
                binding.textViewStatus.text = "Pending Sync"

//            if (user.avatar.isNotEmpty()){
                binding.textViewId.text = "ID: ${user.id}"
//            }

            binding.root.setOnClickListener { onClick(user) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}
