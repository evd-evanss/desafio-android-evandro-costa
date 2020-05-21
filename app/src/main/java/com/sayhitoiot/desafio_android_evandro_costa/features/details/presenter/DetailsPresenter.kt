package com.sayhitoiot.desafio_android_evandro_costa.features.details.presenter

import com.sayhitoiot.desafio_android_evandro_costa.common.data.entity.ComicsEntity
import com.sayhitoiot.desafio_android_evandro_costa.features.details.interact.DetailsInteract
import com.sayhitoiot.desafio_android_evandro_costa.features.details.interact.contract.DetailsInteractToInteract
import com.sayhitoiot.desafio_android_evandro_costa.features.details.interact.contract.DetailsInteractToPresenter
import com.sayhitoiot.desafio_android_evandro_costa.features.details.presenter.contract.DetailsPresenterToPresenter
import com.sayhitoiot.desafio_android_evandro_costa.features.details.presenter.contract.DetailsPresenterToView

class DetailsPresenter(private val view: DetailsPresenterToView) : DetailsPresenterToPresenter
    , DetailsInteractToPresenter {

    private val interact: DetailsInteractToInteract by lazy {
        DetailsInteract(this)
    }

    override fun onCreate() {
        view.initializeViewsForDetails()
    }

    override fun buttonDetailsTapped(characterId: String?) {
        interact.fetchComicsMostExpensive(characterId)
    }

    override fun buttonBackTapped() {
        view.renderCharacterDetails()
    }

    override fun didFinishFetchDataOnAPI(comicsEntity: ComicsEntity) {
        view.renderComicsMostExpensive(comicsEntity)
        view.updateAdapter(comicsEntity)
    }

    override fun didFinishFetchDataOnAPIWithError(messageError: String) {
        view.showError(messageError)
    }

    override fun didFinishInitialize() {
        view.renderCharacterDetails()
    }
}