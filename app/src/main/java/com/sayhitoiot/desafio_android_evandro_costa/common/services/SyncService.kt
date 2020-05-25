package com.sayhitoiot.desafio_android_evandro_costa.common.services

import android.util.Log
import com.sayhitoiot.desafio_android_evandro_costa.common.api.OnGetMarvelCallback
import com.sayhitoiot.desafio_android_evandro_costa.common.api.model.characters.ReturnDataCharacter
import com.sayhitoiot.desafio_android_evandro_costa.common.extensions.toUrl
import com.sayhitoiot.desafio_android_evandro_costa.common.realm.entity.CharacterEntity
import com.sayhitoiot.desafio_android_evandro_costa.common.repository.Repository
import com.sayhitoiot.desafio_android_evandro_costa.common.repository.InteractToApi
import com.sayhitoiot.desafio_android_evandro_costa.features.list.interact.InteractList

/**
 * @author Evandro Ribeiro Costa (revandro77@yahoo.com.br)
 */

class SyncService {

    private val repository: InteractToApi = Repository()

    companion object {
        const val TAG = "sync-service"
        const val OFFSET = 0
    }

    init { fetchCharacters() }

    private fun fetchCharacters() {

        if(CharacterEntity.getAll().isNotEmpty()) {
            return
        }

        repository.getCharacter(OFFSET , InteractList.ALL, object : OnGetMarvelCallback {
            override fun onSuccess(marvelResponse: ReturnDataCharacter) {

                val results = marvelResponse.data.results

                results.forEach {
                    CharacterEntity.create(
                        name = it.name,
                        description = it.description,
                        id = it.id,
                        thumbnail = toUrl(it.thumbnail.path, it.thumbnail.extension)
                    )
                }

                Log.d(TAG,"${CharacterEntity.getAll().size} registros de character incluidos")


            }

            override fun onError() {
                Log.e(TAG, "error on sync data characters")
            }

        })

    }

}