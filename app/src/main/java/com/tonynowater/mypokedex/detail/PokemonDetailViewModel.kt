package com.tonynowater.mypokedex.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tonynowater.core.base.BaseViewModel
import com.tonynowater.core.repository.network.Resource
import com.tonynowater.core.repository.network.exception.ErrorHandler
import com.tonynowater.core.repository.ResourceRepository

import com.tonynowater.mypokedex.detail.uiModel.PokemonDetailModel
import io.reactivex.rxkotlin.subscribeBy

class PokemonDetailViewModel(
    app: Application,
    private val resourceRepository: ResourceRepository,
    private val errorHandler: ErrorHandler
) : BaseViewModel(app) {

    private val _pokemonDetailModel = MutableLiveData<Resource<PokemonDetailModel>>()
    val pokemonDetailModel: LiveData<Resource<PokemonDetailModel>> = _pokemonDetailModel

    fun getPokemonDetail(name: String) {
        resourceRepository
            .getPokemonDetail(name)
            .map { PokemonDetailModel.from(it) }
            .compose(applySchedulers())
            .doOnSubscribe { loading.postValue(true) }
            .doFinally { loading.postValue(false) }
            .subscribeBy(
                onNext = {
                    _pokemonDetailModel.postValue(Resource.success(it))
                },
                onError = {
                    _pokemonDetailModel.postValue(Resource.error(errorHandler.extractErrorState(it), replay = false))
                }
            )
            .also { compositeDisposable.add(it) }
    }
}