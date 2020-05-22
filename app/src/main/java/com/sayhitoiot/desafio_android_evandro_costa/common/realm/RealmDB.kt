package com.sayhitoiot.desafio_android_evandro_costa.common.realm

import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * @author Evandro Ribeiro Costa (revandro77@yahoo.com.br)
 */

object RealmDB {

    const val DEFAULT_STRING = ""

    fun configureRealm(context: Context) {
        Realm.init(context)

        val mRealmConfiguration = RealmConfiguration.Builder()
            .name("marvel-database.realm")
            .schemaVersion(1)
            .build()

        Realm.setDefaultConfiguration(mRealmConfiguration)
        Realm.getInstance(mRealmConfiguration)

    }

    fun terminate() {
        Realm.getDefaultInstance().close()
    }


}