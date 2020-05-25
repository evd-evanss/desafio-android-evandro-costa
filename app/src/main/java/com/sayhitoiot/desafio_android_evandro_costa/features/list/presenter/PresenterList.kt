package com.sayhitoiot.desafio_android_evandro_costa.features.list.presenter

import com.sayhitoiot.desafio_android_evandro_costa.features.list.interact.InteractList
import com.sayhitoiot.desafio_android_evandro_costa.features.list.interact.contract.InteractListToInteract
import com.sayhitoiot.desafio_android_evandro_costa.features.list.interact.contract.InteractListToPresenter
import com.sayhitoiot.desafio_android_evandro_costa.common.realm.entity.CharacterEntity
import com.sayhitoiot.desafio_android_evandro_costa.features.list.presenter.contract.PresenterListToView
import com.sayhitoiot.desafio_android_evandro_costa.features.list.presenter.contract.PresenterListToPresenter

/**
 * @author Evandro Ribeiro Costa (revandro77@yahoo.com.br)
 */

class PresenterList(private val view: PresenterListToView) : PresenterListToPresenter , InteractListToPresenter {

    private val interact: InteractListToInteract by lazy {
        InteractList(this)
    }

    companion object {
        const val PAGES = 20
        const val MESSAGE_END = "Não há mais personagens..."
        const val STATE_EXPANDED = 3
        const val STATE_COLLAPSED = 4
        const val PROFILE_LINKEDIN = "https://www.linkedin.com/in/evandro-costa-0b004058/"
        const val PHONE = "https://api.whatsapp.com/send?phone=5511988587525"
        const val EMAIL = "revandro77@yahoo.com.br"
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

    override fun imageMenuTapped() {
        if(view.state != STATE_EXPANDED) {
            view.setVisibilityForLayoutDeveloper(STATE_EXPANDED)
        } else {
            view.setVisibilityForLayoutDeveloper(STATE_COLLAPSED)
        }
    }

    override fun linkedinButtonTapped() {
        view.openProfileUserLinkedin(PROFILE_LINKEDIN)
    }

    override fun whatsAppButtonTapped() {
        view.openWhatsApp(PHONE)
    }

    override fun emailButtonTapped() {
        view.openEmail(EMAIL)
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