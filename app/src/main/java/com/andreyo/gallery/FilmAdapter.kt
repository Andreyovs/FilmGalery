package com.andreyo.gallery

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class FilmAdapter(val filmList: List<Film>, val callback: Callback,ctx: Context ) :
    RecyclerView.Adapter<FilmAdapter.ViewHolder>() {
    val ctx_priv = ctx
    companion object {
        const val ID = "id"
    }

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

        fun bind(item: Film) {
            filmName.text = item.title
            filmDescr.text = item.overview.trim()

            Picasso.get().load("https://www.themoviedb.org/t/p/w220_and_h330_face" + item.poster_path).into(
                filmImg
            );

            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) callback.onItemClicked(filmList[adapterPosition])
            //открытие нового view
                filmName.setTextColor(Color.parseColor("#FF0000"))
                filmDescr.setTextColor(Color.parseColor("#FF0000"))
                val intent = Intent(ctx_priv, FilmDetails::class.java)
                intent.putExtra(ID, item.id)
                ctx_priv.startActivity(intent)
            }
        }
    }


    interface Callback {
        fun onItemClicked(item: Film)
    }
}
