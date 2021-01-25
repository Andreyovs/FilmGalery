package com.andreyo.gallery

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
class FilmDetails : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.film_details)
        val helper : FilmHelper = FilmHelper()
        val film: Film = FilmHelper.getFilm(intent.getIntExtra(FilmHelper.ID, 0))
        val filmName = this.findViewById<TextView>(R.id.filmName)
        val filmDescr = this.findViewById<TextView>(R.id.filmDescr)
        val filmId = this.findViewById<TextView>(R.id.filmId)
        val buttonSendEmail = this.findViewById<Button>(R.id.buttonSendEmail)
        filmName.text = film.title
        filmDescr.text = film.overview
        filmId.text = film.id.toString()
        buttonSendEmail.setOnClickListener {
            val recipient = "myfriend@yandex.ru"
            val subject = "Приглашение в галлерею"
            val message = "Приглашение в галлерею" + filmId.text

            //method call for email intent with these inputs as parameters
            helper.sendEmail(this,recipient, subject, message)
        }
    }





}