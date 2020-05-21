package com.sayhitoiot.desafio_android_evandro_costa.features.details.presenter.contract

import com.sayhitoiot.desafio_android_evandro_costa.common.data.entity.ComicsEntity

interface DetailsPresenterToView {

    fun initializeViewsForDetails()
    fun renderCharacterDetails()
    fun updateAdapter(comicsEntity: ComicsEntity)
    fun renderComicsMostExpensive(comicsEntity: ComicsEntity)
    fun showError(messageError: String)

}