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

## PROJECT 11: MUSIC PLAYER

### What you will make

A music streaming simulator. Users can create playlists. Free users have limits and hear text-based "audio ads". Premium users have higher limits and no ads.

### Starting data

1. 10 songs hardcoded in a master array (e.g., S1, S2... S10).
2. 2 Users (One Free, One Premium).

### Rules

1. A Free user can have a maximum of 2 playlists.
2. A Premium user can have a maximum of 10 playlists.
3. When a Free user plays a playlist, an Ad must print after every 2 songs.

### PERSON 1: Song class and Exceptions

**What this class is for:** Holds the raw data for one music track.
**Steps:**

1. Private variables: `songId` (String), `title` (String), `artist` (String), `durationInSeconds` (int).
2. Constructor and getters.
3. Write `toString()`: return formatted string like `"S1 - Shape of You by Ed Sheeran"`.
4. **Make 2 custom exceptions:**
   * `SongNotFoundException`: "The song ID you entered does not exist."
   * `PlaylistFullException`: "This playlist has reached its maximum size."

### PERSON 2: User, FreeUser, PremiumUser

**What this class is for:** Enforces limits based on subscription tier.
**Steps:**

1. Make an `abstract class User`.
2. Private variables: `username` (String), `Playlist[] myPlaylists` (size 10), `int listCount = 0`.
3. Abstract methods: `public abstract int getMaxPlaylists();` and `public abstract boolean seesAds();`
4. Make `FreeUser`: `getMaxPlaylists()` returns 2. `seesAds()` returns `true`.
5. Make `PremiumUser`: `getMaxPlaylists()` returns 10. `seesAds()` returns `false`.
6. Write `createPlaylist(Playlist p)` in the `User` class:
   * If `listCount == getMaxPlaylists()`, print "Limit reached, cannot create more playlists."
   * Else, add to array and increase counter.

### PERSON 3: Playlist class

**What this class is for:** A collection of songs a user put together.
**Steps:**

1. Private variables: `playlistName` (String), `Song[] songs` (size 10), `int songCount`.
2. Write `addSong(Song s)`: If `songCount == 10`, **throw** a `PlaylistFullException`. Else, add to array and increase counter.
3. Write `playAll(User owner)`:
   * Use a `for` loop to go through the songs array (from 0 to `songCount`).
   * Print `"Playing: " + songs[i].getTitle()`.
   * **The Ad Logic:** Inside the loop, check `if (owner.seesAds() == true)`. If yes, check `if (i % 2 == 1)` (this means it's the 2nd, 4th, 6th song). If both are true, print `"--- BUY PREMIUM NOW! (Audio Ad) ---"`.

### PERSON 4: Main System Manager

**What this class is for:** Connects users to songs through the text menu.
**Steps:**

1. Arrays: `Song[] library`, `User[] registeredUsers`. Initialize them with the Starting Data.
2. Write `findSongById(String id)`: loops through the library array. If found, return the Song object. If loop finishes and not found, **throw** `SongNotFoundException`.
3. Build the menu:
   * **Search Song:** ask for ID, use `findSongById`, catch exception if bad ID.
   * **Add Song to Playlist:** Ask user name, find user. Ask playlist name, find playlist. Ask song ID, find song. Call `playlist.addSong()`. Catch `PlaylistFullException`.
   * **Play Playlist:** Find user, find playlist, call `playlist.playAll(user)`.

### DEMO: Your group must show these test cases

1. Play a playlist owned by a Premium user (prove no ads print).
2. Play a playlist owned by a Free user (prove ads print every 2nd song).
3. Try to add an 11th song to a playlist. Show the `PlaylistFullException`.
4. Try to search for a song ID that doesn't exist. Show the `SongNotFoundException`.