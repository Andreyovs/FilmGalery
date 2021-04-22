package com.andreyo.gallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.andreyo.gallery.data.Film
import com.andreyo.gallery.helper.FilmHelper
import com.andreyo.gallery.view.FilmDetailsFragment
import com.squareup.picasso.Picasso


class FavoriteAdapter(
    val filmList: MutableList<Film>,
    val callback: Callback,
    val layoutInflater: LayoutInflater
) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.fragment_film_item,
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

        private val filmName = itemView.findViewById<TextView>(R.id.tv_filmName)
        private val filmDescr = itemView.findViewById<TextView>(R.id.tv_filmDescr)
        private val filmImg = itemView.findViewById<ImageView>(R.id.iv_film)
        private val buttonLike = itemView.findViewById<ToggleButton>(R.id.tgb_Fav)


        private fun setListeners(item: Film) {
            val fm =
                (layoutInflater.context as MainActivity).supportFragmentManager

            buttonLike?.setOnClickListener {
                Log.i(
                    "clicked button : " + buttonLike.id.toString() + " id элемента : ",
                    item.id.toString()
                )

                FilmHelper.liked.remove(item.id)
                filmList.remove(item)
                callback.onDeleteClick(adapterPosition)
            }
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) callback.onItemClicked(filmList[adapterPosition])
                FilmHelper.checked.add(item.id)
                FilmHelper.checked = FilmHelper.checked.distinct().toMutableList()
                val args = Bundle()
                args.putInt(FilmHelper.ID, item.id)
                Log.i("itemView write id", item.id.toString())
                val fragment = FilmDetailsFragment()
                fragment.arguments = args
                val sm = fm
                    .beginTransaction()
                if (!fragment.isAdded) {
                    sm.replace(R.id.fragmentContainer, fragment, FilmDetailsFragment.TAG)
                } else {
                    sm.show(fragment)
                }
                sm.addToBackStack(FilmDetailsFragment.TAG)
                sm.commit()
            }

        }

        fun bind(item: Film) {
            filmName.text = item.title
            filmDescr.text = item.overview.trim()
            if (filmImg != null) {
                Picasso.get()
                    .load(FilmHelper.getUrlByPostrPath(item.poster_path, layoutInflater.context))
                    .into(
                        filmImg
                    )
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
