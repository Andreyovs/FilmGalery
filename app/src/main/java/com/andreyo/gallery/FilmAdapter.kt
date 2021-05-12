package com.andreyo.gallery

import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class FilmAdapter(val filmList: List<Film>, val callback: Callback) :
    RecyclerView.Adapter<FilmAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.film_item_layout,
                parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return filmList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filmList[position])

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val filmName = itemView.findViewById<TextView>(R.id.filmName)
        private val filmDescr = itemView.findViewById<TextView>(R.id.filmDescr)
        private val filmImg = itemView.findViewById<ImageView>(R.id.film_ImageView)
        private val buttonSendEmail = itemView.findViewById<View>(R.id.buttonSendEmail)
        private val buttonDetails = itemView.findViewById<View>(R.id.buttonDetails)
        private val url = itemView.resources.getString(R.string.film_url)
        private val buttonLike = itemView.findViewById<ToggleButton>(R.id.tgbFav)

        fun colorItems() {
            filmName.setTextColor(Color.parseColor("#FF0000"))
            filmDescr.setTextColor(Color.parseColor("#FF0000"))

        }
        fun defaultColors() {
            filmName.setTextColor(Color.parseColor("black"))
            filmDescr.setTextColor(Color.parseColor("black"))

        }

        fun SetListeners(item: Film) {
            buttonLike?.setOnClickListener {
                Log.i(
                    "clicked button : " + buttonLike.id.toString() + " id элемента : ",
                    item.id.toString()
                )
                if (!buttonLike.isSelected) {
                    FilmHelper.liked.add(item.id)
                    FilmHelper.liked = FilmHelper.liked.distinct().toMutableList()
                } else {
                    FilmHelper.liked.remove(item.id)
                    FilmHelper.liked = FilmHelper.liked.distinct().toMutableList()
                }
            }

            buttonLike?.setOnLongClickListener {
                Log.i(
                    "clicked long button : " + buttonLike.id.toString() + " id элемента : ",
                    item.id.toString()
                )
                val intent = Intent(buttonLike.context, FilmFavorites::class.java)
                buttonLike.context.startActivity(intent)
                false
            }
            buttonSendEmail?.setOnClickListener {
                val recipient = "myfriend@yandex.ru"
                val subject = "Приглашение в галлерею"
                val message = "Приглашение в галлерею/" + item.id.toString()

                //method call for email intent with these inputs as parameters
                FilmHelper().sendEmail(itemView.context, recipient, subject, message)
            }

            buttonDetails?.setOnClickListener {
                Log.i("button write id", item.id.toString())
                if (adapterPosition != RecyclerView.NO_POSITION) callback.onItemClicked(filmList[adapterPosition])
                colorItems()
                FilmHelper.checked.add(item.id)
                FilmHelper.checked = FilmHelper.checked.distinct().toMutableList()
                val intent = Intent(buttonDetails.context, FilmDetails::class.java)
                intent.putExtra(FilmHelper.ID, item.id)
                Log.i("itemView write id", item.id.toString())
                buttonDetails.context.startActivity(intent)

            }

        }

        fun bind(item: Film) {
            filmName.text = item.title
            filmDescr.text = item.overview.trim()
            if (filmImg != null) {
                Picasso.get()
                    .load(this.url + item.poster_path).into(
                        filmImg
                    );
            }
            if (FilmHelper.checked.contains(item.id)) {
                colorItems()
            } else {
                defaultColors()
            }
            if (FilmHelper.liked.contains(item.id)) {
                buttonLike.setChecked(true)
            }
            SetListeners(item)
        }

    }


    interface Callback {
        fun onItemClicked(item: Film)

    }
}
