package com.tonynowater.mypokedex.detail

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tonynowater.core.base.BaseFragment
import com.tonynowater.core.base.NavigationCommand
import com.tonynowater.core.miscellaneous.EventObserver
import com.tonynowater.mypokedex.R
import com.tonynowater.mypokedex.databinding.FragmentPokemonDetailBinding
import org.koin.android.viewmodel.ext.android.viewModel

class PokemonDetailFragment : BaseFragment<FragmentPokemonDetailBinding>(layout = R.layout.fragment_pokemon_detail) {

    private val viewModel by viewModel<PokemonDetailViewModel>()
    private val arg by navArgs<PokemonDetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindings?.apply {
            vm = viewModel
            pokemonName = arg.pokemonName
            pokemonImageUrl = arg.pokemonImageUrl
            ivPokemon.transitionName = getString(R.string.transition_name_iv_pokemon, arg.pokemonName)
            tvPokemonName.transitionName = getString(R.string.transition_name_tv_pokemon_name, arg.pokemonName)
            ivRounded.transitionName = getString(R.string.transition_name_iv_palette, arg.pokemonName)
        }

        viewModel
            .pokemonDetailModel
            .observe(viewLifecycleOwner, resolve {
                bindings?.apply {
                    model = it
                }
            })

        viewModel
            .navigationCommands
            .observe(viewLifecycleOwner, EventObserver {
                when (it) {
                    is NavigationCommand.Back -> findNavController().popBackStack()
                }
            })

        viewModel.getPokemonDetail(arg.pokemonName)
    }
}