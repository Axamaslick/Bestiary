// presentation/common/adapter/AbilityAdapter.kt
package com.example.bestiary.presentation.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bestiary.databinding.ItemAbilityBinding
import com.example.bestiary.domain.model.SpecialAbility

class AbilityAdapter : ListAdapter<SpecialAbility, AbilityAdapter.AbilityViewHolder>(AbilityDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbilityViewHolder {
        val binding = ItemAbilityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AbilityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AbilityViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AbilityViewHolder(
        private val binding: ItemAbilityBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ability: SpecialAbility) {
            binding.apply {
                textName.text = ability.name
                textDescription.text = ability.desc
            }
        }
    }
}

class AbilityDiffCallback : DiffUtil.ItemCallback<SpecialAbility>() {
    override fun areItemsTheSame(oldItem: SpecialAbility, newItem: SpecialAbility): Boolean =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: SpecialAbility, newItem: SpecialAbility): Boolean =
        oldItem == newItem
}