package com.sayhitoiot.desafio_android_evandro_costa.features.list.view

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.sayhitoiot.desafio_android_evandro_costa.R
import com.sayhitoiot.desafio_android_evandro_costa.common.data.entity.CharacterEntity
import com.sayhitoiot.desafio_android_evandro_costa.features.details.ActivityDetails
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_character.view.*


class AdapterCharacter(
    private val characterEntity: MutableList<CharacterEntity>,
    private val context: Context?
):
    RecyclerView.Adapter<AdapterCharacter.ViewHolder>(){

    companion object {
        const val TAG = "adapter-character"
    }

    private var lastPosition = -1

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.clearAnimation()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_character))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(characterEntity[position])
    }

    override fun getItemCount() = characterEntity.size

    fun updateList(characterEntity: MutableList<CharacterEntity>){
        this.characterEntity.clear()
        this.characterEntity.addAll(characterEntity)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private var textName: TextView = itemView.character_textView_name
        private var imageThumbnail: ImageView = itemView.character_imageView_thumbnail
        private var buttonMoreDetails: MaterialButton = itemView.character_materialButton_moreDetails
        private var container: ConstraintLayout = itemView.container_items

        fun bind(result: CharacterEntity){

            Log.d("image", result.thumbnail)
            val path = (result.thumbnail)

            context?.let {
                Picasso
                    .get()
                    .load(path)
                    .centerCrop()
                    .fit()
                    .error(R.drawable.ic_launcher_background)
                    .into(imageThumbnail)
            }

            textName.text = result.name

            buttonMoreDetails.setOnClickListener {
                startDetailsActivity(
                    result.id,
                    result.name,
                    result.description,
                    path
                ) }

            setAnimation(itemView, adapterPosition)

        }

        private fun startDetailsActivity(
            characterId: String,
            name: String,
            description: String,
            path: String
        ) {
            Log.d("marvel-adapter", characterId)
            val intent = Intent(context, ActivityDetails::class.java)
            intent.putExtra("characterId", characterId)
            intent.putExtra("name", name)
            intent.putExtra("description", description)
            intent.putExtra("path", path)
            context?.startActivity(intent)
        }

        private fun setAnimation(
            viewToAnimate: View,
            position: Int
        ) {
            val animation: Animation =
                AnimationUtils.loadAnimation(context, R.anim.item_animation_fall_down)
            viewToAnimate.startAnimation(animation)
        }


    }
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}