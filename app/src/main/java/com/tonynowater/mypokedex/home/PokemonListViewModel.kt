package com.tonynowater.mypokedex.home

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged
import com.tonynowater.core.base.BaseViewModel
import com.tonynowater.core.repository.ResourceRepository
import com.tonynowater.core.repository.database.entity.PokemonEntity
import com.tonynowater.core.repository.network.Resource
import com.tonynowater.core.utils.LoadMoreInterface
import com.tonynowater.mypokedex.R
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

class PokemonListViewModel(
    private val app: Application,
    private val repository: ResourceRepository
) : BaseViewModel(app), LoadMoreInterface {

    private val _pokemonListPage = MutableLiveData(0)
    private val _pokemonList = MutableLiveData<Resource<List<PokemonEntity>>>()
    var pokemonList: LiveData<Resource<List<PokemonEntity>>> = _pokemonList

    private val _isError = MutableLiveData(false)

    init {
        _pokemonListPage.observeForever {
            if (!isError()) {
                loadMore(it)
            }
        }

        _isError.distinctUntilChanged()
            .observeForever {
                if (it) {
                    Toast.makeText(app, R.string.error_no_network, Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun loadMore() {
        _pokemonListPage.postValue(_pokemonListPage.value!! + 1)
    }

    override fun isLoading(): Boolean = loading.value ?: false

    override fun isError(): Boolean = _isError.value!!

    override fun reset() {
        _pokemonListPage.value = _pokemonListPage.value!! - 1
        _isError.value = false
    }

    private fun loadMore(page: Int) {

        Timber.d("[DEBUG] [loadMore] [page = $page]")

        repository.getPokemonList(page)
            .compose(applySchedulers())
            .doOnSubscribe { loading.value = true }
            .subscribeBy(
                onNext = {
                    if (!isError()) {
                        val originPokemonList = pokemonList.value?.extract() ?: mutableListOf()
                        _pokemonList.postValue(
                            Resource.success(
                                originPokemonList
                                    .toMutableList()
                                    .apply { addAll(it) }
                                    .toList())
                        )
                    }
                }, onError = {
                    loading.value = false
                    _isError.value = true
                },
                onComplete = {
                    loading.value = false
                }
            )
            .also { compositeDisposable.add(it) }
    }
}