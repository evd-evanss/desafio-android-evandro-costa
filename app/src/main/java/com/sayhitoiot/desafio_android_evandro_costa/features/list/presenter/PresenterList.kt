package com.sayhitoiot.desafio_android_evandro_costa.features.list.presenter

import com.sayhitoiot.desafio_android_evandro_costa.features.list.interact.InteractList
import com.sayhitoiot.desafio_android_evandro_costa.features.list.interact.contract.InteractListToInteract
import com.sayhitoiot.desafio_android_evandro_costa.features.list.interact.contract.InteractListToPresenter
import com.sayhitoiot.desafio_android_evandro_costa.common.realm.entity.CharacterEntity
import com.sayhitoiot.desafio_android_evandro_costa.features.list.presenter.contract.PresenterListToView
import com.sayhitoiot.desafio_android_evandro_costa.features.list.presenter.contract.PresenterListToPresenter

class PresenterList(private val view: PresenterListToView) : PresenterListToPresenter , InteractListToPresenter {

    private val interact: InteractListToInteract by lazy {
        InteractList(this)
    }

    companion object {
        const val PAGES = 20
        const val MESSAGE_END = "Não há mais personagens..."
    }

    private var limit = 0
    private var offSet = 0

    override fun onCreate() {
        view.initializeViews()
    }

    override fun fetchCharacters() {
        nextPagination()
    }

    override fun onRefreshScroll() {
        nextPagination()
    }

    private fun nextPagination() {
        limit += PAGES
        when {
            limit <= 100 -> interact.fetchCharacters(offSet, limit)
            else -> view.showMessageEnd(MESSAGE_END)
        }
    }

    override fun didFetchCharacters(characterEntityyList: MutableList<CharacterEntity>) {
        view.didFetchCharactersOnAPI(characterEntityyList)
    }

    override fun didFetchCharactersError(error: String) {
        view.renderViewsForError()
        limit = 0
    }

}