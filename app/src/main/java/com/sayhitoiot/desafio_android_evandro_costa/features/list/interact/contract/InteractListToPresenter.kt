package com.sayhitoiot.desafio_android_evandro_costa.features.list.interact.contract

import com.sayhitoiot.desafio_android_evandro_costa.common.realm.entity.CharacterEntity

/**
 * @author Evandro Ribeiro Costa (revandro77@yahoo.com.br)
 */

interface InteractListToPresenter {
    fun didFetchCharacters(characterEntityyList: MutableList<CharacterEntity>)
    fun didFetchCharactersError(error: String)
}