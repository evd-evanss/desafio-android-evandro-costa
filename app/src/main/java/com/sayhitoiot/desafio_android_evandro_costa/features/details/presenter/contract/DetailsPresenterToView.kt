package com.sayhitoiot.desafio_android_evandro_costa.features.details.presenter.contract

import com.sayhitoiot.desafio_android_evandro_costa.common.realm.entity.ComicsEntity

interface DetailsPresenterToView {

    fun initializeViewsForDetails()
    fun renderCharacterDetails()
    fun renderComicsMostExpensive(
        comicsEntity: ComicsEntity,
        priceMostExpensive: String
    )
    fun showError(messageError: String)

}