package com.tonynowater.mypokedex.home

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {

    viewModel { PokemonListViewModel(app = get(), repository = get()) }
}