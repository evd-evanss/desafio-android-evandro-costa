package com.sayhitoiot.desafio_android_evandro_costa.features.list.presenter.contract

import com.sayhitoiot.desafio_android_evandro_costa.common.realm.entity.CharacterEntity

/**
 * @author Evandro Ribeiro Costa (revandro77@yahoo.com.br)
 */

interface PresenterListToView {
    val state: Int?
    fun initializeViews()
    fun didFetchCharactersOnAPI(characterList: MutableList<CharacterEntity>)
    fun showMessageEnd(message: String)
    fun renderViewsForError()
    fun setVisibilityForLayoutDeveloper(state: Int)
    fun openProfileUserLinkedin(profileLinkedin: String)
    fun openWhatsApp(celphone: String)
    fun openEmail(email: String)
}