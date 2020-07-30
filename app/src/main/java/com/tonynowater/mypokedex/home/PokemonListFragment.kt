package com.tonynowater.mypokedex.home

import android.os.Bundle
import android.view.View
import androidx.core.view.doOnPreDraw
import com.tonynowater.core.base.BaseFragment
import com.tonynowater.core.beginDelayedTransition
import com.tonynowater.core.disableItemAnimator
import com.tonynowater.core.transition.Stagger
import com.tonynowater.core.utils.GridSpaceItemDecorator
import com.tonynowater.mypokedex.R
import com.tonynowater.mypokedex.databinding.FragmentPokemonListBinding
import kotlinx.android.synthetic.main.fragment_pokemon_list.rvPokemons
import org.koin.android.viewmodel.ext.android.viewModel

class PokemonListFragment : BaseFragment<FragmentPokemonListBinding>(layout = R.layout.fragment_pokemon_list) {

    private val viewModel by viewModel<PokemonListViewModel>()
    private val adapter = PokemonsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()

        rvPokemons.doOnPreDraw {
            startPostponedEnterTransition()
        }

        rvPokemons.adapter = adapter
        rvPokemons.disableItemAnimator() // We animate item additions on our side, so disable it in RecyclerView.
        rvPokemons.addItemDecoration(GridSpaceItemDecorator(resources.getDimensionPixelSize(R.dimen.grid_list_margin)))

        bindings?.apply {
            vm = viewModel
        }

        viewModel.pokemonList.observe(viewLifecycleOwner, resolve {
            // Delay the stagger effect until the list is updated.
            beginDelayedTransition(rvPokemons, Stagger())
            adapter.submitList(it)
        })
    }
}