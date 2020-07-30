package com.tonynowater.mypokedex.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tonynowater.core.base.BaseViewModel
import com.tonynowater.core.onIO
import com.tonynowater.core.repository.ResourceRepository
import com.tonynowater.core.repository.network.Resource
import com.tonynowater.core.repository.network.exception.ErrorHandler
import com.tonynowater.mypokedex.detail.uiModel.PokemonDetailModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class PokemonDetailViewModel(
    app: Application,
    private val resourceRepository: ResourceRepository,
    private val errorHandler: ErrorHandler
) : BaseViewModel(app) {

    private val _pokemonDetailModel = MutableLiveData<Resource<PokemonDetailModel>>()
    val pokemonDetailModel: LiveData<Resource<PokemonDetailModel>> = _pokemonDetailModel

    fun getPokemonDetail(name: String) {

        viewModelScope.launch {
            resourceRepository
                .getPokemonDetail(name)
                .onStart { loading.postValue(true) }
                .onCompletion { loading.postValue(false) }
                .catch {
                    loading.postValue(false)
                    _pokemonDetailModel.postValue(
                        Resource.error(
                            errorHandler.extractErrorState(
                                it
                            ), replay = false
                        )
                    )
                }
                .onIO()
                .map { PokemonDetailModel.from(it) }
                .collect {
                    _pokemonDetailModel.postValue(Resource.success(it))
                }
        }
    }
}