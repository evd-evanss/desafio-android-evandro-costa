package com.sayhitoiot.desafio_android_evandro_costa.common.realm

import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration


object RealmDB {

    const val DEFAULT_INTEGER = 0
    const val DEFAULT_STRING = ""
    const val DEFAULT_BOOLEAN = false

    fun configureRealm(context: Context) {
        Realm.init(context)

        val mRealmConfiguration = RealmConfiguration.Builder()
            .name("smnow-teste-database.realm")
            .schemaVersion(0L)
            .build()

        Realm.getInstance(mRealmConfiguration)
        Realm.setDefaultConfiguration(mRealmConfiguration)

    }


}