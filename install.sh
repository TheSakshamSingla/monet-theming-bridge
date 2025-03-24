#!/system/bin/sh
# Monet Theming Bridge Module Installer

MODDIR=${0%/*}

# Set up permissions
set_perm_recursive $MODDIR 0 0 0755 0644
set_perm_recursive $MODDIR/system/bin 0 0 0755 0755
set_perm $MODDIR/system/bin/monet_bridge 0 0 0755

# Print module info
ui_print "- Installing Monet Theming Bridge"
ui_print "- For Android 12+ devices"
ui_print "- Enables Monet theming in web UIs"

# Check Android version
if [ "$(getprop ro.build.version.sdk)" -lt "31" ]; then
  ui_print "! Warning: This module is designed for Android 12+ (SDK 31+)"
  ui_print "! Your device is running an older version"
  ui_print "! Module may not function correctly"
fi

# Create necessary directories
mkdir -p $MODDIR/system/bin
mkdir -p $MODDIR/system/etc/monet_bridge

ui_print "- Installation completed"
ui_print "- Reboot to activate the module"
