package org.example;

public class PlaylistCollection {
    private Song[] songs;
    private int count = 0;
    private static final int MAX_SONGS = 10;

    public PlaylistCollection() {
        songs = new Song[MAX_SONGS];
    }

    public void addSong(Song song) {
        if (count < MAX_SONGS) {
            songs[count] = song;
            count++;
            System.out.println("Added: " + song);
        } else {
            System.out.println("Playlist is full!");
        }
    }

    public Iterator getIterator() {
        return new SongIterator(songs);
    }

    public int getCount() {
        return count;
    }
}
