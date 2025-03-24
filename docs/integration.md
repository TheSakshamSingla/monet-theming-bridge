# Monet Theming Bridge Integration Guide

This guide explains how to integrate the Monet Theming Bridge into your web UI-based Android module.

## Prerequisites

- Your module runs on Android 12+ (API level 31+)
- Your module uses a web UI (HTML/CSS/JavaScript)
- Your module has the necessary permissions to read from `/data/local/tmp/`

## Integration Steps

### 1. Include the JavaScript Library

Copy the `monet-bridge.js` file to your web UI project and include it in your HTML:

```html
<script src="path/to/monet-bridge.js"></script>
```

Or import it in your JavaScript project:

```javascript
// ES6 module
import MonetBridge from './path/to/monet-bridge.js';

// CommonJS
const MonetBridge = require('./path/to/monet-bridge.js');
```

### 2. Include the CSS Utilities (Optional)

If you want to use the pre-built CSS utilities, include the `monet-theme.css` file:

```html
<link rel="stylesheet" href="path/to/monet-theme.css">
```

### 3. Initialize the Monet Bridge

```javascript
// Create a new instance of MonetBridge
const monetBridge = new MonetBridge({
  // Optional: Set a custom refresh interval (in milliseconds)
  refreshInterval: 30000,
  
  // Optional: Callback when colors are loaded
  onColorsLoaded: (colors) => {
    console.log('Monet colors loaded:', colors);
    // You can update your UI here
  },
  
  // Optional: Callback when an error occurs
  onError: (error) => {
    console.error('Error loading Monet colors:', error);
  },
  
  // Optional: Apply colors to CSS variables automatically
  applyToCssVars: true
});

// Initialize the bridge
monetBridge.init().then(success => {
  if (success) {
    console.log('Monet Bridge initialized successfully');
  } else {
    console.error('Failed to initialize Monet Bridge');
  }
});
```

### 4. Using Monet Colors in Your UI

#### Using CSS Variables

If you've enabled `applyToCssVars`, you can use the CSS variables directly:

```css
.my-element {
  background-color: var(--monet-primary);
  color: var(--monet-on-primary);
}
```

#### Using the JavaScript API

You can also access colors programmatically:

```javascript
// Get a specific color
const primaryColor = monetBridge.getColor('accent1_700');

// Get all colors
const allColors = monetBridge.getAllColors();

// Apply colors to an element
document.getElementById('my-button').style.backgroundColor = primaryColor;
```

#### Using the CSS Utility Classes

If you included the CSS utilities, you can use the pre-built classes:

```html
<button class="monet-button">Primary Button</button>
<button class="monet-button-outlined">Outlined Button</button>
<div class="monet-card">
  <h2 class="monet-text-primary">Card Title</h2>
  <p>Card content</p>
</div>
```

### 5. Clean Up

When your web UI is being unloaded, make sure to clean up:

```javascript
// Clean up when done
monetBridge.destroy();
```

## Example Implementation

Here's a complete example of integrating Monet theming in a web UI:

```html
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Monet Themed Web UI</title>
  <link rel="stylesheet" href="path/to/monet-theme.css">
  <style>
    body {
      font-family: 'Roboto', sans-serif;
      margin: 0;
      padding: 16px;
      background-color: var(--monet-background);
      color: var(--monet-on-background);
    }
    
    .container {
      max-width: 800px;
      margin: 0 auto;
    }
    
    .header {
      margin-bottom: 24px;
    }
    
    .card-container {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
      gap: 16px;
      margin-bottom: 24px;
    }
  </style>
</head>
<body>
  <div class="container">
    <div class="header">
      <h1 class="monet-text-primary">My Module</h1>
      <p>This UI is using dynamic Monet theming</p>
    </div>
    
    <div class="card-container">
      <div class="monet-card">
        <h2 class="monet-text-primary">Feature 1</h2>
        <p>Description of feature 1</p>
        <button class="monet-button">Enable</button>
      </div>
      
      <div class="monet-card">
        <h2 class="monet-text-primary">Feature 2</h2>
        <p>Description of feature 2</p>
        <button class="monet-button-outlined">Configure</button>
      </div>
    </div>
    
    <div class="monet-card">
      <h2 class="monet-text-primary">Settings</h2>
      <div style="display: flex; align-items: center; margin-bottom: 16px;">
        <span>Enable Feature 3</span>
        <label class="monet-switch" style="margin-left: auto;">
          <input type="checkbox">
          <span class="monet-switch-slider"></span>
        </label>
      </div>
      <div style="display: flex; align-items: center;">
        <span>Enable Feature 4</span>
        <label class="monet-switch" style="margin-left: auto;">
          <input type="checkbox">
          <span class="monet-switch-slider"></span>
        </label>
      </div>
    </div>
  </div>

  <script src="path/to/monet-bridge.js"></script>
  <script>
    document.addEventListener('DOMContentLoaded', () => {
      const monetBridge = new MonetBridge({
        refreshInterval: 30000,
        applyToCssVars: true,
        onColorsLoaded: (colors) => {
          console.log('Monet colors loaded:', colors);
        }
      });
      
      monetBridge.init().then(success => {
        if (success) {
          console.log('Monet Bridge initialized successfully');
        } else {
          console.error('Failed to initialize Monet Bridge');
          // Fallback to default colors is automatic
        }
      });
      
      // Clean up when page is unloaded
      window.addEventListener('beforeunload', () => {
        monetBridge.destroy();
      });
    });
  </script>
</body>
</html>
```

## Troubleshooting

### Colors Not Loading

If the Monet colors are not loading:

1. Make sure the Monet Theming Bridge module is installed and running
2. Check that your device is running Android 12 or higher
3. Verify that your web UI has permission to read from `/data/local/tmp/`
4. Check the browser console for any error messages

### Fallback Colors

The library automatically falls back to a default Material You-like color palette if it cannot load the actual Monet colors. You can customize these fallback colors:

```javascript
const monetBridge = new MonetBridge({
  fallbackColors: {
    "accent1_700": "#FF0000", // Custom red primary color
    // Add more custom fallback colors as needed
  }
});
```

## Advanced Usage

### Custom Color File Path

If you've modified the module to store the color file in a different location:

```javascript
const monetBridge = new MonetBridge({
  colorFilePath: '/path/to/your/custom/colors.json'
});
```

### Manual CSS Variable Application

If you want to control when CSS variables are applied:

```javascript
const monetBridge = new MonetBridge({
  applyToCssVars: false // Don't apply automatically
});

// Later, apply them manually
monetBridge.loadColors().then(() => {
  monetBridge.applyColorsToCssVariables('--custom-prefix-');
});
```

This will create CSS variables with a custom prefix, like `--custom-prefix-accent1_700`.
