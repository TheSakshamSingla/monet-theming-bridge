package com.monetbridge;

import android.content.Context;
import android.os.Build;
import android.util.Log;

/**
 * MonetExtractor is responsible for extracting Monet theme colors from Android 12+
 * and making them available to web UIs running in sandboxed environments.
 */
public class MonetExtractor {
    private static final String TAG = "MonetExtractor";
    
    static {
        try {
            System.loadLibrary("monet_bridge");
            Log.i(TAG, "Loaded monet_bridge native library");
        } catch (UnsatisfiedLinkError e) {
            Log.e(TAG, "Failed to load native library: " + e.getMessage());
        }
    }
    
    private Context context;
    
    public MonetExtractor(Context context) {
        this.context = context;
    }
    
    /**
     * Checks if the device supports Monet theming (Android 12+)
     */
    public boolean isMonetSupported() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.S;
    }
    
    /**
     * Extracts Monet colors and saves them to a file that can be accessed by web UIs
     */
    public boolean extractAndSaveColors() {
        if (!isMonetSupported()) {
            Log.w(TAG, "Monet theming not supported on this device (Android < 12)");
            return false;
        }
        
        try {
            boolean success = extractColors();
            if (success) {
                Log.i(TAG, "Successfully extracted Monet colors");
            } else {
                Log.e(TAG, "Failed to extract Monet colors");
            }
            return success;
        } catch (Exception e) {
            Log.e(TAG, "Error extracting Monet colors: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Native method that extracts Monet colors using Android's system APIs
     */
    private native boolean extractColors();
}
