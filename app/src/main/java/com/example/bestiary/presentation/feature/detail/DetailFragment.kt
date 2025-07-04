// presentation/feature/detail/DetailFragment.kt
package com.example.bestiary.presentation.feature.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.bestiary.R
import com.example.bestiary.databinding.FragmentDetailBinding
import com.example.bestiary.domain.model.MonsterDetail
import com.example.bestiary.presentation.common.adapter.AbilityAdapter
import com.example.bestiary.presentation.common.adapter.ActionAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()

    private companion object {
        const val TAG = "DetailFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        observeViewModel()
        viewModel.loadMonsterDetail(args.monsterIndex)
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.monsterDetailState.collect { state ->
                when (state) {
                    is DetailState.Loading -> showLoading()
                    is DetailState.Error -> showError(state.message)
                    is DetailState.Success -> {
                        Log.d(TAG, "Received DetailState.Success: ${state.monster}") // Логируем полученный объект
                        showMonsterDetail(state.monster)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isFavorite.collect { isFavorite ->
                binding.fabFavorite.setImageResource(
                    if (isFavorite) R.drawable.ic_favorite_filled
                    else R.drawable.ic_favorite_outline
                )
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.scrollView.visibility = View.GONE
        binding.errorView.root.visibility = View.GONE
    }

    private fun showError(message: String) {
        binding.progressBar.visibility = View.GONE
        binding.scrollView.visibility = View.GONE
        binding.errorView.root.visibility = View.VISIBLE
        binding.errorView.textError.text = message
    }

    private fun showMonsterDetail(monster: MonsterDetail) {
        binding.progressBar.visibility = View.GONE
        binding.errorView.root.visibility = View.GONE
        binding.scrollView.visibility = View.VISIBLE

        with(binding) {
            toolbar.title = monster.name
            val fullImageUrl = monster.imageUrl
            Log.d(TAG, "Loading image with URL: $fullImageUrl") // Логируем URL
            Glide.with(requireContext())
                .load(fullImageUrl)
                .placeholder(R.drawable.ic_monster_placeholder)
                .error(R.drawable.ic_error) // Добавили обработку ошибок
                .into(imageMonster)

            textName.text = monster.name
            textSizeType.text = "${monster.size} ${monster.type}, ${monster.alignment}"
            textArmorClass.text = monster.armorClass.toString()
            textHitPoints.text = "${monster.hitPoints} (${monster.hitDice})"
            textChallengeRating.text = "CR: ${monster.challengeRating} (${monster.xp} XP)"

            // Ability scores
            textStrength.text = monster.strength.toString()
            textDexterity.text = monster.dexterity.toString()
            textConstitution.text = monster.constitution.toString()
            textIntelligence.text = monster.intelligence.toString()
            textWisdom.text = monster.wisdom.toString()
            textCharisma.text = monster.charisma.toString()

            // Special abilities
            if (monster.specialAbilities.isNullOrEmpty()) {
                layoutSpecialAbilities.visibility = View.GONE
            } else {
                layoutSpecialAbilities.visibility = View.VISIBLE
                recyclerSpecialAbilities.adapter = AbilityAdapter().apply {
                    submitList(monster.specialAbilities)
                }
            }

            // Actions
            if (monster.actions.isNullOrEmpty()) {
                layoutActions.visibility = View.GONE
            } else {
                layoutActions.visibility = View.VISIBLE
                recyclerActions.adapter = ActionAdapter().apply {
                    submitList(monster.actions)
                }
            }

            // Description
            if (monster.description.isNullOrBlank()) {
                layoutDescription.visibility = View.GONE
            } else {
                layoutDescription.visibility = View.VISIBLE
                textDescription.text = monster.description
            }

            fabFavorite.setOnClickListener {
                viewModel.toggleFavorite()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
