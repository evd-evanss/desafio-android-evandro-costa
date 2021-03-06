package com.sayhitoiot.desafio_android_evandro_costa.common.realm.model

import com.sayhitoiot.desafio_android_evandro_costa.common.realm.RealmDB
import io.realm.RealmObject

/**
 * @author Evandro Ribeiro Costa (revandro77@yahoo.com.br)
 */

open class CharacterRealm : RealmObject() {
    var name: String = RealmDB.DEFAULT_STRING
    var description: String = RealmDB.DEFAULT_STRING
    var id: String = RealmDB.DEFAULT_STRING
    var thumbnail: String = RealmDB.DEFAULT_STRING
}
