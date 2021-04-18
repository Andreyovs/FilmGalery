package com.andreyo.gallery

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.andreyo.gallery.view.FilmAppInviteFragment
import com.andreyo.gallery.view.FilmListFavoritesFragment
import com.andreyo.gallery.view.FilmListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openFragment(FilmListFragment.TAG, true)
        findViewById<BottomNavigationView>(R.id.navigation).setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_favorites -> {
                    openFragment(FilmListFavoritesFragment.TAG, false)
                    true
                }
                R.id.navigation_invite -> {
                    openFragment(FilmAppInviteFragment.TAG, false)
                    true
                }
                R.id.navigation_home -> {
                    openFragment(FilmListFragment.TAG, true)
                    true
                }
                else -> false
            }
        }


    }

    private fun openFragment(tag: String, isFirstPage: Boolean) {
        var fragment = supportFragmentManager.findFragmentByTag(tag)
        if (fragment == null) {
            when (tag) {
                FilmListFragment.TAG -> fragment =
                    FilmListFragment()
                FilmListFavoritesFragment.TAG -> fragment =
                    FilmListFavoritesFragment()
                FilmAppInviteFragment.TAG -> fragment =
                    FilmAppInviteFragment()
            }
        }
        var sm = supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment!!, tag)
        if (!isFirstPage) {
            sm.addToBackStack(tag)
        }
        sm.commit()
    }


    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            super.onBackPressed()
        } else {
            AlertDialog.Builder(this)
                .setTitle(R.string.exit_dialog)
                .setMessage(R.string.exit_text)
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    super.finishAffinity()
                }
                .setNegativeButton(android.R.string.cancel) { _, _ ->

                }
                .show()
        }
    }

    override fun onResume() {
        super.onResume()
        //toDO


    }


}



