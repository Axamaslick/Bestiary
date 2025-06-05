package com.example.bestiary.presentation.common.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bestiary.databinding.ItemMonsterSearchBinding
import com.example.bestiary.domain.model.Monster

class MonsterSearchAdapter(
    private val onItemClick: (Monster) -> Unit
) : ListAdapter<Monster, MonsterSearchAdapter.MonsterSearchViewHolder>(MonsterDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonsterSearchViewHolder {
        val binding = ItemMonsterSearchBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MonsterSearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MonsterSearchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MonsterSearchViewHolder(
        private val binding: ItemMonsterSearchBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(monster: Monster) {
            binding.apply {
                textName.text = monster.name
                textType.text = "${monster.size} ${monster.type}"
                textCr.text = "CR: ${monster.challengeRating}"

                root.setOnClickListener { onItemClick(monster) }
            }
            Log.d("MonsterSearchAdapter", "Bind monster: $monster")
        }
    }


}

