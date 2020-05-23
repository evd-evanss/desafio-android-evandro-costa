package com.sayhitoiot.desafio_android_evandro_costa.common.api

import com.sayhitoiot.desafio_android_evandro_costa.common.api.model.comics.ResultDataComics
import com.sayhitoiot.desafio_android_evandro_costa.common.api.model.characters.ReturnDataCharacter
import com.sayhitoiot.desafio_android_evandro_costa.common.utils.Constants.Companion.END_POINT_CHARACTER
import com.sayhitoiot.desafio_android_evandro_costa.common.utils.Constants.Companion.END_POINT_COMICS
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApi{

    @GET(END_POINT_CHARACTER)
    fun getCharacters(@Query("ts") ts: String,
                      @Query("apikey") apiKey: String,
                      @Query("hash")hash: String,
                      @Query("limit") limit: Int,
                      @Query("offset") offset: Int): Call<ReturnDataCharacter>

    @GET(END_POINT_COMICS)
    fun getDetailsHQ(@Path("characterid") characterId: Int,
                     @Query("ts") ts: String,
                     @Query("apikey") apiKey: String,
                     @Query("hash")hash: String): Call<ResultDataComics>

}

interface OnGetMarvelCallback{
    fun onSuccess(marvelResponse: ReturnDataCharacter)
    fun onError()
}

interface OnGetComicsCallback{
    fun onSuccess(marvelResponse: ResultDataComics)
    fun onError()
}