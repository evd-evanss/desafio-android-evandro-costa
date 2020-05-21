package com.sayhitoiot.desafio_android_evandro_costa.common.repository
import com.sayhitoiot.desafio_android_evandro_costa.common.api.OnGetComicsCallback
import com.sayhitoiot.desafio_android_evandro_costa.common.api.OnGetMarvelCallback

interface InteractToApi {
    fun getCharacter(offset: Int, limit: Int, callback: OnGetMarvelCallback)
    fun getDetailsHQ(characterId: String?, callback:OnGetComicsCallback)
}