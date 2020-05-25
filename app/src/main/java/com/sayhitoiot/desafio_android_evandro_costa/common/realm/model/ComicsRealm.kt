package com.sayhitoiot.desafio_android_evandro_costa.common.realm.model

import com.sayhitoiot.desafio_android_evandro_costa.common.realm.RealmDB
import io.realm.RealmList
import io.realm.RealmObject

/**
 * @author Evandro Ribeiro Costa (revandro77@yahoo.com.br)
 */

open class ComicsRealm : RealmObject() {
    var id: String? = RealmDB.DEFAULT_STRING
    var title: String = RealmDB.DEFAULT_STRING
    var price: RealmList<Float> = RealmList()
    var description: String = RealmDB.DEFAULT_STRING
    var thumbnail: String = RealmDB.DEFAULT_STRING
}

