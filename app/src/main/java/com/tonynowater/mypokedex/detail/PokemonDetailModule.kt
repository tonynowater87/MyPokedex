package com.tonynowater.mypokedex.detail

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val nextModule = module {
    viewModel { PokemonDetailViewModel(app = get(), resourceRepository = get(), errorHandler = get()) }
}