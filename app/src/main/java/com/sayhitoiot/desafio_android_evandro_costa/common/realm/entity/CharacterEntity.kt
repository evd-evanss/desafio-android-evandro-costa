package com.sayhitoiot.desafio_android_evandro_costa.common.realm.entity

import com.sayhitoiot.desafio_android_evandro_costa.common.realm.model.CharacterRealm
import io.realm.Realm

class CharacterEntity (
    val name: String,
    val description: String,
    val id: String,
    val thumbnail: String
) {

    constructor(characterRealm: CharacterRealm) : this(
        name = characterRealm.name,
        description = characterRealm.description,
        id = characterRealm.id,
        thumbnail = characterRealm.thumbnail
    )

    companion object {

        const val TAG = "character-entity"

        fun create(
            name: String,
            description: String,
            id: String,
            thumbnail: String
        ) {
            val realm = Realm.getDefaultInstance()
            realm.beginTransaction()

            val characterModel = realm.createObject(CharacterRealm::class.java)

            characterModel.name = name
            characterModel.description = description
            characterModel.id = id
            characterModel.thumbnail = thumbnail

            realm.commitTransaction()
            realm.close()
        }



        fun delete() {
            val realm = Realm.getDefaultInstance()

            realm.beginTransaction()

            realm.delete(CharacterRealm::class.java)

            realm.commitTransaction()
            realm.close()
        }

        fun getAll() : MutableList<CharacterEntity> {
            val realm = Realm.getDefaultInstance()

            val characterList = realm.where(CharacterRealm::class.java)
                .findAllAsync()
                .map {
                    CharacterEntity(it)
                }

            realm.close()

            return characterList.toMutableList()
        }

    }

}