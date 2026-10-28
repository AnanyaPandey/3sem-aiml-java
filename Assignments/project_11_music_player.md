# RULES FOR ALL PROJECTS

1. Work in a group of 4. Each person makes ONE main part (one or more classes, as written in your project). Write your name in a comment at the top of your class.
2. Use only Java. Use arrays to store many things (many spots, many books, many accounts). Do not use ArrayList, HashMap, or any database.
3. Your project must use these 5 things. Here is what each one means:
   - **Private variables with getters (encapsulation):** Make your variables private. Give a public method (getter) to read each one. Example: `private String spotId;` and `public String getSpotId()`.
   - **Inheritance:** One parent class, and child classes that extend it. Example: Vehicle is the parent. Car and Bike are children.
   - **Overriding:** The parent has a method. Each child writes its own version of the same method. Example: `getHourlyRate()` gives 20 in Car and 10 in Bike.
   - **Abstract class:** A parent class that you never create objects of directly. You write `abstract` before the class name. Example: `abstract class Vehicle`.
   - **Custom exceptions:** Your own error classes. You make them with `extends Exception`. You use `throw` when something is wrong and `try/catch` to show a nice message.
4. Make a Main class with a menu. The user types a number to choose. If the user types a wrong number, show a message. The program must not crash.
5. **Submit these 4 things:**
   - (a) your code
   - (b) one page diagram with boxes for your classes
   - (c) 5 lines on why you made these classes and what numbers you decided
   - (d) who did what
6. Present for 10 minutes. Every person explains their own class and answers questions on it.

## Tips for working in a group

1. First, sit together and agree on the names of the classes and methods. Write them on paper.
2. Each person writes and tests their own class first, with a small `main` method.
3. Only after that, join all the classes together.
4. If your part needs a class of another person, wait for them or write a simple temporary version to test.
5. Do not copy code from the internet or from AI tools. You will be asked to explain every line of your own class.

------

# PROJECT 11: MUSIC PLAYER

## What you will make

A music program. Users make playlists and add songs. Free users have limits and hear text ads. Premium users have a higher limit and no ads.

## Starting data

1. 10 songs: S1 to S10, each with title, artist and duration in seconds.
2. 2 users: "ravi" (Free) and "neha" (Premium).

## Rules

1. A Free user can have 2 playlists. A Premium user can have 10 playlists.
2. A playlist can have at most 5 songs.
3. The same song cannot be added two times to one playlist (show a message).
4. When a Free user plays a playlist, an ad is printed after every 2nd song.

## PERSON 1: Song, Library and the exceptions

**What these classes are for:** A Song is one track. The Library holds all songs.

**Steps:**

1. `Song`: private variables `songId`, `title`, `artist`, `durationInSeconds`. Constructor and getters.
2. `Song.toString()`: example `S1 - Shape of You by Ed Sheeran (233 sec)`.
3. `Library`: private variables `Song[] songs` (size 10), `songCount`.
4. `addSong(Song s)`, `showAllSongs()`.
5. `findSongById(String id)`: search. If not found, throw `SongNotFoundException`.
6. `searchByTitle(String text)`: print songs whose title contains the text.
7. Make 2 custom exceptions:
   - `SongNotFoundException`: message "The song ID does not exist".
   - `PlaylistFullException`: message "This playlist already has 5 songs".

**How to test your part:** Find S3. Find "S99". The exception must come.

## PERSON 2: User, FreeUser, PremiumUser

**What this class is for:** Different limits and ads for different users.

**Steps:**

1. Make an abstract class `User`.
2. Private variables: `username`, `Playlist[] myPlaylists` (size 10), `listCount`.
3. Constructor takes username. Make `getUsername()`.
4. Abstract methods: `getMaxPlaylists()` and `seesAds()`.
5. `FreeUser`: returns 2 and true. `PremiumUser`: returns 10 and false.
6. `createPlaylist(Playlist p)`: if `listCount` equals `getMaxPlaylists()`, throw `PlaylistLimitException`. Otherwise store it.
7. `findPlaylist(String name)`: return the playlist or null.
8. `showPlaylists()`: print all playlist names with the number of songs.
9. Make the custom exception `PlaylistLimitException`: message "Playlist limit reached for this user".

**How to test your part:** Make a FreeUser. Create 3 playlists. The 3rd must give the exception.

## PERSON 3: Playlist

**What this class is for:** A list of songs that a user made.

**Steps:**

1. Private variables: `playlistName`, `Song[] songs` (size 5), `songCount`.
2. Constructor takes the name. Make getters.
3. `hasSong(String songId)`: true if the song is already in the playlist.
4. `addSong(Song s)`: if `hasSong` is true, print a message and stop. If `songCount` is 5, throw `PlaylistFullException`. Otherwise store it.
5. `showSongs()`: print all songs.
6. `playAll(User owner)`:
   - Loop through the songs. Print "Playing: " and the title.
   - If `owner.seesAds()` is true and `i % 2 == 1`, print "--- BUY PREMIUM NOW! (Audio Ad) ---".
   - If the playlist is empty, print "Playlist is empty".

**How to test your part:** Make a playlist with 4 songs. Play it with a FreeUser and a PremiumUser. Compare.

## PERSON 4: MusicApp and Main

**What this class is for:** To connect users, songs and playlists with a menu.

**Steps:**

1. Private variables: `Library library`, `User[] users` (size 2 or more).
2. In the constructor, create the 10 songs and the 2 users.
3. `findUser(String name)`: return the user or null.
4. Methods for each menu choice. Each one must find the user (and playlist) first, and print a message if not found.
5. Main class: menu in a loop, `try/catch` for all 3 exceptions.

**Menu:**

```
1. Show all songs
2. Search song by title
3. Create playlist
4. Add song to playlist
5. Show playlists of a user
6. Play playlist
7. Stop program
```

## DEMO: your group must show all 6

1. Play a playlist of the Premium user. No ads must show.
2. Play a playlist of the Free user. An ad must show after every 2nd song.
3. Create 3 playlists for the Free user. The "limit" message must show.
4. Add 6 songs to one playlist. The "playlist full" message must show.
5. Add the same song two times. The "already in playlist" message must show.
6. Add song "S99". The "song not found" message must show.