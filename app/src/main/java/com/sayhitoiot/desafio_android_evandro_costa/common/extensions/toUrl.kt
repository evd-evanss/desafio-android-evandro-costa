package com.sayhitoiot.desafio_android_evandro_costa.common.extensions

/**
 * @author Evandro Ribeiro Costa (revandro77@yahoo.com.br)
 */

fun toUrl(patch: String, extension: String) : String {
    return ("$patch.$extension").replace("http", "https")
}