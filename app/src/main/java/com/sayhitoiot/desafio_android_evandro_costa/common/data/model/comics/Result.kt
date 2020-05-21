package com.sayhitoiot.desafio_android_evandro_costa.common.data.model.comics

import com.sayhitoiot.desafio_android_evandro_costa.common.data.model.characters.Events

data class Result(
    val characters: Characters,
    val collectedIssues: List<CollectedIssue>,
    val collections: List<Collection>,
    val creators: Creators,
    val dates: List<Date>,
    val description: String,
    val diamondCode: String,
    val digitalId: String,
    val ean: String,
    val events: Events,
    val format: String,
    val id: String,
    val images: List<Image>,
    val isbn: String,
    val issn: String,
    val issueNumber: String,
    val modified: String,
    val pageCount: String,
    val prices: List<Price>,
    val resourceURI: String,
    val series: Series,
    val stories: Stories,
    val textObjects: List<TextObject>,
    val thumbnail: Thumbnail,
    val title: String,
    val upc: String,
    val urls: List<Url>,
    val variantDescription: String,
    val variants: List<Variant>
)