package com.sayhitoiot.desafio_android_evandro_costa.common.data.model.comics

data class Characters(
    val available: String,
    val collectionURI: String,
    val items: List<Item>,
    val returned: String
)