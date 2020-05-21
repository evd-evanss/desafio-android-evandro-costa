package com.sayhitoiot.desafio_android_evandro_costa.common.extensions


fun  String.toHttps(patch: String, extension: String) : String {
    return ("$patch.$extension").replace("http", "https")
}