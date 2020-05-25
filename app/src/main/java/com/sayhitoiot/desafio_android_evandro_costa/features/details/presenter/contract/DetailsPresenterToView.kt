package com.sayhitoiot.desafio_android_evandro_costa.features.details.presenter.contract

import com.sayhitoiot.desafio_android_evandro_costa.common.realm.entity.ComicsEntity

/**
 * @author Evandro Ribeiro Costa (revandro77@yahoo.com.br)
 */

interface DetailsPresenterToView {

    fun initializeViewsForDetails()
    fun renderCharacterDetails()
    fun renderComicsMostExpensive(
        comicsEntity: ComicsEntity,
        priceMostExpensive: String
    )
    fun showError(messageError: String)
    fun renderImageComicsWithDrawable(resource: Int)
    fun renderPreviousView()

}