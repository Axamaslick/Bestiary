package com.example.bestiary.presentation.feature.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bestiary.databinding.FragmentFavoritesBinding
import com.example.bestiary.domain.model.Monster
import com.example.bestiary.presentation.common.adapter.MonsterAdapter
import com.example.bestiary.presentation.common.decorations.GridSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoritesViewModel by viewModels()
    private lateinit var adapter: MonsterAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = MonsterAdapter(
            onItemClick = { monster ->
                findNavController().navigate(
                    FavoritesFragmentDirections.actionFavoritesToDetail(monster.index)
                )
            },
            onFavoriteClick = { monster ->
                viewModel.toggleFavorite(monster.index)
            }
        )

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = this@FavoritesFragment.adapter
            addItemDecoration(
                GridSpacingItemDecoration(
                    spanCount = 2,
                    spacing = resources.getDimensionPixelSize(R.dimen.grid_spacing),
                    includeEdge = true
                )
            )
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.favoritesState.collect { state ->
                when (state) {
                    is FavoritesState.Loading -> showLoading()
                    is FavoritesState.Empty -> showEmptyState()
                    is FavoritesState.Success -> showMonsters(state.monsters)
                }
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.emptyState.root.visibility = View.GONE
        binding.recyclerView.visibility = View.GONE
    }

    private fun showEmptyState() {
        binding.progressBar.visibility = View.GONE
        binding.emptyState.root.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
    }

    private fun showMonsters(monsters: List<Monster>) {
        binding.progressBar.visibility = View.GONE
        binding.emptyState.root.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
        adapter.submitList(monsters)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}