package com.sayhitoiot.desafio_android_evandro_costa.features.list.presenter.contract

interface PresenterListToPresenter {
    fun onCreate()
    fun fetchCharacters()
    fun onRefreshScroll()
}