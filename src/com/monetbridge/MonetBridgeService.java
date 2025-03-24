package com.monetbridge;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Background service that monitors for wallpaper changes and updates Monet colors
 */
public class MonetBridgeService extends Service {
    private static final String TAG = "MonetBridgeService";
    private static final long UPDATE_INTERVAL_HOURS = 12; // Fallback update interval
    
    private MonetExtractor monetExtractor;
    private ScheduledExecutorService scheduler;
    private WallpaperChangeReceiver wallpaperReceiver;
    
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "MonetBridgeService created");
        
        monetExtractor = new MonetExtractor(this);
        scheduler = Executors.newScheduledThreadPool(1);
        
        // Register receiver for wallpaper changes
        wallpaperReceiver = new WallpaperChangeReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_WALLPAPER_CHANGED);
        registerReceiver(wallpaperReceiver, filter);
        
        // Initial extraction
        extractMonetColors();
        
        // Schedule periodic updates as fallback
        scheduler.scheduleAtFixedRate(
            this::extractMonetColors,
            UPDATE_INTERVAL_HOURS,
            UPDATE_INTERVAL_HOURS,
            TimeUnit.HOURS
        );
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "MonetBridgeService started");
        return START_STICKY;
    }
    
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    
    @Override
    public void onDestroy() {
        Log.i(TAG, "MonetBridgeService destroyed");
        if (scheduler != null) {
            scheduler.shutdown();
        }
        if (wallpaperReceiver != null) {
            unregisterReceiver(wallpaperReceiver);
        }
        super.onDestroy();
    }
    
    private void extractMonetColors() {
        Log.i(TAG, "Extracting Monet colors");
        boolean success = monetExtractor.extractAndSaveColors();
        if (success) {
            Log.i(TAG, "Successfully updated Monet colors");
        } else {
            Log.e(TAG, "Failed to update Monet colors");
        }
    }
    
    /**
     * Receiver for wallpaper change events
     */
    private class WallpaperChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_WALLPAPER_CHANGED.equals(intent.getAction())) {
                Log.i(TAG, "Wallpaper changed, updating Monet colors");
                extractMonetColors();
            }
        }
    }
}
