package com.sayhitoiot.desafio_android_evandro_costa.features.details

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.button.MaterialButton
import com.sayhitoiot.desafio_android_evandro_costa.R
import com.sayhitoiot.desafio_android_evandro_costa.common.realm.entity.ComicsEntity
import com.sayhitoiot.desafio_android_evandro_costa.features.details.presenter.DetailsPresenter
import com.sayhitoiot.desafio_android_evandro_costa.features.details.presenter.contract.DetailsPresenterToPresenter
import com.sayhitoiot.desafio_android_evandro_costa.features.details.presenter.contract.DetailsPresenterToView
import com.squareup.picasso.Callback
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar
import kotlinx.android.synthetic.main.activity_details.*
import java.lang.Exception
import java.util.*

class ActivityDetails : AppCompatActivity() , DetailsPresenterToView{

    companion object{
        const val TAG = "activity_details"
    }

    private val presenter: DetailsPresenterToPresenter by lazy {
        DetailsPresenter(this)
    }

    private var containerDetails: ConstraintLayout? = null
    private var textHeader: TextView? = null
    private var textName: TextView? = null
    private var textDescription: TextView? = null
    private var imageHero: ImageView? = null
    private var buttonHqMostValuable: MaterialButton? = null
    private var buttonBack: ImageView? = null
    private var progress: DilatingDotsProgressBar? = null
    private var progressFetchComics: DilatingDotsProgressBar? = null

    private var containerHQ: ConstraintLayout? = null
    private var textTitleHQ: TextView? = null
    private var textDescriptionHQ: TextView? = null
    private var imageHeroHQ: ImageView? = null
    private var textPrice: TextView? = null

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

    override fun initializeViewsForDetails() {
        containerDetails = activityDetails_constraintLayout_containerDetails
        textHeader = activityDetails_textView_subtitle
        textName = activityDetails_textView_name
        textDescription = activityDetails_textView_description
        imageHero = activityDetails_imageView_thumbnail
        buttonHqMostValuable = activityDetails_materialButton_hq
        progressFetchComics = activityDetail_dilatingDotsProgressBar_fetchComics
        buttonHqMostValuable?.setOnClickListener {
            buttonHqMostValuable?.visibility = INVISIBLE
            progressFetchComics?.show()
            characterId?.let { it -> presenter.buttonDetailsTapped(it) }
        }
        buttonBack = activityDetails_imageView_back
        buttonBack?.setOnClickListener {
            presenter.buttonBackTapped()
        }
        progress = activityDetail_dilatingDotsProgressBar
        containerHQ = activityDetails_constraintLayout_containerHQ
        textTitleHQ = activityDetails_textView_titleHQ
        textDescriptionHQ = activityDetails_textView_descriptionHQ
        imageHeroHQ = activityDetails_imageView_thumbnailHQ
        textPrice = activityDetails_textView_price
        presenter.didFinishInitialize()
    }

    override fun renderCharacterDetails() {
        setAnimationGone(containerHQ!!)
        setAnimationVisibility(containerDetails!!)
        progress?.hide()
        textHeader?.text = getString(R.string.details_subtitle_character)
        buttonHqMostValuable?.visibility = VISIBLE
        buttonBack?.setOnClickListener { super.onBackPressed() }
        textName?.text = this.name?.toUpperCase(Locale.ROOT)
        textDescription?.text = this.description?.toUpperCase(Locale.ROOT)
        Picasso
            .get()
            .load((this.path))
            .centerCrop()
            .fit()
            .error(R.drawable.ic_error)
            .into(imageHero)
    }

    override fun renderComicsMostExpensive(
        comicsEntity: ComicsEntity,
        priceMostExpensive: String
    ) {
        buttonHqMostValuable?.visibility = GONE
        progressFetchComics?.hide()
        progress?.show()
        setAnimationVisibility(containerHQ!!)
        setAnimationGone(containerDetails!!)
        textHeader?.text = getString(R.string.details_subtitle_most_valuable)
        textTitleHQ?.text = comicsEntity.title.toUpperCase(Locale.ROOT)
        textPrice?.text = "U$ $priceMostExpensive"
        buttonBack?.setOnClickListener { presenter.buttonBackTapped() }
        textDescriptionHQ?.text = comicsEntity.description.toUpperCase(Locale.ROOT)

        Picasso
            .get()
            .load(comicsEntity.thumbnail)
            .centerCrop()
            .fit()
            .error(R.drawable.ic_error)
            .memoryPolicy(MemoryPolicy.NO_CACHE)
            .into(imageHeroHQ, object : Callback{
                override fun onSuccess() {
                    progress?.hide()
                }

                override fun onError(e: Exception?) {
                    progress?.hide()
                    Log.d("errorOnLoad", "error onLoading: $e")
                }
            })
    }

    override fun renderImageComicsWithDrawable(drawable: Int) {
        buttonHqMostValuable?.visibility = GONE
        progressFetchComics?.hide()
        Picasso
            .get()
            .load(drawable)
            .centerCrop()
            .fit()
            .error(R.drawable.ic_error)
            .memoryPolicy(MemoryPolicy.NO_CACHE)
            .into(imageHeroHQ, object : Callback{
                override fun onSuccess() {
                    progress?.hide()
                }

                override fun onError(e: Exception?) {
                    progress?.hide()
                    Log.d("errorOnLoad", "error onLoading: $e")
                }
            })
    }

    override fun showError(messageError: String) {
        Toast.makeText(this, messageError, Toast.LENGTH_LONG).show()
    }

    private fun setAnimationGone(view: View) {

        view.animate()
            .translationY(view.height.toFloat())
            .alpha(0.0f)
            .rotation(360f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    view.visibility = GONE
                }
            })
    }

    private fun setAnimationVisibility(view: View) {
        view.animate()
            .translationY(view.height.toFloat())
            .rotation(720f)
            .alpha(1.0f)
            .translationY(0f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    view.visibility = VISIBLE
                }
            })

    }

}
