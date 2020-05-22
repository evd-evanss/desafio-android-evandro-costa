package com.sayhitoiot.desafio_android_evandro_costa.common.api.model.characters

data class Result(
    val comics: Comics,
    val description: String,
    val events: Events,
    val id: String,
    val modified: String,
    val name: String,
    val resourceURI: String,
    val series: Series,
    val stories: Stories,
    val thumbnail: Thumbnail,
    val urls: List<Url>
)