package com.andreyo.gallery

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import java.io.InputStream

class FilmDetails : AppCompatActivity() {
    private lateinit var dataIS: InputStream

    companion object {
        const val ID = "id"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.film_details)
        dataIS = resources.openRawResource(R.raw.films) //потом перенести
        val film: Film = getFilm(intent.getIntExtra(ID, 0))
        val filmName = this.findViewById<TextView>(R.id.filmName)
        val filmDescr = this.findViewById<TextView>(R.id.filmDescr)
        val filmAdult = this.findViewById<TextView>(R.id.filmAdult)
        val buttonSendEmail = this.findViewById<Button>(R.id.buttonSendEmail)
        filmName.text = film.title
        filmDescr.text = film.overview
        filmAdult.text = film.adult.toString()
        buttonSendEmail.setOnClickListener {
            val recipient = "myfriend@yandex.ru"
            val subject = "Приглашение в галлерею"
            val message = "Приглашение в галлерею"

            //method call for email intent with these inputs as parameters
            sendEmail(recipient, subject, message)
        }
    }

    private fun sendEmail(recipient: String, subject: String, message: String) {
        /*ACTION_SEND action to launch an email client installed on your Android device.*/


        val mIntent = Intent(Intent.ACTION_SEND)
        /*To send an email you need to specify mailto: as URI using setData() method
        and data type will be to text/plain using setType() method*/
        mIntent.data = Uri.parse("mailto:")
        mIntent.type = "text/plain"
        // put recipient email in intent
        /* recipient is put as array because you may wanna send email to multiple emails
           so enter comma(,) separated emails, it will be stored in array*/
        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
        //put the Subject in the intent
        mIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        //put the message in the intent
        mIntent.putExtra(Intent.EXTRA_TEXT, message)


        try {
            //start email intent
            startActivity(Intent.createChooser(mIntent, "Choose Email Client..."))
        } catch (e: Exception) {
            //if any thing goes wrong for example no email client application or any exception
            //get and show exception message
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }

    }


    private fun getFilm(id: Int): Film {
        val strJson = dataIS.bufferedReader().use { it.readText() }
        val films: List<Film> = Gson().fromJson(strJson, Film_Results::class.java).results
        Log.i("read id", id.toString())
        return films.filter { it.id.equals(id) }.first()

    }
}