package com.sayhitoiot.desafio_android_evandro_costa.common.data.entity

import com.sayhitoiot.desafio_android_evandro_costa.common.data.model.characters.Thumbnail

data class CharacterEntity(
    val name: String,
    val description: String,
    val id: String,
    val thumbnail: String
)