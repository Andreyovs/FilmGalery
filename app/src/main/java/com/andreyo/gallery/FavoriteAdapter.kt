package com.andreyo.gallery

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class FavoriteAdapter(val filmList: MutableList<Film>, val callback: Callback, ctx: Context) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.film_favorites,
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
        private val url = itemView.resources.getString(R.string.film_url)
        private val buttonLike = itemView.findViewById<ToggleButton>(R.id.tgbFav)


        private fun setListeners(item: Film) {
            buttonLike?.setOnClickListener {
                Log.i(
                    "clicked button : " + buttonLike.id.toString() + " id элемента : ",
                    item.id.toString()
                )

                FilmHelper.liked.remove(item.id)
                filmList.remove(item)
                callback.onDeleteClick(adapterPosition)


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

            buttonLike.setChecked(true)
            setListeners(item)
        }

    }


    interface Callback {
        fun onItemClicked(item: Film)
        fun onDeleteClick(position: Int)

    }
}
