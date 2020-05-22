package com.sayhitoiot.desafio_android_evandro_costa.features.list.presenter.contract

import com.sayhitoiot.desafio_android_evandro_costa.common.realm.entity.CharacterEntity

interface PresenterListToView {
    fun initializeViews()
    fun didFetchCharactersOnAPI(characterList: MutableList<CharacterEntity>)
    fun showMessageEnd(message: String)
    fun renderViewsForError()
}