package com.sayhitoiot.desafio_android_evandro_costa.common.api

import com.sayhitoiot.desafio_android_evandro_costa.common.data.model.comics.ResultDataComics
import com.sayhitoiot.desafio_android_evandro_costa.common.data.model.characters.ReturnData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApi{

    @GET("characters")
    fun getCharacters(@Query("ts") ts: String, // timestamp
                      @Query("apikey") apiKey: String,
                      @Query("hash")hash: String,
                      @Query("limit") limit: Int,
                      @Query("offset") offset: Int): Call<ReturnData>

    @GET("characters/{characterid}/comics")
    fun getDetailsHQ(@Path("characterid") characterId: Int,
                     @Query("ts") ts: String, // timestamp
                     @Query("apikey") apiKey: String,
                     @Query("hash")hash: String): Call<ResultDataComics>

}

interface OnGetMarvelCallback{
    fun onSuccess(marvelResponse: ReturnData)
    fun onError()
}

interface OnGetComicsCallback{
    fun onSuccess(marvelResponse: ResultDataComics)
    fun onError()
}