package com.sayhitoiot.desafio_android_evandro_costa.features.details.interact

import com.sayhitoiot.desafio_android_evandro_costa.common.api.OnGetComicsCallback
import com.sayhitoiot.desafio_android_evandro_costa.common.extensions.toUrl
import com.sayhitoiot.desafio_android_evandro_costa.common.repository.ApiDataManager
import com.sayhitoiot.desafio_android_evandro_costa.common.repository.InteractToApi
import com.sayhitoiot.desafio_android_evandro_costa.common.data.model.comics.Price
import com.sayhitoiot.desafio_android_evandro_costa.common.data.model.comics.Result
import com.sayhitoiot.desafio_android_evandro_costa.common.data.model.comics.ResultDataComics
import com.sayhitoiot.desafio_android_evandro_costa.common.realm.RealmDB
import com.sayhitoiot.desafio_android_evandro_costa.common.realm.entity.ComicsEntity
import com.sayhitoiot.desafio_android_evandro_costa.features.details.interact.contract.DetailsInteractToInteract
import com.sayhitoiot.desafio_android_evandro_costa.features.details.interact.contract.DetailsInteractToPresenter
import io.realm.RealmList

class DetailsInteract(private val presenter: DetailsInteractToPresenter) : DetailsInteractToInteract  {

    companion object{
        const val TAG = "details-interact"
    }

    private val repository: InteractToApi = ApiDataManager()
    private val results: MutableList<Result> = mutableListOf()

    override fun fetchComicsMostExpensive(characterId: String) {
        val comicsList = ComicsEntity.getComicById(characterId)
        if(comicsList == null) {
            fetchDataOnAPI(characterId)
        } else {
            ComicsEntity.getComicById(characterId)?.let { presenter.didFinishFetchData(it) }
        }
    }

    private fun fetchDataOnAPI(characterId: String) {

        repository.getDetailsHQ(characterId, object: OnGetComicsCallback {
            override fun onSuccess(marvelResponse: ResultDataComics) {

                results.addAll(marvelResponse.data.results)

                val result =
                    fetchComicsByMaxPrice(results, characterId)

                if(result != null) {
                    presenter.didFinishFetchData(result)
                } else {
                    presenter.didFinishFetchDataOnAPIWithError("As informações sobre quadrinhos desse personagem foram removidas ou não existe!")
                }

            }

            override fun onError() {

            }

        })
    }

    private fun fetchComicsByMaxPrice(
        results: MutableList<Result>,
        characterId: String
    ): ComicsEntity? {

        if(results.isEmpty()) {
            return null
        }

        val listPrices: RealmList<Float> = RealmList()

        results.forEach {
            for ((cont, prices) in it.prices.withIndex()) {
                listPrices.add(prices.price.toFloat())
            }
        }



        results.forEach {
            ComicsEntity.create (
                id = characterId,
                title = it.title,
                description = it.description,
                price = listPrices,
                thumbnail = "".toUrl(it.thumbnail.path , it.thumbnail.extension)
            )
        }

        return ComicsEntity.getComicByMostValuable(characterId)
    }
}