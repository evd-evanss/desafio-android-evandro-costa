package com.sayhitoiot.desafio_android_evandro_costa.features.list.interact.contract

interface InteractListToInteract {
    fun fetchCharactersOnAPI(offset: Int, limit: Int)
}