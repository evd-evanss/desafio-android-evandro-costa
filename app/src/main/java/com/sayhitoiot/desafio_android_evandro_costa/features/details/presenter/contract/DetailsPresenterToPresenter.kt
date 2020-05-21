package com.sayhitoiot.desafio_android_evandro_costa.features.details.presenter.contract

interface DetailsPresenterToPresenter {
    fun onCreate()
    fun didFinishInitialize()
    fun buttonDetailsTapped(characterId: String?)
    fun buttonBackTapped()
}