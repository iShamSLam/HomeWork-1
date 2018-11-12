package com.example.user.homework;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Track_Adapter extends RecyclerView.Adapter<Track_Adapter.ViewHolder> {
    private List<MusicSpace.Track> tracks;

    Track_Adapter(List<MusicSpace.Track> tracks, ClickCallBack clickCallBack) {
        this.tracks = tracks;
        this.callBack = clickCallBack;
    }

    private ClickCallBack callBack;

    @NonNull
    @Override
    public Track_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.single_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Track_Adapter.ViewHolder holder, int position) {
        MusicSpace.Track track = tracks.get(position) ;
        holder.title.setText(track.getTitle());
        holder.artist.setText(track.getArtist());
        holder.id = position;
        holder.view.setOnClickListener(v -> holder.onClick(holder.itemView));
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView artist, title;
        final View view;
        int id;
        Drawable drawable;

        ViewHolder(View itemView) {
            super(itemView);
            artist = itemView.findViewById(R.id.name);
            title = itemView.findViewById(R.id.surname);
            view = itemView.findViewById(R.id.cv);
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            Intent intent = new Intent(context, Information.class);
            intent.putExtra("name",artist.getText() );
            intent.putExtra("surname", title.getText());
            context.startActivity(intent);
        }
    }
    interface ClickCallBack {
        void onClick();
    }
}
