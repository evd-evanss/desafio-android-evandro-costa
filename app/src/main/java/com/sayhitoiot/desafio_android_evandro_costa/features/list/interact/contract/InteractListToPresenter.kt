package com.sayhitoiot.desafio_android_evandro_costa.features.list.interact.contract

import com.sayhitoiot.desafio_android_evandro_costa.common.data.entity.CharacterEntity

interface InteractListToPresenter {
    fun didFetchCharactersOnAPI(characterEntityList: MutableList<CharacterEntity>)
}