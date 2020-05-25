package com.sayhitoiot.desafio_android_evandro_costa.common.api.model.characters

data class ReturnDataCharacter(
    val attributionHTML: String,
    val attributionText: String,
    val code: String,
    val copyright: String,
    val `data`: Data,
    val etag: String,
    val status: String
)