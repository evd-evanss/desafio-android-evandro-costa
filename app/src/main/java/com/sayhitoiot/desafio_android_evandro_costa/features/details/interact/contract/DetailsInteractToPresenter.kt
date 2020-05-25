package com.sayhitoiot.desafio_android_evandro_costa.features.details.interact.contract

import com.sayhitoiot.desafio_android_evandro_costa.common.realm.entity.ComicsEntity

/**
 * @author Evandro Ribeiro Costa (revandro77@yahoo.com.br)
 */

interface DetailsInteractToPresenter {
    fun didFinishFetchData(comicsEntity: ComicsEntity)
    fun didFinishFetchDataOnAPIWithError(messageError: String)
}