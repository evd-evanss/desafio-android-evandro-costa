package com.sayhitoiot.desafio_android_evandro_costa.features.list.interact

import com.sayhitoiot.desafio_android_evandro_costa.common.api.OnGetMarvelCallback
import com.sayhitoiot.desafio_android_evandro_costa.common.repository.ApiDataManager
import com.sayhitoiot.desafio_android_evandro_costa.common.repository.InteractToApi
import com.sayhitoiot.desafio_android_evandro_costa.features.list.interact.contract.InteractListToPresenter
import com.sayhitoiot.desafio_android_evandro_costa.features.list.interact.contract.InteractListToInteract
import com.sayhitoiot.desafio_android_evandro_costa.common.data.entity.CharacterEntity
import com.sayhitoiot.desafio_android_evandro_costa.common.data.model.characters.ReturnData

class InteractList(private val presenter: InteractListToPresenter) : InteractListToInteract{

    private val repository: InteractToApi = ApiDataManager()

    override fun fetchCharactersOnAPI(offset: Int, limit: Int) {
        getCharacters(offset, limit)
    }

    private fun getCharacters(offSet: Int, limit: Int) {
        repository.getCharacter(offSet, limit, object : OnGetMarvelCallback{
            override fun onSuccess(marvelResponse: ReturnData) {

                val results = marvelResponse.data.results
                val characterEntityList: MutableList<CharacterEntity> = mutableListOf()

                results.forEach {
                    characterEntityList.add(
                        CharacterEntity(
                            name = it.name,
                            description = it.description,
                            id = it.id,
                            thumbnail = it.thumbnail
                        )
                    )
                }

                presenter.didFetchCharactersOnAPI(characterEntityList)

            }

            override fun onError() {

            }

        })
    }

}