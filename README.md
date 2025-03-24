# Monet Theming Bridge

<div align="center">

![Monet Theming Bridge](https://img.shields.io/badge/Monet-Theming%20Bridge-6750A4?style=for-the-badge)
[![Android 12+](https://img.shields.io/badge/Android-12%2B-3DDC84?style=flat-square&logo=android&logoColor=white)](https://www.android.com/android-12/)
[![KernelSU Compatible](https://img.shields.io/badge/KernelSU-Compatible-blue?style=flat-square)](https://kernelsu.org/)
[![Magisk Compatible](https://img.shields.io/badge/Magisk-Compatible-00B0FF?style=flat-square)](https://github.com/topjohnwu/Magisk)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg?style=flat-square)](https://opensource.org/licenses/MIT)

</div>

## Overview

Monet Theming Bridge seamlessly connects Android's dynamic Material You (Monet) theming system with web UIs running in sandboxed environments. This module enables web-based modules (like those used in KernelSU) to access and apply beautiful Monet theming colors derived from the user's wallpaper.

<div align="center">
<img src="https://via.placeholder.com/800x400.png?text=Monet+Theming+Bridge" width="80%" alt="Monet Theming Bridge Demo">
</div>

## Problem Solved

Web UIs in Android modules (such as those used in KernelSU) run in sandboxed environments that cannot directly access Android's dynamic color API. This module:

- Extracts Monet color palette from the Android system
- Makes these colors available to web UIs through a simple JavaScript API
- Automatically updates when the wallpaper changes
- Provides a consistent theming experience across native and web components

## Features

- **Dynamic Color Extraction**: Pulls Monet colors directly from Android 12+ systems
- **Simple JavaScript API**: Easy integration with any web UI
- **CSS Utilities**: Pre-built components and utility classes
- **Automatic Updates**: Colors refresh when wallpaper changes
- **Fallback Support**: Graceful degradation with Material You-inspired fallback colors
- **Dark Mode Support**: Seamless light/dark theme switching
- **Low Overhead**: Minimal performance impact

## Requirements

- Android 12+ (API level 31+)
- Magisk or KernelSU
- Web UI-based module

## Installation

1. Download the latest release from the [Releases](https://github.com/yourusername/monet-theming-bridge/releases) page
2. Flash the module through Magisk or KernelSU
3. Reboot your device
4. Follow the integration guide to implement in your web UI

## Integration

See the [integration documentation](./docs/integration.md) for detailed instructions on how to implement Monet theming in your web UI.

```javascript
// Quick integration example
const monetBridge = new MonetBridge({
  applyToCssVars: true,
  onColorsLoaded: (colors) => {
    console.log('Monet colors loaded:', colors);
  }
});

monetBridge.init();
```

## Documentation

- [Integration Guide](./docs/integration.md) - How to use in your web UI
- [API Reference](./docs/api-reference.md) - Detailed API documentation
- [Examples](./examples/) - Sample implementations
- [Contributing](./CONTRIBUTING.md) - How to contribute to the project

## Building from Source

```bash
# Clone the repository
git clone https://github.com/yourusername/monet-theming-bridge.git
cd monet-theming-bridge

# Build the native component
./gradlew assembleRelease

# Build the module zip
./gradlew makeModule
```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## Acknowledgements

- [Material You](https://material.io/blog/material-you) - For the inspiration
- [KernelSU](https://kernelsu.org/) - For the amazing kernel-based root solution
- [Magisk](https://github.com/topjohnwu/Magisk) - For the powerful module system
