package com.andreyo.gallery

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startRecyclerView()


    }
    private fun startRecyclerView()
    {
        var recyclerView: RecyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = FilmAdapter(FilmHelper.getFilms(), object : FilmAdapter.Callback {
            override fun onItemClicked(item: Film) {

            }
        })
        recyclerView.addItemDecoration(
            DividerItemDecoration(recyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )

    }
    override fun onResume() {
        super.onResume()
        startRecyclerView()

    }
    override fun onBackPressed() {

            // super.onBackPressed()
            AlertDialog.Builder(this)
                .setTitle(R.string.exit_dialog)
                .setMessage(R.string.exit_text)
                .setPositiveButton(android.R.string.ok) { dialog, whichButton ->
                    super.onBackPressed()
                }
                .setNegativeButton(android.R.string.cancel) { dialog, whichButton ->

                }
                .show()

        }

    }



