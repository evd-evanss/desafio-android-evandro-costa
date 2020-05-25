package com.sayhitoiot.desafio_android_evandro_costa.features.list.interact

import com.sayhitoiot.desafio_android_evandro_costa.common.api.OnGetMarvelCallback
import com.sayhitoiot.desafio_android_evandro_costa.common.repository.Repository
import com.sayhitoiot.desafio_android_evandro_costa.common.repository.InteractToApi
import com.sayhitoiot.desafio_android_evandro_costa.features.list.interact.contract.InteractListToPresenter
import com.sayhitoiot.desafio_android_evandro_costa.features.list.interact.contract.InteractListToInteract
import com.sayhitoiot.desafio_android_evandro_costa.common.api.model.characters.ReturnDataCharacter
import com.sayhitoiot.desafio_android_evandro_costa.common.extensions.toUrl
import com.sayhitoiot.desafio_android_evandro_costa.common.realm.entity.CharacterEntity

/**
 * @author Evandro Ribeiro Costa (revandro77@yahoo.com.br)
 */

class InteractList(private val presenter: InteractListToPresenter) : InteractListToInteract{

    private val repository: InteractToApi = Repository()

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
                val characterList = CharacterEntity.getAll()
                presenter.didFetchCharacters(characterList.subList(0,limit))

            }

            override fun onError() {
                presenter.didFetchCharactersError("Falha ao tentar sincronizar, tente novamente mais tarde")
            }

        })
    }

}