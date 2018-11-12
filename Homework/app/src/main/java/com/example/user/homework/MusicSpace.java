package com.example.user.homework;

public class MusicSpace {
    static  Track[] data = {
            new Track("Only you", "Metro Boomin feat. Wizkid, Offset", R.raw.only_you_feat_wizkid_offset),
            new Track("Only 1", "Metro Boomin feat. Travis Scott", R.raw.only_1_feat_travis),
            new Track("Borrowed Love", "Metro Boomin feat. Swae Lee", R.raw.borrowed_love_feat_swae_lee),
            new Track("Lesbian", "Metro Boomin feat. Gunna and Young Thug", R.raw.lesbian_feat_gunna_young_thug),
            new Track("No More", "Metro Boomin feat. Travis Scott and Kodak Black", R.raw.no_more_feat_travis_scott_kodad_black),
    };

    private final int maxIndex = data.length - 1;
    private int currentItemIndex = 0;

    Track getNext() {
        if (currentItemIndex == maxIndex)
            currentItemIndex = 0;
        else
            currentItemIndex++;
        return getCurrent();
    }

    Track getPrevious() {
        if (currentItemIndex == 0)
            currentItemIndex = maxIndex;
        else
            currentItemIndex--;
        return getCurrent();
    }

    Track getCurrent() {
        return data[currentItemIndex];
    }

    static class Track {

        private String title;
        private String artist;
        private int adress;
        private int position;

        Track(String title, String artist, int adress) {
            this.title = title;
            this.artist = artist;
            this.adress = adress;
        }

        String getTitle() {
            return title;
        }

        String getArtist() {
            return artist;
        }

        int getAdress() {
            return adress;
        }

    }
}

