package com.mashkov.mvvm.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mashkov.mvvm.databinding.RvItemBinding
import com.mashkov.mvvm.models.User

class UserAdapter(var users: List<User>, val onClickListener: (String?) -> Unit) :
    RecyclerView.Adapter<UserAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            RvItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        users[position].let { holder.bind(it) }
        holder.itemView.setOnClickListener { onClickListener(users[position].login) }
    }

    override fun getItemCount() = users.size

    class MyViewHolder(private val binding: RvItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.user = user
        }
    }

}