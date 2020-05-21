package com.sayhitoiot.desafio_android_evandro_costa.common.data.model.comics

data class Data(
    val count: String,
    val limit: String,
    val offset: String,
    val results: List<Result>,
    val total: String
)