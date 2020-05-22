package com.sayhitoiot.desafio_android_evandro_costa.common.api.model.comics

import com.sayhitoiot.desafio_android_evandro_costa.common.api.model.characters.ItemX

data class Creators(
    val available: String,
    val collectionURI: String,
    val items: List<ItemX>,
    val returned: String
)