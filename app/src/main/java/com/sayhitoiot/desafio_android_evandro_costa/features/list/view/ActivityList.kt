package com.sayhitoiot.desafio_android_evandro_costa.features.list.view

import android.content.Context
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.sayhitoiot.desafio_android_evandro_costa.R
import com.sayhitoiot.desafio_android_evandro_costa.common.realm.RealmDB
import com.sayhitoiot.desafio_android_evandro_costa.common.realm.entity.CharacterEntity
import com.sayhitoiot.desafio_android_evandro_costa.features.list.presenter.PresenterList
import com.sayhitoiot.desafio_android_evandro_costa.features.list.presenter.contract.PresenterListToPresenter
import com.sayhitoiot.desafio_android_evandro_costa.features.list.presenter.contract.PresenterListToView
import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar
import kotlinx.android.synthetic.main.activity_main.*


class ActivityList : AppCompatActivity(), PresenterListToView{

    private var swipeRefresh: SwipeRefreshLayout? = null
    private var recyclerView: RecyclerView? = null
    private var progress: DilatingDotsProgressBar? = null
    private val context: Context? = this
    private var textError: TextView? = null
    private var imageError: ImageView? = null

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
        progress = activityList_dilatingDotsProgressBar
        textError = activityList_text_error
        imageError = activityList_imageView_error
        progress?.show()
        val layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = false
        recyclerView?.layoutManager = layoutManager
        recyclerView?.itemAnimator = DefaultItemAnimator()
        recyclerView?.adapter = adapter
        presenter.fetchCharacters()
        actionRefresh()
    }

    private fun actionRefresh() {
        swipeRefresh?.setOnRefreshListener {
            presenter.onRefreshScroll()
        }
    }

    override fun didFetchCharactersOnAPI(characterList: MutableList<CharacterEntity>) {
        textError?.visibility = GONE
        imageError?.visibility = GONE
        progress?.hide()
        swipeRefresh?.isRefreshing = false
        recyclerView?.setItemViewCacheSize(characterList.size)
        adapter.updateList(characterList)
        recyclerView?.scheduleLayoutAnimation()
    }

    override fun showMessageEnd(message: String) {
        swipeRefresh?.isRefreshing = false
        progress?.hide()
        Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun renderViewsForError() {
        textError?.visibility = VISIBLE
        imageError?.visibility = VISIBLE
        swipeRefresh?.isRefreshing = false
        progress?.hide()
    }
}
