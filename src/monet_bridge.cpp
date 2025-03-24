#include <jni.h>
#include <string>
#include <fstream>
#include <android/log.h>
#include <unistd.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <dirent.h>
#include <fcntl.h>
#include <json/json.h>

#define LOG_TAG "MonetBridge"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

// Output file path where we'll store the Monet colors
const char* OUTPUT_FILE = "/data/local/tmp/monet_colors.json";
// Path to shared preferences where Android might store theme data
const char* THEME_PREFS_PATH = "/data/data/android.systemui/shared_prefs/theme_prefs.xml";

// Function to extract colors using Android's dynamic colors API
extern "C" JNIEXPORT jboolean JNICALL
Java_com_monetbridge_MonetExtractor_extractColors(JNIEnv* env, jobject /* this */) {
    LOGI("Starting Monet color extraction");
    
    // Get the Android context
    jclass activityThreadClass = env->FindClass("android/app/ActivityThread");
    jmethodID currentActivityThreadMethod = env->GetStaticMethodID(
            activityThreadClass, "currentActivityThread", "()Landroid/app/ActivityThread;");
    jobject activityThread = env->CallStaticObjectMethod(
            activityThreadClass, currentActivityThreadMethod);
    
    jmethodID getSystemContextMethod = env->GetMethodID(
            activityThreadClass, "getSystemContext", "()Landroid/content/Context;");
    jobject context = env->CallObjectMethod(activityThread, getSystemContextMethod);
    
    // Access Android's color system
    jclass contextClass = env->FindClass("android/content/Context");
    jmethodID getResourcesMethod = env->GetMethodID(
            contextClass, "getResources", "()Landroid/content/res/Resources;");
    jobject resources = env->CallObjectMethod(context, getResourcesMethod);
    
    // Get the dynamic colors from Android 12+ systems
    jclass resourcesClass = env->FindClass("android/content/res/Resources");
    jmethodID getColorMethod = env->GetMethodID(
            resourcesClass, "getColor", "(ILandroid/content/res/Resources$Theme;)I");
    
    // Create a JSON object to store our colors
    Json::Value colorData;
    
    // Try to extract primary colors (these resource IDs may vary by device)
    // We're using common Android resource IDs for Material You colors
    int colorIds[] = {
        0x01060054, // system_accent1_10 (primary)
        0x01060055, // system_accent1_50
        0x01060056, // system_accent1_100
        0x01060057, // system_accent1_200
        0x01060058, // system_accent1_300
        0x01060059, // system_accent1_400
        0x0106005a, // system_accent1_500
        0x0106005b, // system_accent1_600
        0x0106005c, // system_accent1_700
        0x0106005d, // system_accent1_800
        0x0106005e, // system_accent1_900
        
        0x0106005f, // system_accent2_10
        0x01060060, // system_accent2_50
        0x01060061, // system_accent2_100
        // ... more color IDs for accent2, accent3, neutral1, neutral2
    };
    
    const char* colorNames[] = {
        "accent1_10", "accent1_50", "accent1_100", "accent1_200", "accent1_300",
        "accent1_400", "accent1_500", "accent1_600", "accent1_700", "accent1_800", "accent1_900",
        "accent2_10", "accent2_50", "accent2_100"
        // ... names for remaining colors
    };
    
    // Extract each color
    for (int i = 0; i < sizeof(colorIds)/sizeof(colorIds[0]); i++) {
        jint resourceId = colorIds[i];
        try {
            jint colorValue = env->CallIntMethod(resources, getColorMethod, resourceId, NULL);
            
            // Convert to hex format
            char hexColor[10];
            snprintf(hexColor, sizeof(hexColor), "#%08X", colorValue);
            
            // Store in our JSON
            colorData[colorNames[i]] = hexColor;
            LOGI("Extracted color %s: %s", colorNames[i], hexColor);
        } catch (...) {
            LOGE("Failed to extract color with ID: %d", resourceId);
        }
    }
    
    // Fallback: If we couldn't get colors via API, try to parse theme prefs file
    if (colorData.size() == 0) {
        LOGI("Attempting fallback method to extract colors");
        // Implementation of XML parsing for theme_prefs.xml would go here
        // This is complex and device-specific, so we're omitting the details
    }
    
    // Write the JSON data to our output file
    Json::StreamWriterBuilder writer;
    std::string jsonString = Json::writeString(writer, colorData);
    
    std::ofstream outFile(OUTPUT_FILE);
    if (outFile.is_open()) {
        outFile << jsonString;
        outFile.close();
        
        // Set permissions so web UI can read it
        chmod(OUTPUT_FILE, 0644);
        
        LOGI("Successfully wrote Monet colors to %s", OUTPUT_FILE);
        return JNI_TRUE;
    } else {
        LOGE("Failed to write to output file");
        return JNI_FALSE;
    }
}
