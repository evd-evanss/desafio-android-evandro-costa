package com.sayhitoiot.desafio_android_evandro_costa.common.data.model.comics

import com.sayhitoiot.desafio_android_evandro_costa.common.data.model.characters.ItemX

data class Creators(
    val available: String,
    val collectionURI: String,
    val items: List<ItemX>,
    val returned: String
)