package com.tonynowater.mypokedex.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tonynowater.core.executeAfter
import com.tonynowater.core.navigateSafely
import com.tonynowater.core.repository.database.entity.PokemonEntity
import com.tonynowater.mypokedex.R
import com.tonynowater.mypokedex.databinding.ItemPokemonBinding

class PokemonsAdapter : ListAdapter<PokemonEntity, PokemonsAdapter.PokemonViewHolder>(PokemonDiff) {

    class PokemonViewHolder(val binding: ItemPokemonBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder(
            ItemPokemonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.binding.executeAfter {
            val item = getItem(position)
            pokemon = item
            val ivTransitionName = root.resources.getString(R.string.transition_name_iv_pokemon, item.name)
            val ivPaletteTransitionName = root.resources.getString(R.string.transition_name_iv_palette, item.name)
            val tvTransitionName = root.resources.getString(R.string.transition_name_tv_pokemon_name, item.name)
            holder.binding.ivPokemon.transitionName = ivTransitionName
            holder.binding.tvItemPokemon.transitionName = tvTransitionName
            holder.binding.ivRounded.transitionName = ivPaletteTransitionName
            root.setOnClickListener {
                val directions = PokemonListFragmentDirections.actionToPokemonDetail(item.name, item.getImageUrl())
                holder.binding.root.findNavController().navigateSafely(
                    directions.actionId,
                    directions.arguments,
                    FragmentNavigatorExtras(
                        holder.binding.ivPokemon to ivTransitionName,
                        holder.binding.tvItemPokemon to tvTransitionName,
                        holder.binding.ivRounded to ivPaletteTransitionName
                    )
                )
            }
        }
    }
}


object PokemonDiff : DiffUtil.ItemCallback<PokemonEntity>() {
    override fun areItemsTheSame(
        oldItem: PokemonEntity,
        newItem: PokemonEntity
    ): Boolean = oldItem == newItem

    override fun areContentsTheSame(
        oldItem: PokemonEntity,
        newItem: PokemonEntity
    ): Boolean = oldItem == newItem

}