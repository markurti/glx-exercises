package org.example;

public class Client {
    public static void main(String[] args) {
        PlaylistCollection playlist = new PlaylistCollection();

        System.out.println("=== Creating Playlist ===");
        playlist.addSong(new Song("Bohemian Rhapsody", "Queen"));
        playlist.addSong(new Song("Hotel California", "Eagles"));
        playlist.addSong(new Song("Imagine", "John Lennon"));
        playlist.addSong(new Song("Stairway to Heaven", "Led Zeppelin"));
        playlist.addSong(new Song("Sweet Child O' Mine", "Guns N' Roses"));

        System.out.println("\n=== Iterating Through Playlist ===");
        Iterator iterator = playlist.getIterator();

        int songNumber = 1;
        while (iterator.hasNext()) {
            Song song = iterator.next();
            System.out.println(songNumber + ". " + song);
            songNumber++;
        }

        System.out.println("\nTotal songs in playlist: " + playlist.getCount());
    }
}