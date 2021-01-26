package com.andreyo.gallery

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class FilmAdapter(val filmList: List<Film>, val callback: Callback, ctx: Context) :
    RecyclerView.Adapter<FilmAdapter.ViewHolder>() {
    val ctx_priv = ctx

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
        private val buttonSendEmail = itemView.findViewById<Button>(R.id.buttonSendEmail)
        private val buttonDetails = itemView.findViewById<Button>(R.id.buttonDetails)

        fun colorItems() {
            filmName.setTextColor(Color.parseColor("#FF0000"))
            filmDescr.setTextColor(Color.parseColor("#FF0000"))

        }

        fun bind(item: Film) {
            filmName.text = item.title
            filmDescr.text = item.overview.trim()
            Picasso.get()
                .load("https://www.themoviedb.org/t/p/w220_and_h330_face" + item.poster_path).into(
                    filmImg
                );
            if (FilmHelper.checked.contains(item.id)) {colorItems()}


            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) callback.onItemClicked(filmList[adapterPosition])
                colorItems()
                FilmHelper.checked.add(item.id)
                val intent = Intent(ctx_priv, FilmDetails::class.java)
                intent.putExtra(FilmHelper.ID, item.id)
                Log.i("itemView write id", item.id.toString())
                ctx_priv.startActivity(intent)
            }

            buttonSendEmail.setOnClickListener {
                val recipient = "myfriend@yandex.ru"
                val subject = "Приглашение в галлерею"
                val message = "Приглашение в галлерею" + item.id.toString()

                //method call for email intent with these inputs as parameters
                FilmHelper().sendEmail(itemView.context, recipient, subject, message)
            }

            buttonDetails.setOnClickListener {
                Log.i("button write id", item.id.toString())
                itemView.performClick()
            }

        }
    }


    interface Callback {
        fun onItemClicked(item: Film)
    }
}
