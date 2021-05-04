package com.andreyo.gallery

import android.graphics.Color
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
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class FilmAdapter(
    val filmList: List<Film>,
    val callback: Callback,
    val layoutInflater: LayoutInflater
) :
    RecyclerView.Adapter<FilmAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            layoutInflater.inflate(
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
        private val mainActivity = (layoutInflater.context as MainActivity)


        private fun showSnackbar(text: String, film: Film) {

            val snackbar =
                Snackbar.make(
                    mainActivity.findViewById(R.id.fragmentContainer),
                    text,
                    Snackbar.LENGTH_LONG
                )
            snackbar.setAction(R.string.undo) {
                updateSelected(
                    FilmHelper.instance.isFavorite(film.id),
                    film
                )
            }
            snackbar.show()
        }

        private fun onFilmFavorite(film: Film) {


            when (if (buttonLike.isSelected) "delete" else "add") {
                "add" -> showSnackbar(
                    mainActivity.getText(R.string.addFavorite).toString(), film
                )
                "delete" -> showSnackbar(
                    mainActivity.getText(R.string.delFavorite).toString(), film
                )
            }
            updateSelected(buttonLike.isSelected, film)
        }

        private fun colorItems() {
            filmName.setTextColor(Color.parseColor("#FF0000"))
            filmDescr.setTextColor(Color.parseColor("#FF0000"))
        }

        private fun colorDefaultItems() {
            filmName.setTextColor(Color.parseColor("black"))
            filmDescr.setTextColor(Color.parseColor("black"))
        }

        private fun updateSelected(selected: Boolean, item: Film) {
            if (!selected) {
                FilmHelper.instance.liked.add(item)
                FilmHelper.instance.liked = FilmHelper.instance.liked.distinct().toMutableList()
                buttonLike.setChecked(true)
            } else {
                FilmHelper.instance.liked.remove(item)
                FilmHelper.instance.liked = FilmHelper.instance.liked.distinct().toMutableList()
                buttonLike.setChecked(false)

            }
        }

        private fun setListeners(item: Film) {
            val fm =
                mainActivity.supportFragmentManager
            buttonLike?.setOnClickListener {
                Log.i(
                    "clicked button : " + buttonLike.id.toString() + " id элемента : ",
                    item.id.toString()
                )
                updateSelected(buttonLike.isSelected, item)
                onFilmFavorite(item)


            }

            buttonLike?.setOnLongClickListener {

                Log.i(
                    "clicked long button : " + buttonLike.id.toString() + " id элемента : ",
                    item.id.toString()
                )
                false
            }

            itemView.setOnClickListener {
                Log.i("button write id", item.id.toString())
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) callback.onItemClicked(filmList[bindingAdapterPosition])
                colorItems()
                FilmHelper.instance.checked.add(item)
                FilmHelper.instance.checked = FilmHelper.instance.checked.distinct().toMutableList()
                val args = Bundle()
                args.putInt(FilmHelper.instance.ID, item.id)
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
                    .load(FilmHelper.instance.getUrlByPostrPath(item.poster_path, layoutInflater.context))
                    .into(
                        filmImg
                    )
            }
            if (FilmHelper.instance.checked.contains(item)) {
                colorItems()
            } else {
                colorDefaultItems()
            }
            if (FilmHelper.instance.liked.contains(item)) {
                buttonLike.setChecked(true)
            } else {
                buttonLike.isChecked = false
            }
            setListeners(item)
        }

    }


    interface Callback {
        fun onItemClicked(item: Film)

    }
}
