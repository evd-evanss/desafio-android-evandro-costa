package com.sayhitoiot.desafio_android_evandro_costa.features.list.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.button.MaterialButton
import com.sayhitoiot.desafio_android_evandro_costa.R
import com.sayhitoiot.desafio_android_evandro_costa.common.realm.entity.CharacterEntity
import com.sayhitoiot.desafio_android_evandro_costa.features.details.view.ImageViewer
import com.sayhitoiot.desafio_android_evandro_costa.features.list.presenter.PresenterList
import com.sayhitoiot.desafio_android_evandro_costa.features.list.presenter.contract.PresenterListToPresenter
import com.sayhitoiot.desafio_android_evandro_costa.features.list.presenter.contract.PresenterListToView
import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.layout_developer.*

/**
 * @author Evandro Ribeiro Costa (revandro77@yahoo.com.br)
 */

class ActivityList : AppCompatActivity(), PresenterListToView{

    private var swipeRefresh: SwipeRefreshLayout? = null
    private var recyclerView: RecyclerView? = null
    private var progress: DilatingDotsProgressBar? = null
    private val context: Context? = this
    private var textError: TextView? = null
    private var imageError: ImageView? = null
    private var imageMenu: ImageView? = null
    private var imagePhoto: ImageView? = null
    private var bottomSheetDeveloper: BottomSheetBehavior<ConstraintLayout>? = null
    private var linkedinButton: ImageView? = null
    private var whatsAppButton: ImageView? = null
    private var emailButton: ImageView? = null

    override val state: Int?
        get() = bottomSheetDeveloper?.state

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
        setContentView(R.layout.activity_list)
        presenter.onCreate()
    }

    override fun initializeViews() {
        swipeRefresh = activityList_swipeRefresh
        recyclerView = activityList_recyclerView
        progress = activityList_dilatingDotsProgressBar
        textError = activityList_text_error
        imageError = activityList_imageView_error
        progress?.show()
        imagePhoto = imageView_Developer
        ImageViewer().setRoundImage(this, R.drawable.ic_photo, imagePhoto)

        imageMenu = activityList_imageView_menu
        bottomSheetDeveloper = BottomSheetBehavior.from(constraintLayout_bottom_sheet_developer)
        linkedinButton = layout_developer_imageView_linkedin
        whatsAppButton = layout_developer_imageView_whatsapp
        emailButton = layout_developer_imageView_email

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.reverseLayout = true
        recyclerView?.layoutManager = layoutManager
        recyclerView?.itemAnimator = DefaultItemAnimator()
        recyclerView?.adapter = adapter

        presenter.fetchCharacters()
        actionMenu()
        actionLinkedin()
        actionWhatsApp()
        actionEmail()
        actionRequestPagination()
    }

    private fun actionMenu() {
        imageMenu?.setOnClickListener { presenter.imageMenuTapped() }
    }

    private fun actionLinkedin() {
        linkedinButton?.setOnClickListener { presenter.linkedinButtonTapped() }
    }

    private fun actionWhatsApp() {
        whatsAppButton?.setOnClickListener { presenter.whatsAppButtonTapped() }
    }

    private fun actionEmail() {
        emailButton?.setOnClickListener { presenter.emailButtonTapped() }
    }

    override fun openProfileUserLinkedin(profileLinkedin: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(profileLinkedin))
        startActivity(intent)
    }

    override fun openWhatsApp(celphone: String) {
        val intent =
            Intent(Intent.ACTION_VIEW, Uri.parse(celphone))
        startActivity(intent)
    }

    override fun openEmail(email: String) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        startActivity(Intent.createChooser(intent, "Enviar e-mail por..."))
    }

    private fun actionRequestPagination() {
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

    override fun setVisibilityForLayoutDeveloper(state: Int) {
        bottomSheetDeveloper?.state = state
    }
}
