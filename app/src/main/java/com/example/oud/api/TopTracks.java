package com.example.oud.api;

public class TopTracks {
    TrackPreview[] tracks;

    public TopTracks(TrackPreview[] tracks) {
        this.tracks = tracks;
    }

    public TrackPreview[] getTracks() {
        return tracks;
    }
}
