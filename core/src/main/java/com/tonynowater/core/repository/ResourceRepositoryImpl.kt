package com.tonynowater.core.repository


import com.tonynowater.core.repository.database.AppDatabase
import com.tonynowater.core.repository.database.entity.PokemonDetailEntity
import com.tonynowater.core.repository.database.entity.PokemonEntity
import com.tonynowater.core.repository.network.retrofit.API
import io.reactivex.Observable
import io.reactivex.functions.Function
import timber.log.Timber

class ResourceRepositoryImpl(
    private val api: API,
    private val database: AppDatabase
) : ResourceRepository {

    companion object {
        private const val PageSize = 20
    }

    override fun getPokemonList(page: Int): Observable<List<PokemonEntity>> {

        val pokemons = mutableListOf<PokemonEntity>()

        return database
            .getPokemonDao()
            .getPokemonsByPage(page)
            .toObservable()
            .flatMap { dataInDb ->
                if (dataInDb.isNotEmpty()) {
                    Timber.d("[DEBUG] pokemon list in db , page = $page")
                    pokemons.addAll(dataInDb)
                    Observable.just(pokemons)
                } else {
                    Timber.d("[DEBUG] pokemon list not in db , page = $page")
                    api
                        .getPokemonList(PageSize, page * PageSize)
                        .flatMapIterable { it.results }
                        .map { it.apply { it.page = page } }
                        .toList()
                        .toObservable()
                        .flatMap {
                            database.getPokemonDao().insert(it)
                            Observable.just(it)
                        }
                }
            }
    }

    override fun getPokemonDetail(name: String): Observable<PokemonDetailEntity> {
        return database
            .getPokemonDetailDao()
            .getPokemonDetailByName(name)
            .toObservable()
            .map {
                Timber.d("[DEBUG] pokemon detail in db , name = $name")
                it
            }
            .onErrorResumeNext(Function {
                Timber.d("[DEBUG] pokemon detail not in db, name = $name, exception = $it")
                api.getPokemonDetail(name)
                    .flatMap { pokemonDetailDto ->
                        Observable.just(PokemonDetailEntity.from(pokemonDetailDto).apply {
                            database.getPokemonDetailDao().insert(this)
                            Timber.d("[DEBUG] insertPokemonDetail => $this")
                        })
                    }
            })
    }
}
