package com.example.bestiary.presentation.common.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bestiary.databinding.ItemActionBinding
import com.example.bestiary.domain.model.MonsterAction

class ActionAdapter : ListAdapter<MonsterAction, ActionAdapter.ActionViewHolder>(ActionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionViewHolder {
        val binding = ItemActionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ActionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ActionViewHolder(
        private val binding: ItemActionBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(action: MonsterAction) {
            binding.apply {
                textName.text = action.name
                textDescription.text = action.desc

                // Отображаем бонус к атаке и урон, если они есть
                if (action.attackBonus != null) {
                    textAttackBonus.text = "+${action.attackBonus}"
                    textAttackBonus.visibility = View.VISIBLE
                } else {
                    textAttackBonus.visibility = View.GONE
                }

                if (!action.damage.isNullOrEmpty()) {
                    textDamage.text = action.damage?.joinToString {
                        "${it.damageDice} (${it.damageType})"
                    } ?: ""
                    textDamage.visibility = View.VISIBLE
                } else {
                    textDamage.visibility = View.GONE
                }
            }
        }
    }
}

class ActionDiffCallback : DiffUtil.ItemCallback<MonsterAction>() {
    override fun areItemsTheSame(oldItem: MonsterAction, newItem: MonsterAction): Boolean =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: MonsterAction, newItem: MonsterAction): Boolean =
        oldItem == newItem
}