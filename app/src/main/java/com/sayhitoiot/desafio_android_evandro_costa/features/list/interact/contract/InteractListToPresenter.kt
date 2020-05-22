package com.sayhitoiot.desafio_android_evandro_costa.features.list.interact.contract

import com.sayhitoiot.desafio_android_evandro_costa.common.realm.entity.CharacterEntity

interface InteractListToPresenter {
    fun didFetchCharacters(characterEntityyList: MutableList<CharacterEntity>)
    fun didFetchCharactersError(error: String)
}