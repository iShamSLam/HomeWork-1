package com.example.user.homework

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class Track_Adapter internal constructor(private val tracks: List<MusicSpace.Track>, private val callBack: ClickCallBack) : RecyclerView.Adapter<Track_Adapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Track_Adapter.ViewHolder {
        val context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.single_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: Track_Adapter.ViewHolder, position: Int) {
        val track = tracks[position]
        holder.title.text = track.title
        holder.artist.text = track.artist
        holder.id = position
        holder.view.setOnClickListener { v -> holder.onClick(holder.itemView) }
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        internal val artist: TextView
        internal val title: TextView
        internal val view: View
        internal var id: Int = 0
        internal var drawable: Drawable? = null

        init {
            artist = itemView.findViewById(R.id.name)
            title = itemView.findViewById(R.id.surname)
            view = itemView.findViewById(R.id.cv)
        }

        override fun onClick(view: View) {
            val context = view.context
            val intent = Intent(context, Information::class.java)
            intent.putExtra("name", artist.text)
            intent.putExtra("surname", title.text)
            context.startActivity(intent)
        }
    }

    internal interface ClickCallBack {
        fun onClick()
    }
}
