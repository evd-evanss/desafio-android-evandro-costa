package com.sayhitoiot.desafio_android_evandro_costa.common.realm.entity

import android.util.Log
import com.sayhitoiot.desafio_android_evandro_costa.common.realm.RealmDB
import com.sayhitoiot.desafio_android_evandro_costa.common.realm.model.ComicsRealm
import io.realm.Realm
import io.realm.RealmList

class ComicsEntity (
    val id: String?,
    val title: String,
    val price: RealmList<Float>,
    val description: String,
    val thumbnail: String
) {

    constructor(comicsRealm: ComicsRealm) : this(
        id = comicsRealm.id,
        title = comicsRealm.title,
        price = comicsRealm.price,
        description = comicsRealm.description,
        thumbnail = comicsRealm.thumbnail
    )


    companion object {

        private const val TAG = "character-entity"

        fun create(
            id: String,
            title: String,
            price: RealmList<Float>?,
            description: String?,
            thumbnail: String
        ) {
            val realm = Realm.getDefaultInstance()
            realm.beginTransaction()

            val comicsModel = realm.createObject(ComicsRealm::class.java)

            comicsModel.id = id
            comicsModel.title = title
            comicsModel.price = price ?: RealmList()
            comicsModel.description = description ?: RealmDB.DEFAULT_STRING
            comicsModel.thumbnail = thumbnail

            realm.commitTransaction()
            realm.close()
        }

        fun getComicById(id: String) : ComicsEntity? {

            val realm = Realm.getDefaultInstance()

            val comicsModel = realm.where(ComicsRealm::class.java)
                .equalTo("id", id)
                .findFirst()

            var comicsEntity: ComicsEntity? = null

            if (comicsModel != null) {
                comicsEntity = ComicsEntity(comicsModel)
            }

            realm.close()

            Log.d(TAG, "comicsEntity found")

            return comicsEntity
        }

        fun getComicByMostValuable(id: String) : ComicsEntity? {

            val realm = Realm.getDefaultInstance()

            val comicsModel = realm.where(ComicsRealm::class.java)
                .equalTo("id", id)
                .findFirst()

            var comicsEntity: ComicsEntity? = null

            if (comicsModel != null) {
                comicsEntity = ComicsEntity(comicsModel)
            }

            realm.close()

            return comicsEntity
        }

    }

}