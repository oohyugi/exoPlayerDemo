package com.vishnu.exoplayerdemo.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.vishnu.exoplayerdemo.pojos.PlaylistItem;
import com.vishnu.exoplayerdemo.pojos.Region;
import com.vishnu.exoplayerdemo.pojos.SmartTemplateData;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by vishnu on 9/8/17.
 */

public class Utils {
    public static final String TAG = "com.vishnu.exoplayerdemo";

    public static void logException(Exception e, String logLabel) {
        Log.e(TAG, logLabel + " :: " + e.getMessage());
        for (StackTraceElement el : e.getStackTrace()) {
            Log.e(TAG, logLabel + " :: " + el.toString());
        }
    }

    public static boolean isFileExists(String absolutePath) {
        if (!TextUtils.isEmpty(absolutePath)) {
            File file = new File(absolutePath);
            return file.exists();
        }
        return false;
    }

    public static ArrayList<PlaylistItem> getDefaultPlaylistItems() {
        ArrayList<PlaylistItem> playlistItems = new ArrayList<>();

        File download = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        File videoFile = new File(download, "1.mp4");

        // videoItem
        PlaylistItem videoItem = new PlaylistItem(videoFile.getAbsolutePath(), "VIDEO");
        // smartTemplate
        PlaylistItem smartTemplate = new PlaylistItem("SMART_TEMPLATE.HTML", "SMART_TEMPLATE");
        smartTemplate.durationInMills = 30000;
        playlistItems.add(videoItem);
        playlistItems.add(smartTemplate);
        return playlistItems;
    }

    public static SmartTemplateData getDefaultSmartTemplateData(){
        File download = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File imageFile = new File(download, "3.png");
        File videoFile = new File(download, "2.mp4");

        SmartTemplateData smartTemplateData = new SmartTemplateData();

        Region videoRegion = new Region();
        videoRegion.path = videoFile.getAbsolutePath();
        videoRegion.type = "VIDEO";
        videoRegion.left = 0;
        videoRegion.top = 0;
        videoRegion.width = 50;
        videoRegion.height = 100;

        Region imageRegion = new Region();
        imageRegion.path = imageFile.getAbsolutePath();
        imageRegion.type = "IMAGE";
        imageRegion.left = 50;
        imageRegion.top = 0;
        imageRegion.width = 50;
        imageRegion.height = 100;

        smartTemplateData.regions.add(videoRegion);
//        smartTemplateData.regions.add(imageRegion);

        return smartTemplateData;
    }

    public static Point getScreenMetrics(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            int height = resources.getDimensionPixelSize(resourceId);
            size.set(size.x, size.y + height);
        }

        return size;
    }
}
