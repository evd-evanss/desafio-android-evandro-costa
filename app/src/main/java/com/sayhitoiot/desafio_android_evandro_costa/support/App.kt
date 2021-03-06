package com.sayhitoiot.desafio_android_evandro_costa.support

import android.app.Application
import com.sayhitoiot.desafio_android_evandro_costa.common.realm.RealmDB
import com.sayhitoiot.desafio_android_evandro_costa.common.services.SyncService

/**
 * @author Evandro Ribeiro Costa (revandro77@yahoo.com.br)
 */

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        RealmDB.configureRealm(applicationContext)
        SyncService()
    }

    override fun onTerminate() {
        RealmDB.terminate()
        super.onTerminate()
    }

}