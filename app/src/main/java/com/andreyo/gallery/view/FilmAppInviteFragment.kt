package com.andreyo.gallery.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.andreyo.gallery.R

class FilmAppInviteFragment : Fragment() {
    companion object {
        const val TAG = "FilmAppInviteFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_films_invite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val emailAddress = view.findViewById<EditText>(R.id.et_email)
        val emailSubject = view.findViewById<EditText>(R.id.et_subject)
        val emailBody = view.findViewById<EditText>(R.id.et_body)
        val btn_sendEmail = view.findViewById<Button>(R.id.btn_send)

        btn_sendEmail.setOnClickListener {
            sendEmail(
               emailAddress.text.toString(), emailSubject.text.toString(),
                emailBody.text.toString()
            )
        }
        }

    private fun sendEmail(address: String, subject: String, text: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, address)
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, text)
        }
        startActivity(intent)
    }
}