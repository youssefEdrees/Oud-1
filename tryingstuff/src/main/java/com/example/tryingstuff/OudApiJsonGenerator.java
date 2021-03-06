package com.example.tryingstuff;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class OudApiJsonGenerator {

    public static final String[] PORTRAITS = {
            "https://preview.redd.it/b8y11p45efo41.jpg?width=640&crop=smart&auto=webp&s=c5812cbdd8da57657396e933c411489e337b0d41",
            "https://preview.redd.it/81hbz34ggdo41.jpg?width=640&crop=smart&auto=webp&s=ab2b5493e6027a261a4e01f8a0fc1f807247b22f",
            "https://preview.redd.it/jtk5l23c2jn41.png?width=640&crop=smart&auto=webp&s=e7374b18b4f25011f6fc1d6e47de6d2af0326c45",
            "https://preview.redd.it/cqbbdskakao41.jpg?width=640&crop=smart&auto=webp&s=f097ab88c95b0c6faadafe2981d4d17310166b5f",
            "https://preview.redd.it/m13wwgnv81o41.jpg?width=640&crop=smart&auto=webp&s=3e8389f267e575638145510f50f18c757c756b60",
            "https://preview.redd.it/gy1pczk5cwn41.jpg?width=640&crop=smart&auto=webp&s=20ec76334cfaef6960b116d60b8cbdb5f494c50d",
            "https://preview.redd.it/bsbed8u7xqn41.jpg?width=640&crop=smart&auto=webp&s=3adc502785791875d56b4c0476727f5f92a8ed8e",
            "https://preview.redd.it/p0gtn3ua8qo41.jpg?width=640&crop=smart&auto=webp&s=6698972aa82b03c9ae708964889d52b581b5d1bd",
            "https://external-preview.redd.it/GJHNq3P2V4MFPQohWp4Y-oPJyjh-OHQpAN7p7SLTiuI.jpg?width=640&crop=smart&auto=webp&s=a0b6773e2719169e815d5743f80e18a6294f4735",
            "https://i.imgur.com/n7WiWDAl.jpg",
            "https://preview.redd.it/sa2k512lgrm41.jpg?width=640&crop=smart&auto=webp&s=47acce81a3f9d21ce7cae0d924e0d14c1dc70ccc",
            "https://preview.redd.it/nk8lu20ju3n41.jpg?width=640&crop=smart&auto=webp&s=585e8f5a43b6dba5b0a891e4385f42fceca44c83",
            "https://preview.redd.it/3u059jbl4gm41.jpg?width=640&crop=smart&auto=webp&s=2676441cc582115f5a6de00ddf452cf2d09258ab",
            "https://preview.redd.it/wvz9019cn3m41.jpg?width=640&crop=smart&auto=webp&s=c556b980c430b8738fd5371cd6cf216816070a7f"
    };

    public static final String[] VECTOR_ART = {
            "https://preview.redd.it/tgo1fl9xdgo41.png?width=640&crop=smart&auto=webp&s=6b82d0c5dc1baa6722c89c4d5d3870caef6a77f7",
            "https://preview.redd.it/kl84hi4j53o41.png?width=640&crop=smart&auto=webp&s=3c5026f192a9bfb8028d80c6f22c8ad161c627e3",
            "https://preview.redd.it/3nn2z153s5o41.png?width=640&crop=smart&auto=webp&s=d2027ad0b659b455ec42f37c53f865119b4793df",
            "https://preview.redd.it/jtk5l23c2jn41.png?width=640&crop=smart&auto=webp&s=e7374b18b4f25011f6fc1d6e47de6d2af0326c45",
            "https://preview.redd.it/ivjv5kyv1in41.jpg?width=640&crop=smart&auto=webp&s=aa9386b9408bb57c2ab818c9658ee247b9d974a2",
            "https://preview.redd.it/28ajodxzbfn41.jpg?width=640&crop=smart&auto=webp&s=24aa6caa8e5e833fc651d0da73f338d15a8d66a4",
            "https://preview.redd.it/f5m4o1rc1an41.png?width=640&crop=smart&auto=webp&s=92bfae8d1f55a9c0f377a2f824120bfeaa2879f0"
    };

    public static final String[] PLAYLIST_COVER = {
            "https://i.pinimg.com/564x/ed/ec/ba/edecbabce240d759b58e04ad579b49c5.jpg",
            "https://i.pinimg.com/564x/44/d0/c7/44d0c71a184a8d13ad7f003ca619c959.jpg",
            "https://i.pinimg.com/564x/a9/4c/91/a94c91150d65355934934143d2705116.jpg",
            "https://i.pinimg.com/564x/73/21/28/732128af10c3cd524d62b622023d9558.jpg",
            "https://i.pinimg.com/564x/fd/9c/42/fd9c42de94ec478b6df75c3ac15b5bd0.jpg",
            "https://i.pinimg.com/564x/96/fb/60/96fb604cfef189092862b5b433941062.jpg",
            "https://i.pinimg.com/564x/78/0a/83/780a8370d88882db1fbb4bae5e36f86d.jpg",
            "https://i.pinimg.com/564x/46/94/3c/46943c56c63a7dbd992b1261e4a33e52.jpg",
            "https://i.pinimg.com/564x/b7/53/08/b75308c6cad247809c910a5a14199a1d.jpg",
            "https://i.pinimg.com/564x/5f/e5/b4/5fe5b4cf91e8c4a06ab099047f7643b7.jpg",
            "https://i.pinimg.com/564x/14/1a/1b/141a1b6d506238ccf7156b97ad074730.jpg",
            "https://i.pinimg.com/564x/e3/ad/41/e3ad415b6072c5f719a21bd7f7c5a1eb.jpg",
            "https://i.pinimg.com/564x/cf/c2/53/cfc253106f0a887d9111cb2dc560fc0e.jpg",
            "https://i.pinimg.com/564x/63/8f/67/638f675e034a4bc8a06b7ad45573c04d.jpg",
            "https://i.pinimg.com/564x/22/41/4f/22414ff7d3807c74c674424893bbf5ed.jpg",
            "https://i.pinimg.com/564x/d2/27/8b/d2278b71927f22a7325ce6d25df34ed3.jpg",
            "https://i.pinimg.com/564x/33/1f/bd/331fbd4386e68cd56faca93ab0a1bdb9.jpg",
            "https://i.pinimg.com/564x/94/44/f7/9444f707c8fa00cd3095e6f9af94c4a3.jpg",
            "https://i.pinimg.com/564x/ba/4d/5f/ba4d5fffce8a890a4284080fde9d40bf.jpg",
            "https://i.pinimg.com/564x/4f/9b/18/4f9b18d2d72e4392dc34076820486f02.jpg",
            "https://i.pinimg.com/564x/61/6a/23/616a236efe07b3c805afcbc1863f6abc.jpg",
            "https://i.pinimg.com/564x/ae/c1/27/aec127df2c251ca67868812231004c0a.jpg",
            "https://i.pinimg.com/564x/a5/2f/af/a52fafba80c7c22f79dbc1f900ba0dce.jpg",
            "https://i.pinimg.com/564x/42/b7/24/42b724475f83ff2228623263de4bba4c.jpg"
    };

    public static final String[] AUDIO_URLS = {
            "https://server10.mp3quran.net/ajm/128/001.mp3",
            "https://server10.mp3quran.net/ajm/128/114.mp3"
    };

    public static final int JSON_GENERATION_ALBUM_TRACK_COUNT = 10;
    public static final int JSON_GENERATION_ARTIST_TRACK_COUNT = 10;




    public static String getJsonRecentlyPlayed(int trackCount) {
        String s = "{\n" +
                   "    \"items\": [ \n";

        for (int i = 0; i < trackCount; i++) {
            s += "      " + getJsonRecentlyPlayedTrack(i);
            if (i < trackCount - 1)
                s+= ", ";
            s+= '\n';
        }

        s +=       "    ],\n" +
                   "    \"limit\": " + trackCount + "\n" +
                   "}";

       return s;
    }

    public static String getJsonRecentlyPlayedTrack(int i) {
        String s = "{" +
                "      \"playedAt\": \"2020-03-26T16:23:02Z\"," +
                "      \"context\": " +
                getJsonContext(null, i) +
                "    }";

        return s;
    }

    public static String getJsonGenre(int i) {
        return "{" +
                "\"name\": \"genre" + i + "\"," +
                "\"_id\": \"genre" + i + "\"" +
                "}";
    }

    /**
     * if type equals null, a random type will be generated.
     * @return
     */
    public static String getJsonContext(String type, int i) {
        String[] types = {"unknown", "artist", "album", "playlist"};
        Random random = new Random();
        if (type == null) {
            type = types[random.nextInt(types.length)];
        }

        return "{" +
                "\"type\": \"" + type + "\"," +
                "\"id\": \"" + type + i + "\"" +
                "}";

    }

    public static String getJsonAlbum(int i) {
        String s = "{" +
                "\"_id\": \"album" + i + "\"," +
                "  \"album_type\": \"album_type" + i + "\"," +
                "  \"artists\": [" +
                getJsonArtistPreview(i) +
                "  ]," +
                "  \"genres\": [" +
                        getJsonGenre(i/2) + ", " +
                        getJsonGenre(i) +
                "  ]," +
                "  \"image\": \"" + VECTOR_ART[i%VECTOR_ART.length] + "\"," +
                "  \"name\": \"album" + i + "\"," +
                "  \"release_date\": \"2020-03-21\"," +
                "  \"tracks\": {" +
                "    \"items\": [";
        for (int _i = 0; _i < JSON_GENERATION_ALBUM_TRACK_COUNT; _i++) {
            s += getJsonTrackPreview(_i + i * JSON_GENERATION_ALBUM_TRACK_COUNT);
            if (_i < JSON_GENERATION_ALBUM_TRACK_COUNT - 1)
                s+= ", ";
        }
        s +=
                "    ]," +
                        "    \"limit\": " + JSON_GENERATION_ALBUM_TRACK_COUNT + "," +
                        "    \"offset\": 0," +
                        "    \"total\": " + JSON_GENERATION_ALBUM_TRACK_COUNT + "" +
                        "  }," +
                        "  \"type\": \"type" + i + "\"," +
                        "  \"released\": true" +
                        "}";
        return s;

    }

    public static String getJsonTrackPreview(int i) {
        int artistIndex = i % JSON_GENERATION_ALBUM_TRACK_COUNT;

        return "{" +
                "  \"_id\": \"track" + i + "\"," +
                "  \"name\": \"track" + i + "\"," +
                "  \"artists\": [" +
                getJsonArtistPreview(artistIndex) +
                "  ]," +
                "  \"type\": \"string\"," +
                "  \"duartion\": 100" + i + "," +
                "  \"views\": 100" + i + "" +
                "}";
    }

    public static String getJsonAlbumPreview(int i) {
        String s = "";

        s += "{" +
                "\"_id\": \"album" + i + "\"," +
                "\"album_type\": \"album_type" + i + "\"," +
                "\"album_group\": \"album_group" + i + "\"," +
                "\"artists\": [" +
                getJsonArtistPreview(i) +
                "]," +
                "\"image\": \"" + VECTOR_ART[i%VECTOR_ART.length] + "\"," +
                "\"name\": \"album" + i + "\"," +
                "\"type\": \"album_type" + i + "\"" +
                "}";



        return s;
    }

    public static String getJsonTrack(int i) {
        int artistIndex = i % JSON_GENERATION_ALBUM_TRACK_COUNT;
        int albumIndex = i % JSON_GENERATION_ALBUM_TRACK_COUNT;
        int urlIndex = i % AUDIO_URLS.length;
        return "{" +
                "  \"_id\": \"track" + i + "\"," +
                "  \"name\": \"track" + i + "\"," +
                "  \"artists\": [" +
                getJsonArtistPreview(artistIndex) +
                "  ]," +
                "  \"albumId\": \"album" + albumIndex + "\"," +
                "\"album\":" +
                getJsonAlbumPreview(albumIndex) + ", " +
                "  \"type\": \"string\"," +
                "  \"audioUrl\": \"" + AUDIO_URLS[urlIndex] + "\"," +
                "  \"duartion\": 100" + i + "," +
                "  \"views\": 100" + i + "" +
                "}";
    }

    public static String getJsonArtistPreview(int i) {
        int portraitIndex = i%PORTRAITS.length;
        return "{" +
                "   \"_id\": \"artist" + i + "\"," +
                "   \"displayName\": \"artist" + i + "\"," +
                "   \"type\": \"artist_type" + i + "\"," +
                "   \"image\": \"" + PORTRAITS[portraitIndex] + "\"" +
                "}";
    }

    public static String getJsonArtist(int i) {
        String s = "";
        s += "{" +
                "  \"_id\": \"artist" + i + "\"," +
                "  \"followersCount\": 10000" + i + "," +
                "  \"genres\": [" +
                        getJsonGenre(i/2) + ", " +
                        getJsonGenre(i) +
                "  ]," +
                "  \"images\": [" +
                "\"" + PORTRAITS[i % PORTRAITS.length] + "\"" +
                "  ]," +
                "  \"displayName\": \"artist" + i + "\"," +
                "  \"bio\": \"I'm artist" + i + "\"," +
                "  \"popularSongs\": [";
        for (int _i = 0; _i < JSON_GENERATION_ARTIST_TRACK_COUNT; _i++) {
            s += getJsonTrack(_i);
            if (_i < JSON_GENERATION_ARTIST_TRACK_COUNT - 1)
                s += ", ";
        }
                s += "  ]," +
                "  \"type\": \"type" + i + "\"" +
                "}";
        return s;
    }

    public static String getJsonArtistAlbumList(int limit, int offset) {
        String s = "";

        s += "{\n" +
                "  \"items\": [";
                for (int i = 0; i < limit; i++) {
                    s += getJsonAlbumPreview(offset + i);
                    if (i < limit - 1)
                        s+= ", ";
                }
        s +=    "  ]," +
                "  \"limit\": " + limit + "," +
                "  \"offset\": " + offset + "," +
                "  \"total\": 10\n" +
                "}";

        return s;
    }


    public static String getJsonListOfCategories(int categoryCount) {
        String s = "{\n" +
                "  \"items\": [\n";
                for (int _i = 0; _i < categoryCount; _i++) {
                    s += getJsonCategory(_i);
                    if (_i < categoryCount - 1)
                        s += ", ";
                    s += '\n';
                }
        s +=    "  ],\n" +
                "  \"limit\": " + categoryCount + "," +
                "  \"offset\": 0," +
                "  \"total\": " + categoryCount + "\n" +
                "}";

        return s;
    }


    public static String getJsonCategory(int i) {


        String s = "{" +
                "  \"_id\": \"category" + i + "\"," +
                "  \"name\": \"category" + i + "\"," +
                "  \"icon\": \"string\"" +
                "}";
        return s;
    }

    /**
     *
     * @param playlistCount if playlistCount == -1, then the number of the generated playlists will be random between 1 (inclusive) & 8 (inclusive).
     * @return
     */
    public static String getJsonCategoryPlaylists(int playlistCount) {
        if (playlistCount == -1) {
            Random random = new Random();
            playlistCount = 1+random.nextInt(8);
        }

        String s = "{\n" +
                "  \"items\": [\n";
        for (int _i = 0; _i < playlistCount; _i++) {
            s += getJsonPlaylist(_i, -1);
            if (_i < playlistCount - 1)
                s += ", ";
            s += '\n';
        }
        s +=    "  ],\n" +
                "  \"limit\": " + playlistCount + "," +
                "  \"offset\": 0," +
                "  \"total\": " + playlistCount + "\n" +
                "}";

        return s;
    }

    public static int playlistCounter = 0;

    /**
     *
     * @param i
     * @param trackCount if trackCount == -1, then the number of the generated tracks will be random between 0 (inclusive) & 4 (inclusive).
     * @return
     */
    public static String getJsonPlaylist(int i, int trackCount) {
        if (trackCount == -1) {
            Random random = new Random();
            trackCount = random.nextInt(5);
        }

        int coverIndex = playlistCounter%PLAYLIST_COVER.length;

        String s = "{" +
                "  \"_id\": \"playlist" + i + "\"," +
                "  \"name\": \"playlist" + i + "\"," +
                "  \"owner\": \"user" + i + "\"," +
                "  \"collaborative\": true," +
                "  \"description\": \"string\"," +
                "  \"followersCount\": " + i + "," +
                "  \"tracks\": [";
                for (int _i = 0; _i < trackCount; _i++) {
                    s += getJsonTrack(_i);
                    if (_i < trackCount - 1)
                        s += ", ";
                }
        s +=    "  ]," +
                "  \"image\": \"" + PLAYLIST_COVER[coverIndex] + "\"," +
                "  \"public\": true," +
                "  \"type\": \"string\"" +
                "}";

                playlistCounter++;
        return s;
    }

    public static String getJsonLikedTrack(Date date, int i) {

        String s = "";

        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String dateString = dateFormat.format(date).replace(' ', 'T') + 'Z';

        s += "{" +
                "\"added_at\": \"" + dateString + "\"," +
                "\"track\": " + getJsonTrack(i) +
                "}";

        return s;
    }

    public static String getJsonSavedAlbum(Date date, int i) {

        String s = "";

        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String dateString = dateFormat.format(date).replace(' ', 'T') + 'Z';

        s += "{" +
                "\"added_at\": \"" + dateString + "\"," +
                "\"album\": " + getJsonAlbum(i) +
                "}";

        return s;
    }

    public static String getJsonLikedTracksList(int limit, int offset) {
        String s = "{\n";

        Date date = new Date(10000000);

        s += "\"items\": [";
                for (int _i = offset ; _i < limit+offset; _i++) {
                    s += getJsonLikedTrack(date, _i);
                    if (_i < limit+offset - 1)
                        s += ", ";
                    date.setTime(date.getTime() + 10000);
                }
        s +=    "],\n" +
                "\"limit\": " + limit + "," +
                "\"offset\": " + offset + "," +
                "\"total\": " + limit +
                "\n}";

        return s;
    }

    public static String getJsonListOfPlaylists(int limit, int offset) {
        String s = "{\n";

        s += "\"items\": [";
        for (int _i = offset ; _i < limit+offset; _i++) {
            s += getJsonPlaylist(_i, 3);
            if (_i < limit+offset - 1)
                s += ", ";
        }
        s +=    "],\n" +
                "\"limit\": " + limit + "," +
                "\"offset\": " + offset + "," +
                "\"total\": " + limit +
                "\n}";

        return s;
    }

    public static String getJsonListOfArtistPreview(int limit, int offset) {
        String s = "{\n";

        s += "\"items\": [";
        for (int _i = offset ; _i < limit+offset; _i++) {
            s += getJsonArtistPreview(_i);
            if (_i < limit+offset - 1)
                s += ", ";
        }
        s +=    "],\n" +
                "\"limit\": " + limit + "," +
                "\"offset\": " + offset + "," +
                "\"total\": " + limit +
                "\n}";

        return s;
    }

    public static String getJsonSavedAlbumsList(int limit, int offset) {
        String s = "{\n";

        Date date = new Date(10000000);

        s += "\"items\": [\n";
        for (int _i = offset ; _i < limit+offset; _i++) {
            s += getJsonSavedAlbum(date, _i);
            if (_i < limit+offset - 1)
                s += ", \n";
            date.setTime(date.getTime() + 10000);
        }
        s +=    "\n],\n" +
                "\"limit\": " + limit + "," +
                "\"offset\": " + offset + "," +
                "\"total\": " + limit +
                "\n}";

        return s;
    }

}
