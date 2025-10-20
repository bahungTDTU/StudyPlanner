# Screenshot Capture Guide

This guide explains how to capture all required screenshots for the PROJECT_REPORT.md.

## Required Screenshots Checklist

- [ ] `github-contributors.png` - GitHub contributors statistics
- [ ] `use-case-diagram.png` - Use case diagram
- [ ] `login-screen.png` - Login interface
- [ ] `register-screen.png` - Registration interface  
- [ ] `dashboard.png` - Dashboard overview
- [ ] `add-task.png` - Add/Edit task screen
- [ ] `task-list.png` - Task list view
- [ ] `calendar.png` - Calendar view
- [ ] `subject-management.png` - Subject management
- [ ] `reports.png` - Reports and analytics
- [ ] `profile.png` - User profile screen

## Method 1: Android Studio Emulator (Recommended)

### Setup
1. Open Android Studio
2. Start the AVD (Android Virtual Device) emulator
3. Run the Study Planner app on the emulator

### Capturing Screenshots
1. Navigate to the screen you want to capture
2. Click the camera icon (📷) in the emulator toolbar on the right side
3. Or use keyboard shortcut:
   - **Windows/Linux**: `Ctrl + S`
   - **Mac**: `Cmd + S`
4. Screenshot saves to `~/Pictures` or `~/Desktop` by default
5. Rename the file according to the required filename
6. Move to `docs/images/` folder

### Tips for Best Screenshots
- Use a modern device profile (e.g., Pixel 5, Pixel 6)
- Set up sample data before taking screenshots
- Make sure the UI is in a clean state (no loading spinners, errors, etc.)
- Take screenshots in portrait orientation
- Ensure good contrast and readability

## Method 2: Physical Android Device

### Prerequisites
1. Enable Developer Options on your device:
   - Go to Settings → About Phone
   - Tap "Build Number" 7 times
2. Enable USB Debugging:
   - Settings → Developer Options → USB Debugging

### Using ADB (Android Debug Bridge)
```bash
# Take screenshot
adb shell screencap -p /sdcard/screenshot.png

# Pull screenshot to computer
adb pull /sdcard/screenshot.png ./docs/images/

# Delete from device
adb shell rm /sdcard/screenshot.png
```

### Using Device Buttons
1. Press **Power + Volume Down** simultaneously
2. Screenshot saves to device gallery
3. Transfer to computer via USB, cloud, or email
4. Rename and move to `docs/images/`

## Method 3: Device Screen Recording (For Demo)

If you want to create a video demo instead:

```bash
# Start recording (max 3 minutes)
adb shell screenrecord /sdcard/demo.mp4

# Stop recording (Ctrl+C)

# Pull video to computer
adb pull /sdcard/demo.mp4 ./docs/videos/
```

## Capturing GitHub Contributors Screenshot

### Steps
1. Open your web browser
2. Navigate to: `https://github.com/bahungTDTU/StudyPlanner/graphs/contributors`
3. Wait for the page to fully load
4. Take a full-page screenshot:
   
   **Chrome/Edge:**
   - Press `F12` to open DevTools
   - Press `Ctrl+Shift+P` (Windows) or `Cmd+Shift+P` (Mac)
   - Type "screenshot" and select "Capture full size screenshot"
   
   **Firefox:**
   - Press `F12` to open DevTools
   - Click the "..." menu
   - Select "Take a screenshot" → "Save full page"
   
   **Using Browser Extension:**
   - Install "Full Page Screen Capture" or similar extension
   - Click the extension icon
   - Save the screenshot

5. Save as `github-contributors.png`
6. Move to `docs/images/`

## Creating Use Case Diagram

### Option 1: Using Online Tools

**draw.io (diagrams.net)**
1. Go to https://app.diagrams.net/
2. Select "Blank Diagram"
3. Use shapes from "UML" category
4. Create use case diagram following the structure in PROJECT_REPORT.md
5. Export as PNG: File → Export as → PNG
6. Save as `use-case-diagram.png`

**PlantUML Online**
1. Go to http://www.plantuml.com/plantuml/
2. Copy content from `docs/use-case-diagram.puml`
3. Paste and render
4. Download PNG
5. Save as `use-case-diagram.png`

**Lucidchart**
1. Go to https://www.lucidchart.com/
2. Create new diagram
3. Choose UML → Use Case Diagram
4. Design your diagram
5. Export as PNG
6. Save as `use-case-diagram.png`

### Option 2: Using Desktop Tools

**PlantUML with VS Code**
```bash
# Install PlantUML extension in VS Code
# Open docs/use-case-diagram.puml
# Right-click → Preview Current Diagram
# Export as PNG
```

**StarUML**
1. Download from http://staruml.io/
2. Create new Use Case Diagram
3. Add actors and use cases
4. Export as PNG

### Option 3: Using Command Line (PlantUML)

```bash
# Install PlantUML
# On Ubuntu/Debian:
sudo apt-get install plantuml

# On Mac:
brew install plantuml

# Generate diagram
plantuml docs/use-case-diagram.puml -o images
```

## Screenshot Quality Guidelines

### Resolution
- **Minimum**: 800px width
- **Recommended**: 1080px width
- **Format**: PNG (preferred) or JPG

### Content
- ✅ Clear and readable text
- ✅ Proper sample data
- ✅ Clean UI state
- ✅ Good lighting/contrast
- ❌ No personal information
- ❌ No debug overlays
- ❌ No errors or broken UI

### File Size
- Keep individual screenshots under 500KB
- Use PNG for UI screenshots (better quality)
- Use JPG for photos if file size is too large

## Organizing Screenshots

### Folder Structure
```
docs/
└── images/
    ├── README.md (this file)
    ├── github-contributors.png
    ├── use-case-diagram.png
    ├── login-screen.png
    ├── register-screen.png
    ├── dashboard.png
    ├── add-task.png
    ├── task-list.png
    ├── calendar.png
    ├── subject-management.png
    ├── reports.png
    └── profile.png
```

### File Naming
- Use lowercase with hyphens: `login-screen.png` not `Login_Screen.png`
- Be descriptive: `dashboard.png` not `screenshot1.png`
- Follow the exact names listed in PROJECT_REPORT.md

## Batch Processing (Optional)

If you have many screenshots to resize or compress:

### Using ImageMagick
```bash
# Install ImageMagick
sudo apt-get install imagemagick  # Ubuntu/Debian
brew install imagemagick           # Mac

# Resize all images to 1080px width
mogrify -resize 1080x docs/images/*.png

# Compress images
mogrify -quality 85 docs/images/*.jpg
```

### Using Python (PIL)
```python
from PIL import Image
import os

for filename in os.listdir('docs/images'):
    if filename.endswith('.png'):
        img = Image.open(f'docs/images/{filename}')
        img.thumbnail((1080, 2400))
        img.save(f'docs/images/{filename}', optimize=True)
```

## Verification Checklist

Before finalizing:
- [ ] All required screenshots are captured
- [ ] Files are named correctly
- [ ] Images are clear and readable
- [ ] Resolution is adequate (min 800px width)
- [ ] File sizes are reasonable (< 500KB each)
- [ ] Screenshots are in `docs/images/` folder
- [ ] Images display correctly in PROJECT_REPORT.md
- [ ] No personal or sensitive information visible
- [ ] Sample data looks professional and realistic

## Troubleshooting

### Screenshot Not Saving
- Check storage permissions
- Verify folder path exists
- Try alternative method

### Screenshot Too Large
- Resize using image editor
- Compress using online tools (tinypng.com)
- Convert to JPG if PNG is too large

### ADB Not Recognized
```bash
# Add Android SDK platform-tools to PATH
export PATH=$PATH:~/Android/Sdk/platform-tools

# Or use full path
~/Android/Sdk/platform-tools/adb shell screencap -p /sdcard/screen.png
```

### Emulator Camera Icon Not Visible
- Click the "..." button at bottom of emulator toolbar
- Navigate to Screenshots section
- Click "Take Screenshot"

## Quick Reference Commands

```bash
# Check connected devices
adb devices

# Take screenshot from device
adb shell screencap -p /sdcard/screen.png && adb pull /sdcard/screen.png

# List files in device
adb shell ls /sdcard/

# Take screenshot from emulator (command line)
adb exec-out screencap -p > screenshot.png

# Record screen
adb shell screenrecord /sdcard/demo.mp4

# Pull multiple files
adb pull /sdcard/screenshots/ ./docs/images/
```

## Additional Resources

- [Android Debug Bridge (ADB) Documentation](https://developer.android.com/studio/command-line/adb)
- [Android Emulator Documentation](https://developer.android.com/studio/run/emulator)
- [PlantUML Documentation](https://plantuml.com/)
- [draw.io User Guide](https://www.diagrams.net/doc/)

---

**Need Help?**
- Check if images display in PROJECT_REPORT.md using markdown preview
- Verify file paths are correct relative to PROJECT_REPORT.md
- Ensure image files are committed to Git
