package com.vishnu.exoplayerdemo.pojos;

/**
 * Created by vishnu on 9/8/17.
 */

public class Region {
    public String type;
    public String path;
    public float width;
    public float height;
    public float top;
    public float left;

    public Region() {
        this.path = "";
        this.type = "";
        this.width = 0f;
        this.height = 0f;
        this.top = 0f;
        this.left = 0f;
    }
}
