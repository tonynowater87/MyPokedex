package com.tonynowater.core.repository


import com.tonynowater.core.repository.database.AppDatabase
import com.tonynowater.core.repository.database.entity.PokemonDetailEntity
import com.tonynowater.core.repository.database.entity.PokemonEntity
import com.tonynowater.core.repository.network.retrofit.API
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import timber.log.Timber

class ResourceRepositoryImpl(
    private val api: API,
    private val database: AppDatabase
) : ResourceRepository {

    companion object {
        private const val PageSize = 20
    }

    override suspend fun getPokemonList(page: Int): Flow<List<PokemonEntity>> {

        val pokemons = mutableListOf<PokemonEntity>()

        return flowOf(database.getPokemonDao().getPokemonsByPage(page))
            .map { dataInDb ->
                if (dataInDb.isNotEmpty()) {
                    Timber.d("[DEBUG] pokemon list in db , page = $page")
                    pokemons.addAll(dataInDb)
                    pokemons
                } else {
                    Timber.d("[DEBUG] pokemon list not in db , page = $page")
                    val result = api.getPokemonList(PageSize, page * PageSize)
                        .results
                        .map {
                            it.apply {
                                it.page = page
                                database.getPokemonDao().insert(it)
                            }
                        }
                    result
                }
            }
    }

    override suspend fun getPokemonDetail(name: String): Flow<PokemonDetailEntity> {
        return flow<PokemonDetailEntity> {
            val dataInDB = database.getPokemonDetailDao().getPokemonDetailByName(name)
            if (dataInDB != null) {
                Timber.d("[DEBUG] pokemon detail in db , name = $name")
                emit(dataInDB)
            } else {
                Timber.d("[DEBUG] pokemon detail not in db, name = $name")
                val detailDTO = api.getPokemonDetail(name)
                val detailEntity = PokemonDetailEntity.from(detailDTO)
                database.getPokemonDetailDao().insert(detailEntity)
                emit(detailEntity)
            }
        }
    }
}
