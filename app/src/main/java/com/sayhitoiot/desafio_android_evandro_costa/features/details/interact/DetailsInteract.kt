package com.sayhitoiot.desafio_android_evandro_costa.features.details.interact

import com.sayhitoiot.desafio_android_evandro_costa.common.api.OnGetComicsCallback
import com.sayhitoiot.desafio_android_evandro_costa.common.extensions.toUrl
import com.sayhitoiot.desafio_android_evandro_costa.common.repository.ApiDataManager
import com.sayhitoiot.desafio_android_evandro_costa.common.repository.InteractToApi
import com.sayhitoiot.desafio_android_evandro_costa.common.data.entity.ComicsEntity
import com.sayhitoiot.desafio_android_evandro_costa.common.data.model.comics.Price
import com.sayhitoiot.desafio_android_evandro_costa.common.data.model.comics.Result
import com.sayhitoiot.desafio_android_evandro_costa.common.data.model.comics.ResultDataComics
import com.sayhitoiot.desafio_android_evandro_costa.features.details.interact.contract.DetailsInteractToInteract
import com.sayhitoiot.desafio_android_evandro_costa.features.details.interact.contract.DetailsInteractToPresenter

class DetailsInteract(private val presenter: DetailsInteractToPresenter) : DetailsInteractToInteract  {

    companion object{
        const val TAG = "details-interact"
    }

    private val repository: InteractToApi = ApiDataManager()
    private var comicsList: MutableList<ComicsEntity> = mutableListOf()
    private val results: MutableList<Result> = mutableListOf()

    override fun fetchComicsMostExpensive(characterId: String?) {
        fetchDataOnAPI(characterId)
    }

    private fun fetchDataOnAPI(characterId: String?) {
        repository.getDetailsHQ(characterId, object: OnGetComicsCallback {
            override fun onSuccess(marvelResponse: ResultDataComics) {

                results.addAll(marvelResponse.data.results)

                val result =
                    fetchComicsByMaxPrice(results)

                if(result != null) {
                    ComicsEntity(
                        title = result.description ?: "",
                        price = result.price,
                        description = result.description,
                        thumbnail = result.thumbnail
                    ).also {
                        presenter.didFinishFetchDataOnAPI(it)
                    }
                } else {
                    presenter.didFinishFetchDataOnAPIWithError("As informações sobre quadrinhos desse personagem foram removidas ou não existe!")
                }



            }

            override fun onError() {

            }

        })
    }

    private fun fetchComicsByMaxPrice(results: MutableList<Result>): ComicsEntity? {

        if(results.isEmpty()) {
            return null
        }

        val pricesFloat = mutableListOf<Float>()
        val prices = mutableListOf<Price>()

        results.forEach {
            prices.addAll(it.prices)
        }

        for ((cont, it) in results.withIndex()) {
            comicsList.add(
                ComicsEntity(
                    title = it.title,
                    description = it.description,
                    price = prices[cont].price.toFloat().toString(),
                    thumbnail = "".toUrl(it.thumbnail.path , it.thumbnail.extension)
                )
            )
        }


        prices.forEach {
            pricesFloat.add(it.price.toFloat())
        }

        val max: String? = pricesFloat.max().toString().trim()

        comicsList.forEach {
            it.description ?: ""
            it.price ?: ""
            it.title ?: ""
            it.thumbnail ?: ""
        }

        return comicsList.filter {
            it.price == max
        }.first()
    }
}