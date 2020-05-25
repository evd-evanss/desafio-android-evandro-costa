package com.sayhitoiot.desafio_android_evandro_costa.features.details.presenter.contract

/**
 * @author Evandro Ribeiro Costa (revandro77@yahoo.com.br)
 */

interface DetailsPresenterToPresenter {
    fun onCreate()
    fun didFinishInitialize()
    fun buttonDetailsTapped(characterId: String)
    fun buttonBackTapped()
}