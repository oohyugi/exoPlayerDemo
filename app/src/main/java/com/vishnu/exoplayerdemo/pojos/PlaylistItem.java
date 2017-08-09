package com.vishnu.exoplayerdemo.pojos;

/**
 * Created by vishnu on 9/8/17.
 */

public class PlaylistItem {
    public String path;
    public String type;
    public boolean isPlayed;
    public String data;
    public long durationInMills;

    public PlaylistItem(String path, String type){
        this.path = path;
        this.type = type;
        this.isPlayed = false;
    }
}
