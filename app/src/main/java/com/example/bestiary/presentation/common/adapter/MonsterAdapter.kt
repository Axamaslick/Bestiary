package com.example.bestiary.presentation.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bestiary.R
import com.example.bestiary.databinding.ItemMonsterBinding
import com.example.bestiary.domain.model.Monster

class MonsterAdapter(
    private val onItemClick: (Monster) -> Unit,
    private val onFavoriteClick: (Monster) -> Unit
) : ListAdapter<Monster, MonsterAdapter.MonsterViewHolder>(MonsterDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonsterViewHolder {
        val binding = ItemMonsterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MonsterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MonsterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MonsterViewHolder(
        private val binding: ItemMonsterBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(monster: Monster) {
            binding.apply {
                name.text = monster.name
                type.text = "${monster.size} ${monster.type}"
                challengeRating.text = "CR: ${monster.challengeRating}"

                Glide.with(root.context)
                    .load(monster.imageUrl)
                    .placeholder(R.drawable.ic_monster_placeholder)
                    .into(image)

                favoriteButton.setImageResource(
                    if (monster.isFavorite) R.drawable.ic_favorite_filled
                    else R.drawable.ic_favorite_outline
                )

                root.setOnClickListener { onItemClick(monster) }
                favoriteButton.setOnClickListener { onFavoriteClick(monster) }
            }
        }
    }
}

class MonsterDiffCallback : DiffUtil.ItemCallback<Monster>() {
    override fun areItemsTheSame(oldItem: Monster, newItem: Monster): Boolean =
        oldItem.index == newItem.index

    override fun areContentsTheSame(oldItem: Monster, newItem: Monster): Boolean =
        oldItem == newItem
}