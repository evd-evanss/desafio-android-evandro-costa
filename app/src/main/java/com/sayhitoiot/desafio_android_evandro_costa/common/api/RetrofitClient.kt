package com.sayhitoiot.desafio_android_evandro_costa.common.api

import com.google.gson.GsonBuilder
import com.sayhitoiot.desafio_android_evandro_costa.common.utils.Constants.Companion.URL_BASE
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

        val webserviceCharacters: MarvelApi by lazy {
            Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build().create(MarvelApi::class.java)
        }

        val webserviceComics: MarvelApi by lazy {
            Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build().create(MarvelApi::class.java)
        }

}