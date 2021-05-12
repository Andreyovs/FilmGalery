@file:Suppress("RedundantOverride")

package com.andreyo.gallery.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.andreyo.gallery.R
import com.andreyo.gallery.data.Film
import com.andreyo.gallery.helper.FilmHelper
import com.squareup.picasso.Picasso

class FilmDetailsFragment : Fragment() {
    private var filmId: Int = 0

    companion object {
        const val TAG = "FilmDetailsFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_film_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filmId = arguments?.getInt(FilmHelper.ID, 0)!!
        val film: Film = FilmHelper.instance.getFilm(filmId)
        view.findViewById<Toolbar>(R.id.pageNameTextView).title = film.title
        if (view.findViewById<ImageView>(R.id.iv_film) != null) {
            Picasso.get()
                .load(FilmHelper.instance.getUrlByPostrPath(film.poster_path,layoutInflater.context)).into(
                    view.findViewById<ImageView>(R.id.iv_film)
                )
        }
        view.findViewById<TextView>(R.id.tv_filmDescr).text = film.overview


        //      val recipient = "myfriend@yandex.ru"
        //      val subject = "Приглашение в галлерею"
        //      val message = "Приглашение в галлерею/id=" + filmId.text

        //method call for email intent with these inputs as parameters
        //      helper.sendEmail(this.requireContext(), recipient, subject, message)
        //}

    }
}