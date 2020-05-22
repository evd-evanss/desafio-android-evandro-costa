package com.sayhitoiot.desafio_android_evandro_costa.features.list.interact

import com.sayhitoiot.desafio_android_evandro_costa.common.api.OnGetMarvelCallback
import com.sayhitoiot.desafio_android_evandro_costa.common.repository.ApiDataManager
import com.sayhitoiot.desafio_android_evandro_costa.common.repository.InteractToApi
import com.sayhitoiot.desafio_android_evandro_costa.features.list.interact.contract.InteractListToPresenter
import com.sayhitoiot.desafio_android_evandro_costa.features.list.interact.contract.InteractListToInteract
import com.sayhitoiot.desafio_android_evandro_costa.common.data.model.characters.ReturnData
import com.sayhitoiot.desafio_android_evandro_costa.common.extensions.toUrl
import com.sayhitoiot.desafio_android_evandro_costa.common.realm.entity.CharacterEntity

class InteractList(private val presenter: InteractListToPresenter) : InteractListToInteract{

    private val repository: InteractToApi = ApiDataManager()

    companion object {
        const val ALL = 100
    }

    override fun fetchCharacters(offset: Int, limit: Int) {
        val characterList = CharacterEntity.getAll()
        if(characterList.isEmpty()) {
            getCharactersOnAPI(offset, limit)
        } else {
            presenter.didFetchCharacters(characterList.subList(0,limit))
        }
    }

    private fun getCharactersOnAPI(offSet: Int, limit: Int) {
        repository.getCharacter(offSet, ALL, object : OnGetMarvelCallback{
            override fun onSuccess(marvelResponse: ReturnData) {

                val results = marvelResponse.data.results

                results.forEach {
                    CharacterEntity.create(
                        name = it.name,
                        description = it.description,
                        id = it.id,
                        thumbnail = "".toUrl(it.thumbnail.path , it.thumbnail.extension)
                    )
                }
                val characterList = CharacterEntity.getAll()
                presenter.didFetchCharacters(characterList.subList(0,limit))

            }

            override fun onError() {

            }

        })
    }

}