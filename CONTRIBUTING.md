# Contributing to Monet Theming Bridge

Thank you for your interest in contributing to Monet Theming Bridge! This document provides guidelines and instructions for contributing to this project.

## 🌟 Code of Conduct

By participating in this project, you agree to abide by our Code of Conduct. Please be respectful and considerate of others.

## 🔄 Development Workflow

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 🛠️ Setting Up Development Environment

### Prerequisites

- Android Studio Arctic Fox or newer
- Android SDK 31+
- Android NDK 25+
- JDK 11+

### Build Instructions

1. Clone your forked repository
   ```bash
   git clone https://github.com/your-username/monet-theming-bridge.git
   cd monet-theming-bridge
   ```

2. Open the project in Android Studio or build from command line:
   ```bash
   ./gradlew assembleRelease
   ```

3. Build the module zip:
   ```bash
   ./gradlew makeModule
   ```

## 📝 Pull Request Guidelines

- Update documentation if needed
- Keep PRs focused on a single topic
- Add tests for new features
- Maintain code style consistency
- Ensure all tests pass before submitting

## 🧪 Testing

- Test on different Android versions (12, 13, 14)
- Test on different devices if possible
- Test with both Magisk and KernelSU

## 📚 Documentation

If you're adding a new feature, please update the relevant documentation:

- README.md for general information
- docs/integration.md for integration instructions
- docs/api-reference.md for API changes

## 🐛 Reporting Bugs

When reporting bugs, please include:

- Description of the issue
- Steps to reproduce
- Expected behavior
- Actual behavior
- Screenshots if applicable
- Device information (Android version, device model)
- Module version

## 💡 Feature Requests

Feature requests are welcome! Please provide:

- Clear description of the feature
- Use cases and benefits
- Any implementation ideas you have

## 📄 License

By contributing to this project, you agree that your contributions will be licensed under the project's MIT License.

Thank you for contributing to Monet Theming Bridge!
