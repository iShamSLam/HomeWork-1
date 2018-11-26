package com.example.user.homework

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

import java.util.ArrayList
import java.util.Random

class Recycle : AppCompatActivity() {

    private val callback: Track_Adapter.ClickCallBack? = null
    internal var tracks: MutableList<MusicSpace.Track> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        val currentTheme = sharedPref.getInt("number", repaint)
        if (repaint <= 3)
            repaint += 1
        else
            repaint = 1
        val themeId: Int
        when (currentTheme) {
            2 -> themeId = R.style.Theme_2
            3 -> themeId = R.style.Theme_3
            4 -> themeId = R.style.Theme4
            else -> themeId = R.style.Theme_1
        }
        setTheme(themeId)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycleview)
        setInitialData()
        val recyclerView = findViewById<RecyclerView>(R.id.tracks)
        val decoration = SpaceItemDecoration(16)
        recyclerView.addItemDecoration(decoration)
        val gridLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = gridLayoutManager
        val adapter = Track_Adapter(tracks) {
            val intent = Intent(this, Information::class.java)
            startActivity(intent)
        }
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        findViewById<View>(R.id.floatingActionButton).setOnClickListener({ v -> this.recreate() }
        )

    }

    private fun setInitialData() {
        tracks.add(MusicSpace.Track("Only you", "Metro Boomin feat. Wizkid, Offset"))
        tracks.add(MusicSpace.Track("Only 1", "Metro Boomin feat. Travis Scott"))
        tracks.add(MusicSpace.Track("Borrowed Love", "Metro Boomin feat. Swae Lee"))
        tracks.add(MusicSpace.Track("Lesbian", "Metro Boomin feat. Gunna and Young Thug"))
        tracks.add(MusicSpace.Track("No More", "Metro Boomin feat. Travis Scott and Kodak Black"))
    }

    companion object {
        internal var repaint = 1
    }
}