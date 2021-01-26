package com.andreyo.gallery

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import java.io.InputStream


class FilmHelper {

    companion object {
        const val ID = "id"
        private lateinit var films: List<Film>
        public  lateinit var checked :MutableList<Int>
        public fun getFilms(): List<Film> {
            if (!this::films.isInitialized) {
                val dataIS: InputStream =
                    this.javaClass.getResource("/res/raw/films.json").openStream()
                val strJson = dataIS.bufferedReader().use { it.readText() }
                dataIS.close()
                films = Gson().fromJson(strJson, Film_Results::class.java).results
                checked = mutableListOf()
            }
            return films
        }

        public fun getFilm(id: Int): Film {
            Log.i("read id", id.toString())
            return films.filter { it.id.equals(id) }.first()

        }

    }

    public fun sendEmail(ctx: Context, recipient: String, subject: String, message: String) {
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
            ctx.startActivity(Intent.createChooser(mIntent, "Choose Email Client..."))
        } catch (e: Exception) {
            //if any thing goes wrong for example no email client application or any exception
            //get and show exception message
            Toast.makeText(ctx, e.message, Toast.LENGTH_LONG).show()
        }

    }
}