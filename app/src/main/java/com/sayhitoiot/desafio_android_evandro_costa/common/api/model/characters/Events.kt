package com.sayhitoiot.desafio_android_evandro_costa.common.api.model.characters

data class Events(
    val available: String,
    val collectionURI: String,
    val items: List<ItemX>,
    val returned: String
)