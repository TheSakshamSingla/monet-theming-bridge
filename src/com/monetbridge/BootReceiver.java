package com.monetbridge;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

/**
 * Broadcast receiver that starts the MonetBridgeService when the device boots
 */
public class BootReceiver extends BroadcastReceiver {
    private static final String TAG = "MonetBridge_Boot";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Log.i(TAG, "Boot completed, starting MonetBridgeService");
            
            // Only start the service on Android 12+ (API 31+)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                Intent serviceIntent = new Intent(context, MonetBridgeService.class);
                
                // Start the service
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(serviceIntent);
                } else {
                    context.startService(serviceIntent);
                }
                
                Log.i(TAG, "MonetBridgeService started");
            } else {
                Log.w(TAG, "Device running Android " + Build.VERSION.SDK_INT + 
                      ", which doesn't support Monet theming. Service not started.");
            }
        }
    }
}
