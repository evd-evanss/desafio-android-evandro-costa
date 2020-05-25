package com.sayhitoiot.desafio_android_evandro_costa.features.details.presenter

import com.sayhitoiot.desafio_android_evandro_costa.common.realm.entity.ComicsEntity
import com.sayhitoiot.desafio_android_evandro_costa.features.details.interact.DetailsInteract
import com.sayhitoiot.desafio_android_evandro_costa.features.details.interact.contract.DetailsInteractToInteract
import com.sayhitoiot.desafio_android_evandro_costa.features.details.interact.contract.DetailsInteractToPresenter
import com.sayhitoiot.desafio_android_evandro_costa.features.details.presenter.contract.DetailsPresenterToPresenter
import com.sayhitoiot.desafio_android_evandro_costa.features.details.presenter.contract.DetailsPresenterToView

/**
 * @author Evandro Ribeiro Costa (revandro77@yahoo.com.br)
 */

class DetailsPresenter(private val view: DetailsPresenterToView) : DetailsPresenterToPresenter,
    DetailsInteractToPresenter {

    private var stack = 0

    companion object{
        const val TAG = "details-presenter"
    }

    private val interact: DetailsInteractToInteract by lazy {
        DetailsInteract(this)
    }

    override fun onCreate() {
        stack = 1
        view.initializeViewsForDetails()
    }

    override fun buttonDetailsTapped(characterId: String) {
        interact.fetchComicsMostExpensive(characterId)
    }

    override fun buttonBackTapped() {

        when (stack) {
            1 -> {
                view.renderPreviousView()
                stack--
            }
            2 -> {
                view.renderCharacterDetails()
                stack--
            }
        }

    }

    override fun didFinishFetchData(comicsEntity: ComicsEntity) {
        view.renderComicsMostExpensive(comicsEntity, getPriceMostExpensive(comicsEntity))
        stack = 2
    }

    private fun getPriceMostExpensive(comicsEntity: ComicsEntity) : String {
        return comicsEntity.price.max().toString()
    }

    override fun didFinishFetchDataOnAPIWithError(messageError: String) {
        view.showError(messageError)
    }

    override fun didFinishInitialize() {
        view.renderCharacterDetails()
        stack = 1
    }
}