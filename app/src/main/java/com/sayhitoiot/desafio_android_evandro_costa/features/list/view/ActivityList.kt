package com.sayhitoiot.desafio_android_evandro_costa.features.list.view

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.sayhitoiot.desafio_android_evandro_costa.R
import com.sayhitoiot.desafio_android_evandro_costa.common.data.entity.CharacterEntity
import com.sayhitoiot.desafio_android_evandro_costa.features.list.presenter.PresenterList
import com.sayhitoiot.desafio_android_evandro_costa.features.list.presenter.contract.PresenterListToPresenter
import com.sayhitoiot.desafio_android_evandro_costa.features.list.presenter.contract.PresenterListToView
import kotlinx.android.synthetic.main.activity_main.*


class ActivityList : AppCompatActivity(), PresenterListToView{

    private var swipeRefresh: SwipeRefreshLayout? = null
    private var recyclerView: RecyclerView? = null
    private val context: Context? = this

    private val presenter: PresenterListToPresenter by lazy {
        PresenterList(this)
    }

    private val adapter: AdapterCharacter by lazy {
        AdapterCharacter (
            mutableListOf(),
            context
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.onCreate()
    }

    override fun initializeViews() {
        swipeRefresh = activityList_swipeRefresh
        recyclerView = activityList_recyclerView
        val layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = false
        recyclerView?.layoutManager = layoutManager
        recyclerView?.itemAnimator = DefaultItemAnimator()
        recyclerView?.adapter = adapter
        presenter.fetchCharactersOnAPI()
        actionRefresh()
    }

    private fun actionRefresh() {
        swipeRefresh?.setOnRefreshListener {
            presenter.onRefreshScroll()
        }
    }

    override fun didFetchCharactersOnAPI(characterList: MutableList<CharacterEntity>) {
        swipeRefresh?.isRefreshing = false
        recyclerView?.setItemViewCacheSize(characterList.size)
        adapter.updateList(characterList)
        recyclerView?.scheduleLayoutAnimation()
    }

    override fun showMessageEnd(message: String) {
        swipeRefresh?.isRefreshing = false
        Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }


}
