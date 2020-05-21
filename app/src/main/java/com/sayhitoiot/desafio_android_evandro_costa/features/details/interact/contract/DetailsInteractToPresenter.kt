package com.sayhitoiot.desafio_android_evandro_costa.features.details.interact.contract

import com.sayhitoiot.desafio_android_evandro_costa.common.data.entity.ComicsEntity

interface DetailsInteractToPresenter {
    fun didFinishFetchDataOnAPI(comicsEntity: ComicsEntity)
}