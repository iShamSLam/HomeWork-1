package com.example.user.homework

class MusicSpace {

    private val maxIndex = data.size - 1
    private var currentItemIndex = 0

    internal val next: Track
        get() {
            if (currentItemIndex == maxIndex)
                currentItemIndex = 0
            else
                currentItemIndex++
            return current
        }

    internal val previous: Track
        get() {
            if (currentItemIndex == 0)
                currentItemIndex = maxIndex
            else
                currentItemIndex--
            return current
        }

    internal val current: Track
        get() = data[currentItemIndex]

    internal class Track(val title: String, val artist: String)

    companion object {
        internal var data = arrayOf(Track("Only you", "Metro Boomin feat. Wizkid, Offset"),
                Track("Only 1", "Metro Boomin feat. Travis Scott"),
                Track("Borrowed Love", "Metro Boomin feat. Swae Lee"),
                Track("Lesbian", "Metro Boomin feat. Gunna and Young Thug"),
                Track("No More", "Metro Boomin feat. Travis Scott and Kodak Black"))
    }
}

