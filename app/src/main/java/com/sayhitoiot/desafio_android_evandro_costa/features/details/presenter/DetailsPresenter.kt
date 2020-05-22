package com.sayhitoiot.desafio_android_evandro_costa.features.details.presenter

import android.util.Log
import com.sayhitoiot.desafio_android_evandro_costa.common.realm.entity.ComicsEntity
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

    override fun buttonDetailsTapped(characterId: String) {
        interact.fetchComicsMostExpensive(characterId)
    }

    override fun buttonBackTapped() {
        view.renderCharacterDetails()
    }

    override fun didFinishFetchData(comicsEntity: ComicsEntity) {
        view.renderComicsMostExpensive(comicsEntity, getPriceMostExpensive(comicsEntity))
    }

    private fun getPriceMostExpensive(comicsEntity: ComicsEntity) : String {

        comicsEntity.price.forEach {
            Log.d(TAG, "preço: $it")
        }
        return comicsEntity.price.max().toString()
    }

    override fun didFinishFetchDataOnAPIWithError(messageError: String) {
        view.showError(messageError)
    }

    override fun didFinishInitialize() {
        view.renderCharacterDetails()
    }

    companion object{
        const val TAG = "details-presenter"
    }
}