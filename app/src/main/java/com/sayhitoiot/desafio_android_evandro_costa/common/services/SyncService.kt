package com.sayhitoiot.desafio_android_evandro_costa.common.services

import android.util.Log
import com.sayhitoiot.desafio_android_evandro_costa.common.api.OnGetComicsCallback
import com.sayhitoiot.desafio_android_evandro_costa.common.api.OnGetMarvelCallback
import com.sayhitoiot.desafio_android_evandro_costa.common.api.model.characters.ReturnData
import com.sayhitoiot.desafio_android_evandro_costa.common.api.model.comics.ResultDataComics
import com.sayhitoiot.desafio_android_evandro_costa.common.extensions.toUrl
import com.sayhitoiot.desafio_android_evandro_costa.common.realm.entity.CharacterEntity
import com.sayhitoiot.desafio_android_evandro_costa.common.realm.entity.ComicsEntity
import com.sayhitoiot.desafio_android_evandro_costa.common.repository.ApiDataManager
import com.sayhitoiot.desafio_android_evandro_costa.common.repository.InteractToApi
import com.sayhitoiot.desafio_android_evandro_costa.features.list.interact.InteractList
import io.realm.RealmList

class SyncService {

    private val repository: InteractToApi = ApiDataManager()

    companion object {
        const val TAG = "sync-service"
        const val OFFSET = 0
    }

    init {
        fetchCharacters()
    }

    private fun fetchCharacters() {

        if(CharacterEntity.getAll().isNotEmpty()) {
            return
        }

        repository.getCharacter(OFFSET , InteractList.ALL, object : OnGetMarvelCallback {
            override fun onSuccess(marvelResponse: ReturnData) {

                val results = marvelResponse.data.results

                results.forEach {
                    CharacterEntity.create(
                        name = it.name,
                        description = it.description,
                        id = it.id,
                        thumbnail = "".toUrl(it.thumbnail.path , it.thumbnail.extension)
                    )
                    fetchComicsById(it.id)
                }

                Log.d(TAG,"${CharacterEntity.getAll().size} registros de character incluidos")


            }

            override fun onError() {
                Log.e(TAG, "error on sync data characters")
            }

        })

    }

    private fun fetchComicsById(characterId: String) {
        repository.getDetailsHQ(characterId, object: OnGetComicsCallback {
            override fun onSuccess(marvelResponse: ResultDataComics) {

                val listPrices: RealmList<Float> = RealmList()

                marvelResponse.data.results.forEach {
                    for ((cont, prices) in it.prices.withIndex()) {
                        listPrices.add(prices.price.toFloat())
                    }
                }

                marvelResponse.data.results.forEach {

                    ComicsEntity.create(
                        id = it.id,
                        title = it.title,
                        description = it.description,
                        price = listPrices,
                        thumbnail = "".toUrl(it.thumbnail.path , it.thumbnail.extension)
                    )

                    Log.d(
                        TAG,
                        "comic found ${it.title} "
                    )

                }

            }

            override fun onError() {
                Log.e(TAG, "error on sync data comics")
            }

        })

    }

}