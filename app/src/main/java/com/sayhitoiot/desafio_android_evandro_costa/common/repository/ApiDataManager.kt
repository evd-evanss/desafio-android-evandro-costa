package com.sayhitoiot.desafio_android_evandro_costa.common.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sayhitoiot.desafio_android_evandro_costa.common.utils.Constants
import com.sayhitoiot.desafio_android_evandro_costa.common.api.MarvelApi
import com.sayhitoiot.desafio_android_evandro_costa.common.api.OnGetComicsCallback
import com.sayhitoiot.desafio_android_evandro_costa.common.api.OnGetMarvelCallback
import com.sayhitoiot.desafio_android_evandro_costa.common.api.RetrofitClient
import com.sayhitoiot.desafio_android_evandro_costa.common.api.model.comics.Result
import com.sayhitoiot.desafio_android_evandro_costa.common.api.model.comics.ResultDataComics
import com.sayhitoiot.desafio_android_evandro_costa.common.api.model.characters.ReturnData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class ApiDataManager: InteractToApi {

    private var serviceCharacters: MarvelApi
    private var serviceComics: MarvelApi
    private var heroesData: MutableLiveData<ReturnData> = MutableLiveData()
    private var comicsData: MutableLiveData<Result> = MutableLiveData()
    private var client = RetrofitClient()

    companion object {
        const val PUBLIC_KEY = Constants.PUBLIC_KEY
        const val PRIVATE_KEY = Constants.PRIVATE_KEY
    }

    init {
        serviceCharacters = client.webserviceCharacters
        serviceComics = client.webserviceComics
    }

    override fun getCharacter(offset: Int, limit: Int, callback: OnGetMarvelCallback) {
        val ts = System.currentTimeMillis().toString()
        val hash = getMd5(ts)

        serviceCharacters.getCharacters(ts, Constants.PUBLIC_KEY, hash, limit, offset)
            .enqueue(object : Callback<ReturnData> {
                override fun onResponse(call: Call<ReturnData>, response: Response<ReturnData>) {

                    if (response.isSuccessful){
                        if (response.body() != null){
                            Log.i("Response", response.toString())
                            heroesData.postValue(response.body())
                            callback.onSuccess(response.body()!!)
                        } else {
                            callback.onError()
                            Log.e("Response", " response null")
                        }

                    }

                }

                override fun onFailure(call: Call<ReturnData>, t: Throwable) {
                    t.printStackTrace()
                    callback.onError()
                    Log.d("Response", "$t")
                }
            })
    }

    override fun getDetailsHQ(characterId: String?, callback: OnGetComicsCallback) {
        val ts = System.currentTimeMillis().toString()
        val hash = getMd5(ts)
        characterId ?: return

        serviceComics.getDetailsHQ(characterId.toInt(), ts, Constants.PUBLIC_KEY, hash)
            .enqueue(object : Callback<ResultDataComics> {
                override fun onResponse(call: Call<ResultDataComics>, response: Response<ResultDataComics>) {

                    if (response.isSuccessful){
                        val body = response.body()
                        if (body != null) {
                            body.data.results.forEach {
                                comicsData.postValue(it)
                            }
                            callback.onSuccess(body)
                            Log.d("Response", response.toString())
                        } else {
                            callback.onError()
                            Log.e("Response", " response null")
                        }

                    }
                    else {
                        callback.onError()
                        Log.e("Response", response.raw().networkResponse.toString())
                    }

                }

                override fun onFailure(call: Call<ResultDataComics>, t: Throwable) {
                    callback.onError()
                    t.printStackTrace()
                }
            })
    }

    private fun getMd5(ts: String): String {
        try {

            val md = MessageDigest.getInstance("MD5")

            val digestMD5 =
                md.digest(ts.toByteArray()
                    + PRIVATE_KEY.toByteArray()
                    + PUBLIC_KEY.toByteArray())

            val value = BigInteger(1, digestMD5)

            var hashtext = value.toString(16)
            while (hashtext.length < 32) {
                hashtext = "0$hashtext"
            }
            Log.i("md5", hashtext)

            return hashtext
        } catch (e: NoSuchAlgorithmException) {
            Log.e("md5", e.toString())
            throw RuntimeException(e)
        }
    }

}