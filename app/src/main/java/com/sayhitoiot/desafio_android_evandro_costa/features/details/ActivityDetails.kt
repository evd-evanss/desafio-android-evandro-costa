package com.sayhitoiot.desafio_android_evandro_costa.features.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.sayhitoiot.desafio_android_evandro_costa.R
import com.sayhitoiot.desafio_android_evandro_costa.common.data.entity.ComicsEntity
import com.sayhitoiot.desafio_android_evandro_costa.features.details.presenter.DetailsPresenter
import com.sayhitoiot.desafio_android_evandro_costa.features.details.presenter.contract.DetailsPresenterToPresenter
import com.sayhitoiot.desafio_android_evandro_costa.features.details.presenter.contract.DetailsPresenterToView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*
import java.util.*

class ActivityDetails : AppCompatActivity() , DetailsPresenterToView{

    companion object{
        const val TAG = "activity_details"
    }

    private val presenter: DetailsPresenterToPresenter by lazy {
        DetailsPresenter(this)
    }

    private var textHeader: TextView? = null
    private var textName: TextView? = null
    private var textDescription: TextView? = null
    private var imageHero: ImageView? = null
    private var buttonHqMostValuable: MaterialButton? = null
    private var buttonBack: ImageView? = null

    private var characterId: String? = ""
    private var name: String? = ""
    private var description: String? = ""
    private var path: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        characterId = intent.extras?.getString("characterId", "")
        Log.d(TAG, "$characterId")
        name = intent.extras?.getString("name", "")
        description = intent.extras?.getString("description", "")
        path = intent.extras?.getString("path", "")
        presenter.onCreate()
    }

    override fun onBackPressed() {
        presenter.buttonBackTapped()
    }

    override fun initializeViews() {
        textHeader = activityDetails_textView_header
        textName = activityDetails_textView_name
        textDescription = activityDetails_textView_description
        imageHero = activityDetails_imageView_thumbnail
        buttonHqMostValuable = activityDetails_materialButton_hq
        buttonHqMostValuable?.setOnClickListener { presenter.buttonDetailsTapped(characterId) }
        buttonBack = activityDetails_imageView_back
        buttonBack?.setOnClickListener { presenter.buttonBackTapped() }
        presenter.didFinishInitialize()
    }

    override fun renderCharacterDetails() {
        textHeader?.text = "PERSONAGEM"
        buttonHqMostValuable?.visibility = VISIBLE
        buttonBack?.setOnClickListener { super.onBackPressed() }
        textName?.text = this.name?.toUpperCase(Locale.ROOT)
        textDescription?.text = this.description?.toUpperCase(Locale.ROOT)
        Picasso
            .get()
            .load((this.path))
            .centerCrop()
            .fit()
            .error(R.drawable.ic_launcher_background)
            .into(imageHero)
    }

    override fun renderComicsMostExpensive(comicsEntity: ComicsEntity) {
        textHeader?.text = "REVISTA"
        textName?.text = comicsEntity.title.toUpperCase(Locale.ROOT)
        buttonBack?.setOnClickListener { presenter.buttonBackTapped() }
        textDescription?.text = comicsEntity.description?.toUpperCase(Locale.ROOT)
        buttonHqMostValuable?.visibility = GONE
        Picasso
            .get()
            .load((comicsEntity.thumbnail))
            .centerCrop()
            .fit()
            .error(R.drawable.ic_launcher_background)
            .into(imageHero)
    }

    override fun updateAdapter(comicsEntity: ComicsEntity) {
        textName?.text = comicsEntity.title
        textDescription?.text = comicsEntity.description?.toUpperCase()
        Picasso
            .get()
            .load((comicsEntity.thumbnail))
            .centerCrop()
            .fit()
            .error(R.drawable.ic_launcher_background)
            .into(imageHero)
    }
}
